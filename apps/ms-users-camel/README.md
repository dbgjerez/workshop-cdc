# CAMEL EXTENSION FOR QUARKUS: MongoDB Backend

This is a basic application to list the citizens from the MongoDB collection. It exposes a basic REST API with GET method to retrieve the whole list.

## How to call the API

Example and output:

```bash
$ curl -s http://localhost:8080/users | jq
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

## Creating a native executable

You have to set the new version to build:

```bash
mvn versions:set -DnewVersion=0.1.1
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
mvn package -Dnative -Dquarkus.native.container-build=true
```

You can directly use it or push it to a container registry.

