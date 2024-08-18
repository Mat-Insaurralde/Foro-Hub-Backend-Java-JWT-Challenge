package com.lastByte.Foro.Hub.presentation.dto.permiso;


import com.lastByte.Foro.Hub.domain.Permiso.Permiso;

public record PermisoDTO(

        Long id,

        String nombre

) {


    public PermisoDTO(Permiso p) {
        this(p.getId(), p.getPermisoEnum().name());
    }
}
