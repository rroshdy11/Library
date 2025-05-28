package com.example.Library.controller;

import com.example.Library.Service.AuthorService;
import com.example.Library.dto.AuthorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
