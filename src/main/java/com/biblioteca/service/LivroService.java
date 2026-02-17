package com.biblioteca.service;

import com.biblioteca.dto.LivroRequest;
import com.biblioteca.dto.LivroResponse;
import com.biblioteca.exception.ResourceNotFoundException;
import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service - Camada de lógica de negócio.
 * 
 * @Service - Marca como componente de serviço do Spring
 * @RequiredArgsConstructor - Lombok: cria construtor com campos final/required
 * 
 * Por que usar Service?
 * 1. Separação de responsabilidades (Controller não tem lógica)
 * 2. Reutilização (vários Controllers podem usar o mesmo Service)
 * 3. Testabilidade (fácil testar lógica de negócio)
 * 4. Transações (controle de commit/rollback)
 * 
 * Fluxo:
 * Controller → Service → Repository → Banco de Dados
 */
@Service
@RequiredArgsConstructor  // Gera construtor com LivroRepository (injeção de dependência)
public class LivroService {
    
    /**
     * Injeção de Dependência.
     * 
     * final - Garante que não será null e não pode ser alterado
     * O Spring injeta automaticamente via construtor (gerado pelo Lombok)
     */
    private final LivroRepository repository;
    
    /**
     * CREATE - Cadastrar novo livro.
     * 
     * @Transactional - Garante atomicidade (tudo ou nada)
     * Se der erro, faz rollback automático
     * 
     * Fluxo:
     * 1. Recebe LivroRequest (DTO com validações)
     * 2. Converte para Entidade Livro
     * 3. Salva no banco
     * 4. Converte para LivroResponse
     * 5. Retorna
     */
    @Transactional
    public LivroResponse cadastrar(LivroRequest request) {
        // Converte DTO → Entidade
        Livro livro = new Livro();
        livro.setTitulo(request.getTitulo());
        livro.setAutor(request.getAutor());
        livro.setAno(request.getAno());
        livro.setEditora(request.getEditora());
        livro.setDisponivel(request.getDisponivel() != null ? request.getDisponivel() : true);
        
        // Salva no banco (INSERT)
        Livro salvo = repository.save(livro);
        
        // Converte Entidade → DTO Response
        return LivroResponse.fromEntity(salvo);
    }
    
    /**
     * READ - Listar todos os livros.
     * 
     * @Transactional(readOnly = true) - Otimização para leitura
     * Não abre transação de escrita (mais rápido)
     * 
     * Stream API:
     * - Pega lista de Livros
     * - Mapeia cada Livro para LivroResponse
     * - Coleta em uma nova lista
     */
    @Transactional(readOnly = true)
    public List<LivroResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(LivroResponse::fromEntity)  // Converte cada Livro → Response
                .collect(Collectors.toList());
    }
    
    /**
     * READ - Buscar livro por ID.
     * 
     * Optional: Pode ter valor ou não (evita NullPointerException)
     * orElseThrow: Se não encontrar, lança exceção
     */
    @Transactional(readOnly = true)
    public LivroResponse buscarPorId(Long id) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro", "id", id));
        
        return LivroResponse.fromEntity(livro);
    }
    
    /**
     * READ - Buscar por autor.
     */
    @Transactional(readOnly = true)
    public List<LivroResponse> buscarPorAutor(String autor) {
        return repository.findByAutor(autor)
                .stream()
                .map(LivroResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * READ - Buscar por título (busca parcial).
     */
    @Transactional(readOnly = true)
    public List<LivroResponse> buscarPorTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(LivroResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * READ - Listar livros disponíveis.
     */
    @Transactional(readOnly = true)
    public List<LivroResponse> listarDisponiveis() {
        return repository.findByDisponivel(true)
                .stream()
                .map(LivroResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * UPDATE - Atualizar livro completo.
     * 
     * Lógica:
     * 1. Busca o livro existente (se não existir, lança exceção)
     * 2. Atualiza TODOS os campos
     * 3. Salva (UPDATE)
     */
    @Transactional
    public LivroResponse atualizar(Long id, LivroRequest request) {
        // Busca livro existente
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro", "id", id));
        
        // Atualiza campos
        livro.setTitulo(request.getTitulo());
        livro.setAutor(request.getAutor());
        livro.setAno(request.getAno());
        livro.setEditora(request.getEditora());
        livro.setDisponivel(request.getDisponivel() != null ? request.getDisponivel() : true);
        
        // Salva (JPA detecta que já tem ID, faz UPDATE)
        Livro atualizado = repository.save(livro);
        
        return LivroResponse.fromEntity(atualizado);
    }
    
    /**
     * UPDATE PARCIAL - Emprestar livro.
     * 
     * Regra de negócio:
     * - Marca o livro como indisponível (disponivel = false)
     * - Não altera outros campos
     * 
     * PATCH vs PUT:
     * - PATCH: Atualiza parte (só o campo disponivel)
     * - PUT: Substitui tudo
     */
    @Transactional
    public LivroResponse emprestar(Long id) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro", "id", id));
        
        // Regra de negócio: marcar como indisponível
        livro.setDisponivel(false);
        
        Livro atualizado = repository.save(livro);
        
        return LivroResponse.fromEntity(atualizado);
    }
    
    /**
     * UPDATE PARCIAL - Devolver livro.
     * 
     * Regra de negócio:
     * - Marca o livro como disponível (disponivel = true)
     */
    @Transactional
    public LivroResponse devolver(Long id) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro", "id", id));
        
        // Regra de negócio: marcar como disponível
        livro.setDisponivel(true);
        
        Livro atualizado = repository.save(livro);
        
        return LivroResponse.fromEntity(atualizado);
    }
    
    /**
     * DELETE - Deletar livro.
     * 
     * Lógica:
     * 1. Verifica se existe (se não, lança exceção)
     * 2. Deleta
     */
    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Livro", "id", id);
        }
        
        repository.deleteById(id);
    }
}
