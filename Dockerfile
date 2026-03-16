# Usa uma imagem do Maven para compilar o projeto
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Usa uma imagem do Tomcat para rodar o sistema
FROM tomcat:10.1-jdk17
# Copia o arquivo .war gerado para a pasta do tomcat
COPY --from=build /target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]