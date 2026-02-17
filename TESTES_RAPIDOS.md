# 🧪 Guia de Testes Rápidos

Comandos prontos para testar todos os endpoints da API.

---

## 🚀 Iniciar Aplicação

```bash
mvn spring-boot:run
```

Aguarde ver:
```
🚀 API Biblioteca está rodando!
```

---

## 📚 Testes com cURL

### **1. Cadastrar Livros**

```bash
# Livro 1
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Dom Casmurro",
    "autor": "Machado de Assis",
    "ano": 1899,
    "editora": "Companhia das Letras"
  }'

# Livro 2
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "1984",
    "autor": "George Orwell",
    "ano": 1949,
    "editora": "Companhia das Letras"
  }'

# Livro 3
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "O Cortiço",
    "autor": "Aluísio Azevedo",
    "ano": 1890,
    "editora": "Ática"
  }'

# Livro 4
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Memórias Póstumas de Brás Cubas",
    "autor": "Machado de Assis",
    "ano": 1881,
    "editora": "Nova Fronteira"
  }'
```

---

### **2. Listar Todos os Livros**

```bash
curl http://localhost:8080/api/livros
```

**Resultado esperado:** Array com 4 livros

---

### **3. Buscar por ID**

```bash
# Buscar livro ID 1
curl http://localhost:8080/api/livros/1

# Buscar livro ID 2
curl http://localhost:8080/api/livros/2
```

---

### **4. Buscar por Autor**

```bash
# Livros de Machado de Assis
curl "http://localhost:8080/api/livros/autor/Machado%20de%20Assis"

# Livros de George Orwell
curl "http://localhost:8080/api/livros/autor/George%20Orwell"
```

**Dica:** `%20` = espaço na URL

---

### **5. Buscar por Título**

```bash
# Buscar "Dom"
curl http://localhost:8080/api/livros/titulo/Dom

# Buscar "Memórias"
curl "http://localhost:8080/api/livros/titulo/Mem%C3%B3rias"
```

---

### **6. Listar Disponíveis**

```bash
curl http://localhost:8080/api/livros/disponiveis
```

**Resultado esperado:** Todos (4 livros)

---

### **7. Emprestar Livro**

```bash
# Emprestar livro ID 1
curl -X PATCH http://localhost:8080/api/livros/1/emprestar

# Emprestar livro ID 2
curl -X PATCH http://localhost:8080/api/livros/2/emprestar
```

**Agora liste os disponíveis novamente:**
```bash
curl http://localhost:8080/api/livros/disponiveis
```

**Resultado esperado:** 2 livros (IDs 3 e 4)

---

### **8. Devolver Livro**

```bash
# Devolver livro ID 1
curl -X PATCH http://localhost:8080/api/livros/1/devolver
```

**Liste disponíveis:**
```bash
curl http://localhost:8080/api/livros/disponiveis
```

**Resultado esperado:** 3 livros (IDs 1, 3 e 4)

---

### **9. Atualizar Livro**

```bash
curl -X PUT http://localhost:8080/api/livros/1 \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Dom Casmurro - Edição Especial",
    "autor": "Machado de Assis",
    "ano": 1899,
    "editora": "Nova Fronteira",
    "disponivel": true
  }'
```

**Verificar:**
```bash
curl http://localhost:8080/api/livros/1
```

---

### **10. Deletar Livro**

```bash
# Deletar livro ID 4
curl -X DELETE http://localhost:8080/api/livros/4
```

**Listar todos:**
```bash
curl http://localhost:8080/api/livros
```

**Resultado esperado:** 3 livros (sem o ID 4)

---

## 🧪 Testar Validações (Erros Esperados)

### **Título vazio (Erro 400)**

```bash
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "",
    "autor": "Teste",
    "ano": 2000
  }'
```

**Resultado esperado:**
```json
{
  "status": 400,
  "errors": {
    "titulo": "O título é obrigatório"
  },
  ...
}
```

---

### **Ano inválido (Erro 400)**

```bash
curl -X POST http://localhost:8080/api/livros \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Teste",
    "autor": "Teste",
    "ano": 500
  }'
```

**Resultado esperado:**
```json
{
  "status": 400,
  "errors": {
    "ano": "Ano deve ser maior que 1000"
  },
  ...
}
```

---

### **ID inexistente (Erro 404)**

```bash
curl http://localhost:8080/api/livros/999
```

**Resultado esperado:**
```json
{
  "status": 404,
  "message": "Livro não encontrado com id: 999",
  "timestamp": "..."
}
```

---

## 🌐 Testar no Navegador

### **GET funciona no navegador:**

```
http://localhost:8080/api/livros
http://localhost:8080/api/livros/1
http://localhost:8080/api/livros/disponiveis
http://localhost:8080/api/livros/autor/Machado de Assis
```

### **POST, PUT, DELETE precisam de ferramenta:**
- Swagger UI (recomendado!)
- Postman
- Insomnia
- cURL

---

## 📖 Testar no Swagger

1. Abra: http://localhost:8080/swagger-ui.html

2. **Cadastrar livro:**
   - Clique em **POST /api/livros**
   - Clique em **Try it out**
   - Cole o JSON
   - Clique em **Execute**

3. **Listar todos:**
   - Clique em **GET /api/livros**
   - Clique em **Try it out**
   - Clique em **Execute**

4. **Emprestar:**
   - Clique em **PATCH /api/livros/{id}/emprestar**
   - Digite o ID (ex: 1)
   - Clique em **Execute**

---

## 💾 Testar no H2 Console

1. Abra: http://localhost:8080/h2-console

2. Conecte com:
   - **JDBC URL**: `jdbc:h2:mem:biblioteca_db`
   - **Username**: `sa`
   - **Password**: *(vazio)*

3. **Comandos SQL:**

```sql
-- Ver estrutura da tabela
SELECT * FROM livros;

-- Contar total
SELECT COUNT(*) as total FROM livros;

-- Livros disponíveis
SELECT * FROM livros WHERE disponivel = true;

-- Livros de Machado de Assis
SELECT * FROM livros WHERE autor = 'Machado de Assis';

-- Livros cadastrados hoje
SELECT * FROM livros WHERE DATE(data_cadastro) = CURRENT_DATE;

-- Inserir direto no banco (sem API)
INSERT INTO livros (titulo, autor, ano, editora, disponivel, data_cadastro)
VALUES ('O Alienista', 'Machado de Assis', 1882, 'Ática', true, CURRENT_TIMESTAMP);
```

---

## ✅ Fluxo de Teste Completo

Execute em ordem:

```bash
# 1. Cadastrar 3 livros
curl -X POST http://localhost:8080/api/livros -H "Content-Type: application/json" -d '{"titulo":"1984","autor":"George Orwell","ano":1949,"editora":"Companhia das Letras"}'
curl -X POST http://localhost:8080/api/livros -H "Content-Type: application/json" -d '{"titulo":"O Cortiço","autor":"Aluísio Azevedo","ano":1890,"editora":"Ática"}'
curl -X POST http://localhost:8080/api/livros -H "Content-Type: application/json" -d '{"titulo":"Dom Casmurro","autor":"Machado de Assis","ano":1899,"editora":"Companhia das Letras"}'

# 2. Listar todos (deve ter 3)
curl http://localhost:8080/api/livros

# 3. Buscar por autor
curl "http://localhost:8080/api/livros/autor/George%20Orwell"

# 4. Emprestar livro 1
curl -X PATCH http://localhost:8080/api/livros/1/emprestar

# 5. Ver disponíveis (deve ter 2)
curl http://localhost:8080/api/livros/disponiveis

# 6. Devolver livro 1
curl -X PATCH http://localhost:8080/api/livros/1/devolver

# 7. Ver disponíveis (deve ter 3 novamente)
curl http://localhost:8080/api/livros/disponiveis

# 8. Atualizar livro 2
curl -X PUT http://localhost:8080/api/livros/2 -H "Content-Type: application/json" -d '{"titulo":"O Cortiço - Nova Edição","autor":"Aluísio Azevedo","ano":1890,"editora":"Nova Fronteira","disponivel":true}'

# 9. Verificar atualização
curl http://localhost:8080/api/livros/2

# 10. Deletar livro 3
curl -X DELETE http://localhost:8080/api/livros/3

# 11. Listar todos (deve ter 2)
curl http://localhost:8080/api/livros
```

---

## 🎯 Resumo dos Códigos HTTP

| Código | Significado | Quando ocorre |
|--------|-------------|---------------|
| 200 | OK | GET, PUT, PATCH bem sucedidos |
| 201 | Created | POST bem sucedido |
| 204 | No Content | DELETE bem sucedido |
| 400 | Bad Request | Dados inválidos (validação) |
| 404 | Not Found | Recurso não existe |
| 500 | Internal Server Error | Erro no servidor (bug) |

---
