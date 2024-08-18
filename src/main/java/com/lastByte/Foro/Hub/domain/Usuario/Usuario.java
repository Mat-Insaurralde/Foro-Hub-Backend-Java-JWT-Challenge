package com.lastByte.Foro.Hub.domain.Usuario;


import com.lastByte.Foro.Hub.presentation.dto.auth.AuthRegisterUserRequest;
import com.lastByte.Foro.Hub.domain.Topico.Topico;
import com.lastByte.Foro.Hub.domain.Role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Table(name = "usuarios")
@Entity(name = "usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") //crea automaticamente los metodos equals y hashcode con el id
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    @Column(unique = true)
    private String username;
    @Setter
    private String password;

    @Column(name = "is_enabled")
    private Boolean isEnabled;
    @Column(name = "account_No_Expired")
    private Boolean accountNoExpired;
    @Column(name = "account_No_Locked")
    private Boolean accountNoLocked;
    @Column(name = "credentials_No_Expired")
    private Boolean credentialsNoExpired;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)//Carga todo de una vez //guarda los roles cuando guardamos el user
    @JoinTable(name = "usuario_roles" , joinColumns = @JoinColumn(name = "usuario_id") , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role>  roles = new HashSet<>();


    @OneToMany(mappedBy = "autor")
    private List<Topico> topicos;


    public Usuario(Set<Role> rolesSet, AuthRegisterUserRequest registroUsuarioDTO, String password) {
        this.nombre= registroUsuarioDTO.nombre();
        this.email=registroUsuarioDTO.email();
        this.username=registroUsuarioDTO.username();
        this.password=password;
        this.isEnabled=true;
        this.accountNoLocked=true;
        this.accountNoExpired=true;
        this.credentialsNoExpired=true;
        this.roles=rolesSet;
    }


    public void desactivarUsuario() {
        this.isEnabled=false;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
   List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        //Traemos los roles
        getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        //Traemos los permisos
        getRoles().stream()
                .flatMap(role -> role.getPermisos().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getPermisoEnum().name())));

        return authorities;
    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public boolean isAccountNonLocked() {
        return accountNoLocked;
    }

    @Override
    public boolean isAccountNonExpired() {
        return  accountNoExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNoExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }


}
