package com.lastByte.Foro.Hub.presentation.dto.curso;

import jakarta.validation.constraints.NotBlank;

public record RequestRegistroCursoDTO(
        @NotBlank(message = "El nombre del curso es obligatorio")
        String nombre,
        @NotBlank(message = "La descripcion del curso es obligatoria")
        String descripcion
) {
}
