docker run -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -v /Users/colin/Workspace/cartisan-learning/elk/elasticsearch/plugins:/usr/share/elasticsearch/plugins -v /Users/colin/Workspace/cartisan-learning/elk/elasticsearch/data:/usr/share/elasticsearch/data  --name elasticsearch elasticsearch:6.4.0


docker run -d -p 5601:5601 --link elasticsearch:es -e "elasticsearch.hosts=http://es:9200" --name kibana kibana:6.4.0


docker run -d -p 4560:4560 --link elasticsearch:es -v /Users/colin/Workspace/cartisan-learning/elk/logstash/logstash-springboot.conf:/usr/share/logstash/pipeline/logstash.conf --name logstash logstash:6.4.0



