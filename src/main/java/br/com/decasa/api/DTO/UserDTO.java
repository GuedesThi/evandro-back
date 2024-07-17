package br.com.decasa.api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank @Size(min = 3) String username,
        @NotBlank String email,
        @NotBlank @Size(min = 8, max = 10) String password
) { }