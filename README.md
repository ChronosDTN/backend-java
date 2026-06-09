# Chronos DTN — API Principal (Java Spring Boot 3)

> Módulo de API REST principal do gateway financeiro **Chronos DTN**, responsável pelo roteamento de transações financeiras cislunares, autenticação JWT, controle de buffer DTN e exposição dos recursos via arquitetura REST + HATEOAS.

---

## 👥 Integrantes do Grupo

| Nome | RM |
|---|---|
| Charlles Fernandes | — |
| Evellyn Ferreira | — |
| Maicon Douglas | — |

Projeto desenvolvido para a **Global Solution — FIAP 2026**.

---

## 🔗 Links do Projeto

| Item | Link |
|---|---|
| 🚀 **Deploy da API** | https://backend-java-xw8j.onrender.com |
| 📖 **Swagger UI (online)** | https://backend-java-xw8j.onrender.com/swagger-ui/index.html |
| 🎥 **Vídeo de Apresentação** (até 10 min) | Em breve |
| 🎬 **Video Pitch** (até 3 min) | Em breve |

---

## ▶️ Instruções de Execução

Para executar o projeto localmente, é necessário ter o **Java 21 JDK**, o **Apache Maven 3.x** e uma instância do **Oracle Database** em execução.

Após configurar as variáveis de ambiente necessárias, a aplicação pode ser iniciada com o comando:

```bash
mvn spring-boot:run
```

## 🛰️ Sobre o Módulo

Esta API representa o núcleo do sistema de liquidação interplanetário **Chronos DTN**. Ela gerencia o ciclo completo de uma transação cislunar: autenticação do operador via JWT, inserção no buffer DTN, correção relativística do timestamp lunar via stored procedure Oracle, e atualização do ciclo de vida da transação.

O projeto utiliza autenticação **JWT Stateless**, **Spring Security**, **Spring Data JPA** com driver Oracle, **Lombok** para redução de boilerplate e documentação interativa via **SpringDoc OpenAPI (Swagger UI)**.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Função |
|---|---|---|
| Java | 21 LTS | Linguagem principal |
| Spring Boot | 3.2.5 | Framework principal da aplicação |
| Spring Security | 6.x | Autenticação e autorização JWT |
| Spring Data JPA | 3.x | ORM e acesso ao banco de dados |
| Spring HATEOAS | 2.x | Navegabilidade hipermídia dos recursos REST |
| Lombok | 1.18.x | Redução de boilerplate (getters, setters, construtores) |
| JJWT | 0.12.5 | Geração e validação de tokens JWT |
| SpringDoc OpenAPI | 2.5.0 | Documentação interativa Swagger UI |
| Oracle JDBC (ojdbc11) | runtime | Driver oficial de conexão com Oracle DB |
| Spring Boot DevTools | runtime | Produtividade em desenvolvimento (hot reload) |
| Maven | 3.x | Gerenciador de dependências e build |

---

## 📂 Estrutura de Pastas

```text
backend-java/
├── src/
│   └── main/
│       ├── java/br/com/fiap/chronos/
│       │   ├── config/
│       │   │   └── OpenApiConfig.java
│       │   ├── controller/
│       │   │   ├── AuthController.java
│       │   │   └── TransactionController.java
│       │   ├── dto/
│       │   │   ├── AuthRequest.java
│       │   │   ├── AuthResponse.java
│       │   │   ├── StatusUpdateRequest.java
│       │   │   ├── TransactionRequest.java
│       │   │   └── TransactionResponse.java
│       │   ├── exception/
│       │   │   ├── ApiErrorResponse.java
│       │   │   ├── GlobalExceptionHandler.java
│       │   │   ├── ProcedureExecutionException.java
│       │   │   └── TransactionNotFoundException.java
│       │   ├── model/
│       │   │   ├── BaseEntity.java
│       │   │   ├── CislunarNodePair.java
│       │   │   ├── NodeRoute.java
│       │   │   ├── NodeRouteId.java
│       │   │   └── TransactionBuffer.java
│       │   ├── repository/
│       │   │   ├── NodeRouteRepository.java
│       │   │   └── TransactionBufferRepository.java
│       │   ├── security/
│       │   │   ├── JwtFilter.java
│       │   │   ├── JwtUtil.java
│       │   │   └── SecurityConfig.java
│       │   ├── service/
│       │   │   └── TransactionService.java
│       │   └── ChronosApplication.java
│       └── resources/
│           └── application.properties
├── .gitignore
├── README.md
└── pom.xml
```

---

## ▶️ Como Executar

### Pré-requisitos

- [Java 21 JDK](https://adoptium.net/) instalado e configurado no `JAVA_HOME`
- [Apache Maven 3.x](https://maven.apache.org/) instalado
- Oracle Database XE 21c em execução

### 1. Configurar variáveis de ambiente

```bash
# Chave secreta do JWT (mínimo 64 caracteres)
export JWT_SECRET="ChronosDTNInterplanetaryKeySecure2026EncryptionSignatureHashHex"

# Senha do banco Oracle
export CHRONOS_DB_PASSWORD="ChronosSecurePassword2026"

# URL e usuário do banco em produção
export SPRING_DATASOURCE_URL="jdbc:oracle:thin:@localhost:1521/CHRONOS_DB"
export SPRING_DATASOURCE_USERNAME="system"
```

### 2. Compilar e executar

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Acessar a documentação Swagger UI

```text
http://localhost:8080/swagger-ui.html
```

---

## 📡 Endpoints da API

| Método | Endpoint | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/api/auth/token` | Gera Bearer Token JWT para autenticação | Público |
| `POST` | `/api/transactions/sync` | Sincroniza nova transação cislunar e executa correção relativística | 🔒 JWT |
| `GET` | `/api/transactions` | Lista todas as transações (filtro opcional: `?status=PENDING`) | 🔒 JWT |
| `GET` | `/api/transactions/{id}` | Busca uma transação específica pelo ID | 🔒 JWT |
| `PATCH` | `/api/transactions/{id}/status` | Atualiza o status da transação | 🔒 JWT |
| `DELETE` | `/api/transactions/{id}` | Remove permanentemente uma transação do buffer | 🔒 JWT |

---

## 🔐 Autenticação JWT

Todos os endpoints `/api/transactions/**` exigem autenticação via **Bearer Token JWT**.

### 1. Obter o token

```http
POST /api/auth/token
Content-Type: application/json

{
  "username": "operador",
  "password": "Chronos2026!"
}
```

### Resposta esperada

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer"
}
```

### 2. Usar o token nas requisições

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### 3. Usar no Swagger UI

No Swagger UI, clique em **Authorize** 🔒, cole o token no campo `bearerAuth` e confirme. Todas as chamadas seguintes enviarão o token automaticamente.

> **Nota de segurança:** O segredo JWT é lido da variável de ambiente `JWT_SECRET` em produção, nunca hardcoded no código-fonte.

---

## 🏗️ Destaques Arquiteturais

### Modelagem Avançada JPA
- **`BaseEntity` (`@MappedSuperclass`)** — herança JPA com campos de auditoria `createdAt` e `updatedAt`
- **`CislunarNodePair` (`@Embeddable`)** — par origem/destino encapsulado como objeto de valor
- **`NodeRouteId` (`@Embeddable`)** — chave composta usada via `@EmbeddedId`
- **`NodeRoute` (`@Entity`)** — segunda tabela do domínio, demonstrando múltiplas entidades e chave composta

### Segurança
- **`SecurityConfig`** — sessão STATELESS, CSRF desabilitado e CORS configurado
- **`JwtFilter`** — `OncePerRequestFilter` para validação do Bearer Token
- **`JwtUtil`** — geração e validação de JWT com JJWT 0.12.5

### Arquitetura REST
- **`TransactionService`** — camada de serviço com regras de negócio e chamada à stored procedure `SP_CORRIGIR_TEMPO_LUNAR`
- **`GlobalExceptionHandler`** — `@RestControllerAdvice` com respostas padronizadas de erro
- **`HATEOAS`** — endpoints retornam `EntityModel` com links de navegação
- **`OpenApiConfig`** — habilita o botão **Authorize** no Swagger UI com esquema `bearerAuth`

---

## 🧪 Como Testar a API

### 1. Gerar token
Use o endpoint:

```http
POST /api/auth/token
```

### 2. Autorizar no Swagger
- abrir `/swagger-ui.html`
- clicar em **Authorize**
- colar o token JWT

### 3. Testar fluxo principal
1. `POST /api/transactions/sync`
2. `GET /api/transactions`
3. `GET /api/transactions/{id}`
4. `PATCH /api/transactions/{id}/status`
5. `DELETE /api/transactions/{id}`

---

## 🌐 Deploy

Para disponibilizar a API externamente, o caminho recomendado é:

- **API Spring Boot**: Railway ou Render
- **Banco Oracle**: Oracle Cloud Free Tier ou outra instância Oracle acessível externamente

### Variáveis recomendadas para produção

```env
PORT=8080
JWT_SECRET=sua_chave_forte
SPRING_DATASOURCE_URL=jdbc:oracle:thin:@SEU_HOST:1521/SEU_SERVICO
SPRING_DATASOURCE_USERNAME=system
CHRONOS_DB_PASSWORD=sua_senha
SPRING_DATASOURCE_DRIVER_CLASS_NAME=oracle.jdbc.OracleDriver
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.OracleDialect
SPRING_JPA_HIBERNATE_DDL_AUTO=none
```

---

## 🔗 Repositórios do Projeto Chronos DTN

| Módulo | Repositório |
|---|---|
| **backend-java** (este) | [ChronosDTN/backend-java](https://github.com/ChronosDTN/backend-java) |
| **backend-dotnet** | [ChronosDTN/backend-dotnet](https://github.com/ChronosDTN/backend-dotnet) |
| **database** | [ChronosDTN/database](https://github.com/ChronosDTN/database) |
| **devops** | [ChronosDTN/devops](https://github.com/ChronosDTN/devops) |
| **iot-esp32** | [ChronosDTN/iot-esp32](https://github.com/ChronosDTN/iot-esp32) |
| **mobile-app2** | [ChronosDTN/mobile-app2](https://github.com/ChronosDTN/mobile-app2) |