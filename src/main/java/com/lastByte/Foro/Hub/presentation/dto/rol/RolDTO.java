package com.lastByte.Foro.Hub.presentation.dto.rol;


import com.lastByte.Foro.Hub.domain.Role.Role;
import com.lastByte.Foro.Hub.presentation.dto.permiso.PermisoDTO;

import java.util.List;


public record RolDTO (

        Long id,

        String nombre ,

        List<PermisoDTO> permisos


) {

    public RolDTO(Role rol) {
    this(rol.getId(), rol.getRoleEnum().name(), rol.getPermisos().stream().map(PermisoDTO::new).toList() );
    }

}
