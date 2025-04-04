#  Student Management System

A Student Management System project created using **Spring Boot**, including JWT-based authentication, role-based access control, and REST APIs for student/course management.

---

## Features

- Admin Login using JWT
- Student validation via student code and DOB
- Add/view students with multiple addresses
- Add/view courses with topics
- Assign courses to students
- Swagger documentation
- In-memory H2 database for testing

---

## Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Security (JWT-based)
- Spring Data JPA (H2)
- Lombok
- Swagger (OpenAPI)

---

##  Setup Instructions

1. **Clone the repo:**

   ```bash
   git clone https://github.com/your-repo/student-management-system.git
   cd student-management-system
   ```

2. **Build the project:**

   ```bash
   ./mvnw clean install
   ```

3. **Run the application:**

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access Swagger UI:**

   ```
   http://localhost:8080/swagger-ui/index.html
   ```

5. **Access H2 Console:**

   ```
   http://localhost:8080/h2-console
   ```

    - JDBC URL: `jdbc:h2:mem:studentdb`
    - Username: `sa`
    - Password: _(leave blank)_

---

## Authentication Flow

### Admin Login
- Endpoint: `POST /api/auth/admin-login`
- Request Body:
  ```json
  {
    "username": "admin",
    "password": "password"
  }
  ```
- Returns: JWT token for authentication

###  Student Login
- Endpoint: `POST /api/auth/student-login`
- Request Body:
  ```json
  {
    "studentCode": "STU123456",
    "dateOfBirth": "2000-01-01"
  }
  ```
- Returns: JWT token for accessing student endpoints

---

##  Sample Admin (Preloaded)
- These admin values are preloaded in the db
```json
{
  "username": "admin",
  "password": "password",
  "role": "ADMIN"
}
```
