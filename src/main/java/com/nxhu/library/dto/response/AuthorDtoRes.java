package com.nxhu.library.dto.response;

import java.util.List;

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
        "name",
        "nationality",
        "books"
})
public class AuthorDtoRes {

    private Long id;
    private String name;
    private String nationality;
    private List<BookDtoRes> books;
}
