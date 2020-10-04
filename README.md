# Event Tracking system

A small application to track the events and log the clients who got times out without any activity for the specified time.Built using Spring Boot for the REST API with H2 for a database and inmemory cache for tracking and messaging system for message persistent .

#### !!! TODO:  FULL TESTS !!!

## Getting Started


### Installing
The Application is configured to profiles, as we have used kafka for messaging persistence and Redis as distributed data store. It is required to install and configure kafka and redis before running the application.

To make it simple, we have configured the application to two profiles (dev and distributed).

With dev profile, we don't need kafka and Redis to be configured.We used HashMap as data store to replace Redis and ActiveMq in place of kafka.   

Make sure to edit your ```application.properties``` and change the database information to reflect where you would like H2 to create the DB file.
It is currently set to the directly from which you are compiling the application.
```sh
spring.datasource.url=jdbc:h2:mem:eventdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
```
To test out this application, you need Maven to build the dependencies.

- First, install the dependencies

```sh
mvn clean install
```
### Running
- We can run the application with 2 profiles
```sh
mvn spring-boot:run -P dev
```

When run with dev profile, it runs without any installations or configurations.
```sh
mvn spring-boot:run -P distributed
```
If distributed profile to be activated, need to satisfy the below pre-requisite.

- Install kafka and configure to default port.
- Install Redis with default port configuration.
 
When the application is first built, it will create a database file in the directory specified in the ```application.properties``` file. 

### Curl Tests

The employee API lives at the route ```/api/v1/employee```. If your application is running on localhost:8080, you would access the API via http://localhost:8080/api/v1/employee.
```json
{
 "firstName" : "first_name",
 "lastName" : "last_name",
 "department" : "department",
 "address" : "address",
 "contactNumber" : "contactnumber"
}
```
To create a new Employee, post a JSON payload to the API endpoint as modeled below:
```curl
POST /api/v1/employee
BODY json
```
Returns: a saved Employee...
Example
```curl
curl -i -H "Content-Type: application/json" -X POST -d '{"firstName" : "first","lastName" : "last","department" : "department","address" : "address","contactNumber" : "contactnumber"}' http://localhost:8080/api/v1/employee
```
Returns:
```json
{
 "id" : 1,
 "firstName" : "first",
  "lastName" : "last",
  "department" : "department",
  "address" : "address",
  "contactNumber" : "contactnumber"
}
```
Get a note using an API call:
```
GET /api/v1/employee
```
Returns: the requested note..
Example:
```curl
curl -i -H "Content-Type: application/json" -X GET http://localhost:8080/api/v1/employee
```
Returns:
```json
[{
 "id" : 1,
 "firstName" : "first",
 "lastName" : "last",
 "department" : "department",
 "address" : "address",
 "contactNumber" : "contactnumber"
}]
```

## Built With


* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/projects/spring-boot) - Quick start Spring Framework web applications
* [H2 Database Engine](https://h2database.com/) - the Java SQL database
* [Apache Kafka](https://kafka.apache.org/) - Message persistence
* [Redis](https://redis.io/) - Distributed data store

