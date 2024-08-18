package com.lastByte.Foro.Hub.infra.exceptions;

public class ValidacionDeIntegridad extends RuntimeException{
    ///Usando Runtime no hace falta agregar el throw en
    // el metodo en el que se lo utiliza

    public ValidacionDeIntegridad(String s) {
        super(s);
    }


}
