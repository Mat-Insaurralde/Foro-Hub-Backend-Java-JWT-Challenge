package com.lastByte.Foro.Hub.presentation.dto.topico;

import com.lastByte.Foro.Hub.domain.Curso.Curso;
import com.lastByte.Foro.Hub.domain.Topico.Topico;
import com.lastByte.Foro.Hub.presentation.dto.respuesta.RespuestaDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ResponseDetalleTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        String autor,
        List<String> cursos,
        List<RespuestaDTO> respuestas

) {
    public ResponseDetalleTopicoDTO(Topico topico  , List<Curso> cursos   ) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getAutor().getNombre(),
                cursos.stream()
                        .map(Curso::getNombre)
                        .collect(Collectors.toList())
                ,
                topico.getRespuestas().stream()
                        .map(RespuestaDTO::new)
                        .collect(Collectors.toList())
                );

    }

}



