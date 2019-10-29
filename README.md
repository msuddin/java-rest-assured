# Java Rest Assured

## Description
To write a number of sample examples showing how to use REST Assured.

## Learning Point
 - REST Assured is a java library that is used to test RESTFUL api's
 - It uses gherkin like syntax to help build up requests
 - Focuses on the HTTP verbs e.g. GET, POST
 - It able to work with JSON, XML, XPATH, Strings
 - It is possible to test the following
   - headers
   - body content
   - response times
   - log

## Testing
In the root directory, run the following command:
```
./gradelw clean build
```
And run the Jar:
```
java -jar build/lib/*.jar
```
Now let's perform a curl:
```
curl -H "Content-Type: application/json" -d '{"name":"billy","age":5}' http://localhost:8080/person
```
This should provide the following output:
```
{
    "name":"billy",
    "age":5
}
```