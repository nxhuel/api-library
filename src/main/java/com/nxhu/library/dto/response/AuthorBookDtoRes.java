package com.nxhu.library.dto.response;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
        "descripcion",
        "gender",
        "numPages",
        "createdAt"
})
public class AuthorBookDtoRes {
    private Long id;
    private String title;
    private String description;
    private String gender;
    private int numPages;
    private Date createdAt;
}
