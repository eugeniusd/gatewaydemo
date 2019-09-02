# Gatewaydemo Application

Gatewaydemo is an application that acts as a gateway for other services. It uses [Spring Gateway](https://spring.io/projects/spring-cloud-gateway) project in order to implement the routing.
Current implementation allows routing to http://httpbin.org or https://jsonplaceholder.typicode.com 
APIs (depending on the profile used: "httpbin" or "jph").

## Prerequisites
In order to build and run the current application one must have Docker and Maven installed.

## Instalation
The application is built using Maven. The following steps are executed during building:
 * generation of the _gatewaydemo.jar_ file
 * generation of the docker image and adding it to the local docker image registry
 
 In order to build the application run the following command:
```bash
mvn package
``` 
To test that the docker image was created and pushed to local image registry run:
```bash
docker images
```
and make sure that the image was generated.

## Starting the application
In order to execute the application after the build was successfull run the following command:
```bash
docker run -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=httpbin" gatewaydemo
```
This command will start a docker container in which will run the generated image.
It will run the application with the profile "httpbin". This will allow to use only the services from http://httpbin.org.

In order to use the services from https://jsonplaceholder.typicode.com, the application must be started with the "jph" profile:
```bash
docker run -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=jph" gatewaydemo
``` 

## Testing the routes
In order to test that the application is correctly routing, check one of the following requests (assuming the application is deployed on local machine).
* For the _httpbin_ profile
  * _http://localhost:8080/httpbin/anything_
  * _http://localhost:8080/httpbin/headers_

* For the _jph_ profile
  * _http://localhost:8080/jph/todos_
  * _http://localhost:8080/jph/comments_
  * _http://localhost:8080/jph/todos/1_
  
  ## Note
  The X-Request-ID will not be visible when calling any _httpbin_ route, since HTTPbin is deployed on Heroku which has a proxy that strips and/or replaces certain headers see details [here](https://github.com/postmanlabs/httpbin/issues/454)
