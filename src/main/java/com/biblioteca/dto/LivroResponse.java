package com.biblioteca.dto;

import com.biblioteca.model.Livro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para ENVIAR dados ao cliente (resposta da API).
 * 
 * Por que usar DTO de resposta?
 * 1. Controle: Decidimos exatamente quais campos retornar
 * 2. Segurança: Não expomos campos sensíveis da entidade
 * 3. Formatação: Podemos formatar os dados de forma diferente
 * 4. Performance: Evitamos lazy loading indesejado
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroResponse {
    
    private Long id;
    private String titulo;
    private String autor;
    private Integer ano;
    private String editora;
    private Boolean disponivel;
    private LocalDateTime dataCadastro;
    
    /**
     * Método estático para converter Entidade → DTO Response.
     * 
     * Padrão: Factory Method (cria um objeto a partir de outro)
     * 
     * Por que usar?
     * - Centralizamos a conversão em um único lugar
     * - Facilita manutenção (se mudar a entidade, mudo só aqui)
     * - Código mais limpo no Service/Controller
     * 
     * @param livro Entidade do banco de dados
     * @return DTO formatado para resposta
     */
    public static LivroResponse fromEntity(Livro livro) {
        return new LivroResponse(
            livro.getId(),
            livro.getTitulo(),
            livro.getAutor(),
            livro.getAno(),
            livro.getEditora(),
            livro.getDisponivel(),
            livro.getDataCadastro()
        );
    }
}
