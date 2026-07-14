# EventApp Backend

Spring Boot REST API for the [EventApp Android client](https://github.com/DyshliukIvan/events-app-frontend). It provides authentication, JWT access/refresh tokens, secured event discovery, PostgreSQL persistence, and deterministic demo data for local development.

## Highlights

- Java 17 and Spring Boot
- Email/password and Google authentication
- Spring Security with JWT access and refresh tokens
- Paginated, date-sorted event API
- PostgreSQL with Spring Data JPA
- Idempotent demo-data seeding
- Isolated H2 test configuration and GitHub Actions CI

## API

```http
POST /api/auth/register
POST /api/auth/login
POST /api/auth/google
POST /api/auth/refresh
GET  /api/auth/me
GET  /api/events
GET  /api/events/{id}
```

## Run locally

Requirements: JDK 17+ and Docker.

```powershell
docker compose up -d
.\gradlew.bat bootRun
```

The API is available at `http://localhost:8080`. The project supports `SPRING_DATASOURCE_*`, `APP_JWT_*`, and `GOOGLE_WEB_CLIENT_ID` environment variables; see `.env.example`.

## Verify

```powershell
.\gradlew.bat test
```
