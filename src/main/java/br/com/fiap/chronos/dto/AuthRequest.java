package br.com.fiap.chronos.dto;
import jakarta.validation.constraints.NotBlank;

public record AutoRequest(
        @NotBlank(message = "Username obrigatorio")
        String username,

        @NotBlank(message = "Password obrigatorio")
        String password
) {}
