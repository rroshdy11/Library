package com.example.Library.Service;

import com.example.Library.dto.BookDto;
import com.example.Library.model.Author;
import com.example.Library.model.Book;
import com.example.Library.model.Category;
import com.example.Library.repository.AuthorRepository;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.CategoryRepository;
import com.example.Library.repository.PublisherRepository;
import com.example.Library.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final DTOMapper dtoMapper;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, DTOMapper dtoMapper,
                       AuthorRepository authorRepository, PublisherRepository publisherRepository,
                       CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.dtoMapper = dtoMapper;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }
    // Add methods to handle book-related operations, such as creating, updating, deleting books,
    public ResponseEntity<?> createBook(BookDto bookDto) {
        // Logic to create a book
        if (bookDto == null || bookDto.getTitle() == null || bookDto.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body("Book title cannot be empty");
        }
        if (bookDto.getId() != null && bookRepository.existsById(bookDto.getId())) {
            return ResponseEntity.badRequest().body("Book with this ID already exists");
        }
        // Convert BookDto to Book entity
        Book book = dtoMapper.maptoBook(bookDto);

        return ResponseEntity.ok(bookRepository.save(book));
    }


    // Get Book by ID
    public ResponseEntity<?> getBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return ResponseEntity.ok(book);
    }

    //GET All Books
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    // Update Book
    public ResponseEntity<?> updateBook(Long id, BookDto bookDto) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Book book = dtoMapper.maptoBook(bookDto);
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Set all fields that are not null in the bookDto
        if (book.getTitle() != null && !book.getTitle().isEmpty()) {
            existingBook.setTitle(book.getTitle());
        }
        if (book.getSummary() != null && !book.getSummary().isEmpty()) {
            existingBook.setSummary(book.getSummary());
        }
        if (book.getISBN() != null && !book.getISBN().isEmpty()) {
            existingBook.setISBN(book.getISBN());
        }
        if (book.getLanguage() != null && !book.getLanguage().isEmpty()) {
            existingBook.setLanguage(book.getLanguage());
        }
        if (book.getEdition() != null && !book.getEdition().isEmpty()) {
            existingBook.setEdition(book.getEdition());
        }
        if (book.getCoverImageUrl() != null && !book.getCoverImageUrl().isEmpty()) {
            existingBook.setCoverImageUrl(book.getCoverImageUrl());
        }
        if (book.getPublisher() != null) {
            if (!publisherRepository.existsById(book.getPublisher().getId())) {
                return ResponseEntity.badRequest().body("Publisher with this ID does not exist");
            }
            existingBook.setPublisher(publisherRepository.findById(book.getPublisher().getId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found")));
        }
        if (book.getCategories() != null && !book.getCategories().isEmpty()) {
            for (Category category : book.getCategories()) {
                if (!categoryRepository.existsById(category.getId())) {
                    return ResponseEntity.badRequest().body("Category with ID " + category.getId() + " does not exist");
                }
            }
            existingBook.setCategories(book.getCategories());
        }
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            for (Author author : book.getAuthors()) {
                if (!authorRepository.existsById(author.getId())) {
                    return ResponseEntity.badRequest().body("Author with ID " + author.getId() + " does not exist");
                }
            }
            existingBook.setAuthors(book.getAuthors());
        }
        if (book.getBorrowings() != null && !book.getBorrowings().isEmpty()) {
            existingBook.setBorrowings(book.getBorrowings());
        }

        return ResponseEntity.ok(bookRepository.save(existingBook));
    }

    // Delete Book
    public ResponseEntity<?> deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.ok("Book deleted successfully");
    }



}
