package com.biblioteca.controller;

import com.biblioteca.dto.LivroRequest;
import com.biblioteca.dto.LivroResponse;
import com.biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller - Camada de apresentação (endpoints REST).
 * 
 * @RestController - Marca como Controller REST
 * Todos os métodos retornam JSON automaticamente (@ResponseBody implícito)
 * 
 * @RequestMapping - Define o caminho base de todas as rotas
 * Todas as rotas começam com /api/livros
 * 
 * @Tag - Documentação Swagger (agrupa endpoints)
 * 
 * Responsabilidades:
 * 1. Receber requisições HTTP
 * 2. Validar dados de entrada (@Valid)
 * 3. Chamar o Service
 * 4. Retornar resposta HTTP (com status code correto)
 */
@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
@Tag(name = "Livros", description = "API de gerenciamento de livros da biblioteca")
public class LivroController {
    
    /**
     * Injeção de dependência do Service.
     */
    private final LivroService service;
    
    /**
     * CREATE - Cadastrar novo livro.
     * 
     * @PostMapping - Mapeia requisições POST
     * @Valid - Valida o objeto (executa as validações do LivroRequest)
     * @RequestBody - Indica que os dados vêm no corpo da requisição (JSON)
     * 
     * ResponseEntity - Permite controlar o status HTTP da resposta
     * HttpStatus.CREATED - Retorna 201 (Created)
     * 
     * URL: POST http://localhost:8080/api/livros
     * Body: { "titulo": "...", "autor": "...", ... }
     */
    @PostMapping
    @Operation(summary = "Cadastrar novo livro", 
               description = "Cadastra um novo livro no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<LivroResponse> cadastrar(@Valid @RequestBody LivroRequest request) {
        LivroResponse response = service.cadastrar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);  // 201
    }
    
    /**
     * READ - Listar todos os livros.
     * 
     * @GetMapping - Mapeia requisições GET
     * 
     * ResponseEntity.ok() - Retorna 200 (OK)
     * 
     * URL: GET http://localhost:8080/api/livros
     */
    @GetMapping
    @Operation(summary = "Listar todos os livros", 
               description = "Retorna a lista completa de livros cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<LivroResponse>> listarTodos() {
        List<LivroResponse> livros = service.listarTodos();
        return ResponseEntity.ok(livros);  // 200
    }
    
    /**
     * READ - Buscar livro por ID.
     * 
     * @PathVariable - Captura variável da URL
     * 
     * URL: GET http://localhost:8080/api/livros/1
     *          GET http://localhost:8080/api/livros/2
     * 
     * {id} na URL é capturado e passado para o parâmetro Long id
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID", 
               description = "Retorna os detalhes de um livro específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Livro encontrado"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<LivroResponse> buscarPorId(@PathVariable Long id) {
        LivroResponse livro = service.buscarPorId(id);
        return ResponseEntity.ok(livro);  // 200
    }
    
    /**
     * READ - Buscar livros por autor.
     * 
     * URL: GET http://localhost:8080/api/livros/autor/Machado de Assis
     */
    @GetMapping("/autor/{autor}")
    @Operation(summary = "Buscar livros por autor", 
               description = "Retorna todos os livros de um autor específico")
    @ApiResponse(responseCode = "200", description = "Livros encontrados")
    public ResponseEntity<List<LivroResponse>> buscarPorAutor(@PathVariable String autor) {
        List<LivroResponse> livros = service.buscarPorAutor(autor);
        return ResponseEntity.ok(livros);  // 200
    }
    
    /**
     * READ - Buscar livros por título (busca parcial).
     * 
     * Busca LIKE no banco (encontra títulos que CONTENHAM o texto)
     * 
     * URL: GET http://localhost:8080/api/livros/titulo/Dom
     * Encontra: "Dom Casmurro", "O Alienista de Dom Pedro", etc
     */
    @GetMapping("/titulo/{titulo}")
    @Operation(summary = "Buscar livros por título", 
               description = "Busca livros cujo título contenha o texto informado (case insensitive)")
    @ApiResponse(responseCode = "200", description = "Livros encontrados")
    public ResponseEntity<List<LivroResponse>> buscarPorTitulo(@PathVariable String titulo) {
        List<LivroResponse> livros = service.buscarPorTitulo(titulo);
        return ResponseEntity.ok(livros);  // 200
    }
    
    /**
     * READ - Listar apenas livros disponíveis.
     * 
     * URL: GET http://localhost:8080/api/livros/disponiveis
     */
    @GetMapping("/disponiveis")
    @Operation(summary = "Listar livros disponíveis", 
               description = "Retorna apenas os livros que estão disponíveis para empréstimo")
    @ApiResponse(responseCode = "200", description = "Livros disponíveis listados")
    public ResponseEntity<List<LivroResponse>> listarDisponiveis() {
        List<LivroResponse> livros = service.listarDisponiveis();
        return ResponseEntity.ok(livros);  // 200
    }
    
    /**
     * UPDATE - Atualizar livro completo.
     * 
     * @PutMapping - Mapeia requisições PUT (atualização completa)
     * 
     * Substitui TODOS os campos do livro
     * 
     * URL: PUT http://localhost:8080/api/livros/1
     * Body: { "titulo": "...", "autor": "...", ... }
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar livro", 
               description = "Atualiza todos os dados de um livro existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<LivroResponse> atualizar(
            @PathVariable Long id, 
            @Valid @RequestBody LivroRequest request) {
        
        LivroResponse livro = service.atualizar(id, request);
        return ResponseEntity.ok(livro);  // 200
    }
    
    /**
     * UPDATE PARCIAL - Emprestar livro.
     * 
     * @PatchMapping - Mapeia requisições PATCH (atualização parcial)
     * 
     * Muda APENAS o campo "disponivel" para false
     * Não altera título, autor, etc
     * 
     * URL: PATCH http://localhost:8080/api/livros/1/emprestar
     */
    @PatchMapping("/{id}/emprestar")
    @Operation(summary = "Emprestar livro", 
               description = "Marca o livro como emprestado (indisponível)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Livro emprestado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<LivroResponse> emprestar(@PathVariable Long id) {
        LivroResponse livro = service.emprestar(id);
        return ResponseEntity.ok(livro);  // 200
    }
    
    /**
     * UPDATE PARCIAL - Devolver livro.
     * 
     * Muda APENAS o campo "disponivel" para true
     * 
     * URL: PATCH http://localhost:8080/api/livros/1/devolver
     */
    @PatchMapping("/{id}/devolver")
    @Operation(summary = "Devolver livro", 
               description = "Marca o livro como devolvido (disponível)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Livro devolvido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<LivroResponse> devolver(@PathVariable Long id) {
        LivroResponse livro = service.devolver(id);
        return ResponseEntity.ok(livro);  // 200
    }
    
    /**
     * DELETE - Deletar livro.
     * 
     * @DeleteMapping - Mapeia requisições DELETE
     * 
     * ResponseEntity<Void> - Não retorna corpo (apenas status)
     * noContent() - Retorna 204 (No Content)
     * 
     * 204: "Deu certo, mas não tenho nada pra te retornar"
     * 
     * URL: DELETE http://localhost:8080/api/livros/1
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar livro", 
               description = "Remove um livro do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();  // 204
    }
}
