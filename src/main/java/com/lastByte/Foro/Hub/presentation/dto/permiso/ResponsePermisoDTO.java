package com.lastByte.Foro.Hub.presentation.dto.permiso;

import com.lastByte.Foro.Hub.presentation.dto.rol.RolDTO;


public record ResponsePermisoDTO(

        String message,
        PermisoDTO data
) {
}
