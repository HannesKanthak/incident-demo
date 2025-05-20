# InciDone - Incident Management Tool

InciDone is a small incident management tool. The backend is built with **Kotlin** and **Spring Boot**, while the frontend uses **React**, **Vite** and **TypeScript**. Data is stored in **PostgreSQL** and events are published to **Kafka**.

## Features

- Create and search incidents with filtering and pagination
- Update incident status with history tracking
- REST API with OpenAPI docs
- Kafka events for creation and status changes
- React UI to manage incidents

## Repository Layout

```
Dockerfile            Container build for the Spring Boot app
build.gradle.kts      Gradle build describing backend and frontend
build-for-pi.sh       Helper to build an ARM64 Docker image
docker-compose.yml    Local setup with PostgreSQL and Kafka
frontend/             React application (Vite + TypeScript)
src/                  Kotlin source code (main and tests)
```

## Running Locally

1. Start the database and Kafka using Docker Compose:

```bash
docker-compose up
```

2. In another terminal, build and run the backend:

```bash
./gradlew bootRun
```

The React frontend is built automatically during the Gradle build. While developing the UI you can run it separately:

```bash
cd frontend
npm install
npm run dev
```

The web application will be available on `http://localhost:5173`.

## Tests

Run backend tests with:

```bash
./gradlew test
```

## API Documentation

A more detailed API description with example payloads is available under [`src/README.md`](src/README.md).

## License

This project is provided for demonstration purposes and does not contain production hardening.

