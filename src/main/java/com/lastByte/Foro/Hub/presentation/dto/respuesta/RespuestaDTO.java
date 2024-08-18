package com.lastByte.Foro.Hub.presentation.dto.respuesta;

import com.lastByte.Foro.Hub.domain.Respuesta.Respuesta;
import com.lastByte.Foro.Hub.presentation.dto.usuario.ResponseDetalleUsuarioDTO;

import java.time.LocalDateTime;
import java.util.List;


public record RespuestaDTO(

        Long id,
        String message,
        LocalDateTime fecha_creacion,
        String titulo_topìco,
        Long respuesta_padre,
        ResponseDetalleUsuarioDTO autor_respuesta,
        List<RespuestaDTO> respuestas_dela_respuesta
        ) {


    public RespuestaDTO(Respuesta respuesta) {
        this(respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaDeCreacion(),
                respuesta.getTopico().getTitulo(),
                // Si la respuesta tiene una respuesta "padre", obtener su ID; de lo contrario, pasar null
                respuesta.getRespuesta() != null ? respuesta.getRespuesta().getId() : null ,
                new ResponseDetalleUsuarioDTO(respuesta.getAutor()),
                respuesta.getRespuestas() != null && !respuesta.getRespuestas().isEmpty()
                        ? respuesta.getRespuestas().stream()
                        .map(RespuestaDTO::new)
                        .toList()
                        : null
        );
    }


}
