package com.lastByte.Foro.Hub.domain.Permiso;

public enum PermisoEnum {

    READ,
    CREATE,
    UPDATE,
    DELETE,
    WRITE;

    public static PermisoEnum fromStringToEnumPermiso(String permisoEnum) {
        try {
            return PermisoEnum.valueOf(permisoEnum.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El permiso " + permisoEnum +" no es valido!");
        }
    }
    }


