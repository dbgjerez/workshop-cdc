build: 
	mvn package -Pnative -Dquarkus.native.container-build=true

image: 
	podman build \
	--no-cache -f src/main/docker/Dockerfile.native-micro \
	-t quay.io/dborrego/cdc-ns-users-fr-change-reader:0.1.1 .