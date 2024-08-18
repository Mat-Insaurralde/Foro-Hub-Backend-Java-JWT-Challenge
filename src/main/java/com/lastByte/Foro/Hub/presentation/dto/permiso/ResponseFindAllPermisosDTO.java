package com.lastByte.Foro.Hub.presentation.dto.permiso;




import java.util.List;

public record ResponseFindAllPermisosDTO(
        String message,
        List<PermisoDTO> listPermisos) {
}
