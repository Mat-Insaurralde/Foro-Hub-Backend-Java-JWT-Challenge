package com.lastByte.Foro.Hub.presentation.controller;


import com.lastByte.Foro.Hub.infra.security.JwtUtils;
import com.lastByte.Foro.Hub.presentation.dto.auth.AuthResponse;
import com.lastByte.Foro.Hub.presentation.dto.auth.AuthRegisterUserRequest;
import com.lastByte.Foro.Hub.presentation.dto.auth.AuthLoginRequest;
import com.lastByte.Foro.Hub.infra.security.UserDetailsServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autentiacion", description = "Endpoints para el inicio de sesion o registro del usuario")
public class AutenticacionController {


    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtils jwtUtils;



    @PostMapping("/login")
    @Transactional
    @Operation(
            summary = "Obtiene el acceso , la autenticacion y el token para el usuario ingresado que da acceso a los demas endpoints",
            description = """
                          Este endpoint permite a un usuario autenticarse en el sistema y obtener un token JWT.
                          El token se debe incluir en los encabezados de las solicitudes subsecuentes para acceder a los endpoints protegidos.""",
            tags = {}
    )
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest datosLogin, HttpServletResponse response) {

        AuthResponse authResponse = this.userDetailsServiceImpl.login(datosLogin);

        setCookieToken(this.userDetailsServiceImpl.createToken(datosLogin.username(), datosLogin.password())
                , response );

        return  ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }




    @PostMapping("/register")
    @Transactional
    @Operation(
            summary = "Registra un nuevo usuario , obtiene la autenticacion y token para el usuario",
            description = """
                    Este endpoint permite registrar un nuevo usuario en el sistema.
                    Al registrarse, el usuario recibe un token JWT que le da acceso a los endpoints protegidos.
                    """,
            tags = {}
    )
    public ResponseEntity register(@RequestBody @Valid AuthRegisterUserRequest datosRegister , HttpServletResponse response ) {

        AuthResponse authResponse = this.userDetailsServiceImpl.registerUser(datosRegister);

         setCookieToken(this.userDetailsServiceImpl.createToken(datosRegister.username(), datosRegister.password())
         , response );

        return  ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }



    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletResponse response){

        setCookieToken(null,response);

        // Limpiar el contexto de seguridad
        SecurityContextHolder.clearContext();

        return  ResponseEntity.status(HttpStatus.OK).body(new AuthResponse("","Sesion Cerrada!",false));
    }
















    public void setCookieToken(String token, HttpServletResponse response) {
        // Crear la cookie
        Cookie cookie = new Cookie("acces_token", token);
        // Configurar la cookie
        cookie.setHttpOnly(true); // Hace que la cookie no sea accesible por JavaScript
        cookie.setSecure(true); // Solo se envía por HTTPS      // Si usas HTTPS (recomendado para producción)
        cookie.setMaxAge(3600); // Duración en segundos (1 hora en este caso)
        cookie.setPath("/"); // La cookie es válida para todo el sitio

        // Añadir la cookie a la respuesta
        response.addCookie(cookie);
    }

}
