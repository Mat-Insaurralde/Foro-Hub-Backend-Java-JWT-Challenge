package com.lastByte.Foro.Hub.infra.security;

import com.lastByte.Foro.Hub.infra.exceptions.CollectionEmptyException;
import com.lastByte.Foro.Hub.presentation.dto.auth.AuthLoginRequest;
import com.lastByte.Foro.Hub.presentation.dto.auth.AuthRegisterUserRequest;
import com.lastByte.Foro.Hub.presentation.dto.auth.AuthResponse;
import com.lastByte.Foro.Hub.domain.Role.Role;
import com.lastByte.Foro.Hub.domain.Role.RoleRepository;
import com.lastByte.Foro.Hub.domain.Usuario.Usuario;
import com.lastByte.Foro.Hub.domain.Usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;

    private final RoleRepository roleRepository;


    private final JwtUtils jwtUtils;


    @Autowired
    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder,
                         UsuarioRepository usuarioRepository,
                         RoleRepository roleRepository,
                         JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));
    }



    /// INICIAR SESION

    public AuthResponse login(AuthLoginRequest datosLogin) {

        Authentication authentication = authenticate(datosLogin.username(), datosLogin.password());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new AuthResponse(datosLogin.username(), "Usuario logeado con exito!",  true);
    }





    ///REGISTRAR USUARIO

    public AuthResponse registerUser(AuthRegisterUserRequest registerUserRequest) {

        if (usuarioRepository.existsByEmail(registerUserRequest.email())) {
            throw new ValidationException("Ya existe un usuario con ese email");
        }
        if (usuarioRepository.existsByUsername(registerUserRequest.username())) {
            throw new ValidationException("Ya existe un usuario con ese username");
        }

        List<String> roleRequest = registerUserRequest.roleRequest().roleList();

         Set<Role> rolesSet = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());

         if (rolesSet.isEmpty()){
             throw new CollectionEmptyException("Los roles especificados no existen");
         }

        var usuario = new Usuario(rolesSet,registerUserRequest, passwordEncoder.encode(registerUserRequest.password()));

        usuarioRepository.save(usuario);

        return new AuthResponse(registerUserRequest.username(), "Usuario registrado con exito!",  true);
    }





    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Username o password invalido!");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password invalido!");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }




    public String createToken(String username, String password){
        Authentication authenticate = authenticate(username,password);
        return jwtUtils.createToken(authenticate);
    }










}
