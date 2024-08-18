package com.lastByte.Foro.Hub.presentation.dto.respuesta;

import jakarta.validation.constraints.NotBlank;

public record RequestUpdateRespuestaDTO(
        @NotBlank(message = "Debe ingresar el nuevo mensaje!")
        String mensaje
) {

}
