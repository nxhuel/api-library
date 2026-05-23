package com.nxhu.library.persistence.entity;

import com.nxhu.library.persistence.entity.enums.Role;
import com.nxhu.library.persistence.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private String photo;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AuthorEntity author;

    @OneToMany(mappedBy = "uploadedBy")
    private List<BookEntity> uploadedBooks;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
