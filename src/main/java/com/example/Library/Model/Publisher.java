package com.example.Library.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publishers")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
    @Id
    private String id;

    private String name;
    private String address;
    private String website;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();


}
