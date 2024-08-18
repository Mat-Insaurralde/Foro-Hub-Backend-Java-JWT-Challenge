package com.lastByte.Foro.Hub.presentation.dto.auth;

import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthCreateRoleRequest(
        @Size(max = 3 , message = "Un usuario solo puede tener 3 roles")
        List<String> roleList
) {
        public AuthCreateRoleRequest(List<String> roleList) {
                this.roleList = roleList;
        }
}
