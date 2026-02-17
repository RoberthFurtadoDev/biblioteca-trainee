package com.biblioteca.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler Global de Exceções.
 * 
 * @RestControllerAdvice - Intercepta TODAS as exceções da aplicação
 * 
 * Por que usar?
 * 1. Centralização: Todas as exceções tratadas em um único lugar
 * 2. Padronização: Respostas de erro consistentes
 * 3. Manutenção: Fácil adicionar novos tratamentos
 * 4. Clean Code: Controllers não precisam de try-catch
 * 
 * Fluxo:
 * Controller → Erro → GlobalExceptionHandler → Resposta padronizada
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Trata ResourceNotFoundException.
     * 
     * @ExceptionHandler - Marca o método que trata a exceção
     * 
     * Quando: Buscar livro que não existe
     * Retorna: HTTP 404 (Not Found)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),           // 404
            ex.getMessage(),                         // Mensagem do erro
            LocalDateTime.now()                      // Timestamp
        );
        
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    /**
     * Trata erros de validação (@Valid no Controller).
     * 
     * Quando: Enviar dados inválidos (ex: título vazio)
     * Retorna: HTTP 400 (Bad Request) com lista de erros
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        
        // Mapa para armazenar os erros de cada campo
        Map<String, String> errors = new HashMap<>();
        
        // Percorre todos os erros de validação
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();      // Nome do campo
            String errorMessage = error.getDefaultMessage();          // Mensagem de erro
            errors.put(fieldName, errorMessage);
        });
        
        // Monta resposta final
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());  // 400
        response.put("errors", errors);                           // Lista de erros
        response.put("timestamp", LocalDateTime.now());
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Trata QUALQUER outra exceção não mapeada.
     * 
     * Quando: Erro inesperado (NullPointer, banco caiu, etc)
     * Retorna: HTTP 500 (Internal Server Error)
     * 
     * Importante: Em produção, não retornar detalhes do erro interno!
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),    // 500
            "Erro interno do servidor",                   // Mensagem genérica
            LocalDateTime.now()
        );
        
        // Em desenvolvimento, você pode logar o erro completo:
        // ex.printStackTrace();
        
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Classe interna para padronizar resposta de erro.
     * 
     * Record: Estrutura imutável do Java (a partir do Java 14)
     * Gera automaticamente: construtor, getters, equals, hashCode, toString
     */
    public record ErrorResponse(int status, String message, LocalDateTime timestamp) {}
}
