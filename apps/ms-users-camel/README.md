# CAMEL EXTENSION FOR QUARKUS: MongoDB Front

This is a basic application to list the citizens from the MongoDB collection. It exposes a basic REST API with GET method to retrieve the whole list.

## How to call the API

Example and output:

```bash
$ curl -s http://localhost:8080/api/citizens | jq
[
  {
    "_id": "002233XX",
    "remoteId": 1,
    "firstName": "Name1",
    "lastName": "Last1",
    "enriched": "Document updated at UTC time [2023-11-08T12:16:58.009533750Z] from remote table [msusers.USERS_FRANCE]",
    "email": "mail@provider.pt",
    "phone": "666555666",
    "gender": "male"
  },
  {
    "_id": "292929ZZ",
    "remoteId": 2,
    "firstName": "Name2",
    "lastName": "Last2",
    "enriched": "Document updated at UTC time [2023-11-08T13:34:47.596056636Z] from remote table [msusers.USERS_PORTUGAL]",
    "email": "mymail@provider.pt",
    "phone": null,
    "gender": null
  }
]
```

## MONGO-DB

You can deploy and test a basic MongoDB instance on OpenShift with the following command:

```bash
oc new-app --name mongodb \
   -e MONGO_INITDB_ROOT_USERNAME=root \
   -e MONGO_INITDB_ROOT_PASSWORD=example \
   --image docker.io/library/mongo:latest

```

Connection URL: ```mongodb://root:example@localhost:27017```

* user: root
* password: example
* login-database: admin



## RUNNING the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
export JAVA_HOME=/etc/alternatives/java_sdk_17_openjdk/

./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.



## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.


If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.


## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/mqtt-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.


## Deploying the microservice container image on OpenShift

The image has been built (natively) and is available here: ```quay.io/ryanezil/mongodb-front:latest```

1. Create a target namespace

```bash
oc new-project mongodb-front
```

2. Create secret from env-vars file

Remember to change the configuration values before creating the secret: use your own ones.

```bash
oc create secret generic mongodb-front-configuration --from-env-file=k8s/configuration.env
```

3. Deploy application

```bash
oc apply -f k8s/deployment.yaml
```