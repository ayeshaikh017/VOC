
# Backend (Spring Boot) - Internship Portal (H2)

## Requirements
- Java 17+
- Maven

## Run
From the `backend` folder:

```bash
mvn clean package
mvn spring-boot:run
```

The backend will start on http://localhost:8080 and expose APIs under /api/**.
H2 console: http://localhost:8080/h2 (jdbc url: jdbc:h2:mem:portaldb)
