# ns-users-change-reader

This application reads changes from different topics where the users changes are saved by Kafka Connect. 

The application get the changes and save it in a new topic. 

## Frameworks and librearies

* Quarkus
* Kafka

## Lifecycle

### Local development

This application reads from a Kafka Topic, so the easiest way to have a Kafka running is to use a ```podman``` container with Kafka in standalone mode:

```bash
podman run \
    -it \
    -d \
    --name kafka-zkless \
    -p 9092:9092 \
    -e LOG_DIR=/tmp/logs \
    quay.io/strimzi/kafka:latest-kafka-2.8.1-amd64 \
    /bin/sh -c 'export CLUSTER_ID=$(bin/kafka-storage.sh random-uuid) && bin/kafka-storage.sh format -t $CLUSTER_ID -c config/kraft/server.properties && bin/kafka-server-start.sh config/kraft/server.properties'
```

Create the topic:

```bash
podman exec -it kafka-zkless bash

bin/kafka-topics.sh --create --replication-factor 1 --partitions 1 --bootstrap-server localhost:9092 --topic fr.users.user
bin/kafka-topics.sh --create --replication-factor 1 --partitions 1 --bootstrap-server localhost:9092 --topic pt.users.user
```

Now, you can start the development mode:

```bash
mvn quarkus:dev
```

### Production build

This application have an issue when the data is deserialized that avoid the native compilation, so we're going to compile in a normal way:

```bash
mvn package \
    quarkus:image-build
```

And now, we'll tag the application with the correct version and name:

```bash
podman image tag \
    b0rr3g0/ns-users-change-reader:1.0.0-SNAPSHOT \
    quay.io/dborrego/cdc-ns-users-change-reader:0.5.15
```
