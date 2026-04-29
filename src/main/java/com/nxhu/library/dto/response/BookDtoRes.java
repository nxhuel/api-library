package com.nxhu.library.dto.response;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nxhu.library.persistence.entity.AuthorEntity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "gender",
        "numPages",
        "createdAt",
        "author"
})
public class BookDtoRes {

    private Long id;
    private String title;
    private String description;
    private String gender;
    private int numPages;
    private Date createdAt;

    private BookAuthorDtoRes author;
}
