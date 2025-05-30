package com.example.Library.Service;

import com.example.Library.dto.BorrowingDTO;
import com.example.Library.model.Borrowing;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.BorrowingRepository;
import com.example.Library.repository.UserRepository;
import com.example.Library.util.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final DTOMapper dtoMapper;

    @Autowired
    public BorrowingService(BorrowingRepository borrowingRepository, UserRepository userRepository,
                            BookRepository bookRepository, DTOMapper dtoMapper) {
        this.borrowingRepository = borrowingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.dtoMapper = dtoMapper;
    }

    //Create A borrowing record
    public ResponseEntity<?> createBorrowingRecord(BorrowingDTO borrowingDTO) {
        if (borrowingDTO == null || borrowingDTO.getMemberId() == null || borrowingDTO.getBookId() == null) {
            return ResponseEntity.badRequest().body("User ID and Book ID cannot be null");
        }

        if (!userRepository.existsById(borrowingDTO.getMemberId())) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        if (!bookRepository.existsById(borrowingDTO.getBookId())) {
            return ResponseEntity.badRequest().body("Book does not exist");
        }

        List<Borrowing> borrowings = borrowingRepository.findByBookId(borrowingDTO.getBookId());

        boolean isBookBorrowed = borrowings.stream()
                .anyMatch(borrowing -> borrowing.getReturnDate() == null);

        if (isBookBorrowed) {
            return ResponseEntity.badRequest().body("Book is currently borrowed by another user.");
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setBorrowDate(borrowingDTO.getBorrowDate());
        borrowing.setDueDate(borrowingDTO.getDueDate());
        borrowing.setReturnDate(borrowingDTO.getReturnDate());

        borrowing.setMember(userRepository.findById(borrowingDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        borrowing.setBook(bookRepository.findById(borrowingDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found")));

        return ResponseEntity.ok(borrowingRepository.save(borrowing));
    }



    // Return a borrowed book
    public ResponseEntity<?> returnBorrowedBook(Long borrowingId) {
        // Check if the borrowing record exists
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new RuntimeException("Borrowing record not found"));

        // Check if the book is already returned
        if (borrowing.getReturnDate() != null) {
            return ResponseEntity.badRequest().body("Book has already been returned.");
        }

        // Set the return date to the current date
        borrowing.setReturnDate(LocalDate.now());

        // Save the updated borrowing record
        borrowingRepository.save(borrowing);

        return ResponseEntity.ok("Book returned successfully.");
    }

    // Get borrowing record by ID
    public ResponseEntity<?> getBorrowingRecordById(Long id) {
        return borrowingRepository.findById(id)
                .map(borrowing -> ResponseEntity.ok(dtoMapper.toDto(borrowing)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all borrowing records
    public ResponseEntity<?> getAllBorrowingRecords() {
        return ResponseEntity.ok(borrowingRepository.findAll().stream()
                .toList());
    }

    // Delete a borrowing record
    public ResponseEntity<?> deleteBorrowingRecord(Long id) {
        if (!borrowingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        borrowingRepository.deleteById(id);
        return ResponseEntity.ok("Borrowing record deleted successfully.");
    }

    // get borrowing record by id
    public ResponseEntity<?> getBorrowingRecord(Long id) {
        return borrowingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
