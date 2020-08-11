# backend
## setup
+ mysql docker
```bash
docker run --name backend-mysql -p 3307:3306  -e MYSQL_DATABASE=backend -e MYSQL_ROOT_PASSWORD=root -d mysql:latest
```
+ elasticsearch docker
```
docker run --name backend-es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.8.1

./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.8.1/elasticsearch-analysis-ik-7.8.1.zip
```
+ Neo4j docker
```

```
## elasticsearch

+ [配置](https://www.jianshu.com/p/d8b0c736070f)
curl -X POST \
'http://localhost:9200/index/_analyze?pretty=true' \
-H 'Content-Type: application/json' \
-d '{"text":"我们是软件工程师","tokenizer":"ik_max_word"}'

