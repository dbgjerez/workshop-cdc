# Applications

| Name                   | Type     | Subtype      | Use case                                                                                                                |
| ---------------------- | -------- | ------------ | ----------------------------------------------------------------------------------------------------------------------- |
| [s-users-pt](s-users-pt/README.md)             | Backend  | Service      | Legacy application which contains the Portugal users                                                                    |
| [s-users-fr](s-users-fr/README.md)             | Backend  | Service      | Legacy application which contains the Portugal              | backend  | Service      | Legacy application which contains the France users                                                                      |
| [ms-users](ms-users/README.md)               | Backend  | Microservice | Microservice with the consolidate data                                                                                  |
| [front-users-pt](front-users-pt/README.md)         | Frontent | ---          | Portugal users frontend                                                                                                 |
| [front-users-fr](front-users-fr/README.md)         | Frontent | ---          | France users frontend                                                                                                   |
| [front-users](front-users/README.md)            | Frontent | ---          | Frontend where you can see the consolidate data                                                                         |
| [ns-users-change-reader](ns-users-change-reader/README.md) | Banckend | Nanoservice  | Application that reads from a topic where KafkaConnect store the data. It's call to ms-users with the consolidated data |
