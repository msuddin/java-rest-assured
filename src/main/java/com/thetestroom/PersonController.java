package com.thetestroom;

import org.springframework.web.bind.annotation.*;

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

}
