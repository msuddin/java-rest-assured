package com.thetestroom;

import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @RequestMapping("/hi/{name}")
    public String hello(@PathVariable("name") String name) {
        return "hello " + name;
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST, consumes="application/json", produces = "application/json")
    public Person person(@RequestBody Person persons) {
        return new Person(persons.getName(), persons.getAge());
    }

}

