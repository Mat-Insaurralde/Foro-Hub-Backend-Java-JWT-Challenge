package com.lastByte.Foro.Hub.presentation.dto.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestCreateRespuestaDTO(

        @NotBlank(message = "Debes ingresar la respuesta!")
        String mensaje,
        @NotNull(message = "Debes ingresar el id del topico al que respondes!")
        Long topico_id,
        //OPCIONAL SI RESPONDES UNA RESPUESTA
        Long respuesta_id

) {
}
