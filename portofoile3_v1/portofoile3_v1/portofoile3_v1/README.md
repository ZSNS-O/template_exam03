# Portfolio3 Application

## Overview

This application is a Spring Boot project with a MySQL database.
It includes functionalities to manage universities and their associated course modules.
This README provides instructions on how to set up, run, and test the application.

## Prerequisites

Ensure you have the following installed:

* Docker
* Docker Compose
* Java JDK 17
* Maven 3.6.3 or higher

## Setup

### Building the Docker Image

First, build the application JAR file using Maven:

#### **`mvn clean install`**

Then, build the Docker image:
To start the application along with the MySQL database, run:

#### **`docker-compose up --build`**


## This will start two services:

* mysql: The MySQL database.
* portfolio3: The Spring Boot application.

The application will be accessible at http://localhost:8080.

## Stopping the Application

To stop the application, run:

**`docker-compose down`**



## Application Configuration

The application uses the following environment variables for configuration:

* SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/universitydb
* SPRING_DATASOURCE_USERNAME: root
* SPRING_DATASOURCE_PASSWORD: password
* SPRING_JPA_HIBERNATE_DDL_AUTO: update
* SPRING_JPA_SHOW_SQL: true
* SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT: org.hibernate.dialect.MySQLDialect
* SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL: true


These variables are defined in the **docker-compose.yml** file.


## Running Tests:

Integration tests are located in the src/test/java/de.thws.portfolio3 directory. 

You can run them using Maven:
**`mvn verify`**


## API Endpoints

### University Endpoints

* GET /api/v1/university: Get all universities.
* GET /api/v1/university/{id}: Get a university by ID.
* POST /api/v1/university: Create a new university.
* PUT /api/v1/university/{id}: Update an existing university.
* DELETE /api/v1/university/{id}: Delete a university by ID.
* GET /api/v1/university/search: Search for universities by name.

### Course Module Endpoints

* GET /api/v1/university/{universityId}/coursemodule: Get all course modules for a university.
* GET /api/v1/university/{universityId}/coursemodule/{id}: Get a course module by ID.
* POST /api/v1/university/{universityId}/coursemodule: Create a new course module.
* PUT /api/v1/university/{universityId}/coursemodule/{id}: Update an existing course module.
* DELETE /api/v1/university/{universityId}/coursemodule/{id}: Delete a course module by ID.