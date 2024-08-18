package com.lastByte.Foro.Hub.domain.Role;

public enum RoleEnum {
    ADMIN,
    USER,
    INVITED,
    DEVELOPER;



    public static RoleEnum fromStringToEnumRole(String roleName) {
        try {
            return RoleEnum.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El nombre del rol " + roleName+" no es valido!");
        }
    }





}
