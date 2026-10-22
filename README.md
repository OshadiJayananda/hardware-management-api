# Hardware Management API

Spring Boot REST API for managing hardware brands and units. The service uses PostgreSQL for persistence and listens on port `8081` by default.

## Requirements

- Java 17 or later
- PostgreSQL

## Configure PostgreSQL

Create a database named `hardware_management` in PostgreSQL. The default connection settings are in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hardware_management
spring.datasource.username=postgres
spring.datasource.password=hardware123
```

Change the username, password, or URL there if your local PostgreSQL setup uses different values. Ensure PostgreSQL is running before starting the API.

## Run the API

From the project directory, run the Maven wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux, use:

```bash
export DB_URL="jdbc:postgresql://localhost:5432/hardware_management"
export DB_USERNAME="postgres"
export DB_PASSWORD="your-strong-password"

./mvnw spring-boot:run
```

The API will be available at [http://localhost:8081](http://localhost:8081). Stop it with `Ctrl+C`.

## Build

```powershell
.\mvnw.cmd clean package
```

The executable JAR is created under `target/`. Run it with:

```powershell
java -jar target/hardware-management-api-0.0.1-SNAPSHOT.jar
```
