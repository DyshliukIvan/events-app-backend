# EventApp Backend

Spring Boot backend for EventApp, a mobile event discovery application. The API handles Google authentication, JWT access tokens, and event data for the Android client.

Android frontend repository:

```text
https://github.com/DyshliukIvan/events-app-frontend
```

## Features

- Google ID token verification
- JWT access and refresh tokens
- Email/password register and login endpoints
- Secured event API
- Paginated event response
- PostgreSQL persistence with Spring Data JPA
- Seeded demo events for local development
- Docker Compose PostgreSQL setup

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Gradle
- Docker Compose

## Run Locally

Requirements:

- Java 17+
- Docker

Start PostgreSQL:

```powershell
docker compose up -d
```

Run the backend:

```powershell
.\gradlew.bat bootRun
```

The API runs at:

```text
http://localhost:8080
```

## API Endpoints

```http
POST /api/auth/google
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh
GET  /api/auth/me
GET  /api/events
GET  /api/events/{id}
```

Authenticated event requests require:

```http
Authorization: Bearer <accessToken>
```

## Configuration

Environment variables:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
APP_JWT_SECRET
APP_JWT_EXPIRATION_MS
APP_JWT_REFRESH_EXPIRATION_MS
GOOGLE_WEB_CLIENT_ID
```

See `.env.example` for local development values.

## Tests

```powershell
.\gradlew.bat test
```
