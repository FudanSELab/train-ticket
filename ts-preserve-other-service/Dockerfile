FROM eclipse-temurin:8-jdk-jammy

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/ts-preserve-other-service-1.0.jar /app/
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar .
ENV JAVA_TOOL_OPTIONS "-javaagent:./opentelemetry-javaagent.jar"
CMD ["java", "-Xmx200m",  "-jar", "/app/ts-preserve-other-service-1.0.jar"]

EXPOSE 14569