# Builder stage
FROM maven:3.8.8-sapmachine-21 AS builder
WORKDIR /builder

COPY pom.xml .
COPY src ./src
COPY settings.xml /root/.m2/settings.xml

RUN mvn clean package -DskipTests

RUN java -Djarmode=layertools -jar target/*.jar extract

FROM bellsoft/liberica-openjre-debian:21-cds
WORKDIR /application

COPY --from=builder /builder/dependencies/ ./
COPY --from=builder /builder/spring-boot-loader/ ./
COPY --from=builder /builder/snapshot-dependencies/ ./
COPY --from=builder /builder/application/ ./

EXPOSE 8184:8084

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]