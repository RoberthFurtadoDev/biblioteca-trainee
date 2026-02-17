# 🖥️ Configurando o Projeto no IntelliJ IDEA

Guia passo a passo para abrir e executar o projeto no IntelliJ.

---

## 📥 Importar o Projeto

### **Opção 1: Importar arquivo extraído**

1. **Extrair o arquivo**
   - Extraia o `biblioteca-trainee.tar.gz` em uma pasta

2. **Abrir no IntelliJ**
   - IntelliJ → `File` → `Open`
   - Navegue até a pasta `biblioteca-trainee`
   - Selecione a pasta e clique em `OK`

3. **Aguardar indexação**
   - O IntelliJ vai indexar o projeto
   - Espere a barra de progresso no canto inferior direito

### **Opção 2: Importar como projeto Maven**

1. IntelliJ → `File` → `New` → `Project from Existing Sources`
2. Selecione a pasta `biblioteca-trainee`
3. Escolha: **"Import project from external model"**
4. Selecione: **Maven**
5. Clique em `Finish`

---

## ⚙️ Configurar Java 17

### **Verificar versão do Java**

1. `File` → `Project Structure` (ou `Ctrl + Alt + Shift + S`)
2. Em **"Project"**:
   - **SDK**: Java 17 (se não aparecer, clique em `Add SDK` → `Download JDK`)
   - **Language level**: 17
3. Clique em `Apply` e `OK`

### **Se não tiver Java 17:**

1. `File` → `Project Structure` → `SDKs`
2. Clique no `+` → `Download JDK`
3. Selecione:
   - **Version**: 17
   - **Vendor**: Oracle OpenJDK, Amazon Corretto, ou outro
4. Clique em `Download`

---

## 📦 Baixar Dependências Maven

### **Automático:**

O IntelliJ baixa automaticamente quando você abre o projeto.

Verifique no canto inferior direito: **"Maven: Importing..."**

### **Manual (se não baixar automaticamente):**

1. Abra a aba **Maven** (canto direito)
2. Clique no ícone de **"Reload All Maven Projects"** (setas circulares)
3. Aguarde o download das dependências

### **Verificar se baixou:**

No painel Maven, você deve ver:
```
biblioteca-api
├── Lifecycle
├── Plugins
└── Dependencies
    ├── spring-boot-starter-web
    ├── spring-boot-starter-data-jpa
    ├── h2
    ├── lombok
    └── ...
```

---

## 🔧 Habilitar Lombok

O Lombok precisa de um plugin no IntelliJ.

### **Instalar plugin:**

1. `File` → `Settings` (ou `Ctrl + Alt + S`)
2. `Plugins` → Aba `Marketplace`
3. Busque por: **"Lombok"**
4. Clique em `Install`
5. Reinicie o IntelliJ

### **Habilitar annotation processing:**

1. `File` → `Settings` → `Build, Execution, Deployment` → `Compiler` → `Annotation Processors`
2. Marque: ☑️ **"Enable annotation processing"**
3. Clique em `Apply` e `OK`

**Importante:** Sem o Lombok, o código vai dar erro de compilação!

---

## ▶️ Executar o Projeto

### **Método 1: Classe Main**

1. Abra o arquivo: `src/main/java/com/biblioteca/BibliotecaApplication.java`
2. Clique com botão direito no arquivo
3. Selecione: **"Run 'BibliotecaApplication'"**
4. Ou clique no ícone ▶️ verde ao lado da classe

### **Método 2: Maven**

1. Abra a aba **Maven** (canto direito)
2. Expanda: `biblioteca-api` → `Plugins` → `spring-boot`
3. Clique duas vezes em: **`spring-boot:run`**

### **Método 3: Terminal no IntelliJ**

1. Abra o terminal integrado: `View` → `Tool Windows` → `Terminal`
2. Execute:
```bash
mvn spring-boot:run
```

---

## ✅ Verificar se está Rodando

Você deve ver no console:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.0)

========================================
API Biblioteca está rodando!
========================================
API: http://localhost:8080/api/livros
Swagger UI: http://localhost:8080/swagger-ui.html
Console H2: http://localhost:8080/h2-console
========================================
```

---

## 🌐 Testar a API

### **1. No navegador:**

Abra: http://localhost:8080/api/livros

Deve retornar: `[]` (lista vazia)

### **2. No Swagger:**

Abra: http://localhost:8080/swagger-ui.html

Você verá a interface interativa!

### **3. Cadastrar um livro (Swagger):**

1. Clique em **"POST /api/livros"**
2. Clique em **"Try it out"**
3. Cole o JSON:
```json
{
  "titulo": "1984",
  "autor": "George Orwell",
  "ano": 1949,
  "editora": "Companhia das Letras"
}
```
4. Clique em **"Execute"**
5. Veja a resposta com status **201 Created**

---

## 💾 Acessar Banco H2

1. **Abrir console:** http://localhost:8080/h2-console

2. **Configurar conexão:**
   - **JDBC URL**: `jdbc:h2:mem:biblioteca_db`
   - **Username**: `sa`
   - **Password**: *(deixe vazio)*

3. **Clicar em "Connect"**

4. **Ver dados:**
```sql
SELECT * FROM livros;
```

---

## 🛑 Parar a Aplicação

### No IntelliJ:

- Clique no botão ⏹️ vermelho (Stop) no painel "Run"
- Ou pressione: `Ctrl + F2`

---

## 🐛 Resolução de Problemas

### **Erro: "Cannot resolve symbol 'Data'"**

**Problema:** Lombok não está instalado ou habilitado

**Solução:**
1. Instale o plugin Lombok
2. Habilite annotation processing
3. Reinicie o IntelliJ

---

### **Erro: "Port 8080 is already in use"**

**Problema:** Outra aplicação está usando a porta 8080

**Solução 1:** Parar o outro processo
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <número> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

**Solução 2:** Mudar a porta no `application.properties`
```properties
server.port=8081
```

---

### **Erro: Maven não baixa dependências**

**Problema:** Firewall, proxy ou repositório offline

**Solução:**
1. Verificar conexão com internet
2. IntelliJ → `File` → `Settings` → `Build Tools` → `Maven`
3. Marcar: ☑️ **"Work offline"** (desmarcar se estiver marcado)
4. Clicar em **"Reload All Maven Projects"**

---

### **Erro: Java version não compatível**

**Problema:** Projeto configurado para Java 17, mas você tem outra versão

**Solução:**
1. `File` → `Project Structure` → `Project`
2. **SDK**: Selecione Java 17 ou faça download
3. **Language level**: 17

---

### **Erro ao compilar: "package lombok does not exist"**

**Problema:** Dependências Maven não foram baixadas

**Solução:**
1. Abra a aba **Maven** (canto direito)
2. Clique em **"Reload All Maven Projects"** (ícone de setas circulares)
3. Aguarde o download completo

---

## 🎯 Atalhos Úteis do IntelliJ

| Atalho | Ação |
|--------|------|
| `Ctrl + Alt + L` | Formatar código |
| `Ctrl + /` | Comentar/Descomentar linha |
| `Ctrl + Space` | Autocompletar |
| `Ctrl + Click` | Ir para definição |
| `Alt + Enter` | Quick fix / Import |
| `Shift + F10` | Executar |
| `Ctrl + F2` | Parar aplicação |
| `Shift Shift` | Buscar em tudo |

---

## 📝 Estrutura de Pastas no IntelliJ

```
biblioteca-trainee/
├── .idea/                          # Configurações do IntelliJ (ignorar)
├── src/
│   ├── main/
│   │   ├── java/com/biblioteca/    # Código fonte
│   │   └── resources/               # Arquivos de configuração
│   └── test/                        # Testes (vazio por enquanto)
├── target/                          # Arquivos compilados (gerado)
├── pom.xml                          # Dependências Maven
└── README.md                        # Documentação
```

---

## ✅ Checklist Final

Antes de ir para a entrevista, verifique:

- [ ] Projeto abre sem erros no IntelliJ
- [ ] Lombok está instalado e funcionando
- [ ] Aplicação executa sem erros
- [ ] Console mostra "API Biblioteca está rodando!"
- [ ] Swagger abre em http://localhost:8080/swagger-ui.html
- [ ] Consigo cadastrar um livro pelo Swagger
- [ ] H2 Console abre e mostra a tabela livros
- [ ] Consigo listar livros em /api/livros

---

