# 💰 Expense Tracker REST API

A production-ready, clean-architecture RESTful API built with **Spring Boot 3** and **Java 21**. It enables users to track personal expenses, filter by categories and date ranges, and generate monthly spending analytics.

Built purely with **native Java 21 features (Records, Pattern Matching)** and zero third-party boilerplate libraries like Lombok.

---

## 🚀 Key Highlights & Architecture

- **Clean Layered Architecture:** Decoupled `Controller` ➔ `Service` ➔ `Repository` ➔ `Entity` / `DTO`.
- **Pure Java 21 Records:** Used for immutable Data Transfer Objects (`ExpenseRequest`, `ExpenseResponse`, `ExpenseSummaryResponse`, `ErrorResponse`) with zero third-party dependencies.
- **Constructor Injection:** Follows Spring best practices for testability and immutability without `@Autowired` field injection.
- **Robust Bean Validation:** Strict input checking (`@NotBlank`, `@Positive`, `@NotNull`) with field-level error mapping.
- **Centralized Exception Handling:** Uses `@RestControllerAdvice` to translate domain exceptions into standardized, client-friendly error cards with appropriate HTTP status codes.
- **Zero-Setup Database:** Runs out of the box with an in-memory **H2 Database** pre-seeded with sample data via `data.sql`.

---

## 🛠️ Tech Stack

- **Language:** Java 21 (LTS)
- **Framework:** Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Validation)
- **Database:** H2 Database (In-Memory)
- **Build Tool:** Apache Maven
- **Architecture Style:** RESTful API with DTO pattern

---

## 📡 API Endpoints

| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/expenses` | Log a new expense | `201 Created` |
| `GET` | `/api/v1/expenses` | Retrieve all expenses (with optional filters) | `200 OK` |
| `GET` | `/api/v1/expenses/{id}` | Get expense by ID | `200 OK` |
| `PUT` | `/api/v1/expenses/{id}` | Update an existing expense | `200 OK` |
| `DELETE` | `/api/v1/expenses/{id}` | Remove an expense | `204 No Content` |
| `GET` | `/api/v1/expenses/summary` | Monthly spending total & category breakdown | `200 OK` |

---

## 🔍 Filtering Examples

You can filter expenses using query parameters:
- **By Category:** `GET /api/v1/expenses?category=FOOD`
- **By Date Range:** `GET /api/v1/expenses?startDate=2026-11-01&endDate=2026-11-30`
- **Combined:** `GET /api/v1/expenses?category=TRANSPORT&startDate=2026-11-01&endDate=2026-11-30`

---

## 📊 Sample Payloads

### 1. Create Expense (`POST /api/v1/expenses`)
**Request Body:**
```json
{
  "title": "Grocery Shopping",
  "amount": 1500.00,
  "category": "FOOD",
  "expenseDate": "2026-11-17"
}
```

**Response (`201 Created`):**
```json
{
  "id": 1,
  "title": "Grocery Shopping",
  "amount": 1500.00,
  "category": "FOOD",
  "expenseDate": "2026-11-17",
  "createdAt": "2026-11-17T19:20:00"
}
```

### 2. Monthly Summary (`GET /api/v1/expenses/summary?year=2026&month=11`)
**Response (`200 OK`):**
```json
{
  "totalSpent": 6920.00,
  "totalTransactions": 6,
  "categoryBreakdown": {
    "FOOD": 2750.00,
    "UTILITIES": 2200.00,
    "TRANSPORT": 500.00,
    "ENTERTAINMENT": 850.00,
    "HEALTH": 620.00
  }
}
```

### 3. Validation Error Example (`400 Bad Request`)
If invalid data is sent (e.g. empty title or negative amount):
```json
{
  "timestamp": "2026-11-17T19:25:00",
  "status": 400,
  "error": "VALIDATION_FAILED",
  "message": {
    "title": "Title is required",
    "amount": "Amount must be greater than 0"
  }
}
```

---

## 🏃 Running Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/BhagyashreeDev/expense-tracker-api.git
   cd expense-tracker-api
   ```

2. **Run the application:**
   ```bash
   # Windows
   .\mvnw.cmd spring-boot:run

   # Linux/macOS
   ./mvnw spring-boot:run
   ```

3. **Access the H2 Web Console:**
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:expensedb`
   - Username: `sa`
   - Password: *(leave blank)*

---

## 👤 Author
- **Bhagyashree Badadal** - [GitHub](https://github.com/BhagyashreeDev)
