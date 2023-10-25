# ms-users

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
    quarkus:image-build
```

And now, we'll tag the application with the correct version and name:

```bash
podman image tag \
    b0rr3g0/ms-users:1.0.0-SNAPSHOT \
    quay.io/dborrego/cdc-ms-users:0.2.16
```

