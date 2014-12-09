#!/bin/bash

# This script will run as the postgres user due to the Dockerfile USER directive

DATADIR="/var/lib/postgresql/9.3/main"
CONF="/etc/postgresql/9.3/main/postgresql.conf"
POSTGRES="/usr/lib/postgresql/9.3/bin/postgres"
INITDB="/usr/lib/postgresql/9.3/bin/initdb"
SQLDIR="/usr/share/postgresql/9.3/contrib/postgis-2.1/"

# test if DATADIR is existent
if [ ! -d $DATADIR ]; then
  echo "Creating Postgres data at $DATADIR"
  mkdir -p $DATADIR
fi

# Note that $USERNAME and $PASS below are optional paramters that can be passed
# via docker run e.g.
#docker run --name="postgis" -e USERNAME=qgis -e PASS=qgis -d -v 
#/var/docker-data/postgres-dat:/var/lib/postgresql -t qgis/postgis:6

# If you dont specify a user/password in docker run, we will generate one
# here and create a user called 'docker' to go with it.


# test if DATADIR has content
if [ ! "$(ls -A $DATADIR)" ]; then

  # No content yet - first time pg is being run!
  # Initialise db
  echo "Initializing Postgres Database at $DATADIR"
  #chown -R postgres $DATADIR
  $INITDB $DATADIR
fi

# Make sure we have a user set up
if [ -z "$USERNAME" ]; then
  USERNAME=docker
fi  
if [ -z "$PASS" ]; then
  PASS=docker
fi  
# redirect user/pass into a file so we can echo it into
# docker logs when container starts
# so that we can tell user their password
echo "postgresql user: $USERNAME" > /tmp/PGPASSWORD.txt
echo "postgresql password: $PASS" >> /tmp/PGPASSWORD.txt
$POSTGRES --single -D $DATADIR -c config_file=$CONF <<< "CREATE USER $USERNAME WITH SUPERUSER ENCRYPTED PASSWORD '$PASS';"

trap "echo \"Sending SIGTERM to postgres\"; killall -s SIGTERM postgres" SIGTERM

$POSTGRES -D $DATADIR -c config_file=$CONF &

# Wait for the db to start up before trying to use it....

sleep 10

RESULT=`psql -l | grep postgis | wc -l`
if [[ $RESULT == '1' ]]
then
    echo 'Postgis Already There'
else
    echo "Postgis is missing, installing now"
    # Note the dockerfile must have put the postgis.sql and spatialrefsys.sql scripts into /root/
    # We use template0 since we want t different encoding to template1
    echo "Creating template postgis"
    createdb template_postgis -E UTF8 -T template0
    echo "Enabling template_postgis as a template"
    psql template1 -c "UPDATE pg_database SET datistemplate = TRUE WHERE datname = 'template_postgis';"
    echo "Loading postgis.sql"
    psql template_postgis -f $SQLDIR/postgis.sql
    echo "Loading spatial_ref_sys.sql"
    psql template_postgis -f $SQLDIR/spatial_ref_sys.sql

    # Needed when importing old dumps using e.g ndims for constraints
    echo "Loading legacy sql"
    psql template_postgis -f $SQLDIR/legacy_minimal.sql
    psql template_postgis -f $SQLDIR/legacy_gist.sql
    echo "Granting on geometry columns"
    psql template_postgis -c 'GRANT ALL ON geometry_columns TO PUBLIC;'
    echo "Granting on geography columns"
    psql template_postgis -c 'GRANT ALL ON geography_columns TO PUBLIC;'
    echo "Granting on spatial ref sys"
    psql template_postgis -c 'GRANT ALL ON spatial_ref_sys TO PUBLIC;'
fi

if [ -f ~/db_initialized  ]
  then
    echo "Userdata is already initialized"
  else 
    echo "Userdata is not installed. Now installing"
    export PGPASSWORD=microservices
    createuser -d -s -r -l microservices
    psql postgres -c "ALTER USER microservices WITH ENCRYPTED PASSWORD 'microservices'"
    createdb -O microservices -T template_postgis microservices
    psql -U microservices -f /opt/railway_stations.sql microservices
    touch ~/db_initialized
fi

psql -l microservices

wait $!
