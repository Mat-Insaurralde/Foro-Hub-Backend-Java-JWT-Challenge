package com.lastByte.Foro.Hub.presentation.dto.respuesta;

public record ResponseCreateRespuestaDTO(

        String message ,
        RespuestaDTO respuestaDTO


) {


    public ResponseCreateRespuestaDTO(String message, RespuestaDTO respuestaDTO) {
        this.message = message;
        this.respuestaDTO = respuestaDTO;
    }

}
