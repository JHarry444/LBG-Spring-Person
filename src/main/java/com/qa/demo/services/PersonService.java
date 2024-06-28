package com.qa.demo.services;

import com.qa.demo.dtos.PersonDto;
import com.qa.demo.entities.Person;
import com.qa.demo.repos.PersonRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepo repo;

    public PersonService(PersonRepo repo) {
        this.repo = repo;
    }

    public List<PersonDto> getAll() {
        List<Person>  found = this.repo.findAll();
        List<PersonDto> dtos = new ArrayList<>();
        for (Person p : found) {
            dtos.add(new PersonDto(p));
        }
        return dtos;
    }

    public ResponseEntity<?> get(Integer id) {

        Optional<Person> optionalPerson =  this.repo.findById(id);

        if (optionalPerson.isEmpty())
            return new ResponseEntity<>("No person found with id: " + id, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new PersonDto(optionalPerson.get()));
    }

    public PersonDto createPerson(Person person) {
        Person created =  this.repo.save(person);

        return new PersonDto(created);
    }

    public ResponseEntity<?> updatePerson(Integer id, String name, Integer age, String job) {
        Optional<Person> optionalPerson =  this.repo.findById(id);

        if (optionalPerson.isEmpty())
            return new ResponseEntity<>("No person found with id: " + id, HttpStatus.NOT_FOUND);

        Person toUpdate = optionalPerson.get();

        if (name != null) toUpdate.setName(name);
        if (age != null) toUpdate.setAge(age);
        if (job != null) toUpdate.setJob(job);

        return ResponseEntity.ok(this.repo.save(toUpdate));
    }

    @Transactional
    public ResponseEntity<?> removePerson(Integer id) {
        Optional<Person> optionalPerson =  this.repo.findById(id);

        if (optionalPerson.isEmpty())
            return new ResponseEntity<>("No person found with id: " + id, HttpStatus.NOT_FOUND);

        Person removed = optionalPerson.get();
        this.repo.deleteById(id);
        return ResponseEntity.ok(new PersonDto(removed));
    }

}
