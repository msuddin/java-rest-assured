package com.thetestroom;

import com.sun.net.httpserver.Headers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PersonController {

    @RequestMapping("/hi/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return "hello " + name;
    }

    @RequestMapping(value = "/hey", method = RequestMethod.GET)
    public Name sayHelloWithParams(@RequestParam("name") String name) {
        return new Name(name);
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public Person person(@RequestBody Person persons) {
        return new Person(persons.getName(), persons.getAge());
    }

    @RequestMapping(
            value = "/persona",
            method = RequestMethod.POST,
            consumes="application/json",
            produces = "application/json",
            headers = "persona1=value1"
    )
    public Persona persona(@CookieValue("personaCookie") String personaCookie, @RequestBody Persona persons) {
        return new Persona(persons.getPersona());
    }

    @RequestMapping("/cookieTest")
    public String getTestCookie(HttpServletResponse response) {
        response.addCookie(new Cookie("cookieKey", "cookieValue"));
        return "cookie test";
    }

    @RequestMapping("/headerTest")
    public String getTestHeader(HttpServletResponse response) {
        response.addHeader("headerKey", "headerValue");
        return "header test";
    }

}