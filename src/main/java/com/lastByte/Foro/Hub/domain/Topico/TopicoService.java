package com.lastByte.Foro.Hub.domain.Topico;

import com.lastByte.Foro.Hub.domain.Curso.Curso;
import com.lastByte.Foro.Hub.domain.Curso.CursoRepository;
import com.lastByte.Foro.Hub.domain.Curso.CursoService;
import com.lastByte.Foro.Hub.domain.Topico.validaciones.CursosCorrespondientes;
import com.lastByte.Foro.Hub.domain.Usuario.UsuarioRepository;
import com.lastByte.Foro.Hub.domain.Usuario.UsuarioService;
import com.lastByte.Foro.Hub.infra.exceptions.CollectionEmptyException;
import com.lastByte.Foro.Hub.infra.exceptions.ResourceNotFoundException;
import com.lastByte.Foro.Hub.infra.exceptions.ValidacionDeIntegridad;
import com.lastByte.Foro.Hub.presentation.dto.topico.RequestActualizarTopicoDTO;
import com.lastByte.Foro.Hub.presentation.dto.topico.ResponseDetalleTopicoDTO;
import com.lastByte.Foro.Hub.presentation.dto.topico.RequestRegistroTopicoDTO;
import com.lastByte.Foro.Hub.presentation.dto.topico.RequestTopicoAnioYfechaDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TopicoService {


    private final CursoRepository cursoRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final CursoService cursoService;
    private final CursosCorrespondientes cursosCorrespondientes;

    @Autowired
    public TopicoService(CursoRepository cursoRepository,
                           TopicoRepository topicoRepository,
                           UsuarioRepository usuarioRepository,
                           UsuarioService usuarioService,
                           CursoService cursoService,
                           CursosCorrespondientes cursosCorrespondientes) {
        this.cursoRepository = cursoRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.cursoService = cursoService;
        this.cursosCorrespondientes = cursosCorrespondientes;
    }


    public ResponseDetalleTopicoDTO registrarTopico(RequestRegistroTopicoDTO registroTopicoDTO) {

        var username = usuarioService.obtenerUsernameEnSesion();

        if (username == null) {
            throw new ValidacionDeIntegridad("Debe registrarse o loguearse!");
        }

        //BUSCAMOS EL USUARIO PARA GUARDARLO COMO EL AUTOR DEL TOPICO
        var usuario = usuarioRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Debe registrarse o loguearse!")
        );



        //VERIFICAR QUE EL TITULO Y MENSAJE NO SEA DUPLICADO
        if (topicoRepository.existsByTituloAndMensajeIgnoreCase(registroTopicoDTO.titulo(), registroTopicoDTO.mensaje())) {
            throw new ValidacionDeIntegridad("Ya existe un topico con el mismo titulo y mensaje");
        }


        //Creamos el topico
        var topico = new Topico(registroTopicoDTO);

        topico.setAutor(usuario);


        //VALIDAMOS QUE AL MENOS UN CURSO INGRESADO EXISTA
        List<Curso> cursos = cursosCorrespondientes.validar(registroTopicoDTO.cursos());


        //REGISTRAMOS EL TOPICO
        topicoRepository.save(topico);

        //REGISTRAMOS A QUE CURSOS SE VINCULA EL TOPICO
        for (Curso curso : cursos) {
            topicoRepository.registrarCursoDelTopico(topico.getId(), curso.getId());
        }


        //AGREGAMOS EL TOPICO AL USUARIO Y LO GUARDAMOS
        usuario.getTopicos().add(topico);
        usuarioRepository.save(usuario);


        return new ResponseDetalleTopicoDTO(topico, cursos);

    }


    public ResponseDetalleTopicoDTO buscarTopicoPorid(Long id) {

        if (id == null) {
            throw new ValidationException("No se ah ingresado un ID!");
        }

        var topico = topicoRepository.findById(id);

        if (topico.isEmpty()) throw new ResourceNotFoundException("Topico no encontrado con el ID proporcionado");

        var cursos = cursoRepository.findCursosByTopicoId(topico.get().getId());

        return new ResponseDetalleTopicoDTO(topico.get(), cursos);
    }


    public ResponseDetalleTopicoDTO actualizarTopicoPorId(Long id, RequestActualizarTopicoDTO actualizarTopicoDTO) {

        if (id == null) {
            throw new ValidationException("No se ah ingresado un ID!");
        }

        Optional<Topico> opTopico = topicoRepository.findById(id);

        if (opTopico.isEmpty()) {
            throw new ResourceNotFoundException("Topico no encontrado con el ID proporcionado");
        }

        //VERIFICAR QUE EL TITULO Y MENSAJE NO SEA DUPLICADO
        if (topicoRepository.existsByTituloAndMensajeIgnoreCase(actualizarTopicoDTO.titulo(),actualizarTopicoDTO.mensaje())) {
            throw new ValidacionDeIntegridad("Ya existe un topico con el mismo titulo y mensaje");
        }

        List<Curso> cursosTopico = cursoRepository.findCursosByTopicoId(id);

        var topico = opTopico.get();


        List<Curso> cursosNuevos = new ArrayList<>();


        if (!actualizarTopicoDTO.cursos().isEmpty()) {
            //VALIDAMOS QUE AL MENOS UN CURSO INGRESADO EXISTA
            //VERIFICA LOS CURSOS Y LOS TRAE
            cursosNuevos = cursosCorrespondientes.validar(actualizarTopicoDTO.cursos());

            ///VALIDAMOS QUE LOS CURSOS SEAN DISTINTOS

            if (!contienenLosMismosCursos(cursosTopico, cursosNuevos)) {

                ///ELIMINAMOS LAS REFERENCIAS DE CURSOS AL TOPICO
                topicoRepository.eliminarCursosDelTopico(id);
                //REGISTRAMOS LOS NUEVOS CURSOS DEL TOPICO
                for (Curso curso : cursosNuevos) {
                    topicoRepository.registrarCursoDelTopico(id, curso.getId());
                }

            }
        }

        topico.actualizarDatos(actualizarTopicoDTO);

        topicoRepository.save(topico);

        return new ResponseDetalleTopicoDTO(topico, cursosNuevos);
    }


    public String eliminarTopicoPorId(Long id) {

        if (!topicoRepository.existsById(id)){
            throw new ResourceNotFoundException("Topico no encontrado con el ID proporcionado");
        }
        topicoRepository.deleteById(id);

        ///ELIMINAMOS LAS REFERENCIAS DE CURSOS AL TOPICO
        topicoRepository.eliminarCursosDelTopico(id);

        return "Topico eliminado con exito!";
    }

    public List<ResponseDetalleTopicoDTO> buscarTodosLosTopicos() {

        var topicos = topicoRepository.findAll();

        if (topicos.isEmpty()) {
            throw new CollectionEmptyException("No hay topicos registrados!");
        }

        return   topicos.stream()
                .map(topico -> {
                    return new ResponseDetalleTopicoDTO(topico,cursoRepository.findCursosByTopicoId(topico.getId()));})
                .collect(Collectors.toList());

    }


    public Page<ResponseDetalleTopicoDTO> buscarTop10TopicosPorFechaCreacionASC(Pageable paginacion) {
         var lista = topicoRepository.findByOrderByFechaDeCreacionAsc(paginacion)
                 .map(t -> {return new ResponseDetalleTopicoDTO(t,cursoRepository.findCursosByTopicoId(t.getId()));});
         if (lista.isEmpty()){ throw new CollectionEmptyException("No hay topicos registrados!");}
         return lista;
    }


    public Page<ResponseDetalleTopicoDTO> buscarTopicosPorNombreYAnio(Pageable paginacion, RequestTopicoAnioYfechaDTO datosRequest) {
        var lista = topicoRepository.findByCursosPorNombreYanio(datosRequest.nombreDecurso(), datosRequest.anio() ,paginacion)
                .map(t -> {return new ResponseDetalleTopicoDTO(t,cursoRepository.findCursosByTopicoId(t.getId()));});

       if (lista.isEmpty()){ throw new CollectionEmptyException("No hay topicos registrados con esos criterios de busqueda!");}
        return lista;
    }





    public static boolean contienenLosMismosCursos(List<Curso> cursosTopico, List<Curso> cursosNuevos) {

        cursosTopico.sort(Comparator.comparing(Curso::getNombre));
        cursosNuevos.sort(Comparator.comparing(Curso::getNombre));

        if (cursosTopico.equals(cursosNuevos)) {
            return true;
        } else {
            return false;
        }
    }



}
