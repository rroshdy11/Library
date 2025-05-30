package com.example.Library.repository;

import com.example.Library.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    Optional<Borrowing> findByBookIdAndReturnDateIsNull(Long bookId);

    List<Borrowing> findByBookId(Long bookId);
}
