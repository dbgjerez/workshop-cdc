build: 
	mvn package quarkus:image-build

image: 
	podman image tag \
    b0rr3g0/ns-users-change-reader:1.0.0-SNAPSHOT \
    quay.io/dborrego/cdc-ns-users-change-reader:0.3