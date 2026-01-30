# Payment Gateway Simulation (Spring Boot)

A Spring Boot–based payment gateway simulation designed to demonstrate real‑world payment processing concepts including authorization, capture, voids, full and partial refunds, validation, logging, and centralized error handling.

This project was built as a portfolio and learning project with a strong focus on **clean API design**, **layered architecture**, and **production‑style patterns** commonly used in fintech and payment systems.

---

## 🚀 Features

* Transaction authorization
* Capture of authorized transactions
* Void authorized (but not captured) transactions
* Full and partial refunds
* Retrieve transaction details
* Centralized exception handling
* Request validation using Jakarta Bean Validation
* Structured application logging
* Interactive API documentation with Swagger / OpenAPI
* In‑memory H2 database for local testing

---

## 🧱 Architecture Overview

The application follows a layered architecture:

* **Controller layer** – Handles HTTP requests and responses
* **Service layer** – Enforces business rules and transaction state changes
* **Repository layer** – Persists transactions using Spring Data JPA
* **DTOs** – Separate API contracts from persistence entities
* **GlobalExceptionHandler** – Centralized error handling and response formatting

---

## 🔄 Transaction Lifecycle

A transaction flows through the following states:

1. **AUTHORIZED** – Funds are authorized
2. **CAPTURED** – Authorized funds are captured
3. **VOIDED** – Authorized transaction is voided
4. **REFUNDED / PARTIALLY_REFUNDED** – Captured funds are refunded

Business rules are enforced to prevent invalid state transitions.

---

## 📡 API Endpoints

### Authorize

```
POST /payments/authorize
```

Request body:

```json
{
  "amount": 50.00
}
```

---

### Capture

```
POST /payments/capture/{transactionId}
```

---

### Void

```
POST /payments/void/{transactionId}
```

---

### Refund (Full or Partial)

```
POST /payments/refund/{transactionId}
```

Request body:

```json
{
  "amount": 25.00
}
```

---

### Retrieve Transaction

```
GET /transactions/{transactionId}
```

---

## ❗ Error Handling

All errors are returned in a consistent format:

```json
{
  "code": "INVALID_REFUND",
  "message": "Refund exceeds remaining captured amount"
}
```

Handled error scenarios include:

* Transaction not found
* Invalid transaction state
* Invalid refund or capture amounts
* Validation errors
* Unexpected server errors

---

## ✅ Validation

Request validation is handled using Jakarta Bean Validation annotations:

* Required fields (`@NotNull`, `@NotBlank`)
* Positive monetary amounts (`@DecimalMin`)

Validation errors are intercepted globally and returned as structured API errors.

---

## 📋 Logging

Structured logging is implemented across the application:

* Incoming requests
* Successful transaction events
* Business rule violations
* Unhandled exceptions

Logs include transaction identifiers to support traceability and debugging.

---

## 📖 API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

Raw OpenAPI spec:

```
http://localhost:8080/v3/api-docs
```

---

## 🗄️ Database

The application uses an in‑memory H2 database for local development.

H2 Console:

```
http://localhost:8080/h2-console
```

(Default JDBC URL and credentials can be found in `application.properties`.)

---

## ▶️ Running the Application

1. Clone the repository
2. Build and run using Maven:

   ```bash
   mvn spring-boot:run
   ```
3. Access Swagger UI to test endpoints

---

## 🧠 Key Learnings

* Designing payment APIs with clear transaction state management
* Separating DTOs from persistence entities
* Implementing centralized exception handling without polluting business logic
* Enforcing validation at both request and domain levels
* Debugging SpringDoc / Swagger compatibility issues

---

## 🔮 Future Enhancements

* Idempotency keys
* Partial capture support
* Authentication and authorization
* Persistent database (PostgreSQL)
* Integration tests
* Metrics and tracing

---

## 👤 Author

**Kelly Syp**
Software Engineer | Payments | Fintech

This project was built as part of a continued effort to deepen expertise in payment systems and backend API design.

