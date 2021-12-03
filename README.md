# Spring-Boot-With-Kafka


  *Command to consume messeges from Kafka topic: bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic kafka_example --from-beginning


  *Command to list out all the topics: bin/kafka-topics.sh --list --bootstrap-server localhost:9092


  *Command to push message to topics (product messages) from terminal: bin/kafka-console-producer.sh --broker-list localhost:9092 --topic kafka_example


  *Command to create topic in kafka: bin/kafka-topics.sh --create --botstrap-server localhost:9092 -replication-factor 1 --partitions 1 --topic topic_name
