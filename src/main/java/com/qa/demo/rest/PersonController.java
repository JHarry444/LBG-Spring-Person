package com.qa.demo.rest;

import com.qa.demo.dtos.PersonDto;
import com.qa.demo.entities.Person;
import com.qa.demo.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {

    private PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<PersonDto> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return this.service.get(id);
    }

    @PostMapping("/create")
    public PersonDto createPerson(@RequestBody Person person) {
        return this.service.createPerson(person);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable Integer id,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) Integer age,
                               @RequestParam(required = false) String job) {
        return this.service.updatePerson(id, name, age, job);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removePerson(@PathVariable Integer id) {
        return this.service.removePerson(id);
    }
}
