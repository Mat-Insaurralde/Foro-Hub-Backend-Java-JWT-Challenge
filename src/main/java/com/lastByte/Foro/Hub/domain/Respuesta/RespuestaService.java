package com.lastByte.Foro.Hub.domain.Respuesta;


import com.lastByte.Foro.Hub.domain.Topico.TopicoRepository;
import com.lastByte.Foro.Hub.domain.Usuario.UsuarioService;
import com.lastByte.Foro.Hub.infra.exceptions.ResourceNotFoundException;
import com.lastByte.Foro.Hub.presentation.dto.respuesta.RequestCreateRespuestaDTO;
import com.lastByte.Foro.Hub.presentation.dto.respuesta.RequestUpdateRespuestaDTO;
import com.lastByte.Foro.Hub.presentation.dto.respuesta.ResponseCreateRespuestaDTO;
import com.lastByte.Foro.Hub.presentation.dto.respuesta.RespuestaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RespuestaService {


    @Autowired
    private RespuestaRepository respuestaRepository;


    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    private TopicoRepository topicoRepository;




    public ResponseCreateRespuestaDTO createRespuesta(RequestCreateRespuestaDTO datos){

      var username = usuarioService.obtenerUsernameEnSesion();

       var usuario = usuarioService.obtenerUsuarioEnSesion(username).orElseThrow(
               () -> new ResourceNotFoundException("Usuario no encontrado")
       );


      var topico = topicoRepository.findById(datos.topico_id()).orElseThrow(
              () -> new ResourceNotFoundException("No se encontro topico con el ID : " +datos.topico_id())
      );


      var respuesta = new Respuesta(datos.mensaje(), usuario , topico );


      // Si hay un ID de respuesta, es una subrespuesta
      if (datos.respuesta_id()!=null){
          var respuestaBuscada = respuestaRepository.findById(datos.respuesta_id())
                  .orElseThrow(() -> new ResourceNotFoundException("No se encontro respuesta con el ID: " +datos.respuesta_id()));
          // Establecemos la respuesta "padre" de la nueva respuesta
          respuesta.setRespuesta(respuestaBuscada);

          // Agregamos la nueva respuesta a la lista de respuestas de la respuesta padre
          respuestaBuscada.getRespuestas().add(respuesta);

          respuestaRepository.save(respuesta);

          respuestaRepository.save(respuestaBuscada);

          return   new ResponseCreateRespuestaDTO("Respuesta creada con exito!", new RespuestaDTO(respuesta) );

      }

       // Agregamos la nueva respuesta a la lista de respuestas del tópico
        topico.getRespuestas().add(respuesta);

         respuestaRepository.save(respuesta);

        topicoRepository.save(topico);



         return   new ResponseCreateRespuestaDTO("Respuesta creada con exito!", new RespuestaDTO(respuesta) );


    }


    public RespuestaDTO findRespuestaById(Long id) {

        var respuesta = respuestaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Respuesta con ID: "+id+" No encontrada!") );

        return new RespuestaDTO(respuesta);   }




    public String deleteRespuestaById(Long id) {
        var respuesta = respuestaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Respuesta con ID: "+id+" No encontrada!") );

        respuestaRepository.delete(respuesta);

        return "Respuesta con ID: "+id+" eliminada con exito!";
    }




    public RespuestaDTO actualizarRespuestaById(Long id, RequestUpdateRespuestaDTO updateRespuestaDTO) {
        var respuesta = respuestaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Respuesta con ID: "+id+" No encontrada!") );

         if (updateRespuestaDTO.mensaje().trim().isEmpty() ){
             throw new IllegalArgumentException("El mensaje no puede estar vacío");
         }

        respuesta.setMensaje(updateRespuestaDTO.mensaje());

         respuestaRepository.save(respuesta);

         return new RespuestaDTO(respuesta);
    }






}
