# Assignment 4. Microservices (part II)

Assignments 4 will consist on implementing an even driven communication between services and build all the infrastructure patterns
learnt at class. Microservices should work both in the IDE (or the local machine) and with docker containers

We will divide this assignment in two parts (weeks):
1. **First week**: Build the message event driven part. In short, we will keep the communication we already have between composite and 
the rest of the microservices via blocking rest communication for GET (read) operations, and the creation and deletion of products, comments and revisions
   should be done via asynchronous even communication. 
   This week you can also add a discovery service.
   
1. **Second week:** Complete the infrastructure patterns. Namely, edge service, circuit breaker, centralized configuration
and centralized logs (or distributed tracing).

## The base code
You have the assignment 3 completed. It is based on the work of one of your colleagues group.
 
## The Microservices (that you already have)
We will have four microservices. One to manage **products**, another no manage product **reviews**, and a third one to manage 
product **recommendations**. The last one is going to **compose** the product: product + reviews + recommendations

### Product Service
The product service manages product information and describes each product with the following attributes:
* Product ID
* Name
* Description
* Weight

The service will provide methods for:
* List all products
* List a product given its id
* Create a product
* Delete a product given its id

### Review Service 
The review service manages product reviews and stores the following information about each review:
* Product ID
* Review ID
* Author
* Subject
* Content

The service will provide methods for:
* List product's review given its id (of the product)
* Create a review (for a product)
* Delete all product's reviews given its id (of the product)

### Recommendation Service
The recommendation service manages product recommendations and stores the following information about each recommendation:
* Product ID
* Recommendation ID
* Author
* Rate
* Content

The service will provide methods for:
* List product's recommendation given its id (of the product)
* Create a recommendation (for a product)
* Delete all product's recommendations given its id (of the product)

### Product Composite Service
Product composite service
The product composite service aggregates information from the three core services and presents information about a product as follows:
* Product information, as described in the product service
* A list of product reviews for the specified product, as described in the review service
* A list of product recommendations for the specified product, as described in the recommendation service

The service will provide methods for:
* List all product-composites
* List a product-composite given the product id
* Create a product-composite
* Delete a product-compsite given the product id

Note that this service in order to create, delete or list product composites needs to call the other three services.

## The new part
The composite service will keep the communication with the other services via the GET REST calls. 
However, when the composite is asked to delete or create a new product with its reviews and recommendations it will send an event to 
three different channels. One for each microservice, that is, productChannel, recommendationChannel and reviewChannel. See also
that if there are more than one instance of a type of microservice (product, recommendation or review) only one instance should
receive the event. Recall that for this to happen instances should belong to the same consumer group. Remember that you have an
example in https://github.com/DSI-Tecnocampus/SendReceiveRabbitMQ

### Messages
The payload of a message will be an event consisting on:
* The **type** of event, for example, create or delete an event
* A **key**, that identifies the data, for example, a product ID. It will only be used in the deletion of a produt
* A **data** element, that is, the actual data in the event
* A **timestamp**, which describes when the event occurred

The event will be implemented as the class below. Note that the in the class:
* The Event class is a generic class parameterized over the types of its key and data field, K and T.
* The event type is declared as an enumerator with the allowed values, that is, CREATE and DELETE.
* The class defines two constructors, one empty and one that can be used to initialize the type, key, and value members.
* Finally, the class defines getter methods for its member variables.

```Java
public class Event<K, T> {

    public enum Type {CREATE, DELETE}

    private Event.Type eventType;
    private K key;
    private T data;
    private LocalDateTime eventCreatedAt;

    public Event() {
        this.eventType = null;
        this.key = null;
        this.data = null;
        this.eventCreatedAt = null;
    }

    public Event(Type eventType, K key, T data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.eventCreatedAt = now();
    }

    public Type getEventType() {
        return eventType;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getEventCreatedAt() {
        return eventCreatedAt;
    }
}
```
### Message broker: RabbitMQ

As a message broker we'll use the RabbitMQ. You'll need to install it in your local machine (do it!!! You'll also need it
for the practical exam) for running your microservices in your local machine (with no docker containers). And we'll nedd a 
contanized version when working with dockers. You can add the following to your docker-compose.yml file:

```
  rabbitmq:
    image: rabbitmq:3.8.12-management
    mem_limit: 350m
    ports:
      - 5672:5672
      - 15672:15672
    healthcheck:
      test: ["CMD", "rabbitmqctl", "status"]
      interval: 20s
      timeout: 5s
      retries: 10
```

### Infrastructure Patterns

You'll need to implement the following patterns:
* discovery service
* edge service
* circuit breaker
* centralized configuration
* centralized logs (or distributed tracing)

Remember that you have the videos in the e-campus and you have a repository with the example shown in the videos:
https://github.com/DSI-Tecnocampus/microservicesSimpleInfrastructure.git