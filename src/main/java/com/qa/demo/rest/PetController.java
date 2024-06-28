package com.qa.demo.rest;

import com.qa.demo.dtos.PetDto;
import com.qa.demo.entities.Pet;
import com.qa.demo.services.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<PetDto> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return this.service.get(id);
    }

    @PostMapping("/create")
    public PetDto createPet(@RequestBody Pet pet) {
        return this.service.createPet(pet);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Integer id,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) Integer age,
                               @RequestParam(required = false) String job) {
        return this.service.updatePet(id, name, age, job);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removePet(@PathVariable Integer id) {
        return this.service.removePet(id);
    }
}
