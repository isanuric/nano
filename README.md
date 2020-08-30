# nano
Spring Boot, RESTful, Mongodb 


## Quick Start
`$ docker run -d -p 27017:27017 --name mongodb mongo:latest`

`$ mvn clean install`

`$ mvn spring-boot:run`

## Endpoints
#### Get All
`$ curl --header "Content-Type: application/json" 
 --request GET 
 http://localhost:8080/artist/all | json_pp` 
#### Get
`$ curl --header "Content-Type: application/json" 
--request GET 
http://localhost:8080/artist/<uid>` 
#### Add
`$ curl --header "Content-Type: application/json" 
--request POST 
--data '{"firstName" : "xyz", "lastName" : "xyz", "genre":"xyz"}' 
http://localhost:8080/artist/add`