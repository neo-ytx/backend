
# backend

## 1. Develop and Debug
### Docker Compose environment setup
+ Please install docker and docker-compose first.
+ Sse docker-compose to setup mysql, elasticsearch, Neo4j
    + configuration in docker-compose.yml
+ Run bash
```bash
# create container
docker-compose up -d
# start
docker-compose start
# stop
dcoker-compose stop
# delete
docker-compose rm
```
+ It contains mysql, elasticsearch, Neo4j.
### elasticsearch config
+ Spring dependency
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
    </dependency> 
</dependencies>  
```
#### ik analyzer setup
+ ik analyzer can analyze Chinese text.
+ Elasticsearch setup ik analyze(Notice: change the version in url)
```bash
docker-compose exec elasticsearch elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.8.1/elasticsearch-analysis-ik-7.8.1.zip
docker restart ${container_name}
# Or
docker -it exec ${container_name} bash
./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.8.1/elasticsearch-analysis-ik-7.8.1.zip
```
+ Test ik analyzer.
```bash
curl -X PUT http://localhost:9200/index
curl -X POST \
'http://localhost:9200/index/_analyze?pretty=true' \
-H 'Content-Type: application/json' \
-d '{"text":"我们是软件工程师","tokenizer":"ik_max_word"}'
```
#### elasticsearch volume config
+ config
```
```
+ data
```bash
```
#### elasticsearch cluster setup
+ [cluster config](https://www.jianshu.com/p/d8b0c736070f)

### Neo4j
+ add Neo4j dependency
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-neo4j</artifactId>
    </dependency>
    <dependency>
        <groupId>org.neo4j</groupId>
        <artifactId>neo4j-ogm-http-driver</artifactId>
    </dependency>
</dependencies>
```
### Idea
+ Use Idea to run the project.
    + please add environment first.
    + please run docker-compose first to prepare mysql, es, neo4j.
    + please change save folder location.
```
DATABASE_HOST=localhost;
DATABASE_PORT=3307;
DATABASE_USERNAME=root;
DATABASE_PASSWORD=root;
NEO_USERNAME=neo4j;
NEO_PASSWORD=123456;
NEO_HOST=localhost;
NEO_PORT=7474;
ES_HOST=localhost;
ES_PORT=9200;
ES_USERNAME=elastic;
ES_PASSWORD=123456;
UPLOAD_FOLDER=./uploadFiles/;
API_SWITCH=true;
SERVER_PORT=8080
```
## 2. Compile

### 2.1 Frontend
1. Compile Frontend project:
```bash
yarn build
```
2. Please copy frontend static files to `/src/main/resources/static` 

### 2.2 Backend:Maven
+ Package command
    + Jar is in the `target` folder.
```bash
mvn package
```

### 2.3 Backend:Docker
+ Docker build
    + modify the Dockerfile to customize.
```bash
docker build -t backend -f Dockerfile .
```

## 3. Run
### environment
+ ENV list
```bash
DATABASE_HOST=localhost
DATABASE_PORT=3307
DATABASE_USERNAME=root
DATABASE_PASSWORD=root
NEO_USERNAME=neo4j
NEO_PASSWORD=123456
NEO_HOST=localhost
NEO_PORT=7474
ES_HOST=localhost
ES_PORT=9200
ES_USERNAME=elastic
ES_PASSWORD=123456
UPLOAD_FOLDER=./uploadFiles/
API_SWITCH=true
SERVER_PORT=8080
```
### JAVA Run
+ Use maven to package the project, and then use java command to run it. 
    + it can use `localhost`.
```bash
java -jar \
-DDATABASE_HOST=localhost \
-DDATABASE_PORT=3307 \
-DDATABASE_USERNAME=root \
-DDATABASE_PASSWORD=root \
-DNEO_USERNAME=neo4j \
-DNEO_PASSWORD=123456 \
-DNEO_HOST=localhost \
-DNEO_PORT=7474 \
-DES_HOST=localhost \
-DES_PORT=9200 \
-DES_USERNAME=elastic \
-DES_PASSWORD=123456 \
-DUPLOAD_FOLDER=./uploadFiles/ \
-DAPI_SWITCH=true \
-DSERVER_PORT=8080 \
target/backend-0.0.1-SNAPSHOT.jar
```
### Docker Run
+ Then use docker to build image and use container to run.
    + should set `HOST` in ENV. Don't use `localhost`.
```bash
docker run \
-e "DATABASE_HOST=10.177.35.50" \
-e "DATABASE_PORT=3307" \
-e "DATABASE_USERNAME=root" \
-e "DATABASE_PASSWORD=root" \
-e "NEO_USERNAME=neo4j" \
-e "NEO_PASSWORD=123456" \
-e "NEO_HOST=10.177.35.50" \
-e "NEO_PORT=7474" \
-e "ES_HOST=10.177.35.50" \
-e "ES_PORT=9200" \
-e "ES_USERNAME=elastic" \
-e "ES_PASSWORD=123456" \
-e "UPLOAD_FOLDER=./uploadFiles/" \
-e "API_SWITCH=true" \
-e "SERVER_PORT=8080" \
-p 8080:8080 \
-t backend
```
## Prod run
+ Docker-compose file
    + please setup mysql, es, neo4j first. backend need it.
```
version: "3.2"
services:
  backend:
    image: backend:latest
    environment:
      - "DATABASE_HOST=mysql"
      - "DATABASE_PORT=3306"
      - "DATABASE_USERNAME=root"
      - "DATABASE_PASSWORD=root"
      - "NEO_USERNAME=neo4j"
      - "NEO_PASSWORD=123456"
      - "NEO_HOST=Neo4j"
      - "NEO_PORT=7474"
      - "ES_HOST=elasticsearch"
      - "ES_PORT=9200"
      - "ES_USERNAME=elastic"
      - "ES_PASSWORD=123456"
      - "UPLOAD_FOLDER=./uploadFiles/"
      - "API_SWITCH=true"
      - "SERVER_PORT=8080"
    ports:
      - 8080:8080

  elasticsearch:
    image: elasticsearch:7.8.1
    ports:
      - 9200:9200
      - 9300:9300
    environment:
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - discovery.type=single-node
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - ./docker-vol/elasticsearch/data:/usr/share/elasticsearch/data

  mysql:
    image: mysql:latest
    ports:
      - 3306:3306
    environment:
      - MYSQL_DATABASE=backend
      - MYSQL_ROOT_PASSWORD=root
    volumes:
      - ./docker-vol/mysql/conf/my.cnf:/etc/my.cnf
      - ./docker-vol/mysql/data:/var/lib/mysql

  Neo4j:
    image: neo4j:4.1
    ports:
      - 7474:7474
      - 7687:7687
    volumes:
      - ./docker-vol/neo4j/data:/data
      - ./docker-vol/neo4j/import:/import
```