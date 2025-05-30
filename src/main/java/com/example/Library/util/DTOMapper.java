package com.example.Library.util;

import com.example.Library.dto.AuthorDTO;
import com.example.Library.dto.BookDto;
import com.example.Library.dto.CategoryDTO;
import com.example.Library.dto.PublisherDTO;
import com.example.Library.model.Author;
import com.example.Library.model.Book;
import com.example.Library.model.Category;
import com.example.Library.model.Publisher;
import com.example.Library.repository.AuthorRepository;
import com.example.Library.repository.PublisherRepository;
import com.example.Library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOMapper {
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DTOMapper(AuthorRepository authorRepository,
                     PublisherRepository publisherRepository,
                     CategoryRepository categoryRepository) {
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
    }

    public Book maptoBook(BookDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setSummary(dto.getSummary());
        book.setISBN(dto.getISBN());
        book.setLanguage(dto.getLanguage());
        book.setEdition(dto.getEdition());
        book.setCoverImageUrl(dto.getCoverImageUrl());

        if (dto.getPublisher() != null) {
            book.setPublisher(
                    publisherRepository.findById(dto.getPublisher().getId())
                            .orElseThrow(() -> new RuntimeException("Publisher not found"))
            );
        }
        if (dto.getAuthors() != null) {
            book.setAuthors(dto.getAuthors().stream()
                    .map(a -> authorRepository.findById(a.getId())
                            .orElseThrow(() -> new RuntimeException("Author not found: " + a.getId())))
                    .collect(Collectors.toList()));
        }

        // Example: map categories
        if (dto.getCategories() != null) {
            book.setCategories(dto.getCategories().stream()
                    .map(c -> categoryRepository.findById(c.getId())
                            .orElseThrow(() -> new RuntimeException("Category not found: " + c.getId())))
                    .collect(Collectors.toList()));
        }

        return book;
    }

    public BookDto mapBookToDTO(Book book) {
        BookDto bookDTO = new BookDto();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setISBN(book.getISBN());
        bookDTO.setEdition(book.getEdition());
        bookDTO.setSummary(book.getSummary());
        bookDTO.setLanguage(book.getLanguage());
        bookDTO.setCoverImageUrl(book.getCoverImageUrl());

        // Map authors
        if (book.getAuthors() != null) {
            List<AuthorDTO> authorDTOS = new ArrayList<>();
            for (Author author : book.getAuthors()) {
                AuthorDTO authorDTO = new AuthorDTO();
                authorDTO.setId(author.getId());
                authorDTO.setName(author.getName());
                authorDTO.setBio(author.getBio());
                // Convert dateOfBirth from LocalDate to String
                if (author.getDateOfBirth() != null) {
                    authorDTO.setDateOfBirth(author.getDateOfBirth().toString());
                }
                authorDTO.setNationality(author.getNationality());
                authorDTOS.add(authorDTO);
            }
            bookDTO.setAuthors(authorDTOS);
        }

        // Map publisher
        if (book.getPublisher() != null) {
            PublisherDTO publisherDTO = new PublisherDTO();
            publisherDTO.setId(book.getPublisher().getId());
            publisherDTO.setName(book.getPublisher().getName());
            publisherDTO.setAddress(book.getPublisher().getAddress());
            publisherDTO.setWebsite(book.getPublisher().getWebsite());
            bookDTO.setPublisher(publisherDTO);
        }

        // Map categories
        if (book.getCategories() != null) {
            List<CategoryDTO> categoryDTOS=new ArrayList<>();
            for (Category category : book.getCategories()) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
                categoryDTOS.add(categoryDTO);

            }
            bookDTO.setCategories(categoryDTOS);
        }

        return bookDTO;
    }

    public Author mapToAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setName(authorDTO.getName());
        author.setBio(authorDTO.getBio());
        // Convert dateOfBirth from String to LocalDate as dd-MM-yyyy
        if (authorDTO.getDateOfBirth() != null) {
            // take dateOfBirth in the format dd-MM-yyyy
            String[] dateParts = authorDTO.getDateOfBirth().split("-");
            if (dateParts.length == 3) {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                author.setDateOfBirth(LocalDate.of(year, month, day));
            } else {
                throw new IllegalArgumentException("Invalid date format. Expected dd-MM-yyyy");
            }
        }
        author.setNationality(authorDTO.getNationality());
        return author;
    }
    public AuthorDTO mapAuthorToDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setBio(author.getBio());
        // Convert dateOfBirth from LocalDate to String
        if (author.getDateOfBirth() != null) {
            authorDTO.setDateOfBirth(author.getDateOfBirth().toString());
        }
        authorDTO.setNationality(author.getNationality());
        return authorDTO;
    }
    public Publisher mapToPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setId(publisherDTO.getId());
        publisher.setName(publisherDTO.getName());
        publisher.setAddress(publisherDTO.getAddress());
        publisher.setWebsite(publisherDTO.getWebsite());
        return publisher;
    }
    public PublisherDTO mapPublisherToDTO(Publisher publisher) {
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setId(publisher.getId());
        publisherDTO.setName(publisher.getName());
        publisherDTO.setAddress(publisher.getAddress());
        publisherDTO.setWebsite(publisher.getWebsite());
        return publisherDTO;
    }
    public Category mapToCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        // Set parent category if it exists
        if( categoryDTO.getParentCategoryId() != null &&
            categoryRepository.existsById(categoryDTO.getParentCategoryId())) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentCategoryId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parentCategory);
        } else {
            category.setParent(null); // No parent category
        }
        return category;
    }
    public CategoryDTO mapCategoryToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        // Set parent category ID if parent exists
        if (category.getParent() != null) {
            categoryDTO.setParentCategoryId(category.getParent().getId());
        } else {
            categoryDTO.setParentCategoryId(null);
        }
        return categoryDTO;
    }



}
