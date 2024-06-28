package com.qa.demo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.demo.dtos.PersonDto;
import com.qa.demo.entities.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:person-schema.sql", "classpath:person-data.sql"})
public class PersonControllerMvcTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testNotFound() throws Exception {
        final int id = 999;
        RequestBuilder mockRequest = MockMvcRequestBuilders.get("/get/" + id);

        ResultMatcher checkStatus = MockMvcResultMatchers.status().isNotFound();

        ResultMatcher checkBody = MockMvcResultMatchers.content().string("No person found with id: " + id);

        this.mvc.perform(mockRequest).andExpect(checkStatus).andExpect(checkBody);
    }

    @Test
    void testCreate() throws Exception {
        Person newPerson = new Person("Bob", 42, "Builder");
        String newPersonAsJson = this.mapper.writeValueAsString(newPerson);
        RequestBuilder mockRequest = MockMvcRequestBuilders.post("/create")
                .contentType(MediaType.APPLICATION_JSON).content(newPersonAsJson);

        ResultMatcher checkStatus = MockMvcResultMatchers.status().isOk();
        PersonDto createdPerson = new PersonDto(2, "Bob", 42, "Builder");
        String createdPersonAsJson = this.mapper.writeValueAsString(createdPerson);
        ResultMatcher checkBody = MockMvcResultMatchers.content().json(createdPersonAsJson);

        this.mvc.perform(mockRequest).andExpect(checkStatus).andExpect(checkBody);
    }

    @Test
    void testRead() throws Exception {
        int id = 1;
        RequestBuilder mockRequest = MockMvcRequestBuilders.get("/get/" + id);

        ResultMatcher checkStatus = MockMvcResultMatchers.status().isOk();

        PersonDto existing = new PersonDto(id, "Pat", 53, "Postman");
        String existingPersonAsJson = this.mapper.writeValueAsString(existing);
        ResultMatcher checkBody = MockMvcResultMatchers.content().json(existingPersonAsJson);

        this.mvc.perform(mockRequest).andExpect(checkStatus).andExpect(checkBody);
    }

    @Test
    void testUpdate() throws Exception {
        int id = 1;
        PersonDto updated = new PersonDto(id, "Top", 50, "Cat");

        RequestBuilder mockRequest = MockMvcRequestBuilders.patch("/update/" + id)
                .queryParam("name", updated.getName())
                .queryParam("age", String.valueOf(updated.getAge()))
                .queryParam("job", updated.getJob());

        ResultMatcher checkStatus = MockMvcResultMatchers.status().isOk();

        String updatedPersonAsJson = this.mapper.writeValueAsString(updated);
        ResultMatcher checkBody = MockMvcResultMatchers.content().json(updatedPersonAsJson);

        this.mvc.perform(mockRequest).andExpect(checkStatus).andExpect(checkBody);
    }

    @Test
    void testDelete() throws Exception {
        int id = 1;
        RequestBuilder mockRequest = MockMvcRequestBuilders.delete("/remove/" + id);

        ResultMatcher checkStatus = MockMvcResultMatchers.status().isOk();

        PersonDto existing = new PersonDto(id, "Pat", 53, "Postman");
        String existingPersonAsJson = this.mapper.writeValueAsString(existing);
        ResultMatcher checkBody = MockMvcResultMatchers.content().json(existingPersonAsJson);

        this.mvc.perform(mockRequest).andExpect(checkStatus).andExpect(checkBody);
    }
}   
