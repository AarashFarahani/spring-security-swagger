package com.example.secureswagger.controller;

import com.example.secureswagger.model.Person;
import com.example.secureswagger.service.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("/")
    @ApiOperation(value = "get")
    public ResponseEntity<List<Person>> get() {
        return ResponseEntity.ok(this.personService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> get(@PathVariable int id) {
        return ResponseEntity.ok(this.personService.find(id));
    }

    @PostMapping("/")
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return ResponseEntity.ok(this.personService.save(person));
    }
}
