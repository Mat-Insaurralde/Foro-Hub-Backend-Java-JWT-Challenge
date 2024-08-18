package com.lastByte.Foro.Hub.presentation.dto.auth;

import jakarta.validation.constraints.NotNull;

public record AuthLoginRequest(
        @NotNull
        String username,
        @NotNull
        String password
) {
}
