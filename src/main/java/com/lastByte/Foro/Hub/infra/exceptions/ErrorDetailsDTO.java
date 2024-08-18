package com.lastByte.Foro.Hub.infra.exceptions;

import java.util.Date;

public record ErrorDetailsDTO(

     Date timestamp,
     String message


    ){


    public ErrorDetailsDTO(String message ) {
       this(new Date(),message);
    }


}
