package com.thetestroom;

import java.util.ArrayList;

public class Person {
    private String name;
    private int age;
    private ArrayList<Pet> pets = new ArrayList<Pet>();

    public Person() {}
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        pets.add(new Pet("cat", "spike"));
        pets.add(new Pet("cat", "sparky"));
        pets.add(new Pet("dog", "dino"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<Pet> getPets() {
        return this.pets;
    }

    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }
}