package br.com.decasa.api.DTO;

public record UserUpdateProfileDTO(
        String username,
        String email,
        String address,
        String cep
        ) { }