package com.lastByte.Foro.Hub.presentation.dto.usuario;

import com.lastByte.Foro.Hub.domain.Usuario.Usuario;

public record ResponseDetalleUsuarioDTO(
        Long id,
        String nombre,
        String email,
        String username

)
{
    public ResponseDetalleUsuarioDTO(Usuario autor) {
        this(autor.getId(), autor.getNombre(), autor.getEmail(), autor.getUsername());
    }
}
