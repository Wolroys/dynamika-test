package com.dynamika.dto;

import com.dynamika.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String title;

    private String author;

    private String isbn;

    private User owner;

    private Date takenAt;
}
