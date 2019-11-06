package com.thetestroom;

class Pet {
    private String petType;
    private String name;

    public Pet() { }
    public Pet(String petType, String name) {
        this.petType = petType;
        this.name = name;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}