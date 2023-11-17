# Applications

| Name                   | Type     | Subtype      |  Main technology      | Use case                                                                                                                |
| ---------------------- | -------- | ------------ |  ------------      | ----------------------------------------------------------------------------------------------------------------------- |
| [s-users-pt](s-users-pt/README.md)             | Backend legacy  | Service | Quarkus      | Legacy application which contains the Portugal users                                                                    |
| [s-users-fr](s-users-fr/README.md)             | Backend legacy  | Service  | Quarkus    | Legacy application which contains the Portugal              | backend  | Service      | Legacy application which contains the France users                                                                      |
| [ms-users-camel](ms-users-camel/README.md) | Backend | Microservice | Quarkus + Camel | Application that list all the documents in a MongoDB collection. Exposes a basic REST API with the GET method |
| [front-users-pt](front-users-pt/README.md)         | Frontent | ---          | ReactJS | Portugal users frontend                                                                                                 |
| [front-users-fr](front-users-fr/README.md)         | Frontent | ---          | ReactJS | France users frontend                                                                                                   |
| [front-users](front-users/README.md)            | Frontent | ---          | ReactJS | Frontend where you can see the consolidate data                                                                         |
| [ns-users-camel-reader](ns-users-camel-reader/README.md) | Banckend | Nanoservice | Quarkus + Camel | Application that reads from a topic where KafkaConnect store the data. In addition, the data is consolidated into MongoDB.  |
