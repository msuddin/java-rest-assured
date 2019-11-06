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

## Sample tests include
 - Asserting on status code with simple get(), then()
 - Passing in parameters using with()
 - Passing in parameters using pathParam using given()
 - Passing in parameters using queryParam using given()
 - Extracting response as a string and asserting on it
 - Setting contentType and setting body as part of given()
 - Extracting a post and performing multiple assertions
 - Performing json schema validation
 - Asserting on arrays which are returned as part of a request
 - Asserting on the content type and json parts individually
 - Setting cookie and headers as part of the given()
 - Asserting on cookies
 - Asserting on headers
 - Using request specification to build a spec for given()
 - Using response specification to build a spec for then()
 - Using json path to get values in the json response
 - Measuring response times in miliseconds
 - Measuring response times in seconds
 - Asserting on logs

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