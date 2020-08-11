# backend
## setup
+ mysql docker
```bash
docker run --name backend-mysql -p 3307:3306  -e MYSQL_DATABASE=backend -e MYSQL_ROOT_PASSWORD=root -d mysql:latest
```
+ elasticsearch docker
```
docker run --name backend-es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.8.1
```
+ Neo4j docker
```

```