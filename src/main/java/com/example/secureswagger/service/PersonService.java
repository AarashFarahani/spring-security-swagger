package com.example.secureswagger.service;

import com.example.secureswagger.model.Person;
import com.example.secureswagger.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    public List<Person> fetchAll() {
        return this.repository.findAll();
    }

    public Person find(Integer id) {
        return this.repository.findById(id).orElse(new Person());
    }

    public Person save(Person person) {
        return this.repository.save(person);
    }
}
