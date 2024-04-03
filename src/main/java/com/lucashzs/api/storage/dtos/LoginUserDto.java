package com.lucashzs.api.storage.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginUserDto (
        @NotBlank(message = "An email is required to login!") String email,
        @NotBlank (message = "An password is required to login!") String password){
}
