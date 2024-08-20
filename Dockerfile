# Fase 1: Costruzione
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Imposta la directory di lavoro
WORKDIR /app

# Copia il file pom.xml e scarica le dipendenze
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia tutto il resto del progetto
COPY . .

# Costruisci il progetto
RUN mvn clean package -Pprod -DskipTests

# Fase 2: Immagine Finale
FROM eclipse-temurin:21-jdk-alpine

# Espone la porta 8080
EXPOSE 8080

# Copia il jar dalla fase di build
COPY --from=build /app/target/*.jar app.jar

# Comando di avvio
ENTRYPOINT ["java", "-jar", "app.jar"]