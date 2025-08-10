# Usa una imagen base que ya incluya JDK y Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el pom.xml y descarga las dependencias para aprovechar cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia todo el código fuente
COPY src ./src

# Construye el proyecto (crea el .jar)
RUN mvn clean package -DskipTests

# Segunda etapa: imagen ligera para correr la app
FROM eclipse-temurin:17-jdk-alpine

# Directorio para la app
WORKDIR /app

# Copia el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto que usa Spring Boot (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
