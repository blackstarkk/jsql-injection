docker build -t mysql \
-f src/test/resources/docker/Dockerfile.mysql .

docker build -t mysql \
-f src/test/resources/docker/Dockerfile.mysql-5.5.40 .

docker build -t postgres \
-f src/test/resources/docker/Dockerfile.postgres .

docker build -t sqlserver \
-f src/test/resources/docker/Dockerfile.sqlserver .