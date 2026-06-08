package br.com.fiap.chronos.dto;

/**
 * DTO de resposta com o token JWT gerado para o operador autenticado.
 */
public record AutoResponse(String token, String type) {
    public AutoResponse(String token) {
        this(token, "Bearer");
    }
}