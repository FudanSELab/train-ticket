FROM java:8-jre

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/ts-gateway-service-1.0.jar \
    ./opentelemetry-javaagent.jar \
    ./ot-java-agent-extension-1.28.0.jar \
    /app/

CMD ["java", "-Xmx1024m", "-javaagent:/app/opentelemetry-javaagent.jar", "-Dotel.javaagent.extensions=/app/ot-java-agent-extension-1.28.0.jar", "-jar", "/app/ts-gateway-service-1.0.jar"]

EXPOSE 18888
