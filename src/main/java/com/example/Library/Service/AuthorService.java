package com.example.Library.Service;

import com.example.Library.dto.AuthorDTO;
import com.example.Library.model.Author;
import com.example.Library.repository.AuthorRepository;
import com.example.Library.repository.BookRepository;
import com.example.Library.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        // Convert AuthorDTO to Author entity
        Author author = dtoMapper.mapToAuthor(authordto);
        return ResponseEntity.ok(authorRepository.save(author));
    }


}
