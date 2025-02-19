FROM openjdk:17-jdk
VOLUME /tmp
COPY target/receiptprocessor-0.0.1-SNAPSHOT.jar receiptprocessor.jar
ENV IS_LLM_GENERATED=false
ENTRYPOINT ["java", "-jar", "/receiptprocessor.jar"]
