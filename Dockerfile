# Usa uma imagem com Java 21 instalado
FROM eclipse-temurin:21-jdk-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o pom.xml e os scripts do Maven
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Dá permissão de execução ao mvnw
RUN chmod +x mvnw

# Baixa as dependências (isso acelera o build)
RUN ./mvnw dependency:go-offline

# Copia o código-fonte e compila
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Define o arquivo gerado (ajuste se o nome for diferente no seu target/)
ARG JAR_FILE=target/Estudiantes-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Define a porta e o comando de execução
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]