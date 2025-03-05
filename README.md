# HeyFood - APIs com Ktor e Kotlin

HeyFood é um conjunto de microserviços desenvolvidos com [Ktor](https://ktor.io/) e [Kotlin](https://kotlinlang.org/), projetados para gestão de lojas, produtos, serviços, entregadores e clientes. O sistema inclui entrega monitorada e alertas via WhatsApp.

## 📌 Visão Geral
O projeto evoluirá com novas funcionalidades para tornar a solução robusta. Os microserviços operarão dentro de um cluster Kubernetes com suporte a:

- [Kafka](https://kafka.apache.org/) e [RabbitMQ](https://www.rabbitmq.com/) para mensageria e filas.
- [Longhorn](https://longhorn.io/) para armazenamento distribuído.
- [Grafana](https://grafana.com/) e [Prometheus](https://prometheus.io/) para monitoramento e análise de logs.
- [Elastic APM](https://www.elastic.co/apm/) para rastreamento de desempenho.

## 🛠️ Tecnologias Utilizadas

### Backend
- [Kotlin](https://kotlinlang.org/) + [Ktor](https://ktor.io/): Desenvolvimento de APIs performáticas.
- [PostgreSQL](https://www.postgresql.org/): Banco de dados relacional.
- [HikariCP](https://github.com/brettwooldridge/HikariCP): Gerenciamento de conexões.
- [Valiktor](https://valiktor.io/) e [ktor.server.request.validation](https://ktor.io/docs/validation.html): Validação de requisições.

### Arquitetura
O projeto segue uma estrutura modular:
- **Use Cases**: Regras de negócio.
- **Repositories**: Acesso ao banco de dados.
- **Mappers**: Conversão entre entidades e DTOs.
- **DTOs (Data Transfer Objects)**: Requests e responses.

### Infraestrutura
- [Kubernetes](https://kubernetes.io/): Escalabilidade e orquestração.
- [Longhorn](https://longhorn.io/): Armazenamento distribuído.
- [Grafana](https://grafana.com/) + [Prometheus](https://prometheus.io/): Monitoramento e logs.
- [Elastic APM](https://www.elastic.co/apm/): Rastreabilidade de desempenho.
- [RabbitMQ](https://www.rabbitmq.com/): Comunicação assíncrona entre serviços.

## 🚀 Funcionalidades Planejadas
- Cadastro e gerenciamento de **lojas**, **produtos**, **serviços** e **cardápios**.
- Integração com **entregadores** e **clientes**.
- Monitoramento de **entregas em tempo real**.
- **Alertas via WhatsApp** para notificações.

## 🎯 Organização e Contribuição
O código é estruturado para facilitar colaboração e evolução do projeto. Contribuições são bem-vindas!

---
**HeyFood - Transformando a experiência de delivery com tecnologia escalável!**
