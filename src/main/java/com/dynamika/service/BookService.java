package com.dynamika.service;

import com.dynamika.dto.BookDto;
import com.dynamika.dto.UserDto;
import com.dynamika.entity.Book;
import com.dynamika.entity.User;
import com.dynamika.mapper.BookMapper;
import com.dynamika.mapper.UserMapper;
import com.dynamika.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserMapper userMapper;

    @Async
    public CompletableFuture<List<BookDto>> findAll(){
        return CompletableFuture.completedFuture(
                bookRepository.findAll()
                        .stream()
                        .map(book -> bookMapper.map(book, BookDto.class))
                        .collect(Collectors.toList())
        );
    }

    @Async
    @Transactional
    public CompletableFuture<BookDto> create(BookDto bookDto){
        return CompletableFuture.completedFuture(
                bookMapper.map(
                        bookRepository.save(bookMapper.map(bookDto, Book.class)),
                        BookDto.class
                )
        );
    }

    @Async
    public CompletableFuture<Optional<BookDto>> findById(Long id){
        return CompletableFuture.completedFuture(
                bookRepository.findById(id)
                        .map(book -> bookMapper.map(book, BookDto.class))
        );
    }

    @Async
    @Transactional
    public CompletableFuture<Optional<BookDto>> update(Long id, BookDto updatedBook){
        return CompletableFuture.completedFuture(
                bookRepository.findById(id)
                        .map(book -> bookMapper.update(updatedBook, book))
                        .map(bookRepository::save)
                        .map(book -> bookMapper.map(book, BookDto.class))
        );
    }

    @Async
    @Transactional
    public CompletableFuture<Boolean> deleteBook(Long id){
        return CompletableFuture.completedFuture(
                bookRepository.findById(id)
                        .map(book -> {
                            bookRepository.delete(book);
                            return true;
                        }).orElse(false)
        );
    }

    @Async
    @Transactional
    public CompletableFuture<BookDto> takeBook(Long id, UserDto userDto){
        return CompletableFuture.completedFuture(
                bookRepository.findById(id)
                        .map(book -> {
                            book.setOwner(userMapper.map(userDto, User.class));
                            return bookMapper.map(bookRepository.save(book), BookDto.class);
                        }).orElse(null)
        );
    }

    @Async
    @Transactional
    public CompletableFuture<BookDto> releaseBook(Long id){
        return CompletableFuture.completedFuture(
                bookRepository.findById(id)
                        .map(book -> {
                            book.setOwner(null);
                            return bookMapper.map(bookRepository.save(book), BookDto.class);
                        }).orElse(null)
        );
    }
}

