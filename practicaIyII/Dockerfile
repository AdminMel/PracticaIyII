# ===== Build stage =====
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copia wrapper y POM primero para aprovechar cache
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Descarga dependencias (sin tests) para cachear
RUN ./mvnw -q -DskipTests dependency:go-offline

# Copia el resto del código
COPY src ./src

# Empaqueta (sin tests)
RUN ./mvnw -q -DskipTests package

# ===== Run stage =====
FROM eclipse-temurin:21-jre AS run
WORKDIR /app

# Copia el jar generado
COPY --from=build /app/target/*.jar app.jar

# Render te asigna un puerto en $PORT. Forzamos Spring a usarlo.
ENV JAVA_OPTS=""
EXPOSE 8080
CMD ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]
