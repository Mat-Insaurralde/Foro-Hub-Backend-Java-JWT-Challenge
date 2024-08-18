package com.lastByte.Foro.Hub.presentation.dto.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestTopicoAnioYfechaDTO(
        @NotBlank
        String nombreDecurso,
        @NotNull
        Integer anio
) {
}
