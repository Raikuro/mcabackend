FROM maven:3.9.6-eclipse-temurin-21 as build
WORKDIR /mcaBackend

COPY ./pom.xml .
COPY ./src src

RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:21-jdk as app

ARG DEPENDENCY="/mcaBackend/target/dependency"

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.mca.McaApplication"]
