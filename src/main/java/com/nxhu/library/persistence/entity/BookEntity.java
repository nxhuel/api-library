package com.nxhu.library.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String gender;

    @Column(name = "num_pages")
    private Integer numPages;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "pdf_path")
    private String pdfPath;

    @Column(name = "downloads_count")
    @Builder.Default
    private Integer downloadsCount = 0;

    private Double rating;

    @Builder.Default
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    private UserEntity uploadedBy;
}
