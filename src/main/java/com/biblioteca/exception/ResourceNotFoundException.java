package com.biblioteca.exception;

/**
 * Exceção lançada quando um recurso não é encontrado no banco.
 * 
 * Exemplo: Buscar livro com ID 999 que não existe.
 * 
 * Por que criar exceção customizada?
 * - Semântica: O nome já explica o problema
 * - Tratamento: Podemos capturar e tratar especificamente
 * - HTTP Status: Podemos mapear para 404 (Not Found)
 */
public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Construtor simples com mensagem.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Construtor que monta mensagem padrão.
     * 
     * Exemplo: ResourceNotFoundException("Livro", "id", 999)
     * Resultado: "Livro não encontrado com id: 999"
     */
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s não encontrado com %s: %s", resource, field, value));
    }
}
