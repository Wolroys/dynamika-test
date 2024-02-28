package com.dynamika.mapper;

import com.dynamika.dto.BookDto;
import com.dynamika.entity.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class BookMapper {

    private final ModelMapper modelMapper;

    public <T, R> R map(T source, Class<R> destinationClass){
        return source == null ? null : map(source, destinationClass);
    }

    public Book update(BookDto source, Book destination){
        modelMapper.map(source, destination);
        return (destination);
    }
}
