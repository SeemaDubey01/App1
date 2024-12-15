## This is a Spring Boot application exposing RESTful APIs. 
It demonstrates how to create, run, and interact with a basic Spring Boot REST API. T
he API allows users to interact with resources, like creating, reading, updating, and deleting data.

### Prerequisites
Before running the Spring Boot application, ensure you have the following installed:

Java 17 or later (recommended for compatibility)
Maven 3.6+ (or Gradle, depending on the project setup)
IDE (IntelliJ IDEA, Eclipse, or any editor of your choice)
Download & Install
Download and install Java JDK from OpenJDK or Oracle JDK.
Download and install Maven from Maven's official website.
Cloning the Repository
Clone the repository to your local machine using git:

bash
Copy code
git clone 
Setting Up the Project
Once you have cloned the repository, you need to navigate to the project directory and install dependencies.

Using Maven
Open a terminal in the project directory.
Run the following command to build and run the application:
bash
Copy code
mvn clean install

### Running the Application
Running with Maven:
After building the project, you can run the application using the following command:
bash
Copy code
mvn spring-boot:run
Running with Java:
Alternatively, you can also run the application directly using the java command after building the JAR file:

bash
Copy code
java -jar target/App1-1.0.jar

**Default Port**
By default, the Spring Boot application will run on http://localhost:9000. You can change this by modifying the application.properties or application.yml file.


Since this is an application which is calling another application named App2 so make sure that the App2 is available
and running the default port for App2 is http://localhost:8080

### Security
This is secure rest api
Only three roles passed as part of a property of request header are allowed to access
Key        Value
role       ADMIN|USER|MANAGER

**Authentication**
BasicAuth
UserName        Password
Admin           Admin123
User            User123
Manager         Manager123

**Authorization**
Admin can create/update/get or delete the resource
User can get/update the resource
Action                       URL
Create             http://localhost:9000/employees
Get                http://localhost:9000/employees/{id}
Update             http://localhost:9000/employees/{id}
Delete             http://localhost:9000/employees/{id}


### API Documentation
All rest end points and sample payload structure can be found at
http://localhost:9000/swagger-ui/index.html

