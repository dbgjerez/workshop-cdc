# ns-users-change-reader

This application reads changes from different topics where the users changes are saved by Kafka Connect. 

The application get the changes and save it in a new topic. 

## Dev

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_** Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Build

### Native build
I have a problem with a native build regard to the JSON object serialization. 
// TODO: Create a issue and reference it.

### No native build

```shell script
mvn package quarkus:image-build
```

It produces a image with a automatic name. It's important to change the image version and name. We can do it customizing the mvn package properties, but I'm going to do that just tagging the imagen again.

```shell script
podman image tag \
    b0rr3g0/ns-users-change-reader:1.0.0-SNAPSHOT \
    quay.io/dborrego/cdc-ns-users-change-reader:0.2
```