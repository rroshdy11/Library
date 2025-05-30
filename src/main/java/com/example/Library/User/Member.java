package com.example.Library.User;

import com.example.Library.model.Book;
import com.example.Library.model.Borrowing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @SequenceGenerator(name = "member_sequence", sequenceName = "member_sequence", allocationSize = 1)
    @GeneratedValue(generator = "member_sequence")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Borrowing> borrowings = new ArrayList<>();


}
