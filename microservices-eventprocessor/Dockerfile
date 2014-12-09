FROM dgageot/java8
MAINTAINER Jan Boonen <jan.boonen@geodan.nl>

COPY target/microservices-eventprocessor.jar /opt/microservices-eventprocessor.jar
CMD java -jar \
  -Dspring.datasource.url=jdbc:postgresql://${DB_PORT_5432_TCP_ADDR}:${DB_PORT_5432_TCP_PORT}/microservices \
  -Dspring.datasource.username=microservices \
  -Dspring.datasource.password=microservices \
  -Dspring.rabbitmq.host=${RABBITMQ_PORT_5672_TCP_ADDR} \
  -Dspring.boot.admin.url=http://${ADMIN_PORT_8080_TCP_ADDR}:${ADMIN_PORT_8080_TCP_PORT} \
  -Dspring.boot.admin.client.url=http://janb-docker:8082 \
  -Dlogback.configurationFile=/opt/microservices-eventprocessor/logback-custom.xml \
  /opt/microservices-eventprocessor.jar