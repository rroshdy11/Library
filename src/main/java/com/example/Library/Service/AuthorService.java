package com.example.Library.Service;

import com.example.Library.dto.AuthorDTO;
import com.example.Library.model.Author;
import com.example.Library.repository.AuthorRepository;
import com.example.Library.repository.BookRepository;
import com.example.Library.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final DTOMapper dtoMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, DTOMapper dtoMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.dtoMapper = dtoMapper;
    }

    // Add methods to handle author-related operations, such as creating, updating, deleting authors,
    // and retrieving authors along with their books.
    public ResponseEntity<?> createAuthor(AuthorDTO authordto) {
        // Logic to create an author
        if (authordto == null || authordto.getName() == null || authordto.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Author name cannot be empty");
        }
        if (authordto.getId() != null && authorRepository.existsById(authordto.getId())) {
            return ResponseEntity.badRequest().body("Author with this ID already exists");
        }
        if (!authorRepository.findByName(authordto.getName()).isEmpty()) {
            return ResponseEntity.badRequest().body("Author with this name already exists");
        }
        // Convert AuthorDTO to Author entity
        Author author = dtoMapper.mapToAuthor(authordto);
        return ResponseEntity.ok(authorRepository.save(author));
    }
    public ResponseEntity<?> updateAuthor(Long id, AuthorDTO authorDTO) {
        // Logic to update an author
        if (!authorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Author author = dtoMapper.mapToAuthor(authorDTO);
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        // update the author fields based on the provide dto and ignore unsent fields
        if(author.getName() != null && !author.getName().isEmpty()) {
            existingAuthor.setName(author.getName());
        }
        if(author.getBio() != null && !author.getBio().isEmpty()) {
            existingAuthor.setBio(author.getBio());
        }
        if(author.getDateOfBirth() != null ) {
            //convert the dateOfBirth string to LocalDate
            existingAuthor.setDateOfBirth(author.getDateOfBirth());
        }
        if(author.getNationality() != null && !author.getNationality().isEmpty()) {
            existingAuthor.setNationality(author.getNationality());
        }
        return ResponseEntity.ok(authorRepository.save(existingAuthor));
    }

    public ResponseEntity<?> deleteAuthor(Long id) {
        // Logic to delete an author
        if (!authorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        authorRepository.deleteById(id);
        return ResponseEntity.ok("Author deleted successfully");
    }
    public ResponseEntity<?> getAuthorById(Long id) {
        // Logic to retrieve an author by ID
        return authorRepository.findById(id)
                .map(author -> ResponseEntity.ok(dtoMapper.mapAuthorToDTO(author)))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> getAllAuthors() {
        // Logic to retrieve all authors
        return ResponseEntity.ok(authorRepository.findAll().stream()
                .map(dtoMapper::mapAuthorToDTO)
                .toList());
    }

    public ResponseEntity<?> getBooksByAuthorId(Long authorId) {
        // Logic to retrieve books by author ID
        return authorRepository.findById(authorId)
                .map(author -> ResponseEntity.ok(author.getBooks().stream()
                        .map(dtoMapper::mapBookToDTO)
                        .toList()))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> getAuthorsByBookId(Long bookId) {
        // Logic to retrieve authors by book ID
        return bookRepository.findById(bookId)
                .map(book -> ResponseEntity.ok(book.getAuthors().stream()
                        .map(dtoMapper::mapAuthorToDTO)
                        .toList()))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> getAuthorByName(String name) {
        // retrieve all authors by name
        return ResponseEntity.ok(authorRepository.findByName(name).stream()
                .map(dtoMapper::mapAuthorToDTO)
                .toList());
    }





}
