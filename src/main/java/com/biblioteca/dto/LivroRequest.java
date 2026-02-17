package com.biblioteca.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para RECEBER dados do cliente.
 * 
 * Usado nos endpoints POST e PUT para criar/atualizar livros.
 * 
 * Por que usar DTO?
 * 1. Segurança: Não expõe a entidade diretamente
 * 2. Validação: Podemos validar os dados antes de processar
 * 3. Flexibilidade: Podemos ter campos diferentes da entidade
 * 4. Versionamento: Facilita mudanças na API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroRequest {
    
    /**
     * Título do livro.
     * 
     * @NotBlank - Não pode ser null, vazio ou só espaços
     * @Size - Define tamanho mínimo e máximo
     */
    @NotBlank(message = "O título é obrigatório")
    @Size(min = 3, max = 200, message = "O título deve ter entre 3 e 200 caracteres")
    private String titulo;
    
    /**
     * Nome do autor.
     */
    @NotBlank(message = "O autor é obrigatório")
    @Size(min = 3, max = 150, message = "O autor deve ter entre 3 e 150 caracteres")
    private String autor;
    
    /**
     * Ano de publicação.
     * 
     * @NotNull - Não pode ser null (mas pode ser zero)
     * @Min / @Max - Define valor mínimo e máximo
     */
    @NotNull(message = "O ano é obrigatório")
    @Min(value = 1000, message = "Ano deve ser maior que 1000")
    @Max(value = 2100, message = "Ano deve ser menor que 2100")
    private Integer ano;
    
    /**
     * Nome da editora (opcional).
     */
    @Size(max = 100, message = "A editora deve ter no máximo 100 caracteres")
    private String editora;
    
    /**
     * Se o livro está disponível (opcional, padrão é true).
     */
    private Boolean disponivel;
}
