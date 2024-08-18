package com.lastByte.Foro.Hub.domain.Curso;


import com.lastByte.Foro.Hub.infra.exceptions.CollectionEmptyException;
import com.lastByte.Foro.Hub.infra.exceptions.ValidacionDeIntegridad;
import com.lastByte.Foro.Hub.presentation.dto.curso.ResponseDetalleCursoDTO;
import com.lastByte.Foro.Hub.presentation.dto.curso.RequestRegistroCursoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;


    public ResponseDetalleCursoDTO registrarCurso(RequestRegistroCursoDTO registroCursoDTO) {

        if (cursoRepository.existsByNombreIgnoreCase(registroCursoDTO.nombre())) {
            throw new ValidacionDeIntegridad("Ya existe un curso con ese nombre!");
        }

        var curso = new Curso(registroCursoDTO);

        cursoRepository.save(curso);

        return new ResponseDetalleCursoDTO(curso);
    }


    public String eliminarCurso(Long id) {

        var curso = cursoRepository.findById(id);

        if (curso.isEmpty()) {
            throw new ValidationException("Curso no encontrado!");
        }

        cursoRepository.delete(curso.get());

        return "Curso eliminado con exito";
    }


    public List<ResponseDetalleCursoDTO> listadoCursos() {
        var cursos = cursoRepository.findAll();

        if (cursos.isEmpty()) {
            throw new CollectionEmptyException("No tiene cursos registrados!");
        }

        var cursosDetalle = cursos.stream()
                .map(ResponseDetalleCursoDTO::new)
                .collect(Collectors.toList());

        return cursosDetalle;
    }


    public ResponseDetalleCursoDTO findById(Long id) {
        var curso = cursoRepository.findById(id);

        if (curso.isEmpty()) {
            throw new ValidationException("Curso no encontrado!");
        }

        return new ResponseDetalleCursoDTO(curso.get());
    }
}
