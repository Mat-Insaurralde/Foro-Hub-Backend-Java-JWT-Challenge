package com.lastByte.Foro.Hub.domain.Role;



import com.lastByte.Foro.Hub.domain.Permiso.Permiso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Table(name = "roles")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //crea automaticamente los metodos equals y hashcode con el id
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)//Carga todo de una vez
    @JoinTable(name = "role_permisos" , joinColumns = @JoinColumn(name = "role_id") , inverseJoinColumns = @JoinColumn(name = "permiso_id"))
    private Set<Permiso> permisos = new HashSet<>();


    public Role(RoleEnum roleEnum, Set<Permiso> listPermisos) {
       this.roleEnum = roleEnum;
       this.permisos = listPermisos;
    }
}
