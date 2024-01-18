FROM eclipse-temurin:21 as jre-build
COPY target/lib /usr/src/lib
COPY target/AccountService-1.0.0.jar /usr/src/
WORKDIR /usr/src/
CMD java -Xmx32m -jar AccountService-1.0.0.jar
