# AgriTrust: Distributed Agricultural Supply Chain Transparency System

**Course:** CENG 465 - Distributed Computing  
**Project Type:** RESTful Web Service (v1)  
**Date:** December 2025

---

## Project Abstract

The agricultural supply chain in Türkiye currently operates as a "Black Box," creating a severe disconnect between the point of harvest and the point of sale. AgriTrust is a distributed system designed to ensure **"Value Chain Integrity"** by utilizing a **Distributed Event-Log Architecture**.

Unlike centralized systems which act as single points of failure, AgriTrust implements a **Federated Node Architecture** with sharding to ensure high availability and resilience across different geographic regions.

---

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         Docker Network                          │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │  PostgreSQL     │  │  PostgreSQL     │  │    RabbitMQ     │ │
│  │  MASTER         │◄─┤  SLAVE          │  │  Message Broker │ │
│  │  (Port: 5440)   │  │  (Port: 5433)   │  │  (Port: 15672)  │ │
│  └────────┬────────┘  └─────────────────┘  └────────┬────────┘ │
│           │              Replication                 │          │
│           ▼                                          ▼          │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │                 Spring Boot Backend                        │ │
│  │                    (Port: 8080)                            │ │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────────────┐  │ │
│  │  │  Auth   │ │ Product │ │  Event  │ │   Certificate   │  │ │
│  │  │Controller│ │Controller│ │Controller│ │   Controller   │  │ │
│  │  └─────────┘ └─────────┘ └─────────┘ └─────────────────┘  │ │
│  └───────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

---

## Key Features

| Feature | Description |
|---------|-------------|
| **Distributed Architecture** | Federated nodes simulating regional instances |
| **Master-Slave Replication** | PostgreSQL replication for high availability |
| **Message Queue** | RabbitMQ integration to buffer high-volume requests |
| **JWT Authentication** | Secure token-based authentication |
| **Role-Based Access** | ADMIN, PRODUCER, DISTRIBUTOR, AUDITOR, END_CONSUMER |
| **Event Sourcing** | Immutable event log for audit trail |
| **Containerization** | Fully Dockerized environment |

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.2.0 |
| **Database** | PostgreSQL (Bitnami - Master/Slave) |
| **Message Broker** | RabbitMQ 3 |
| **ORM** | Spring Data JPA / Hibernate |
| **Security** | Spring Security + JWT |
| **Build Tool** | Maven |
| **Containerization** | Docker & Docker Compose |

---

## Quick Start

### Prerequisites

- Docker Desktop installed
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/your-repo/AgriTrust.git
cd AgriTrust
```

### 2. Start All Services

```bash
docker-compose up --build -d
```

This will start:
- `agritrust-db-master` - PostgreSQL Master (Port: 5440)
- `agritrust-db-slave` - PostgreSQL Slave (Port: 5433)
- `agritrust-rabbitmq` - RabbitMQ (Ports: 5672, 15672)
- `agritrust-backend` - Spring Boot API (Port: 8080)

### 3. Check Services Status

```bash
docker-compose ps
```

### 4. View Logs

```bash
docker-compose logs -f agritrust-backend
```

### 5. Stop Services

```bash
docker-compose down
```

### 6. Clean Restart (with volume reset)

```bash
docker-compose down -v
docker-compose up --build -d
```

---

## API Endpoints

Base URL: `http://localhost:8080/api`

### Authentication

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/auth/register` | Register new user | No |
| POST | `/auth/login` | User login (returns JWT) | No |
| POST | `/auth/logout` | User logout | Yes |

### Users

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/users` | List all users | Yes |
| GET | `/users/{id}` | Get user by ID | Yes |
| POST | `/users/{id}` | Update user | Yes |
| GET | `/admin/users/{id}` | Delete user (soft) | Yes (Admin) |

### Products

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/product` | Create product batch | Yes (Producer) |
| GET | `/product` | List all products | Yes |
| GET | `/product/{id}` | Get product by ID | Yes |

### Logistics & Events

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/product/logistics/{batchId}` | Record transfer event | Yes |
| GET | `/product/logistics/{batchId}` | Get transfer trace | Yes |

### Certificates

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/product/cert/issue/{id}` | Issue certificate | Yes (Auditor) |
| GET | `/product/cert/revoke/{certNumber}` | Revoke certificate | Yes (Auditor) |
| GET | `/product/cert/{id}` | Get certificates for product | Yes |

---

## User Roles

| Role | Description | Permissions |
|------|-------------|-------------|
| `ADMIN` | System administrator | Full access |
| `PRODUCER` | Farmer/Producer | Create products, record events |
| `DISTRIBUTOR` | Logistics provider | Record transfers |
| `AUDITOR` | Certificate authority | Issue/revoke certificates |
| `END_CONSUMER` | End consumer | View product info |

---

## RabbitMQ Management

Access the RabbitMQ Management UI:

- **URL:** http://localhost:15672
- **Username:** guest
- **Password:** guest

### Queue Information

| Queue Name | Purpose |
|------------|---------|
| `agritrust.event.queue` | Async event processing |

---

## Health & Monitoring

| Endpoint | Description |
|----------|-------------|
| `/api/actuator/health` | Application health status |
| `/api/actuator/info` | Application info |
| `/api/actuator/metrics` | Application metrics |

---

## Project Structure

```
AgriTrust/
├── src/main/java/com/agritrust/
│   ├── config/           # Configuration classes
│   │   ├── SecurityConfig.java
│   │   ├── JwtAuthFilter.java
│   │   └── RabbitMQConfig.java
│   ├── controller/       # REST API controllers
│   ├── dto/              # Data Transfer Objects
│   ├── entity/           # JPA entities
│   ├── enums/            # Enumerations
│   ├── messaging/        # RabbitMQ producer/consumer
│   ├── repos/            # JPA repositories
│   └── service/          # Business logic
├── src/main/resources/
│   └── application.yml   # Application configuration
├── docker-compose.yml    # Docker services definition
├── DockerFile            # Multi-stage Docker build
├── pom.xml               # Maven dependencies
└── README.md
```

---

## Configuration

### Environment Variables (Docker)

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | Database connection URL | `jdbc:postgresql://postgres-master:5432/agritrust_db` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `agri_user` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `agri_pass` |
| `SPRING_RABBITMQ_HOST` | RabbitMQ host | `rabbitmq` |
| `SPRING_RABBITMQ_PORT` | RabbitMQ port | `5672` |

### Local Development

For local development (without Docker), update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5440/agritrust_db
  rabbitmq:
    host: localhost
```

---

## Team

| Name | Role | Responsibilities |
|------|------|------------------|
| **Aleyna Benzer** | Backend Developer | API Design, Core Logic, Authentication |
| **Reyyan Temel** | Infrastructure | Database Design, Containerization, Documentation |

---

## License

This project is developed for educational purposes as part of CENG 465 - Distributed Computing course.
