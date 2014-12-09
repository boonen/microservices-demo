FROM dgageot/java8
MAINTAINER Jan Boonen <jan.boonen@geodan.nl>

COPY target/microservices-web.jar /opt/
ENTRYPOINT java -jar \
  -Dspring.rabbitmq.host=${RABBITMQ_PORT_5672_TCP_ADDR} \
  -Dspring.boot.admin.url=http://${ADMIN_PORT_8080_TCP_ADDR}:${ADMIN_PORT_8080_TCP_PORT} \
  -Dspring.boot.admin.client.url=http://janb-docker:8081 \
  /opt/microservices-web.jar