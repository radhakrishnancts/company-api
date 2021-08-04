FROM openjdk:8-jdk-alpine
ADD target/company-api.jar company-api.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","company-api.jar"]