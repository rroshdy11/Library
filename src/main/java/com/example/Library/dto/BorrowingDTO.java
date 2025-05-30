package com.example.Library.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BorrowingDTO {
    private Long id;
    private String memberId;
    private Long bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
