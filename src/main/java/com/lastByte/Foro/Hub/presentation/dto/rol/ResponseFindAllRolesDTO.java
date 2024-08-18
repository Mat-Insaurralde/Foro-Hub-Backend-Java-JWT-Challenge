package com.lastByte.Foro.Hub.presentation.dto.rol;

import java.util.List;

public record ResponseFindAllRolesDTO(
        String message,
        List<RolDTO> listRoles
) {
}
