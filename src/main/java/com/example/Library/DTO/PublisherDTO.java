package com.example.Library.DTO;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublisherDTO {
    private Long id;
    private String name;
    private String address;
    private String website;

}
