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

    public Book maptoBook (BookDto bookDTO){
        Book book=new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        //search for the authors by id from authorDTO sent
        if (bookDTO.getAuthors() != null && !bookDTO.getAuthors().isEmpty()) {
            List<Author> authors = bookDTO.getAuthors().stream()
                    .map(authorDTO -> authorRepository.findById(authorDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Author not found")))
                    .toList();
            book.setAuthors(authors);
        }

        // search for the publisher by id
        if (bookDTO.getPublisher() != null) {
            PublisherDTO publisherDTO = bookDTO.getPublisher();
            Publisher publisher = publisherRepository.findById(publisherDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found"));
            book.setPublisher(publisher);
        }
        //Search for the categories by id
        if (bookDTO.getCategories() != null && !bookDTO.getCategories().isEmpty()) {
            List<Category> categories = bookDTO.getCategories().stream()
                    .map(categoryDTO -> categoryRepository.findById(categoryDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Category not found")))
                    .toList();
            book.setCategories(categories);
        }
        book.setISBN(bookDTO.getISBN());
        book.setEdition(bookDTO.getISBN());
        book.setSummary(bookDTO.getSummary());
        book.setLanguage(bookDTO.getLanguage());
        book.setCoverImageUrl(bookDTO.getCoverImageUrl());

        return book;

    }

    public BookDto mapBookTODTO(Book book) {
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
                authorDTO.setDateOfBirth(author.getDateOfBirth());
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
        author.setDateOfBirth(authorDTO.getDateOfBirth());
        author.setNationality(authorDTO.getNationality());
        return author;
    }
    public AuthorDTO mapAuthorToDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setBio(author.getBio());
        authorDTO.setDateOfBirth(author.getDateOfBirth());
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
        return category;
    }
    public CategoryDTO mapCategoryToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }



}
