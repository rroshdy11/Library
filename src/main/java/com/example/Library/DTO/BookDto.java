package com.example.Library.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String description;
    private String isbn;
    private String language;
    private int pageCount;
    private String coverImageUrl;
    private Long publisherId; // Assuming publisher is a string ID
    private Long categoryId; // Assuming category is a string ID
    private Long authorId; // Assuming author is a string ID
}
