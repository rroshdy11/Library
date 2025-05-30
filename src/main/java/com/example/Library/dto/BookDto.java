package com.example.Library.dto;

import com.example.Library.model.Author;
import com.example.Library.model.Publisher;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Data
public class BookDto {
    private Long id;
    private String title;
    private String summary;
    private String ISBN;
    private String language;
    private String edition;
    private int pageCount;
    private String coverImageUrl;
    private PublisherDTO publisher;
    private List<CategoryDTO> categories= new ArrayList<>();
    private List<AuthorDTO> authors= new ArrayList<>();

}
