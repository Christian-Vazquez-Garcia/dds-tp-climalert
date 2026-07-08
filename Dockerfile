# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copiar el pom.xml y resolver dependencias primero (cache)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw
# Hacemos esto para cachear las dependencias sin copiar el source aún
RUN ./mvnw dependency:go-offline -B

# Ahora copiamos el código fuente y compilamos
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/climalert-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
