package com.thetestroom;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
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
        given().when().get("/hi/billy")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldGetContentForHi() {
        String response = given().when().get("hi/jimmy")
                .then()
                .extract()
                .asString();
        assertThat(response, is("hello jimmy"));
    }

    @Test
    public void shouldGetStatusForPerson() {
        given().when()
                .contentType("application/json")
                .body(new Person("Robin", 25))
                .when().post("/person")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldGetCorrectBodyResponseFromPersonAndAssertAsString() {
        String response = given().when()
                .contentType("application/json")
                .body(new Person("James Gorden", 35))
                .post("/person")
                .asString();
        assertThat(response, is("{\"name\":\"James Gorden\",\"age\":35}"));
    }

    @Test
    public void shouldGetCorrectBodyResponseFromPersonAndAssertAsJsonParts() {
        given().contentType("application/json")
                .when()
                .body(new Person("Gothem", 1000))
                .request("POST", "/person")
                .then()
                .assertThat()
                .body("name", is("Gothem"))
                .body("age", is(1000));
    }

    @Test
    public void shouldHaveResponseTimeLessThenExpectedResponseTime() {
        Response response = given().contentType("application/json")
                .when()
                .body(new Person("Password", 1234))
                .request("POST", "/person");
        assertThat(response.time(), is(lessThan(1500l)));
    }

    @Test
    public void shouldHaveResponseTimeLessThenFiveSeconds() {
        given().contentType("application/json")
                .when()
                .body(new Person("Password", 1234))
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
