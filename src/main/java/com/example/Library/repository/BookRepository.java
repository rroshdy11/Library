package com.example.Library.repository;

import com.example.Library.model.Book;
import com.example.Library.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    boolean existsByPublisherId(Long publisherId);
}