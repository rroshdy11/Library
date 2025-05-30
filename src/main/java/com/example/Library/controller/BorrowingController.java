package com.example.Library.controller;

import com.example.Library.Service.BorrowingService;
import com.example.Library.dto.BorrowingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrowings")
public class BorrowingController {
    private final BorrowingService borrowingService;
    @Autowired
    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('MEMEBR', 'ADMIN')")
    public ResponseEntity<?> createBorrowingRecord(@RequestBody BorrowingDTO borrowingDTO) {
        return borrowingService.createBorrowingRecord(borrowingDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN', 'STAFF', 'MEMBER')")
    public ResponseEntity<?> getBorrowingRecord(@PathVariable Long id) {
        return borrowingService.getBorrowingRecord(id);
    }

    @PutMapping("returnbook/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN', 'STAFF', 'MEMBER')")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        return borrowingService.returnBorrowedBook(id);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN', 'STAFF', 'MEMBER')")
    public ResponseEntity<?> getAllBorrowings() {
        return borrowingService.getAllBorrowingRecords();
    }


}
