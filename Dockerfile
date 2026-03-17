# ESTÁGIO 1: Compilação (Onde o Maven cria o arquivo)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o arquivo de configuração e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e gera o arquivo .jar ou .war
COPY src ./src
RUN mvn clean package -DskipTests

# ESTÁGIO 2: Execução (Onde o sistema realmente roda)
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# O segredo: copiamos o arquivo gerado no ESTÁGIO 1 (build) para cá
# O Maven do estágio anterior sempre coloca o resultado em /app/target/
COPY --from=build /app/target/*.jar app.jar

# Se o seu projeto gera um .war em vez de .jar, use a linha abaixo:
# COPY --from=build /app/target/*.war app.jar

EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]