# Chronos DTN — API Principal (Java Spring Boot 3)

> Módulo de API REST principal do gateway financeiro **Chronos DTN**, responsável pelo roteamento de transações financeiras cislunar, autenticação JWT, controle de buffer DTN e exposição dos endpoints de telemetria. Construído em **Java 21** com **Spring Boot 3.2** e conectado ao Oracle Database XE 21c.

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
| 🚀 **Deploy da API** | [https://SEU-DOMINIO.onrender.com](https://SEU-DOMINIO.onrender.com) |
| 📖 **Swagger UI (online)** | [https://SEU-DOMINIO.onrender.com/swagger-ui.html](https://SEU-DOMINIO.onrender.com/swagger-ui.html) |
| 🎥 **Vídeo de Apresentação** (até 10 min) | [Link YouTube / Google Drive]() |
| 🎬 **Video Pitch** (até 3 min) | [Link YouTube / Google Drive]() |

## 🛰️ Sobre o Módulo

Esta API representa o núcleo do sistema de liquidação interplanetário **Chronos DTN**. Ela gerencia o ciclo completo de uma transação cislunar: autenticação do operador, inserção no buffer de atraso tolerante, correção relativística do timestamp e atualização de status via PATCH.

O projeto utiliza autenticação **JWT Stateless**, **Spring Security**, **Spring Data JPA** com driver Oracle e documentação via **SpringDoc OpenAPI (Swagger UI)**.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Função |
|---|---|---|
| Java | 21 LTS | Linguagem principal |
| Spring Boot | 3.2.5 | Framework principal da aplicação |
| Spring Security | 6.x | Autenticação e autorização JWT |
| Spring Data JPA | 3.x | ORM e acesso ao banco de dados |
| Spring HATEOAS | 2.x | Navegabilidade hipermídia dos recursos REST |
| JJWT | 0.12.5 | Geração e validação de tokens JWT |
| SpringDoc OpenAPI | 2.5.0 | Documentação interativa Swagger UI |
| Oracle JDBC (ojdbc11) | runtime | Driver oficial de conexão com Oracle DB |
| Maven | 3.x | Gerenciador de dependências e build |

---

## 📂 Estrutura de Pastas

```
backend-java/
├── src/
│   └── main/
│       ├── java/br/com/fiap/chronos/
│       │   ├── controller/      # TransactionController (endpoints REST + HATEOAS)
│       │   ├── dto/             # TransactionRequest, TransactionResponse, StatusUpdateRequest
│       │   ├── exception/       # GlobalExceptionHandler, TransactionNotFoundException, ApiErrorResponse
│       │   ├── model/           # TransactionBuffer (@Entity) + CislunarNodePair (@Embeddable)
│       │   ├── repository/      # TransactionBufferRepository (Spring Data JPA)
│       │   ├── security/        # JwtFilter, JwtUtil, SecurityConfig
│       │   ├── service/         # TransactionService (regras de negócio)
│       │   └── ChronosApplication.java
│       └── resources/
│           └── application.properties
└── pom.xml
```

---

## ▶️ Como Executar

### Pré-requisitos

- [Java 21 JDK](https://adoptium.net/) instalado e configurado no `JAVA_HOME`
- [Apache Maven 3.x](https://maven.apache.org/) instalado
- Oracle Database XE 21c em execução (use o módulo `devops/` com Docker Compose)

### 1. Configurar variáveis de ambiente

```bash
# Chave secreta do JWT (mínimo 64 caracteres)
export JWT_SECRET="ChronosDTNInterplanetaryKeySecure2026EncryptionSignatureHashHex"

# Senha do banco Oracle (opcional se usar o padrão do devops/)
export CHRONOS_DB_PASSWORD="ChronosSecurePassword2026"
```

### 2. Compilar e executar

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Acessar a documentação Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## 📡 Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/transactions/sync` | Sincroniza nova transação cislunar e executa correção relativística |
| `GET` | `/api/transactions` | Lista todas as transações (filtro opcional: `?status=PENDING`) |
| `GET` | `/api/transactions/{id}` | Busca uma transação específica pelo ID |
| `PATCH` | `/api/transactions/{id}/status` | Atualiza o status: PENDING → SYNCED \| CANCELLED |

---

## 🔐 Autenticação JWT

A API usa **JWT Bearer Token** para autenticação. O token é gerado via `JwtUtil.generateToken()` e injetado no header:

```
Authorization: Bearer <seu_token>
```

> **Nota de segurança:** O secret JWT é lido da variável de ambiente `JWT_SECRET` em produção, nunca hardcoded no código-fonte.

---

## 🏗️ Destaques Arquiteturais

- **`@Embeddable CislunarNodePair`** — par origem/destino encapsulado como objeto de valor JPA na mesma tabela
- **`TransactionService`** — camada de serviço isolando regras de negócio do Controller (SRP)
- **`GlobalExceptionHandler`** — `@RestControllerAdvice` com mapeamento 404/422/400/500
- **`TransactionResponse`** — DTO de resposta Record separando contrato da API da entidade JPA
- **`HATEOAS`** — todos os endpoints retornam `EntityModel` com links de navegação

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
