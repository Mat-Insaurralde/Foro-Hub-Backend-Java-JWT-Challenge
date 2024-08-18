package com.lastByte.Foro.Hub.presentation.dto.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthRegisterUserRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        String nombre,
        @NotBlank(message = "El email es obligatorio")
        String email,
        @NotBlank(message = "El username del usuario es obligatorio")
        String username,
        @NotBlank(message = "La contraseña es obligatoria")
        String password,
        @Valid
        AuthCreateRoleRequest roleRequest
) {

        public AuthRegisterUserRequest(String nombre, String email, String username,String password, AuthCreateRoleRequest roleRequest) {
             this.nombre= nombre;
             this.username= username;
             this.email= email;
             this.password= password;
             this.roleRequest= roleRequest;
        }


}
