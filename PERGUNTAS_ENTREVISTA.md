# 🎤 Guia de Perguntas Técnicas - Entrevista

Respostas práticas baseadas NO SEU projeto.

---

## 📚 SOBRE O PROJETO

### **Q: Explique seu projeto**

**R:** 
> "Desenvolvi uma API REST de gerenciamento de biblioteca usando Java 17 e Spring Boot 3.2. O sistema permite o CRUD completo de livros, com 10 endpoints cobrindo cadastro, busca por ID/autor/título, listagem, empréstimo, devolução e exclusão.
>
> Utilizei arquitetura em camadas com Controller para receber requisições HTTP, Service para processar regras de negócio, e Repository para acessar o banco H2. Implementei DTOs para separar a API da entidade, validações com Bean Validation, e tratamento global de exceções. Documentei tudo com Swagger para facilitar testes."

---

### **Q: Por que você escolheu uma biblioteca?**

**R:**
> "Escolhi porque é um domínio simples de entender, mas que permite demonstrar conceitos importantes: CRUD completo, regras de negócio (disponibilidade), relacionamentos potenciais (Usuario, Emprestimo), e buscas variadas. É fácil explicar para qualquer pessoa, mas técnico o suficiente para demonstrar boas práticas."

---

## 🏗️ ARQUITETURA

### **Q: Explique a arquitetura do seu projeto**

**R:**
> "Usei arquitetura em camadas seguindo o padrão MVC adaptado para APIs:
>
> **Controller**: Camada de apresentação. Recebe requisições HTTP, valida dados com @Valid, chama o Service, e retorna ResponseEntity com o status HTTP correto.
>
> **Service**: Camada de lógica de negócio. Contém as regras (ex: ao emprestar, marcar como indisponível), coordena operações, gerencia transações com @Transactional, e converte DTOs para Entidades.
>
> **Repository**: Camada de acesso a dados. Interface que estende JpaRepository, fornece métodos automáticos e query methods customizados.
>
> **Model**: A entidade Livro mapeada para tabela no banco com JPA."

---

### **Q: Por que separar em camadas?**

**R:**
> "Por vários motivos:
> 1. **Responsabilidade única**: Cada camada faz uma coisa só
> 2. **Testabilidade**: Posso testar Service sem HTTP
> 3. **Manutenção**: Se mudar o banco, só mexo no Repository
> 4. **Reutilização**: Vários Controllers podem usar o mesmo Service
> 5. **Clean Code**: Código organizado e fácil de entender"

---

## 🌐 API REST

### **Q: O que é uma API REST?**

**R:**
> "REST é um estilo de arquitetura para APIs que usa o protocolo HTTP. Ela trabalha com recursos (no meu caso, livros) e usa os verbos HTTP para indicar ações:
> - **GET**: Buscar/listar
> - **POST**: Criar
> - **PUT**: Atualizar completo
> - **PATCH**: Atualizar parcial
> - **DELETE**: Remover
>
> No meu projeto tenho endpoints como GET /api/livros para listar e POST /api/livros para cadastrar."

---

### **Q: Diferença entre PUT e PATCH?**

**R:**
> "**PUT** substitui o recurso completo. No meu endpoint PUT /api/livros/{id}, envio o livro inteiro com todos os campos (titulo, autor, ano, editora, disponivel).
>
> **PATCH** atualiza apenas parte. Nos meus endpoints PATCH /emprestar e /devolver, mudo só o campo disponivel, sem alterar titulo, autor, etc."

---

### **Q: Quais códigos HTTP você usa?**

**R:**
> "Meu projeto usa:
> - **200 OK**: GET, PUT e PATCH bem sucedidos
> - **201 Created**: POST quando cadastro livro
> - **204 No Content**: DELETE bem sucedido
> - **400 Bad Request**: Dados inválidos (validação falhou)
> - **404 Not Found**: Livro não encontrado
> - **500 Internal Server Error**: Erro não tratado (capturado pelo GlobalExceptionHandler)"

---

## 🍃 SPRING BOOT

### **Q: O que é Spring Boot?**

**R:**
> "Spring Boot é um framework que facilita a criação de aplicações Java. Ele fornece configuração automática, servidor web embutido (Tomcat), e starters que agrupam dependências.
>
> No meu projeto, só adicionei spring-boot-starter-web e spring-boot-starter-data-jpa no pom.xml, e ele configurou tudo automaticamente: servidor, JPA, Hibernate, validações, etc."

---

### **Q: O que é @Autowired?**

**R:**
> "É injeção de dependência. Quando coloco @Autowired (ou uso @RequiredArgsConstructor do Lombok), o Spring cria e gerencia o objeto automaticamente.
>
> No meu Controller, não faço `new LivroService()`. Apenas declaro:
> ```java
> private final LivroService service;
> ```
> E o Spring injeta a instância. Isso facilita testes e reduz acoplamento."

---

### **Q: Explique as anotações do seu projeto**

**R:**
> "**Camadas:**
> - @RestController: Controller REST
> - @Service: Camada de serviço
> - @Repository: Acesso a dados
> - @Entity: Entidade JPA (tabela)
>
> **Mapeamento:**
> - @RequestMapping: Rota base
> - @GetMapping, @PostMapping, @PutMapping, @PatchMapping, @DeleteMapping: Verbos HTTP
> - @PathVariable: Captura {id} da URL
> - @RequestBody: Recebe JSON no corpo
> - @Valid: Valida o objeto
>
> **JPA:**
> - @Id: Chave primária
> - @GeneratedValue: Auto-incremento
> - @Column: Configuração da coluna
> - @PrePersist: Executado antes de salvar"

---

## 💾 BANCO DE DADOS

### **Q: Por que usar H2?**

**R:**
> "H2 é um banco em memória perfeito para desenvolvimento e demos. Não precisa instalar nada, é só rodar a aplicação. Os dados são temporários - quando reinicia, perde tudo. Ideal para aprender e testar. Em produção usaria PostgreSQL ou MySQL."

---

### **Q: O que é JPA/Hibernate?**

**R:**
> "JPA (Java Persistence API) é uma especificação Java para trabalhar com bancos relacionais. Hibernate é a implementação que uso.
>
> Ele faz ORM (Object-Relational Mapping): mapeia objetos Java para tabelas do banco. A classe Livro com @Entity vira a tabela livros automaticamente. Não preciso escrever SQL manualmente."

---

### **Q: Como funciona o save()?**

**R:**
> "O save() do JPA é inteligente:
> - Se o objeto NÃO tem ID (ou ID é null), ele faz INSERT
> - Se o objeto JÁ tem ID, ele faz UPDATE
>
> No meu Service, uso o mesmo método save() tanto para cadastrar quanto para atualizar."

---

### **Q: O que são Query Methods?**

**R:**
> "São métodos no Repository que o Spring cria queries SQL automaticamente baseado no nome.
>
> No meu LivroRepository tenho:
> ```java
> List<Livro> findByAutor(String autor);
> ```
> O Spring lê 'findBy' + 'Autor' e gera:
> ```sql
> SELECT * FROM livros WHERE autor = ?
> ```
> Também tenho findByDisponivel, findByTituloContainingIgnoreCase, etc."

---

## 🎯 LÓGICA DE NEGÓCIO

### **Q: Explique o método emprestarLivro()**

**R:**
> "```java
> public LivroResponse emprestar(Long id) {
>     // 1. Busco o livro pelo ID
>     Livro livro = repository.findById(id)
>         .orElseThrow(() -> new ResourceNotFoundException(...));
>     
>     // 2. Regra de negócio: marcar como indisponível
>     livro.setDisponivel(false);
>     
>     // 3. Salvo no banco (UPDATE)
>     Livro atualizado = repository.save(livro);
>     
>     // 4. Converto para DTO e retorno
>     return LivroResponse.fromEntity(atualizado);
> }
> ```
> Se o livro não existir, lança ResourceNotFoundException que é capturada pelo GlobalExceptionHandler e retorna 404."

---

### **Q: Por que usar Optional?**

**R:**
> "Optional é um container que pode ter valor ou não. O findById() retorna Optional<Livro> porque o livro pode não existir.
>
> Uso orElseThrow() para dizer: 'Se não encontrar, lança exceção'. Isso evita NullPointerException e torna o código mais expressivo."

---

## 📝 VALIDAÇÕES

### **Q: Como funciona a validação?**

**R:**
> "Uso Bean Validation com anotações no LivroRequest:
> ```java
> @NotBlank(message = "O título é obrigatório")
> @Size(min = 3, max = 200, message = "Título deve ter entre 3 e 200 caracteres")
> private String titulo;
> ```
>
> No Controller, o @Valid valida automaticamente:
> ```java
> public ResponseEntity<LivroResponse> cadastrar(@Valid @RequestBody LivroRequest request)
> ```
>
> Se falhar, lança MethodArgumentNotValidException, que é capturada pelo GlobalExceptionHandler e retorna 400 com os erros."

---

### **Q: O que é o GlobalExceptionHandler?**

**R:**
> "@RestControllerAdvice intercepta TODAS as exceções da aplicação. Centralizo o tratamento:
>
> - **ResourceNotFoundException** → 404
> - **MethodArgumentNotValidException** (validação) → 400
> - **Exception** (qualquer outra) → 500
>
> Isso garante respostas padronizadas e Controllers limpos (sem try-catch)."

---

## 🔄 DTOs

### **Q: Por que usar DTOs?**

**R:**
> "Por vários motivos:
>
> 1. **Segurança**: Não exponho a entidade diretamente
> 2. **Validação**: Posso validar dados específicos da API
> 3. **Versionamento**: Posso mudar a API sem alterar a entidade
> 4. **Flexibilidade**: DTO pode ter campos diferentes da entidade
>
> No meu projeto: LivroRequest para receber dados e LivroResponse para enviar."

---

### **Q: Como converte DTO para Entidade?**

**R:**
> "No Service, faço manualmente no método cadastrar():
> ```java
> Livro livro = new Livro();
> livro.setTitulo(request.getTitulo());
> livro.setAutor(request.getAutor());
> // ...
> ```
>
> E uso um método estático no LivroResponse para converter Entidade → DTO:
> ```java
> return LivroResponse.fromEntity(livro);
> ```"

---

## 🔐 TRANSAÇÕES

### **Q: O que é @Transactional?**

**R:**
> "Garante atomicidade: tudo ou nada. Se der erro no meio de uma operação, faz rollback automático.
>
> Uso @Transactional nos métodos do Service que modificam dados. Para leitura, uso @Transactional(readOnly = true) como otimização."

---

### **Q: Exemplo de uso de transação?**

**R:**
> "No método emprestar():
> ```java
> @Transactional
> public LivroResponse emprestar(Long id) {
>     // Busca + Atualiza + Salva
> }
> ```
> Se qualquer parte falhar (livro não existe, banco caiu), o Spring desfaz TUDO automaticamente."

---

## 📖 SWAGGER

### **Q: Para que serve o Swagger?**

**R:**
> "Gera documentação automática e interface web interativa para testar a API. Acesso em http://localhost:8080/swagger-ui.html.
>
> Ele lê as anotações @Operation, @ApiResponse do Controller e gera:
> - Lista de todos os endpoints
> - Parâmetros necessários
> - Exemplos de JSON
> - Interface para testar (Try it out)"

---

## 🚀 MELHORIAS FUTURAS

### **Q: O que você melhoraria no projeto?**

**R:**
> "Várias coisas:
>
> 1. **Testes**: Unitários (JUnit + Mockito) e integração
> 2. **Relacionamentos**: Adicionar entidade Usuario e Emprestimo
> 3. **Paginação**: Para listar muitos registros
> 4. **Busca avançada**: Filtros combinados, ordenação
> 5. **Segurança**: Spring Security com JWT
> 6. **Auditoria**: Quem criou/atualizou e quando
> 7. **Migrations**: Flyway para versionar o banco
> 8. **Logs**: SLF4J estruturado
> 9. **Cache**: Redis para consultas frequentes
> 10. **CI/CD**: Pipeline de deploy automático"

---

## 💡 CONCEITOS GERAIS

### **Q: O que são os 4 pilares da POO?**

**R:**
> "**Encapsulamento**: Proteção de dados. Campos private, acesso por métodos.
>
> **Herança**: Reutilização. LivroRepository extends JpaRepository.
>
> **Polimorfismo**: Mesmo método, comportamentos diferentes. save() serve para INSERT e UPDATE.
>
> **Abstração**: Esconder complexidade. Uso Repository sem saber como acessa o banco."

---

### **Q: O que é SOLID? (se perguntarem)**

**R:**
> "**S** - Single Responsibility: Cada classe faz uma coisa. Service só tem lógica, Controller só HTTP.
>
> **O** - Open/Closed: Aberto para extensão. Posso adicionar novo Repository sem mexer no existente.
>
> **L** - Liskov: Subst ituição. Qualquer JpaRepository pode substituir outro.
>
> **I** - Interface Segregation: Interfaces pequenas. Repository só com métodos necessários.
>
> **D** - Dependency Inversion: Dependo de abstrações (JpaRepository interface), não implementações."

---

## 🎓 PERGUNTAS PRÁTICAS

### **Q: Como você adicionaria busca por ano?**

**R:**
> "1. No Repository:
> ```java
> List<Livro> findByAno(Integer ano);
> ```
>
> 2. No Service:
> ```java
> public List<LivroResponse> buscarPorAno(Integer ano) {
>     return repository.findByAno(ano).stream()
>         .map(LivroResponse::fromEntity)
>         .collect(Collectors.toList());
> }
> ```
>
> 3. No Controller:
> ```java
> @GetMapping("/ano/{ano}")
> public ResponseEntity<List<LivroResponse>> buscarPorAno(@PathVariable Integer ano) {
>     return ResponseEntity.ok(service.buscarPorAno(ano));
> }
> ```"

---

### **Q: Como impedir cadastro de livro duplicado?**

**R:**
> "No Service, antes de salvar:
> ```java
> if (repository.existsByTitulo(request.getTitulo())) {
>     throw new DuplicateResourceException("Livro com este título já existe");
> }
> ```
>
> E criar a exceção + tratamento no GlobalExceptionHandler retornando 409 Conflict."

---

## 🎯 DICAS FINAIS

**Se não souber:**
> "Não implementei isso neste projeto, mas sei que seria importante. Posso pesquisar a melhor abordagem e implementar."

**Se pedirem para explicar código:**
> "Posso abrir o projeto e mostrar na prática?" (Demonstração vale ouro!)

**Se perguntarem sobre ferramenta que não conhece:**
> "Não tenho experiência prática com [X], mas estou estudando e tenho facilidade de aprender novas tecnologias."

---

**Lembre-se: Para trainee, ninguém espera que você saiba tudo!**  
**O importante é demonstrar fundamentos sólidos e vontade de aprender! 🚀**
