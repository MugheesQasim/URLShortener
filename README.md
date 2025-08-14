# URL Shortener Service

A URL Shortener service built with Spring Boot and MongoDB. This application allows users to generate short URLs that redirect to original long URLs, similar to TinyURL.

## Table of Contents

* [Technologies Used](#technologies-used)
* [Getting Started](#getting-started)
* [Running the Docker Container](#running-the-docker-container)
* [Engineering](#engineering)

## Technologies Used

* **Programming Language:** Java 21
* **Backend Framework:** Spring Boot
* **Dependency Manager:** Maven
* **Libraries:** Lombok
* **Database:** MongoDB

## Getting Started

### Running the Spring Boot Project

#### Method 1: Running from the Terminal

1. **Using Maven**:

   ```bash
   mvn spring-boot:run
   ```

2. **Using the JAR File**:

   ```bash
   mvn clean package
   java -jar target/your-app-name.jar
   ```

   Replace `your-app-name.jar` with the actual JAR file name.

3. **Access the Application**:

    * Go to `http://localhost:80`

#### Method 2: Running with IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Build the project via **Build > Build Project**.
3. Run the main class annotated with `@SpringBootApplication`.
4. Access the app at `http://localhost:80`.

### Database Setup

#### Prerequisites

* Install MongoDB ([installation guide](https://docs.mongodb.com/manual/installation/)).

#### Starting MongoDB

```bash
brew services start mongodb/brew/mongodb-community
mongo --eval 'db.runCommand({ ping: 1 })'
```

#### Configure Your Application

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/your_database_name
```

Replace `your_database_name` with your DB name.

## Running the Docker Container

Docker compose is added in the project so a single docker compose build command will start a mongodb and a spring boot container

**Build the Docker image**:

```bash
docker compose up --build
```

Trigger the following api to check if the url shortener is up and running:

```bash
curl http://localhost/api/health
```


## Engineering

* **MVC Architecture:** Standard Spring Web MVC for API design.
* **UrlGenerator:** Core backend component responsible for generating unique URLs.
* **Base58 Encoding:** Used for generating short URL codes efficiently.
