# s-users-pt

## Local development

### MariaDB

```bash
podman run \
    -d  \
    --name mariadb-users  \
    --env MARIADB_USER=user1234 \
    --env MARIADB_PASSWORD=user1234 \
    --env MARIADB_ROOT_PASSWORD=user1234 \
    --env MARIADB_DATABASE=users \
    -p 3306:3306 \
    mariadb:latest
```

### API

#### Create an user

```bash
curl -X POST \
    --data '{ 
                "firstName":"David", 
                "lastName":"Borrego", 
                "dni":"00000000C", 
                "phone":"+34 123 456 789", 
                "gender": "H" 
            }' \
    -H 'Content-Type: application/json' \
    localhost:8080/users
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
mvn quarkus:dev
```

## Native compilation

### Creating a native executable

You can create a native executable using: 

```shell script
mvn package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/s-users-fr-0.1-runner`

### Build a native container

```shell script
podman build --no-cache -f src/main/docker/Dockerfile.native-micro -t quay.io/dborrego/cdc-s-users-pt:0.1 .
```