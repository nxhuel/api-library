package com.nxhu.library.dto.response;

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
})
public class BookAuthorDtoRes {

    private Long id;
    private String name;
}
