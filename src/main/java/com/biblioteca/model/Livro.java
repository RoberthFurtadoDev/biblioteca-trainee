package com.biblioteca.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade Livro - Representa a tabela "livros" no banco de dados.
 * 
 * @Entity - Marca esta classe como uma entidade JPA (vira tabela no banco)
 * @Table - Define o nome da tabela (se não colocar, usa o nome da classe)
 * @Data - Lombok: gera automaticamente getters, setters, toString, equals e hashCode
 * @NoArgsConstructor - Lombok: gera construtor vazio (obrigatório para JPA)
 * @AllArgsConstructor - Lombok: gera construtor com todos os campos
 */
@Entity
@Table(name = "livros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {
    
    /**
     * ID único do livro (chave primária).
     * 
     * @Id - Marca este campo como chave primária
     * @GeneratedValue - Gera o ID automaticamente (auto-increment)
     * IDENTITY - Estratégia de geração: o banco cria o próximo número
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Título do livro.
     * 
     * @Column - Configura a coluna no banco
     * nullable = false - Campo obrigatório (NOT NULL)
     * length = 200 - Tamanho máximo: 200 caracteres
     */
    @Column(nullable = false, length = 200)
    private String titulo;
    
    /**
     * Nome do autor do livro.
     */
    @Column(nullable = false, length = 150)
    private String autor;
    
    /**
     * Ano de publicação.
     */
    @Column(nullable = false)
    private Integer ano;
    
    /**
     * Nome da editora (opcional).
     */
    @Column(length = 100)
    private String editora;
    
    /**
     * Indica se o livro está disponível para empréstimo.
     * Valor padrão: true (disponível)
     */
    @Column(nullable = false)
    private Boolean disponivel = true;
    
    /**
     * Data e hora de cadastro do livro.
     * 
     * updatable = false - Este campo não pode ser alterado depois de criado
     */
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;
    
    /**
     * Método executado automaticamente ANTES de salvar no banco.
     * Define a data de cadastro automaticamente.
     */
    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
        
        // Se não definiu disponibilidade, assume true
        if (this.disponivel == null) {
            this.disponivel = true;
        }
    }
}
