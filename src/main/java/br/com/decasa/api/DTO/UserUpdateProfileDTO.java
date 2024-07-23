package br.com.decasa.api.DTO;

public record UserUpdateProfileDTO(
        String username,
        String atualEmail,
        String email,
        String address,
        String cep
        ) { }