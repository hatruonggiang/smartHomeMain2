# ---------- Build stage ----------
FROM maven:3.9.7-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
RUN ./mvnw -B clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "app.jar"]
