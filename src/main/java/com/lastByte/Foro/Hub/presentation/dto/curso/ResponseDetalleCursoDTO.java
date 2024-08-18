package com.lastByte.Foro.Hub.presentation.dto.curso;

import com.lastByte.Foro.Hub.domain.Curso.Curso;

import java.time.LocalDateTime;

public record ResponseDetalleCursoDTO(
        Long id,
        String nombre,
        String descripcion,
        LocalDateTime fechaDeCreacion

) {
    public ResponseDetalleCursoDTO(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getDescripcion(), curso.getFechaDeCreacion());
    }
}
