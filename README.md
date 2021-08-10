# springBootAppWithKafka-BH

1. Kafka Setup:
* Download Kafka from https://kafka.apache.org/downloads
* Start Zookeper server
 - > ./zookeeper-server-start.sh ./config/zookeeper.properties
* Start Kafka Server 
 -	> ./kafka-server-start.sh ./config/server.properties


IDE: Eclipse Version: 2020-12 (4.18.0) Java 11

SpringBoot version: 2.5.3

Spring Kafka version: 2.5.3


# application.propeties file define the configuration of the Producer and Consumer

spring.kafka.consumer.bootstrap-servers = localhost:9092

spring.kafka.consumer.group-id= my-springboot-app

spring.kafka.consumer.auto-offset-reset = earliest

spring.kafka.consumer.key-deserializer = org.apache.kafka.common.serialization.StringDeserializer

spring.kafka.consumer.value-deserializer = org.apache.kafka.common.serialization.StringDeserializer




spring.kafka.producer.bootstrap-servers = localhost:9092

spring.kafka.producer.key-serializer = org.apache.kafka.common.serialization.StringSerializer

spring.kafka.producer.value-serializer = org.apache.kafka.common.serialization.StringSerializer

spring.kafka.producer.acks = all



env.timeout = 4s // for the timeout when waiting for response from the consumer
