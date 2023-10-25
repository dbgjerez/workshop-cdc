# front-users-fr

Frontend to show the users. This application need the backend.

## Frameworks and libraries
* ReactJS

## Lifecycle

### Dev

```bash
npm start
```

### Production

This mode generates the static files to execute the applicaiton.

```bash
npm run build
```

### Build container

```bash
SERVICE_NAME=cdc-front-users-fr
SERVICE_VERSION=0.1
podman build \
    -t quay.io/dborrego/$SERVICE_NAME:$SERVICE_VERSION \
    -f Containerfile.run
```

