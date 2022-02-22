#multi stage docker build

#base image
FROM openjdk:11-jdk-slim as builder
#setup the working directory
WORKDIR /app
#copy the mvn files from the host to the image directly
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
#give the mvn the executable permission
RUN chmod +x ./mvnw && ./mvnw -B dependency:go-offline
RUN apt update && apt install -y curl
#copy the source code
COPY src src
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


# app
FROM openjdk:11.0.13-jre-slim-buster as stage

#argument
ARG DEPENDENCY=/app/target/dependency
RUN apt update && apt install -y curl
# Copy the dependency application file from builder stage artifact
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app

#expose 8222
EXPOSE 8222

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.classpath.ordermicroservice.OrderMicroserviceApplication"]
