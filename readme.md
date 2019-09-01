docker build image
mvn clean package 
docker build -t gatewaydemo .

run docker image:
docker run -d -p 8080:8080 gatewaydemo

run docker image with httpbin profile
docker run -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=httpbin" gatewaydemo

request examples:
localhost:8080/httpbin/anything
localhost:8080/httpbin/headers

run docker image with jph profile
localhost:8080/jph/todos
localhost:8080/jph/todos/1

