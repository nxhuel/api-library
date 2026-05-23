package com.nxhu.library.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "authors")
public class AuthorEntity {

    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String nationality;

    private String bio;

    @Column(name = "star_counts")
    @Builder.Default
    private Integer starCounts = 0;

    @OneToMany(mappedBy = "author")
    private List<BookEntity> books;
}
