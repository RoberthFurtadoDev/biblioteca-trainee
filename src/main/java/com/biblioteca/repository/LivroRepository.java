package com.biblioteca.repository;

import com.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository - Camada de acesso aos dados.
 * 
 * @Repository - Marca como componente de acesso a dados
 * extends JpaRepository - Herda métodos prontos do Spring Data JPA
 * 
 * JpaRepository<Livro, Long>:
 * - Livro: A entidade que vamos gerenciar
 * - Long: O tipo do ID da entidade
 * 
 * O Spring Data JPA já fornece automaticamente:
 * - save(livro)           - Salvar/Atualizar
 * - findById(id)          - Buscar por ID
 * - findAll()             - Listar todos
 * - deleteById(id)        - Deletar por ID
 * - existsById(id)        - Verificar se existe
 * - count()               - Contar registros
 * 
 * E muito mais! Sem escrever SQL!
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    /**
     * QUERY METHODS - O Spring cria a query automaticamente!
     * 
     * Como funciona?
     * 1. O Spring lê o nome do metodo
     * 2. Identifica as palavras-chave (findBy, autor)
     * 3. Gera o SQL automaticamente: SELECT * FROM livros WHERE autor = ?
     * 
     * Não precisamos escrever SQL ou JPQL manualmente!
     */
    
    /**
     * Busca livros por autor.
     * 
     * SQL gerado: SELECT * FROM livros WHERE autor = ?
     */
    List<Livro> findByAutor(String autor);
    
    /**
     * Busca livros por disponibilidade.
     * 
     * SQL gerado: SELECT * FROM livros WHERE disponivel = ?
     */
    List<Livro> findByDisponivel(Boolean disponivel);
    
    /**
     * Busca livros cujo título CONTÉM o texto (case insensitive).
     * 
     * Containing - Busca com LIKE %texto%
     * IgnoreCase - Ignora maiúsculas/minúsculas
     * 
     * SQL gerado: SELECT * FROM livros WHERE LOWER(titulo) LIKE LOWER(?%)
     * 
     * Exemplo: buscar "dom" encontra "Dom Casmurro" e "Domingos"
     */
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    
    /**
     * Busca livros por ano de publicação.
     * 
     * SQL gerado: SELECT * FROM livros WHERE ano = ?
     */
    List<Livro> findByAno(Integer ano);
    
    /**
     * Verifica se existe livro com aquele título (útil para evitar duplicados).
     * 
     * SQL gerado: SELECT COUNT(*) > 0 FROM livros WHERE titulo = ?
     */
    boolean existsByTitulo(String titulo);
    
    /**
     * Conta quantos livros estão disponíveis.
     * 
     * SQL gerado: SELECT COUNT(*) FROM livros WHERE disponivel = ?
     */
    Long countByDisponivel(Boolean disponivel);
}
