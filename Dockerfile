# ESTÁGIO 1: Build (Compilação)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para aproveitar o cache das dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e gera o JAR
COPY src ./src
RUN mvn clean package -DskipTests

# ESTÁGIO 2: Runtime (Execução)
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# O segredo está aqui: copiamos do estágio "build", não da sua máquina
COPY --from=build /app/target/Estudiantes-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]