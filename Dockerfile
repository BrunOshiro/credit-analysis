# Define a imagem base
FROM openjdk:17
WORKDIR /app
COPY target/credit-analysis-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "credit-analysis-0.0.1-SNAPSHOT.jar"]