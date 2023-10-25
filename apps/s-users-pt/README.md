# s-users-pt

## Frameworks and librearies

* Quarkus
* MariaDB

## Lifecycle

### Local development

The application needs a ```MariaDB``` database running to run correctly. So, we'll start a container with MariaDB using ```podman```.

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

Now, we can start the application with the ```Maven``` command:

```bash
mvn quarkus:dev
```

### Production build

We will compile a native image, using ```Maven```:

```bash
mvn package \
    -Pnative \
    -Dquarkus.native.container-build=true
```

After some minutes, we can build the final image:

```bash
podman build \
    --no-cache \
    -f src/main/docker/Dockerfile.native-micro \
    -t quay.io/dborrego/cdc-s-users-pt:0.1 .
```

### API

#### Create an user

```bash
curl -X POST \
    --data '{ 
                "firstName":"David", 
                "lastName":"Borrego", 
                "dni":"00000000C", 
                "email": "dborrego@redhat.com" 
            }' \
    -H 'Content-Type: application/json' \
    localhost:8080/users
```