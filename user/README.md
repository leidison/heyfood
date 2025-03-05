# Microserviço de Usuário - HeyFood

O microserviço **User** do HeyFood é responsável pelo gerenciamento de usuários. Ele foi desenvolvido
utilizando [Ktor](https://ktor.io/) e [Kotlin](https://kotlinlang.org/), estruturado de forma modular para facilitar a
manutenção e evolução.

- [Documentação do Ktor](https://ktor.io/docs/home.html)
- [Repositório do Ktor no GitHub](https://github.com/ktorio/ktor)
- [Comunidade do Ktor no Slack](https://app.slack.com/client/T09229ZC6/C0A974TJ9) *(é
  necessário [solicitar um convite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) para participar).*

## 📁 Estrutura de Pastas

| Caminho                                                           | Descrição                                                                                                                 |
|-------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| `src/main/resources/application.conf`                             | Arquivo de configuração da aplicação, onde são definidas variáveis como porta do servidor e conexão com o banco de dados. |
| `src/main/kotlin/com/heyfood/database/`                           | Configuração do pool de conexão com o banco de dados PostgreSQL utilizando JDBC + HikariCP.                               |
| `src/main/kotlin/com/heyfood/exceptions/`                         | Define exceções personalizadas para tratamento de erros específicos do serviço.                                           |
| `src/main/kotlin/com/heyfood/models/`                             | Contém as representações das entidades do sistema, como usuários e seus dados associados.                                 |
| `src/main/kotlin/com/heyfood/plugins/`                            | Contém configurações adicionais do Ktor.                                                                                  |
| `src/main/kotlin/com/heyfood/plugins/ContentNegotiationPlugin.kt` | Configuração de serialização e desserialização de dados.                                                                  |
| `src/main/kotlin/com/heyfood/plugins/CorsPlugin.kt`               | Controle de CORS para requisições externas.                                                                               |
| `src/main/kotlin/com/heyfood/plugins/ExceptionPlugin.kt`          | Middleware para tratamento global de exceções.                                                                            |
| `src/main/kotlin/com/heyfood/plugins/PaginationPlugin.kt`         | Implementação de paginação para consultas grandes.                                                                        |
| `src/main/kotlin/com/heyfood/plugins/ValidationPlugin.kt`         | Configuração de validação de requisições usando Valiktor.                                                                 |
| `src/main/kotlin/com/heyfood/repositories/`                       | Implementa a camada de acesso ao banco de dados, centralizando as queries e operações.                                    |
| `src/main/kotlin/com/heyfood/routing/`                            | Define as rotas da API e suas respectivas implementações.                                                                 |
| `src/main/kotlin/com/heyfood/routing/Routing.kt`                  | Configuração das rotas do microserviço.                                                                                   |
| `src/main/kotlin/com/heyfood/routing/UserRoute.kt`                | Definição específica de rotas para operações de usuário.                                                                  |
| `src/main/kotlin/com/heyfood/mappers/`                            | Responsável pela conversão entre entidades do banco e DTOs usados nas requisições e respostas.                            |
| `src/main/kotlin/com/heyfood/requests/`                           | Define os objetos de requisição para entrada de dados, como criação e atualização de usuários.                            |
| `src/main/kotlin/com/heyfood/responses/`                          | Define os objetos de resposta utilizados para retornar informações estruturadas para o cliente.                           |
| `src/main/kotlin/com/heyfood/usecases/`                           | Implementa a lógica de negócio do serviço, organizando as regras de manipulação dos dados.                                |
| `src/main/kotlin/com/heyfood/utils/`                              | Contém funções auxiliares, como validações reutilizáveis.                                                                 |
| `src/main/kotlin/com/heyfood/Application.kt`                      | Ponto de entrada do microserviço, onde as configurações são carregadas e o servidor é inicializado.                       |
| `gradle/`                                                         | Contém os scripts e configurações do Gradle para gerenciamento de dependências e builds.                                  |
| `build.gradle.kts`                                                | Arquivo de configuração do Gradle para definir dependências, plugins e configurações do projeto.                          |

## 🌍 Variáveis de Ambiente

O microserviço usa as seguintes variáveis de ambiente:

| Variável | Descrição                                             |
|----------|-------------------------------------------------------|
| `PORT`   | Porta onde a aplicação será executada.                |
| `DB_URL` | URL de conexão JDBC para o banco de dados PostgreSQL. |

## 🚀 Como Rodar o Serviço

## 📦 Build e Execução

Para construir ou rodar o projeto, utilize um dos seguintes comandos:

| Comando                       | Descrição                                        |
|-------------------------------|--------------------------------------------------|
| `./gradlew test`              | Executa os testes                                |
| `./gradlew build`             | Compila o projeto                                |
| `buildFatJar`                 | Gera um JAR executável com todas as dependências |
| `buildImage`                  | Constrói a imagem Docker para rodar com o JAR    |
| `publishImageToLocalRegistry` | Publica a imagem Docker localmente               |
| `run`                         | Inicia o servidor                                |
| `runDocker`                   | Executa o servidor utilizando a imagem Docker    |

Se o servidor iniciar corretamente, você verá um output semelhante a:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:3000
```

### 🔍 Endpoints Principais

| Método | Endpoint         | Descrição                           |
|--------|------------------|-------------------------------------|
| `POST` | `/api/user`      | Criação de usuários.                |
| `GET`  | `/api/user/{id}` | Consulta de um usuário específico.  |
| `GET`  | `/api/user`      | Listagem paginada de usuários.      |
| `PUT`  | `/api/user/{id}` | Atualização de dados de um usuário. |

## 📌 Contribuição

O código segue uma estrutura modular, facilitando novas implementações. Contribuições são bem-vindas!

---
**HeyFood - Microserviços escaláveis para uma experiência de delivery inteligente!**
