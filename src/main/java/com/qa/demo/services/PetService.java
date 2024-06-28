package com.qa.demo.services;

import com.qa.demo.dtos.PetDto;
import com.qa.demo.entities.Pet;
import com.qa.demo.repos.PetRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private PetRepo repo;

    public PetService(PetRepo repo) {
        this.repo = repo;
    }

    public List<PetDto> getAll() {
        List<Pet>  found = this.repo.findAll();
        List<PetDto> dtos = new ArrayList<>();
        for (Pet p : found) {
            dtos.add(new PetDto(p));
        }
        return dtos;
    }

    public ResponseEntity<?> get(Integer id) {

        Optional<Pet> optionalPet =  this.repo.findById(id);

        if (optionalPet.isEmpty())
            return new ResponseEntity<>("No pet found with id: " + id, HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new PetDto(optionalPet.get()));
    }

    public PetDto createPet(Pet pet) {
        Pet created =  this.repo.save(pet);

        return new PetDto(created);
    }

    public ResponseEntity<?> updatePet(Integer id, String name, Integer age, String type) {
        Optional<Pet> optionalPet =  this.repo.findById(id);

        if (optionalPet.isEmpty())
            return new ResponseEntity<>("No pet found with id: " + id, HttpStatus.NOT_FOUND);

        Pet toUpdate = optionalPet.get();

        if (name != null) toUpdate.setName(name);
        if (age != null) toUpdate.setAge(age);
        if (type != null) toUpdate.setType(type);

        return ResponseEntity.ok(this.repo.save(toUpdate));
    }

    @Transactional
    public ResponseEntity<?> removePet(Integer id) {
        Optional<Pet> optionalPet =  this.repo.findById(id);

        if (optionalPet.isEmpty())
            return new ResponseEntity<>("No pet found with id: " + id, HttpStatus.NOT_FOUND);

        Pet removed = optionalPet.get();
        this.repo.deleteById(id);
        return ResponseEntity.ok(new PetDto(removed));
    }

}
