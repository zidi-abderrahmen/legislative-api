# Legislative API

A RESTful API for managing **Laws** and **Keywords**, built with **Spring Boot** and **PostgreSQL**.  
This API provides CRUD operations, search capabilities, and structured responses for smooth integration with any frontend (Web, Android, or others).

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Setup](#setup)
- [API Endpoints](#api-endpoints)
- [Laws](#laws)
- [Keywords](#keywords)
- [Error Handling](#error-handling)
- [Contributing](#contributing)

---

## Features

- CRUD operations for **Laws** and **Keywords**
- Many-to-Many relationship between Laws and Keywords
- Full-text search support for titles and texts
- Validation with meaningful error responses
- Structured API responses (DTOs)
- Global exception handling for consistent error messages
- CORS enabled for any frontend integration

---

## Tech Stack

- **Backend:** Java 17, Spring Boot
- **Database:** PostgreSQL
- **JPA / Hibernate** for ORM
- **Validation:** Jakarta Validation (`@Valid`, `@NotBlank`, `@Size`)
- **Mapping:** DTOs with dedicated mappers
- **Build Tool:** Maven

---

## Setup

1. **Clone the repository**
```bash
git clone <repo-url>
cd legislative-api
```

---

## Configure environment variables

1. .env or application.properties
```bash
DB_URL=your_database_url
DB_USER=your_username
DB_PASSWORD=your_password
```

---

## Run the application

1. with maven
```bash
./mvnw spring-boot:run
```

---

## Access the API (Locally)

```bash
http://localhost:8080/api/
```

---

## API Endpoints

1. Laws
| Method | Endpoint                           | Description                      |
| ------ | ---------------------------------- | -------------------------------- |
| GET    | `/api/laws`                        | Get all laws                     |
| GET    | `/api/laws/{id}`                   | Get law by ID                    |
| GET    | `/api/laws/title/{title}`          | Get law by title                 |
| GET    | `/api/laws/search?query=keyword`   | Search laws by text              |
| GET    | `/api/laws/id/{id}/keywords`       | Get keywords of a law by ID      |
| GET    | `/api/laws/title/{title}/keywords` | Get keywords of a law by title   |
| POST   | `/api/laws`                        | Create a new law (`201 Created`) |
| PUT    | `/api/laws/{id}`                   | Update a law                     |
| DELETE | `/api/laws/{id}`                   | Delete a law (`204 No Content`)  |

2. Keywords
| Method | Endpoint                             | Description                          |
| ------ | ------------------------------------ | ------------------------------------ |
| GET    | `/api/keywords`                      | Get all keywords                     |
| GET    | `/api/keywords/{id}`                 | Get keyword by ID                    |
| GET    | `/api/keywords/text/{text}`          | Get keyword by text                  |
| GET    | `/api/keywords/search?query=keyword` | Search keywords                      |
| GET    | `/api/keywords/id/{id}/laws`         | Get laws of a keyword by ID          |
| GET    | `/api/keywords/text/{text}/laws`     | Get laws of a keyword by text        |
| POST   | `/api/keywords`                      | Create a new keyword (`201 Created`) |
| PUT    | `/api/keywords/{id}`                 | Update a keyword                     |
| DELETE | `/api/keywords/{id}`                 | Delete a keyword (`204 No Content`)  |

---

## Error Handling

All errors return structured JSON :
```json
{
  "status": 404,
  "message": "Resource not found",
  "timestamp": 171000000
}
```

Validation errors example :
```json
{
  "title": "must not be blank",
  "description": "must not be blank"
}
```

---

## Contributing

- Follow RESTful principles
- Keep validation consistent in DTOs
- Use services for business logic
- Map entities to DTOs for responses
- Use GlobalExceptionHandler for errors