package com.dynamika.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookUserDto {

    private String fullName;
    private Date birthdate;
    private String bookTitle;
    private String bookAuthor;
    private String bookIsbn;
    private Date takenAt;
}
