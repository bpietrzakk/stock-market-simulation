# ===== STAGE 1: build =====
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# copy pom.xml first to cache dependencies — avoids re-downloading on every build
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copy source and build JAR
COPY src ./src
RUN mvn clean package -DskipTests -B

# ===== STAGE 2: runtime =====
# smaller image — only JRE, no Maven or compiler
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# copy only the JAR from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]