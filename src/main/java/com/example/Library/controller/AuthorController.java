package com.example.Library.controller;

import com.example.Library.Service.AuthorService;
import com.example.Library.dto.AuthorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authors")
public class AuthorController {
    AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    // This method will handle the creation of a new author
    @PostMapping("/create")
    public ResponseEntity<?> createAuthor(@RequestBody  AuthorDTO authorDTO) {
        return  authorService.createAuthor(authorDTO);
    }

    // This method will handle the update of an existing author
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable("id")  Long id,@RequestBody AuthorDTO authorDTO) {
        return authorService.updateAuthor(id,authorDTO);
    }

    // This method will handle the deletion of an author
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") Long id) {
        return authorService.deleteAuthor(id);
    }

    // This method will handle the retrieval of an author by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }
    // This method will handle the retrieval of all authors
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    // This method will handle the retrieval of authors by name
    @GetMapping("/getByName/{name}")
    public ResponseEntity<?> getAuthorsByName(@PathVariable("name") String name) {
        return authorService.getAuthorByName(name);
    }



}
