FROM kartoza/postgis
MAINTAINER Jan Boonen <jan.boonen@geodan.nl>

COPY data/railway_stations.sql /opt/railway_stations.sql
RUN echo "local all microservices   md5" > /etc/postgresql/9.3/main/pg_hba.conf
RUN echo "local all all   peer" >> /etc/postgresql/9.3/main/pg_hba.conf
RUN echo "host all all 192.168.0.0/16 md5" >> /etc/postgresql/9.3/main/pg_hba.conf
RUN echo "host all all 0.0.0.0/0 md5" >> /etc/postgresql/9.3/main/pg_hba.conf

# We will run any commands in this when the container starts
COPY init/init-postgis.sh /init-postgis.sh

USER postgres
CMD ["/init-postgis.sh"]
