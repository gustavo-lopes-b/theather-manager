# Theater manager
This project manage seats for movie sessions.


## Installation
Java: 
openjdk version "17.0.8" 2023-07-18
OpenJDK Runtime Environment Temurin-17.0.8+7 (build 17.0.8+7)
OpenJDK 64-Bit Server VM Temurin-17.0.8+7 (build 17.0.8+7, mixed mode, sharing)

Maven: 3.9.1

Use the package manager maven and run

```bash
mvn clean install
```

## Usage

```bash
java -jar ./target/theater-manager-0.0.1-SNAPSHOT.jar
```
After the software is started, you can run the requests as following:

Create Movie:
```bash
curl --location --request POST 'localhost:8080/movie' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "FIRST MOVIE",
    "description": "THIS MOVIE IS THE FIRST"
}'
```

Create Theater:
```bash
curl --location --request POST 'localhost:8080/theater' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "FIRST THEATER"
}'
```

Create Movie Session:
```bash
curl --location --request POST 'localhost:8080/theater/1/create-session/1' \
--data-raw ''
```

Get Theaters with Movie Session:
```bash
curl --location --request GET 'localhost:8080/theater/by-movie/1'
```

Reserve Seat:
```bash
curl --location --request PUT 'localhost:8080/theater/1/1/reserve-seat/17' \
--header 'Content-Type: application/json' \
--data-raw '{
    "code": "a1",
    "user": "Gustavo"
}'
```

Remember that you need to apply the correct IDs for every step.