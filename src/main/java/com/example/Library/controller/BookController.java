package com.example.Library.controller;

import com.example.Library.Service.BookService;
import com.example.Library.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
        return bookService.updateBook(id, bookDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBooks() {
        return bookService.getAllBooks();
    }




}
