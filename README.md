# URL Shortener Service

A URL Shortener service built with Spring Boot and MongoDB. This application allows users to generate short URLs that redirect to original long URLs, similar to TinyURL.

## Table of Contents
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Engineering](#engineering)

## Technologies Used

- **Programming Language:**     Java 21
- **Backend Framework:**     Spring Boot
- **Dependency Manager:**     Maven
- **Libraries:**     Lombok
- **Database:**     MongoDB

## Getting Started

### Running the Spring Boot Project

#### Method 1: Running from the Terminal
1. **Using Maven**:
   - Navigate to the project root and run:
     ```bash
     mvn spring-boot:run
     ```

2. **Using the JAR File**:
   - Build the JAR file if it’s not already created:
     ```bash
     mvn clean package
     ```
   - Run the application using:
     ```bash
     java -jar target/your-app-name.jar
     ```
   - Replace `your-app-name.jar` with the actual JAR file name generated in the `target` folder.

3. **Access the Application**:
   - Open your browser and go to `http://localhost:80`

#### Method 2: Running with IntelliJ IDEA
1. **Open the Project**:
   - Open IntelliJ IDEA and select **File > Open**.
   - Navigate to your project directory and open it.

2. **Build the Project**:
   - IntelliJ may automatically build the project; otherwise, go to **Build > Build Project** to compile it.

3. **Run the Application**:
   - Locate the main class (annotated with `@SpringBootApplication`) in the `src/main/java` folder.
   - Right-click on the main class file and select **Run 'UrlShortenerApplication'**.

4. **Access the Application**:
   - Once running, you can access the application at `http://localhost:80` (or the specified port in the application.properties file).

### Database Setup

#### Prerequisites

Make sure you have MongoDB installed on your machine. You can follow the instructions for installation [here](https://docs.mongodb.com/manual/installation/).

#### Starting MongoDB

1. **Start the MongoDB server**:
    - If you installed MongoDB via Homebrew, you can start the service using the following command:
      ```bash
      brew services start mongodb/brew/mongodb-community
      ```
    - If you installed MongoDB through another method, refer to the respective instructions for starting the service.

2. **Verify MongoDB is running**:
    - You can verify that MongoDB is running by checking the logs or using the following command:
      ```bash
      mongo --eval 'db.runCommand({ ping: 1 })'
      ```
    - You should see a response indicating that the server is up and running.

#### Configure Your Application

1. **Database Connection**:
    - Ensure your Spring Boot application is configured to connect to MongoDB. You may need to specify the database connection details in the `application.properties` or `application.yml` file:
      ```properties
      spring.data.mongodb.uri=mongodb://localhost:27017/your_database_name
      ```
    - Replace `your_database_name` with the name of the database you want to use for your URL shortener application.

2. **Run the Application**:
    - With the MongoDB server running, you can start your Spring Boot application. Use one of the following methods:
        - **From IntelliJ**:
            - Click the green "Run" button or right-click on your main application class and select "Run".
        - **From Terminal**:
            - Navigate to the root directory of your project and run:
              ```bash
              ./mvnw spring-boot:run
              ```


## Engineering
If you are reading this section, one can safely assume that you are interested in the engineering behind the project.
Below is a summary of the architecture and some engineering principles being used:
- MVC Architecture: standard mvc architecture in spring boot using Spring Web is being used for the api design
- UrlGenerator: url generator service can be considered as the core of the backend due to the main processing being done here
- Base58 Encoding: Base64 or other encoding schemes  