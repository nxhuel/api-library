package com.nxhu.library.dto.request;

import java.sql.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDtoReq {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "NumPages is required")
    @Min(value = 1, message = "NumPages must be greater than 0")
    private int numPages;

    @PastOrPresent(message = "CreatedAt must be in the past or present")
    private Date createdAt;
    
    private Long authorId;
}
