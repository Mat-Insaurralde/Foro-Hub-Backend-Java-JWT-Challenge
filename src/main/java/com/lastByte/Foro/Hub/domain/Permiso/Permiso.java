package com.lastByte.Foro.Hub.domain.Permiso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "permisos")
@Entity(name = "permiso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //crea automaticamente los metodos equals y hashcode con el id
public class Permiso {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permiso_name")
    @Enumerated(EnumType.STRING)
    private PermisoEnum permisoEnum;


    public Permiso(PermisoEnum permisoEnum) {
        this.permisoEnum=permisoEnum;
    }
}
