FROM eclipse-temurin:17 AS builder
WORKDIR workspace
LABEL authors="vbukitag"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} backend.jar
RUN java -Djarmode=layertools -jar backend.jar extract

FROM eclipse-temurin:17
RUN useradd spring
USER spring
WORKDIR workspace
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]