package com.qa.demo.dtos;

import com.qa.demo.entities.Person;
import com.qa.demo.entities.Pet;
import jakarta.persistence.Column;

import java.util.ArrayList;
import java.util.List;

public class PersonDto {

    private Integer id;

    @Column(name = "full_name", nullable = false, unique = true )
    private String name;

    private Integer age;

    private String job;

    private List<PetDto> pets = new ArrayList<>();

    public PersonDto() {
        super();
    }

    public PersonDto(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.age = person.getAge();
        this.job = person.getJob();

        if (person.getPets() != null) {
            for (Pet pet: person.getPets()) {
                this.pets.add(new PetDto(pet));
            }
        }
    }

    public PersonDto(Integer id, String name, Integer age, String job) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public PersonDto(String name, Integer age, String job) {
        super();
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<PetDto> getPets() {
        return pets;
    }

    public void setPets(List<PetDto> pets) {
        this.pets = pets;
    }
}
