# 🏢 HexaOrder B2B Platform

A modern B2B marketplace order management platform built with **Domain-Driven Design (DDD)** and **Hexagonal Architecture**.

## 📋 Overview

HexaOrder B2B is a comprehensive order management system designed for business-to-business marketplace operations. The platform enables professional clients to place orders from multiple vendors while managing the complete order lifecycle including payments, deliveries, and business status tracking.

### 🎯 Key Features

- **Multi-vendor Marketplace**: Manage orders across multiple suppliers
- **Complete Order Lifecycle**: Create, modify, confirm, and track orders
- **Payment Processing**: Handle various payment methods and transactions
- **Delivery Tracking**: Monitor shipment status and logistics
- **Secure Authentication**: OAuth2 integration with Keycloak
- **Modern Tech Stack**: Spring Boot backend + Angular frontend
- **Containerized Deployment**: Docker and Docker Compose ready

## 🏗️ Architecture

The platform is built following **Domain-Driven Design** principles with **Hexagonal Architecture**:

```
┌──────────────────────────────────────────────────────────────┐
│                     Frontend (Angular)                       │
│                  hexa-order-b2b-ui (Port 4200)               │
└───────────────────────────┬──────────────────────────────────┘
                            │ REST API
┌───────────────────────────▼──────────────────────────────────┐
│                   Backend (Spring Boot)                      │
│                hexa-order-b2b-api (Port 8080)                │
│                                                              │
│  ┌────────────────────────────────────────────────────┐      │
│  │              Hexagonal Architecture                │      │
│  │                                                    │      │
│  │  Web Layer → Application → Domain → Infrastructure │      │
│  │  (Controllers)  (Use Cases)  (Entities)  (JPA/DB)  │      │
│  └────────────────────────────────────────────────────┘      │
└──────────────────────────────────────────────────────────────┘
```

## 🧩 Bounded Contexts (DDD)

The system is divided into 5 distinct bounded contexts:

| Context               | Responsibility                                 | Status            |
|-----------------------|------------------------------------------------|-------------------|
| **Identity & Access** | Authentication, authorization, user management | 🚧 Planned        |
| **Catalog**           | Product management, inventory, pricing         | 🚧 Planned        |
| **Order**             | Order lifecycle, validation, status management | ✅ Implemented    |
| **Payment**           | Payment processing, transactions, refunds      | 🚧 Planned        |
| **Delivery**          | Shipping, tracking, logistics                  | 🚧 Planned        |

### Context Interactions

```
Identity & Access ──────────┐
                            │
Catalog ────────────┐       │
                    ▼       ▼
                   Order ◄──┘
                    │
                    ├──────► Payment
                    │
                    └──────► Delivery
```

## 🚀 Quick Start

### Prerequisites

- **Docker** & **Docker Compose**
- **Java 21+** (for local backend development)
- **Node.js 20+** (for local frontend development)
- **Maven 3.9+** (for backend)
- **Angular CLI** (for frontend)

### Start with Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd hexa-order-b2b
   ```

2. **Start all services**
   ```bash
   docker-compose up -d --build
   ```

3. **Access the application**
   - Frontend: http://localhost:4200
   - Backend API: http://localhost:8080
   - API Documentation: http://localhost:8080/swagger-ui/index.html

4. **Stop all services**
   ```bash
   docker-compose down
   ```

### View Logs

```bash
# All services
docker-compose logs -f

# Backend only
docker-compose logs -f backend

# Frontend only
docker-compose logs -f frontend
```

## 📁 Project Structure

```
hexa-order-b2b/
├── hexa-order-b2b-api/              # Backend (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/larbi/hexa_order_b2b/
│   │   │   │       ├── application/      # Use Cases
│   │   │   │       ├── domain/           # Business Logic
│   │   │   │       ├── infrastructure/   # Technical Details
│   │   │   │       └── web/              # REST API
│   │   │   └── resources/
│   │   └── test/
│   ├── Dockerfile
│   ├── pom.xml
│   └── README.md                    # Backend documentation
│
├── hexa-order-b2b-ui/               # Frontend (Angular)
│   ├── src/
│   │   ├── app/                     # Angular components
│   │   ├── assets/
│   │   └── environments/
│   ├── Dockerfile
│   ├── package.json
│   └── README.md                    # Frontend documentation
│
├── docker-compose.yaml              # Docker orchestration
├── .gitignore
└── README.md                        # This file
```

## 🔧 Development

### Backend Development

See detailed instructions in [hexa-order-b2b-api/README.md](./hexa-order-b2b-api/README.md)

**Quick start:**
```bash
cd hexa-order-b2b-api
mvn spring-boot:run
```

**Available at:** http://localhost:8080

### Frontend Development

See detailed instructions in [hexa-order-b2b-ui/README.md](./hexa-order-b2b-ui/README.md)

**Quick start:**
```bash
cd hexa-order-b2b-ui
npm install
npm start
```

**Available at:** http://localhost:4200

## 📚 API Documentation

### Swagger UI
Interactive API documentation available at:
```
http://localhost:8080/swagger-ui/index.html
```

### Key Endpoints

| Method | Endpoint                    | Description        |
|--------|-----------------------------|--------------------|
| `POST` | `/orders`                   | Create a new order |
| `POST` | `/orders/{id}/items`        | Add item to order  |
| `POST` | `/orders/{id}/confirm`      | Confirm order      |
| `GET` | `/orders/{id}`              | Get order details  |
| `GET` | `/orders`                   | List all orders    |

### Example Request

**Create Order:**
```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "550e8400-e29b-41d4-a716-446655440000",
    "items": [
      {
        "productId": "660e8400-e29b-41d4-a716-446655440001",
        "quantity": 2,
        "unitPrice": 100.00
      }
    ]
  }'
```

## 🧪 Testing

### Run All Tests

**Backend:**
```bash
cd hexa-order-b2b-api
mvn test
```

**Frontend:**
```bash
cd hexa-order-b2b-ui
npm test
```

### Integration Tests

**Backend:**
```bash
cd hexa-order-b2b-api
mvn verify
```

**Frontend E2E:**
```bash
cd hexa-order-b2b-ui
ng e2e
```

## 🐳 Docker Configuration

### Services

The `docker-compose.yaml` defines two services:

**Frontend Service:**
- Container: `hexa-order-b2b-ui`
- Port: `4200:4200`
- Volume: Hot-reload enabled
- Network: `hexa-order-b2b-network`

**Backend Service:**
- Container: `hexa-order-b2b-api`
- Port: `8080:8080`
- Database: H2 (in-memory)
- Network: `hexa-order-b2b-network`

### Rebuild Services

```bash
# Rebuild all services
docker-compose build --no-cache

# Rebuild specific service
docker-compose build --no-cache backend
docker-compose build --no-cache frontend
```

## 🌐 Environment Configuration

### Backend Environment Variables

```yaml
SPRING_PROFILES_ACTIVE: dev
SERVER_PORT: 8080
SPRING_DATASOURCE_URL: jdbc:h2:mem:management_db
SPRING_DATASOURCE_USERNAME: sa
SPRING_DATASOURCE_PASSWORD: sa
```

### Frontend Environment Variables

```yaml
CHOKIDAR_USEPOLLING: "true"  # Enable hot-reload in Docker
```

## 🗺️ Roadmap

### ✅ Completed
- [x] Hexagonal Architecture foundation
- [x] Order Context implementation
- [x] RESTful API with Swagger
- [x] Docker containerization
- [x] Angular frontend setup
- [x] Docker Compose orchestration

### 🚧 In Progress
- [ ] Catalog Context (Product management)
- [ ] Payment Context (Payment processing)
- [ ] Delivery Context (Shipping & tracking)
- [ ] Identity & Access Context (OAuth2/Keycloak)

### 📋 Planned
- [ ] Event-driven communication between contexts
- [ ] PostgreSQL production database
- [ ] Frontend-Backend integration
- [ ] CI/CD pipeline (GitHub Actions)
- [ ] Kubernetes deployment
- [ ] Monitoring & Observability (Prometheus, Grafana)
- [ ] API Gateway (Spring Cloud Gateway)

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. **Follow the architecture guidelines:**
   - Keep domain logic pure (no framework dependencies)
   - Use ports and adapters pattern
   - Respect bounded context boundaries
   - Write tests for business rules
   - Document your code

4. **Commit your changes**
   ```bash
   git commit -m 'Add some AmazingFeature'
   ```
5. **Push to the branch**
   ```bash
   git push origin feature/AmazingFeature
   ```
6. **Open a Pull Request**

### Coding Standards

- **Backend**: Follow Java coding conventions, use Spring Boot best practices
- **Frontend**: Follow Angular style guide
- **Tests**: Maintain >80% code coverage
- **Documentation**: Update README files for any new features
- **DDD**: Follow tactical patterns (Entities, Value Objects, Aggregates)

## 📖 Documentation

- [Backend README](./hexa-order-b2b-api/README.md) - Detailed backend documentation
- [Frontend README](./hexa-order-b2b-ui/README.md) - Detailed frontend documentation
- [API Documentation](http://localhost:8080/swagger-ui/index.html) - Interactive API docs

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 4.0.2
- **Language**: Java 21
- **Architecture**: Hexagonal (Ports & Adapters)
- **Database**: H2 (dev), PostgreSQL (prod)
- **API Documentation**: Swagger/OpenAPI
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito

### Frontend
- **Framework**: Angular 21
- **Language**: TypeScript
- **Styling**: CSS/SCSS
- **Build Tool**: Angular CLI
- **Testing**: Jasmine, Karma

### DevOps
- **Containerization**: Docker
- **Orchestration**: Docker Compose
- **CI/CD**: GitHub Actions (planned)
- **Cloud**: Kubernetes (planned)

## 🔐 Security

- OAuth2/OpenID Connect with Keycloak (planned)
- JWT token-based authentication
- Role-based access control (RBAC)
- HTTPS in production
- Input validation and sanitization

## 👥 Authors

- **Larbi AIT EL HADJ** - Lead Developer & Architect
