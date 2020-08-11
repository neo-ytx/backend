# backend
## setup docker
+ use docker-compose to setup mysql, elasticsearch, Neo4j
    + configuration in docker-compose.yml
+ run bash
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
## elasticsearch config
### ik analyzer setup
+ elasticsearch setup ik analyze(Notice: change the version in url)
```bash
docker-compose exec es01 elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.8.1/elasticsearch-analysis-ik-7.8.1.zip
docker restart ${container_name}
# Or
docker -it exec ${container_name} bash
./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.8.1/elasticsearch-analysis-ik-7.8.1.zip
```
+ test 
```bash
curl -X PUT http://localhost:9200/index
curl -X POST \
'http://localhost:9200/index/_analyze?pretty=true' \
-H 'Content-Type: application/json' \
-d '{"text":"我们是软件工程师","tokenizer":"ik_max_word"}'
```
### elasticsearch volume config
+ config
```

```
+ data
```bash


```
+
```bash


``` 
### elasticsearch cluster setup
+ [cluster config](https://www.jianshu.com/p/d8b0c736070f)


## Neo4j
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