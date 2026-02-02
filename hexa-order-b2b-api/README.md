# HexaOrder B2B API

A B2B marketplace order management platform built with **Hexagonal Architecture** and **Domain-Driven Design (DDD)** principles using Spring Boot.

## 📋 Table of Contents

- [Overview](#overview)
- [Business Context](#business-context)
- [Architecture](#architecture)
- [Bounded Contexts](#bounded-contexts)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Docker Deployment](#docker-deployment)
- [Contributing](#contributing)

## 🎯 Overview

HexaOrder B2B is a comprehensive order management platform designed for B2B marketplace operations. The platform enables professional clients to place orders from multiple vendors while managing the complete lifecycle including orders, payments, deliveries, and business status tracking.

### Key Features

- **Multi-vendor Support**: Manage orders across multiple suppliers
- **Order Lifecycle Management**: Create, modify, confirm, and track orders
- **Payment Integration**: Handle various payment methods and statuses
- **Delivery Tracking**: Monitor shipment status and logistics
- **Security**: OAuth2 authentication with Keycloak integration
- **RESTful API**: Clean and well-documented endpoints
- **Interactive Documentation**: Swagger UI for API exploration

## 🏢 Business Context

### Platform Concept

The HexaOrder B2B platform is a marketplace where:

- **Professional clients** browse catalogs and place orders
- **Vendors** offer products and manage inventory
- **The system** orchestrates:
  - Order processing and validation
  - Payment transactions
  - Delivery scheduling and tracking
  - Business rule enforcement
  - Status management across workflows

This is an ideal use case for DDD + Hexagonal Architecture because it contains:
- Rich business rules and complex domain logic
- Multiple bounded contexts with clear boundaries
- Well-defined use cases
- Integration points with external systems (payment gateways, shipping providers)

## 🏗️ Architecture

### Hexagonal Architecture (Ports & Adapters)

The project follows hexagonal architecture principles to separate business logic from technical concerns:

```
┌─────────────────────────────────────────────────────────┐
│                     Adapters (Web)                      │
│              Controllers, DTOs, Mappers                 │
└─────────────────────┬───────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────┐
│                 Application Layer                       │
│              Use Cases, Services                        │
└─────────────────────┬───────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────┐
│                  Domain Layer                           │
│      Entities, Value Objects, Domain Services,          │
│         Business Rules, Repository Interfaces           │
└─────────────────────┬───────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────┐
│              Infrastructure Layer                       │
│     JPA, Database, External Services Adapters           │
└─────────────────────────────────────────────────────────┘
```

### Layer Responsibilities

**Domain Layer** (Core Business Logic)
- Entities: `Order`, `OrderItem`, `Money`
- Value Objects: Immutable objects representing domain concepts
- Domain Exceptions: `OrderDomainException`
- Repository Interfaces: `OrderRepository` (port)

**Application Layer** (Use Cases)
- Application Services: `OrderApplicationService`
- Orchestrates domain objects
- Transaction boundaries
- Business workflows

**Infrastructure Layer** (Technical Details)
- JPA implementations
- Database persistence
- External API integrations
- Technical configurations

**Web Layer** (API Interface)
- REST Controllers: `OrderController`
- DTOs: `CreateOrderRequest`, `OrderResponse`
- Mappers: `OrderWebMapper`

## 🧩 Bounded Contexts

The platform is organized into distinct bounded contexts following DDD principles:

### 1. Identity & Access Context
**Responsibility**: User authentication, authorization, and access control

**Key Concepts**:
- User accounts (clients, vendors, administrators)
- Roles and permissions
- OAuth2 tokens
- Session management

**Integration**: Keycloak for OAuth2/OpenID Connect

### 2. Catalog Context
**Responsibility**: Product information and inventory management

**Key Concepts**:
- Products and SKUs
- Categories and attributes
- Pricing rules
- Inventory levels
- Vendor catalogs

**Integration Points**: 
- Order Context (product availability)
- Vendor management

### 3. Order Context ⭐ (Current Implementation)
**Responsibility**: Order lifecycle management

**Key Concepts**:
- Order creation and modification
- Order items and quantities
- Order status transitions (CREATED → CONFIRMED → PROCESSING → COMPLETED)
- Business rule validation

**Entities**:
- `Order`: Aggregate root
- `OrderItem`: Entity within Order aggregate
- `Money`: Value object for monetary amounts

**Use Cases**:
- Create new order
- Add items to order
- Confirm order
- Retrieve order details

### 4. Payment Context
**Responsibility**: Payment processing and financial transactions

**Key Concepts**:
- Payment methods
- Payment status tracking
- Transaction history
- Refunds and chargebacks
- Payment gateway integration

**Integration Points**:
- Order Context (payment confirmation)
- External payment providers

### 5. Delivery Context
**Responsibility**: Shipping and logistics management

**Key Concepts**:
- Delivery addresses
- Shipping methods
- Tracking information
- Delivery status
- Carrier integration

**Integration Points**:
- Order Context (fulfillment)
- External shipping providers

### Context Map

```
┌──────────────────┐
│  Identity &      │
│  Access          │◄──────────────┐
└──────────────────┘               │
                                   │
┌──────────────────┐          ┌────▼─────────────┐
│   Catalog        │◄─────────┤   Order          │
└──────────────────┘          └────┬─────────────┘
                                   │
                              ┌────▼─────────────┐
                              │   Payment        │
                              └────┬─────────────┘
                                   │
                              ┌────▼─────────────┐
                              │   Delivery       │
                              └──────────────────┘
```

## 📦 Prerequisites

- **Java 21+** (JDK 21 or later)
- **Maven 3.9+** for dependency management
- **Docker & Docker Compose** (optional, for containerized deployment)
- **PostgreSQL** or **H2** (in-memory database for development)
- **Keycloak** (for OAuth2 authentication in production)

## 🚀 Installation

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd hexa-order-b2b/hexa-order-b2b-api
   ```

2. **Install dependencies**
   ```bash
   mvn install
   ```

   This command downloads all dependencies defined in `pom.xml` and prepares the application for execution.

## 🏃 Running the Application

### Development Mode

Start the application using Maven:

```bash
mvn spring-boot:run
```

The application will start on port **8080**. Access the API at: `http://localhost:8080`

### Configuration Profiles

The application supports multiple Spring profiles:

- **dev**: Development profile (H2 in-memory database)
- **prod**: Production profile (PostgreSQL)

Set the active profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Or via environment variable:
```bash
export SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run
```

## 📚 API Documentation

### Swagger UI

Interactive API documentation is available through Swagger UI.

**Access Swagger UI**:
```
http://localhost:8080/swagger-ui/index.html
```

### Available Endpoints

#### Order Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/orders` | Create a new order |
| `POST` | `/orders/{id}/items` | Add item to an order |
| `POST` | `/orders/{id}/confirm` | Confirm an order |
| `GET` | `/orders/{id}` | Retrieve order details |
| `GET` | `/orders` | List all orders |

### API Examples

#### 1. Create Order

**Request**:
```http
POST /orders
Content-Type: application/json

{
  "clientId": "550e8400-e29b-41d4-a716-446655440000",
  "items": [
    {
      "productId": "660e8400-e29b-41d4-a716-446655440001",
      "quantity": 2,
      "unitPrice": 100.00
    }
  ]
}
```

**Response**:
```json
{
  "orderId": "770e8400-e29b-41d4-a716-446655440002"
}
```

#### 2. Add Item to Order

**Request**:
```http
POST /orders/770e8400-e29b-41d4-a716-446655440002/items
Content-Type: application/json

{
  "productId": "660e8400-e29b-41d4-a716-446655440003",
  "quantity": 1,
  "unitPrice": 150.00
}
```

**Response**: `200 OK`

#### 3. Confirm Order

**Request**:
```http
POST /orders/770e8400-e29b-41d4-a716-446655440002/confirm
```

**Response**: `200 OK`

#### 4. Get Order Details

**Request**:
```http
GET /orders/770e8400-e29b-41d4-a716-446655440002
```

**Response**:
```json
{
  "orderId": "770e8400-e29b-41d4-a716-446655440002",
  "clientId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "CONFIRMED",
  "total": 350.00,
  "items": [
    {
      "productId": "660e8400-e29b-41d4-a716-446655440001",
      "quantity": 2,
      "unitPrice": 100.00
    },
    {
      "productId": "660e8400-e29b-41d4-a716-446655440003",
      "quantity": 1,
      "unitPrice": 150.00
    }
  ]
}
```

## 📁 Project Structure

```
hexa-order-b2b-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── larbi/
│   │   │           └── hexa_order_b2b/
│   │   │               ├── application/           # Application Layer
│   │   │               │   └── usecase/
│   │   │               │       └── OrderApplicationService.java
│   │   │               ├── domain/                # Domain Layer
│   │   │               │   ├── model/
│   │   │               │   │   ├── Order.java
│   │   │               │   │   ├── OrderItem.java
│   │   │               │   │   └── Money.java
│   │   │               │   ├── exception/
│   │   │               │   │   └── OrderDomainException.java
│   │   │               │   └── repository/
│   │   │               │       └── OrderRepository.java (interface)
│   │   │               ├── infrastructure/        # Infrastructure Layer
│   │   │               │   └── persistence/
│   │   │               │       ├── JpaOrderRepository.java
│   │   │               │       └── OrderEntity.java
│   │   │               └── web/                   # Web Layer
│   │   │                   ├── controller/
│   │   │                   │   └── OrderController.java
│   │   │                   ├── dto/
│   │   │                   │   ├── CreateOrderRequest.java
│   │   │                   │   ├── AddItemRequest.java
│   │   │                   │   └── OrderResponse.java
│   │   │                   └── mapper/
│   │   │                       └── OrderWebMapper.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-dev.properties
│   └── test/
│       └── java/
│           └── com/
│               └── larbi/
│                   └── hexa_order_b2b/
│                       ├── domain/
│                       ├── application/
│                       └── web/
├── Dockerfile
├── pom.xml
└── README.md
```

### Key Components

**OrderController** (`web/controller`)
- Exposes REST endpoints
- Handles HTTP requests/responses
- Delegates to application services
- Validates input

**OrderApplicationService** (`application/usecase`)
- Implements business use cases
- Orchestrates domain operations
- Manages transactions
- Coordinates between repositories and domain

**Order** (`domain/model`)
- Aggregate root
- Encapsulates business rules
- Manages order lifecycle
- Contains OrderItems

**OrderRepository** (`domain/repository`)
- Port (interface) for persistence
- Defines contract for data access
- Implementation-agnostic

**OrderWebMapper** (`web/mapper`)
- Converts domain objects to DTOs
- Translates external data to domain models
- Prevents domain model leakage

## 🧪 Testing

### Run Unit Tests

```bash
mvn test
```

### Run Integration Tests

```bash
mvn verify
```

### Test Coverage

Generate test coverage report:
```bash
mvn clean test jacoco:report
```

View coverage report: `target/site/jacoco/index.html`

### Test Structure

```
src/test/java/
├── domain/              # Domain logic tests
│   ├── OrderTest.java
│   └── MoneyTest.java
├── application/         # Use case tests
│   └── OrderApplicationServiceTest.java
└── web/                 # Integration tests
    └── OrderControllerTest.java
```

## 🐳 Docker Deployment

### Using Docker Compose (Recommended)

The project includes a complete Docker Compose setup for both backend and frontend services.

**Start all services**:
```bash
docker-compose up -d --build
```

**Stop all services**:
```bash
docker-compose down
```

**View logs**:
```bash
docker-compose logs -f backend
```

### Backend Service Configuration

The backend container is configured with:
- **Port**: 8080
- **Database**: H2 in-memory (development)
- **Network**: hexa-order-b2b-network
- **Multi-stage build**: Maven build + JRE runtime

### Using Docker Only

**Build the Docker image**:
```bash
docker build -t hexa-order-b2b-api .
```

**Run the container**:
```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  hexa-order-b2b-api
```

### Production Deployment

For production, update the database configuration:

```yaml
environment:
  SPRING_PROFILES_ACTIVE: prod
  SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/orderdb
  SPRING_DATASOURCE_USERNAME: ${DB_USER}
  SPRING_DATASOURCE_PASSWORD: ${DB_PASSWORD}
```

## 🔧 Configuration

### Database Configuration

**Development (H2)**:
```properties
spring.datasource.url=jdbc:h2:mem:management_db
spring.datasource.username=sa
spring.datasource.password=sa
spring.jpa.hibernate.ddl-auto=create
```

**Production (PostgreSQL)**:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/orderdb
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
```

### Security Configuration (Future Implementation)

```properties
# OAuth2 / Keycloak
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://keycloak:8080/realms/b2b
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://keycloak:8080/realms/b2b/protocol/openid-connect/certs
```

## 🗺️ Roadmap

### Implemented ✅
- [x] Order Context (Create, Add Items, Confirm)
- [x] Hexagonal Architecture foundation
- [x] Domain-Driven Design structure
- [x] RESTful API with Swagger documentation
- [x] Docker containerization
- [x] H2 in-memory database support

### Planned 🚧
- [ ] Catalog Context (Product management)
- [ ] Payment Context (Payment processing)
- [ ] Delivery Context (Shipping management)
- [ ] Identity & Access Context (OAuth2 with Keycloak)
- [ ] Event-driven communication between contexts
- [ ] PostgreSQL production setup
- [ ] Integration tests with Testcontainers
- [ ] CI/CD pipeline
- [ ] Observability (Logging, Metrics, Tracing)

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Follow the architecture guidelines:
   - Keep domain logic pure (no framework dependencies)
   - Use ports (interfaces) for external dependencies
   - Write tests for all business rules
4. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
5. Push to the branch (`git push origin feature/AmazingFeature`)
6. Open a Pull Request

### Coding Standards

- Follow clean code principles
- Write self-documenting code
- Add JavaDoc for public APIs
- Maintain test coverage > 80%
- Follow DDD tactical patterns
- Respect bounded context boundaries

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **Larbi AIT EL HADJ** - Lead Developer & Architect
