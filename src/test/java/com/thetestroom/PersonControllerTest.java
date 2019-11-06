package com.thetestroom;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void shouldGetStatusForHi() {
        get("/hi/billy")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldGetCorrectResultWhenPassingUsingParamForHey() {
        with()
                .param("name", "billy")
                .when()
                .request("GET","hey")
                .then()
                .assertThat()
                .body("name", is("billy"))
                .body("name", equalTo("billy"));
    }

    @Test
    public void shouldGetCorrectResultWhenPassingUsingPathParamForHey() {
        given()
                .pathParam("name", "bobby")
                .when()
                .request("GET", "hey?name={name}")
                .then()
                .assertThat()
                .body("name", is("bobby"));
    }

    @Test
    public void shouldGetCorrectResultWhenPassingUsingQueryParamForHey() {
        given()
                .queryParam("name", "barry")
                .get("/hey")
                .then()
                .assertThat()
                .body("name", is("barry"));
    }

    @Test
    public void shouldGetContentForHi() {
        String response = get("hi/jimmy")
                .then()
                .extract()
                .asString();
        assertThat(response, is("hello jimmy"));
    }

    @Test
    public void shouldGetStatusForPerson() {
        given().contentType("application/json")
                .body(new Person("Robin", 25))
                .when()
                .post("/person")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldGetCorrectBodyResponseFromPersonAndAssertAsString() {
        String response = given()
                .contentType("application/json")
                .body(new Person("James Gorden", 35))
                .post("/person")
                .asString();
        assertThat(response, containsString("\"name\":\"James Gorden\""));
        assertThat(response, containsString("\"age\":35"));
    }

    @Test
    public void shouldValidatePersonEndpointWithJsonSchema() {
        given()
                .contentType("application/json")
                .body(new Person("James Gorden", 36))
                .post("/person")
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("person.json"));
    }

    @Test
    public void shouldGetListOfPets() {
        given()
                .contentType("application/json")
                .body(new Person("Jack", 22))
                .when()
                .request("POST", "/person")
                .then()
                .assertThat()
                .body("pets.petType", hasItems("cat", "dog"));
    }

    @Test
    public void shouldGetCorrectBodyResponseFromPersonAndAssertAsJsonParts() {
        given().contentType("application/json")
                .body(new Person("Gothem", 1000))
                .when()
                .request("POST", "/person")
                .then()
                .assertThat()
                .body("name", is("Gothem"))
                .body("age", is(1000))
                .contentType("application/json");
    }

    @Test
    public void shouldBeAbleToSetCookieAndHeader() {
        given()
                .cookie("personaCookie")
                .header(new Header("persona1", "value1"))
                .contentType("application/json")
                .body(new Persona("darkSide"))
                .when()
                .request("POST", "/persona")
                .then()
                .assertThat()
                .body("persona", is("darkSide"));
    }

    @Test
    public void shouldBeAbleToSetCookieAndHeaderUsingRequestSpecification() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addCookie("personaCookie")
                .addHeader("persona1", "value1")
                .setContentType("application/json")
                .setBody(new Persona("darkSide"))
                .build();

        given()
                .spec(requestSpecification)
                .when()
                .request("POST", "/persona")
                .then()
                .assertThat()
                .body("persona", is("darkSide"));
    }

    @Test
    public void checkCookieKeyAndValue() {
        given()
                .get("/cookieTest")
                .then()
                .assertThat()
                .cookie("cookieKey", is("cookieValue"));
    }

    @Test
    public void checkCookieKeyAndValueWithResponseSpecification() {
        ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectCookie("cookieKey").build();

        given()
                .get("/cookieTest")
                .then()
                .assertThat()
                .spec(responseSpecification);
    }

    @Test
    public void checkHeaderKeyAndValue() {
        given()
                .get("/headerTest")
                .then()
                .assertThat()
                .header("headerKey", is("headerValue"));
    }

    @Test
    public void getJsonForHey() {
        JsonPath jsonPath = get("/hey?name=magic").thenReturn().jsonPath();
        assertThat(jsonPath.get("name"), is("magic"));
    }

    @Test
    public void shouldHaveResponseTimeLessThenExpectedResponseTime() {
        Response response = given().contentType("application/json")
                .body(new Person("Password", 1234))
                .when()
                .request("POST", "/person");
        assertThat(response.time(), is(lessThan(1500l)));
    }

    @Test
    public void shouldHaveResponseTimeLessThenFiveSeconds() {
        given().contentType("application/json")
                .body(new Person("Password", 1234))
                .when()
                .request("POST", "/person")
                .then()
                .time(lessThan(5000l))
                .time(lessThan(5l), TimeUnit.SECONDS);
    }

    @Test
    public void shouldGetLogs() {
        given().get("/hi/billy")
                .then()
                .log()
                .body()
                .statusCode(200);
    }
}
