# BUILD
FROM maven:3.8.5-openjdk-17

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn package

# RUN
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=0 /app/target/product_service-0.0.1.jar .

CMD ["java", "-jar", "product_service-0.0.1.jar"]