package com.dynamika.service;

import com.dynamika.dto.BookDto;
import com.dynamika.dto.UserDto;
import com.dynamika.entity.Book;
import com.dynamika.mapper.BookMapper;
import com.dynamika.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDto> findAll(){
        return bookRepository.findAll()
                .stream()
                .map(book -> bookMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    public Optional<BookDto> findById(Long id){
        return bookRepository.findById(id)
                .map(book -> bookMapper.map(book, BookDto.class));
    }

    @Transactional
    public BookDto create(BookDto bookDto){
        return bookMapper.map(
                bookRepository.save(bookMapper.map(bookDto, Book.class)),
                BookDto.class
        );
    }

    @Transactional
    public Optional<BookDto> update(Long id, BookDto updatedBook){
        return bookRepository.findById(id)
                .map(book -> bookMapper.update(updatedBook, book))
                .map(bookRepository::saveAndFlush)
                .map(book -> bookMapper.map(book, BookDto.class));
    }

    @Transactional
    public boolean deleteBook(Long id){
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    bookRepository.flush();
                    return true;
                }).orElse(false);
    }
}
