# Sutton Framework and Demo Application

This repository belongs to the module "Foundations of Distributed Systems" at the Technical University of
Applied Sciences Würzburg-Schweinfurt. It contains the Sutton Framework and a demo application that uses the framework.

## Differences between the version used in the videos and this version

This version of Sutton is a bit different from the version used in the videos. The main differences are:

### New version of some libraries

* It uses Java 21 (without any VM arguments to disable the Java module system)
* It uses Tomcat 10 (instead of Tomcat 8)
* It does not use Genson anymore but Jackson for JSON and XML serialization
* It uses Jersey 3 (instead of Jersey 2)
* It uses JUnit 5 (instead of JUnit 4)

### Sutton and Jersey

The Sutton classes are **not** dependent on Jersey anymore. This means that you can use Sutton with any other REST framework. 
We have already successfully tested it with Spring. However, to reduce complexity, we still use Jersey in the demo application.
If you want to use Sutton with Spring, let me know, and I will provide you with an example.

### Changes in the Sutton Framework

We removed the Builder pattern from the `AbstractState` class. Instead, we pass all parameters to the constructor of the `AbstractState` class 
or their subclasses. This makes the code easier to understand and reduces the number of classes and lines of code you have to write. 

In case of GET collection requests, the query parameters and also the paging parameters are wrapped into a class of type `AbstractQuery` (and its subclasses). This
query class also implements how to access the database layer. 

Because Sutton is not dependent on Jersey anymore, the `@InjectLink` should not be used anymore. Instead, use the
`@SuttonLink` annotation which has a very similar interface. See class `PersonModel` for an example.

## How to use the Sutton Framework

The Sutton Framework is a simple framework that helps you to create RESTful web services. The main idea is that every endpoint 
is represented by a class that extends the `AbstractState` class or one of its subclasses.
Each of these `AbstractState` classes implements the outline of the process of handling a request. It makes use of the 
idea of the [Template method design pattern](https://en.wikipedia.org/wiki/Template_method_pattern). Please note, that you 
are working with a very small version of Sutton. In more complex applications, the processes of handling a request is more complex, of course. 

Subclasses only need to implement the abstract methods like `loadModelById` or `defineTransitionLinks`. The benefit of using Sutton is
that you can focus on the business logic of your application and don't have to deal with the technical details of handling HTTP requests. 
In combination with the in-memory storage implementation, you can quickly create a RESTful web service that can be used for testing purposes.

### How to create a new endpoint

As shown in the videos, you first need a service class that is responsible for processing all HTTP requests at a specific URL. In this 
class, you implement methods for handling all kind of HTTP requests (GET, POST, PUT, DELETE). Each of these methods should
delegate the processing of the request to a class that extends `AbstractState`. In the demo application, you can find an example
of such a service class in the `PersonService` class.

Next, you need to create a class that extends `AbstractState` or one of its subclasses. In this class, you implement the abstract methods
like `loadModelById` or `defineTransitionLinks`. In the demo application, you can find an example of such a class in the `GetSinglePerson` class.

### The database layer

The Sutton framework only provides an in-memory storage implementation. This implementation can be used for the implementation of the 
portfolio assignment. However, in a real-world application, you would use a database like MySQL, PostgreSQL, or MongoDB. To make it easier
to switch between different database implementations, we use the [Data Access Object (DAO) pattern](https://en.wikipedia.org/wiki/Data_access_object).
For every resource, you need to provide an interface that extends the `IDatabaseAccessObject` interface. This interface defines the methods
that are needed to access the database. However, it is necessary to add methods for all queries you want to offer. 
In the demo application, you can find an example of such an interface in the `PersonDao` interface. The in-memory storage implementation that is
part of the Sutton framework must then be used as shown in class `PersonStorage`. Here again, you only need to implement the methods for 
queries you want to offer.

It should be added tht you can implement your own database layer by using Hibernate or some other ORM framework. In this case, you would 
use class `DaoFactory` to decide which implementation (in-memory storage or Hibernate) to use.

## How to start the demo application

### Use class `Start`

Execute method `main` in class `Start`. This will start the embedded Tomcat server and deploy the demo application. The demo application
is available at `http://localhost:8080/demo/api`.

### Use Docker

### For manual testing 

Use file `src/main/docker/Dockerfile` as an example how to create a Docker image for the demo application. You first need to build 
the WAR file of the demo application using `mvn package`. Then execute `$> docker build .` (don't forget the point after `build`) to 
create an image. Finally, execute `$> docker run -p 8080:8080 --rm <IMAGE_ID>` to start the container.

### For integration testing

Call `mvn verify` to start the integration tests. This will create a Docker image and start a container for the demo application.  
Then the integration tests will be executed. Finally, the container will be stopped and removed. All integration tests must
be located in the `src/test/java` directory and must end with `IT`. See class `TestDemoAppIT` for an example.

