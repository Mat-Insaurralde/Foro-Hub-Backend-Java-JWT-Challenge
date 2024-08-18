package com.lastByte.Foro.Hub.presentation.dto.permiso;

import jakarta.validation.constraints.NotBlank;

public record RequestCreatePermisoDTO(
        @NotBlank(message = "No has ingresado el nuevo permiso")
        String permiso

) {
}
