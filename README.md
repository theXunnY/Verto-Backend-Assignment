# Verto-Backend-Assignment

**Backend API for Inventory Management System — ASE Challenge**

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)
![H2 Database](https://img.shields.io/badge/Database-H2-blue)
![Maven](https://img.shields.io/badge/Build-Maven-red)

## 📋 Table of Contents

- [About](#about)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Data Models](#data-models)
- [Request/Response Examples](#requestresponse-examples)
- [Error Handling](#error-handling)
- [Project Structure](#project-structure)
- [Database Configuration](#database-configuration)
- [Testing](#testing)
- [Contributing](#contributing)

---

## 📖 About

This project is a comprehensive **RESTful backend API** for an **Product Management System**, developed as part of the ASE Challenge by Verto. The system enables complete product lifecycle management including creation, modification, stock management, and low-stock monitoring.

The application follows modern Spring Boot practices with clean architecture, proper exception handling, and efficient data transfer objects (DTOs) for API communication.

---

## ✨ Features

- **Complete Product CRUD Operations** - Create, Read, Update, Delete products
- **Advanced Stock Management** - Purchase and sell stock with validation
- **Low Stock Alerts** - Automatic detection and reporting of low-stock products
- **Data Validation** - Comprehensive input validation and business rule enforcement
- **Exception Handling** - Global exception handling with proper HTTP status codes
- **In-Memory Database** - H2 database for quick setup and testing
- **Clean Architecture** - Separation of concerns with DTOs, Services, and Controllers
- **Lombok Integration** - Reduced boilerplate code

---

## 🚀 Tech Stack

- **Backend Framework:** Spring Boot 3.5.6
- **Language:** Java 21
- **Database:** H2 (In-memory)
- **ORM:** Spring Data JPA / Hibernate
- **Build Tool:** Maven
- **Additional Libraries:** 
  - Lombok (Code generation)
  - Spring Boot DevTools (Development productivity)

---

## 🏁 Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.6+**
- **Git**

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/theXunnY/Verto-Backend-Assignment.git
   cd Verto-Backend-Assignment/backendtask
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080/api`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:warehouse_db`
     - Username: `root`
     - Password: `root`

---

## 🔗 API Endpoints

### Product Management

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| **GET** | `/api/product` | Get all products | - | `List<ProductResponseDto>` |
| **POST** | `/api/product` | Create new product | `ProductRequestDto` | `ProductResponseDto` |
| **GET** | `/api/product/{id}` | Get product by ID | - | `ProductResponseDto` |
| **PUT** | `/api/product/{id}` | Update product | `ProductRequestDto` | `ProductResponseDto` |
| **DELETE** | `/api/product/{id}` | Delete product | - | `204 No Content` |

### Stock Operations

| Method | Endpoint | Description | Parameters | Response |
|--------|----------|-------------|------------|----------|
| **POST** | `/api/product/{id}/purchase` | Purchase stock | `stockAmount` (query param) | `ProductResponseDto` |
| **POST** | `/api/product/{id}/sell` | Sell stock | `stockAmount` (query param) | `ProductResponseDto` |

### Alerts & Monitoring

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| **GET** | `/api/product/low-stocks` | Get products with low stock | `List<ProductResponseDto>` |

---

## 📊 Data Models

### Product Entity
```java
{
  "id": Long,
  "name": String,
  "description": String,
  "stockQuantity": Long,
  "lowStockThreshold": Long
}
```

### ProductRequestDto
```java
{
  "name": String,
  "description": String,
  "stockQuantity": Long,
  "lowStockThreshold": Long
}
```

### ProductResponseDto
```java
{
  "id": Long,
  "name": String,
  "description": String,
  "stockQuantity": Long,
  "lowStockThreshold": Long
}
```

---

## 📝 Request/Response Examples

### Create Product
```http
POST /api/product
Content-Type: application/json

{
  "name": "Laptop",
  "description": "High-performance laptop",
  "stockQuantity": 50,
  "lowStockThreshold": 10
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "stockQuantity": 50,
  "lowStockThreshold": 10
}
```

### Purchase Stock
```http
POST /api/product/1/purchase?stockAmount=20
```

**Response:**
```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "stockQuantity": 70,
  "lowStockThreshold": 10
}
```

### Get Low Stock Products
```http
GET /api/product/low-stocks
```

**Response:**
```json
[
  {
    "id": 2,
    "name": "Mouse",
    "description": "Wireless mouse",
    "stockQuantity": 5,
    "lowStockThreshold": 10
  }
]
```

---

## ⚠️ Error Handling

The application includes comprehensive error handling with the following HTTP status codes:

| Status Code | Description | Example Scenarios |
|-------------|-------------|-------------------|
| **200 OK** | Successful operation | Product retrieved, updated |
| **201 Created** | Resource created | Product created successfully |
| **204 No Content** | Successful deletion | Product deleted |
| **400 Bad Request** | Invalid input / Business rule violation | Negative stock amount, Insufficient stock |
| **404 Not Found** | Resource not found | Product ID doesn't exist |
| **500 Internal Server Error** | Server error | Database connection issues |

### Custom Exceptions

- **ProductNotFoundException**: Thrown when product ID doesn't exist
- **InsufficientStockException**: Thrown when trying to sell more stock than available
- **IllegalArgumentException**: Thrown for invalid input parameters

---

## 📁 Project Structure

```
backendtask/
├── src/main/java/com/verto/backendtask/
│   ├── controller/
│   │   └── ProductController.java      # REST API endpoints
│   ├── service/
│   │   └── ProductService.java         # Business logic
│   ├── repository/
│   │   └── ProductRepository.java      # Data access layer
│   ├── model/
│   │   └── Product.java                # JPA entity
│   ├── dto/
│   │   ├── ProductRequestDto.java      # Request DTO
│   │   └── ProductResponseDto.java     # Response DTO
│   ├── mapper/
│   │   └── ProductMapper.java          # DTO-Entity mapping
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java # Global exception handling
│   │   ├── ProductNotFoundException.java
│   │   └── InsufficientStockException.java
│   └── BackendtaskApplication.java     # Main application class
├── src/main/resources/
│   └── application.properties          # Configuration
└── pom.xml                            # Maven dependencies
```

---

## 🗄️ Database Configuration

The application uses **H2 in-memory database** for easy setup and testing:

```properties
# Database Configuration
spring.datasource.url=jdbc:h2:mem:warehouse_db
spring.datasource.username=root
spring.datasource.password=root

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# H2 Console Access
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

**Access H2 Console:** `http://localhost:8080/h2-console`

---

## 🧪 Testing

### Manual Testing with cURL

1. **Create a product:**
   ```bash
   curl -X POST http://localhost:8080/api/product \
     -H "Content-Type: application/json" \
     -d '{"name":"Laptop","description":"Gaming laptop","stockQuantity":25,"lowStockThreshold":5}'
   ```

2. **Get all products:**
   ```bash
   curl http://localhost:8080/api/product
   ```

3. **Purchase stock:**
   ```bash
   curl -X POST "http://localhost:8080/api/product/1/purchase?stockAmount=10"
   ```

4. **Check low stock:**
   ```bash
   curl http://localhost:8080/api/product/low-stocks
   ```

### Running Unit Tests
```bash
mvn test
```

---

## 📄 License

This project is part of the ASE Challenge by Verto. All rights reserved.

---

## 👨‍💻 Developer

**theXunnY** - [GitHub Profile](https://github.com/theXunnY)

---

**Happy Coding! 🚀**



