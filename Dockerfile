# ESTÁGIO 1: Compilação (Onde o Maven cria o arquivo)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o arquivo de configuração e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e gera o arquivo .jar ou .war
COPY src ./src
RUN mvn clean package -DskipTests

# ESTÁGIO 2: Execução
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# O Maven gerou um .war, então buscamos por .war
COPY --from=build /app/target/*.war app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]