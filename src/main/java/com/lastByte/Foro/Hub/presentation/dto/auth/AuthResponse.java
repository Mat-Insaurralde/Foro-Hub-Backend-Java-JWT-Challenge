package com.lastByte.Foro.Hub.presentation.dto.auth;

public record AuthResponse(
        String username,
         String message,
         Boolean status

) {
    public AuthResponse(String username , String message, Boolean status) {
          this.username = username;
          this.message = message;
          this.status = status;
    }
}
