# Camel Quarkus and Debezium

This project uses Quarkus, the Supersonic Subatomic Java Framework. If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

The microservice is implemented with Camel extensions for Quarkus.

## Scenario

![Big picture](pictures/diagram.png)


Process flow:

1. The microservice consumes _Debezium_ events from a Kafka topic
   + Events are extracted from a remote SQL database by a Debezium KafkaConnector already deployed and running. Debezium serializes the events using [AVRO](https://avro.apache.org/) and publishes the schemas into the [Apicurio Registry](https://www.apicur.io/registry/) and the events into a Kafka topic.
   + The **Camel Kafka** component consumes the events from the topic and it is also connected to the Apicurio Registry to deserialize the events from AVRO to JSON.

2. The microservice maps the consumed event into a target POJO/entity by using [AtlasMap](https://www.atlasmap.io/)
   + The microservice configuration defines which mapper is used.
   + There are two mappers included in the project, for two different source JSON schemas (`france` and `portugal`):
     + france-to-central-mapping.adm
     + portugal-to-central-mapping.adm
   
     Both of them map the values to the same target POJO (the `central` JSON schema).
   
   + The mapping also applies some basic transformations (trimming spaces and it changes some text to uppercase).

    Example: mapping records from the SQL _France_ table into the POJO

    ![Atlasmap - France schema to POJO mapping](pictures/france-mapping.png)

    Example: mapping records from the SQL _Portugal_ table into the POJO

    ![Atlasmap - Portugal schema to POJO mapping](pictures/portugal-mapping.png)


3. It enriches the POJO with additional data (just adding the last time the document was updated).

4. Depending on the debezium event:
   
   + It creates/inserts a new document into a MongoDB collection persisting the POJO.
   + It updates the previous existing document in MongoDB
   + It deletes the document in MongoDB



## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
mvn compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application
## Creating a native executable

You have to set the new version to build:

```bash
mvn versions:set -DnewVersion=0.2.3
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can directly use it or push it to a container registry.
