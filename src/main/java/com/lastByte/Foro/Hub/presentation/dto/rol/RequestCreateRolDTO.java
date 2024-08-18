package com.lastByte.Foro.Hub.presentation.dto.rol;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RequestCreateRolDTO(

    @NotBlank(message = "Debe ingresar el nombre del rol")
    String nombre,

    @NotEmpty(message = "Debe ingresar la lista de permisos del rol")
    List<String> listPermisos

) {


}
