FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/cafeteria-codequal-0.0.1-SNAPSHOT.jar"]
