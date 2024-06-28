package com.qa.demo.dtos;

import com.qa.demo.entities.Person;
import com.qa.demo.entities.Pet;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

public class PetDto {

    private Integer id;


    private String type;

    private String name;

    private Integer age;


    public PetDto() {
    }

    public PetDto(Pet pet) {
        this.id = pet.getId();
        this.type = pet.getType();
        this.name = pet.getName();
        this.age = pet.getAge();
    }

    public PetDto(Integer id, String type, String name, Integer age) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
