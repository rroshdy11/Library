package com.example.Library.controller;

import com.example.Library.Service.BookService;
import com.example.Library.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<?> createBook(@RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN', 'STAFF')")
    public ResponseEntity<?> updateBook(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
        return bookService.updateBook(id, bookDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN', 'STAFF', 'MEMBER')")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN', 'STAFF', 'MEMBER')")
    public ResponseEntity<?> getAllBooks() {
        return bookService.getAllBooks();
    }




}
