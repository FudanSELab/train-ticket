wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
wget https://github.com/alibabacloud-observability/opentelemetry-best-practice/raw/refs/heads/main/opentelemetry-javaagent-extension/ot-java-agent-extension-1.28.0.jar

mv opentelemetry-javaagent.jar ./ts-gateway-service/
mv ot-java-agent-extension-1.28.0.jar ./ts-gateway-service/
