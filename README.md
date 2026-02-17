# 📚 API Biblioteca - Sistema de Gerenciamento

> **Projeto desenvolvido por Roberth Furtado**  
> API REST completa com Spring Boot, JPA e H2 Database

---

## 🎯 Sobre o Projeto

Sistema de gerenciamento de biblioteca que permite:

✅ **CRUD completo** - Cadastrar, listar, atualizar e deletar livros  
✅ **Buscas inteligentes** - Por ID, autor, título, disponibilidade  
✅ **Empréstimo/Devolução** - Controle de disponibilidade  
✅ **Documentação Swagger** - Interface interativa para testar a API  
✅ **Banco H2** - Em memória, não precisa instalar nada!  

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| Java | 17 | Linguagem de programação |
| Spring Boot | 3.2.0 | Framework para criar APIs |
| Spring Data JPA | 3.2.0 | Abstração para acesso a dados |
| H2 Database | 2.2.x | Banco de dados em memória |
| Lombok | 1.18.x | Reduz código boilerplate |
| Swagger/OpenAPI | 2.2.0 | Documentação automática |
| Maven | 3.6+ | Gerenciador de dependências |

---

## 📋 Arquitetura do Projeto

```
biblioteca-api/
├── src/main/java/com/biblioteca/
│   ├── config/
│   │   └── SwaggerConfig.java          # Configuração do Swagger
│   ├── controller/
│   │   └── LivroController.java        # Endpoints REST
│   ├── service/
│   │   └── LivroService.java           # Lógica de negócio
│   ├── repository/
│   │   └── LivroRepository.java        # Acesso ao banco
│   ├── model/
│   │   └── Livro.java                  # Entidade JPA
│   ├── dto/
│   │   ├── LivroRequest.java           # DTO de entrada
│   │   └── LivroResponse.java          # DTO de saída
│   ├── exception/
│   │   ├── ResourceNotFoundException.java
│   │   └── GlobalExceptionHandler.java
│   └── BibliotecaApplication.java      # Classe principal
├── src/main/resources/
│   └── application.properties          # Configurações
└── pom.xml                             # Dependências Maven
```

### 🔍 Camadas da Aplicação

**1. Controller (Apresentação)**
- Recebe requisições HTTP
- Valida dados de entrada
- Retorna respostas JSON
- Define códigos de status HTTP

**2. Service (Lógica de Negócio)**
- Processa regras de negócio
- Coordena operações
- Gerencia transações
- Converte DTOs ↔ Entidades

**3. Repository (Acesso a Dados)**
- Comunica com o banco
- Query methods automáticos
- Fornecido pelo Spring Data JPA

**4. Model (Entidade)**
- Representa tabela do banco
- Mapeamento JPA
- Validações de campo

---

## 🚀 Como Executar

### **Pré-requisitos**
- ☕ Java 17 ou superior
- 📦 Maven 3.6 ou superior
- 🖥️ IDE (IntelliJ IDEA recomendado)

### **Passo 1: Clonar/Extrair o projeto**

Extraia o arquivo `biblioteca-trainee.tar.gz` ou clone do repositório.

### **Passo 2: Compilar**

```bash
mvn clean install
```

### **Passo 3: Executar**

```bash
mvn spring-boot:run
```

### **Passo 4: Acessar**

✅ **API**: http://localhost:8080/api/livros  
✅ **Swagger**: http://localhost:8080/swagger-ui.html  
✅ **Console H2**: http://localhost:8080/h2-console  

---

## 📡 Endpoints da API

### **CREATE - Cadastrar Livro**
```http
POST /api/livros
Content-Type: application/json

{
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "ano": 1899,
  "editora": "Companhia das Letras"
}
```

**Resposta: 201 Created**
```json
{
  "id": 1,
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "ano": 1899,
  "editora": "Companhia das Letras",
  "disponivel": true,
  "dataCadastro": "2024-02-18T10:30:00"
}
```

---

### **READ - Listar Todos**
```http
GET /api/livros
```

**Resposta: 200 OK**
```json
[
  {
    "id": 1,
    "titulo": "Dom Casmurro",
    "autor": "Machado de Assis",
    "ano": 1899,
    "disponivel": true,
    ...
  }
]
```

---

### **READ - Buscar por ID**
```http
GET /api/livros/1
```

---

### **READ - Buscar por Autor**
```http
GET /api/livros/autor/Machado de Assis
```

---

### **READ - Buscar por Título (contém)**
```http
GET /api/livros/titulo/Dom
```
*Encontra "Dom Casmurro", "O Domador de Gigantes", etc*

---

### **READ - Listar Disponíveis**
```http
GET /api/livros/disponiveis
```

---

### **UPDATE - Atualizar Completo**
```http
PUT /api/livros/1
Content-Type: application/json

{
  "titulo": "Dom Casmurro - Edição Especial",
  "autor": "Machado de Assis",
  "ano": 1899,
  "editora": "Nova Fronteira",
  "disponivel": true
}
```

---

### **PATCH - Emprestar Livro**
```http
PATCH /api/livros/1/emprestar
```
*Marca disponivel = false*

---

### **PATCH - Devolver Livro**
```http
PATCH /api/livros/1/devolver
```
*Marca disponivel = true*

---

### **DELETE - Deletar**
```http
DELETE /api/livros/1
```

**Resposta: 204 No Content**

---

## 💾 Banco de Dados H2

### **Acessar Console H2**

1. Abra: http://localhost:8080/h2-console
2. Configure:
   - **JDBC URL**: `jdbc:h2:mem:biblioteca_db`
   - **Username**: `sa`
   - **Password**: *(deixe vazio)*
3. Clique em "Connect"

### **Comandos SQL úteis**

```sql
-- Ver todos os livros
SELECT * FROM livros;

-- Contar total de livros
SELECT COUNT(*) FROM livros;

-- Ver apenas disponíveis
SELECT * FROM livros WHERE disponivel = true;

-- Buscar por autor
SELECT * FROM livros WHERE autor = 'Machado de Assis';
```

---

## 🧪 Testando com cURL

```bash
# Cadastrar
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo":"1984","autor":"George Orwell","ano":1949,"editora":"Companhia das Letras"}'

# Listar todos
curl http://localhost:8080/api/livros

# Buscar por ID
curl http://localhost:8080/api/livros/1

# Emprestar
curl -X PATCH http://localhost:8080/api/livros/1/emprestar

# Devolver
curl -X PATCH http://localhost:8080/api/livros/1/devolver

# Deletar
curl -X DELETE http://localhost:8080/api/livros/1
```

---

## 📖 Documentação Swagger

Acesse: **http://localhost:8080/swagger-ui.html**

O Swagger fornece:
- 📋 Lista de todos os endpoints
- 📝 Descrição de cada operação
- ▶️ Interface para testar diretamente
- 📊 Modelos de dados (Request/Response)
- ✅ Códigos de status HTTP

---

## 🎯 Conceitos Demonstrados

### **1. CRUD Completo**
✅ Create (POST)  
✅ Read (GET)  
✅ Update (PUT/PATCH)  
✅ Delete (DELETE)  

### **2. API REST**
✅ Verbos HTTP corretos  
✅ Códigos de status apropriados  
✅ Request/Response com JSON  
✅ Nomes de rotas semânticos  

### **3. Arquitetura em Camadas**
✅ Separação de responsabilidades  
✅ Controller → Service → Repository  
✅ DTOs para entrada/saída  

### **4. Spring Boot**
✅ Injeção de dependência (@Autowired)  
✅ Anotações (@RestController, @Service, @Repository)  
✅ Configuração automática  

### **5. JPA/Hibernate**
✅ Mapeamento Objeto-Relacional  
✅ Query Methods  
✅ Transações (@Transactional)  

### **6. Validação**
✅ Bean Validation (@NotBlank, @Min, @Max)  
✅ Tratamento de erros global  
✅ Mensagens customizadas  

### **7. Boas Práticas**
✅ Código comentado  
✅ Nomenclatura clara  
✅ Tratamento de exceções  
✅ Documentação (Swagger)  

---

## 🎓 Sobre o Projeto

### **Explicação:**

> *"Desenvolvi uma API REST de gerenciamento de biblioteca usando Java 17 e Spring Boot. O sistema possui CRUD completo com 10 endpoints, incluindo funcionalidades de empréstimo e devolução de livros.*
>
> *Utilizei arquitetura em camadas: Controller para receber requisições HTTP, Service para processar a lógica de negócio, e Repository para acessar o banco H2.*
>
> *Implementei validações com Bean Validation, tratamento global de exceções, e documentei tudo com Swagger para facilitar testes e integração."*

### **Perguntas Comuns:**

**Q: Por que usar DTOs?**  
A: Para desacoplar a API da entidade, validar dados, e ter flexibilidade para versionar a API.

**Q: Diferença entre PUT e PATCH?**  
A: PUT substitui o recurso completo, PATCH atualiza apenas parte (ex: só o campo disponivel).

**Q: Como funciona @Transactional?**  
A: Garante atomicidade. Se der erro, faz rollback automático. Tudo ou nada.

**Q: O que é Query Method?**  
A: O Spring cria queries SQL automaticamente baseado no nome do método (ex: findByAutor).

---

## 🔧 Comandos Úteis

```bash
# Compilar
mvn clean install

# Executar
mvn spring-boot:run

# Compilar sem testes
mvn clean install -DskipTests

# Gerar JAR
mvn clean package

# Executar JAR
java -jar target/biblioteca-api-1.0.0.jar
```

---

## 📚 Próximas Melhorias

- [ ] Testes unitários (JUnit + Mockito)
- [ ] Testes de integração
- [ ] Paginação e ordenação
- [ ] Relacionamento com entidade Usuario
- [ ] Histórico de empréstimos
- [ ] Autenticação/Autorização (Spring Security)
- [ ] Migrations (Flyway)

---

## 👨‍💻 Autor

**Roberth**  
Desenvolvedor | Java + Spring Boot

---

