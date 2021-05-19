# fuego-quasar-meli
Challenge fuego de quasar MELI

##### Requirements
- Spring Framework and Spring Boot
- Gradle
- Postgres
- Kotlin
- Flyway

## Build

##### LOCAL
Para correr en local 
```-Dspring.profiles.active=local```

######Servicios

- Top secret POST: localhost:8080/communication/topsecret
- Top secret split POST: localhost:8080/communication/topsecret_split/{satellite_name}
- Top secret GET: localhost:8080/communication/topsecret_split

##### DEV

######Servicios

- Top secret POST: https://fuego-quasar-meli.uc.r.appspot.com/communication/topsecret
- Top secret split POST: https://fuego-quasar-meli.uc.r.appspot.com/communication/topsecret_split/{satellite_name}
- Top secret GET: https://fuego-quasar-meli.uc.r.appspot.com/communication/topsecret_split

##### TEST
Para correr las pruebas unitarias correr el comando
```test --tests * jacocoRootReport```

Database

Create a local Postgres database

(THE PORT BY DEFAULT THAT WORK THE SERVICE IS 3000)