# Chronos DTN — API Principal (Java Spring Boot 3)

> Módulo de API REST principal do gateway financeiro **Chronos DTN**, responsável pelo roteamento de transações financeiras cislunar, autenticação JWT, controle de buffer DTN e exposição dos endpoints de telemetria. Construído em **Java 21** com **Spring Boot 3.2** e conectado ao Oracle Database XE 21c.

---

## 🛰️ Sobre o Módulo

Esta API representa o núcleo do sistema de liquidação interplanetário **Chronos DTN**. Ela gerencia o ciclo completo de uma transação cislunar: autenticação do operador, inserção no buffer de atraso tolerante, correção relativística do timestamp e liquidação final no ledger.

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
│       │   ├── controller/      # Controllers REST (endpoints públicos e protegidos)
│       │   ├── domain/          # Entidades JPA mapeadas para o Oracle
│       │   ├── dto/             # Data Transfer Objects (request/response)
│       │   ├── repository/      # Interfaces Spring Data JPA
│       │   ├── service/         # Regras de negócio e lógica de aplicação
│       │   ├── security/        # Filtros JWT, UserDetailsService e configuração
│       │   └── ChronosDtnApplication.java  # Ponto de entrada principal
│       └── resources/
│           └── application.properties  # Configurações de datasource e JWT
└── pom.xml                      # Dependências e configuração Maven
```

---

## ▶️ Como Executar

### Pré-requisitos

- [Java 21 JDK](https://adoptium.net/) instalado e configurado no `JAVA_HOME`
- [Apache Maven 3.x](https://maven.apache.org/) instalado
- Oracle Database XE 21c em execução (use o módulo `devops/` com Docker Compose)

### 1. Configurar o datasource

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521/CHRONOS_DB
spring.datasource.username=system
spring.datasource.password=ChronosSecurePassword2026
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
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

## 🔐 Autenticação

A API usa **JWT Bearer Token**. Para acessar endpoints protegidos:

1. Faça `POST /api/auth/login` com credenciais válidas.
2. Copie o token retornado no campo `token`.
3. Inclua no header de todas as requisições protegidas:
   ```
   Authorization: Bearer <seu_token>
   ```

---

## 🔗 Repositórios do Projeto Chronos DTN

| Módulo | Descrição |
|---|---|
| [backend-dotnet](https://github.com/seu-usuario/chronos-backend-dotnet) | API secundária .NET 8 + EF Core |
| [database](https://github.com/seu-usuario/chronos-database) | Scripts Oracle SQL e Procedure PL/SQL |
| [devops](https://github.com/seu-usuario/chronos-devops) | Docker Compose e Dockerfile |
| [iot-esp32](https://github.com/seu-usuario/chronos-iot-esp32) | Firmware C++ Arduino para ESP32 |
| [mobile-app](https://github.com/seu-usuario/chronos-mobile-app) | App React Native com Expo Router |

---

## 👤 Autores

Projeto desenvolvido para a **Global Solution — FIAP 2026**.
