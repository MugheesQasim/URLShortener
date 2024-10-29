# URL Shortener Service

A URL Shortener service built with Spring Boot and MongoDB. This application allows users to generate short URLs that redirect to original long URLs, similar to TinyURL.

## Table of Contents
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)

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

## Project Structure
