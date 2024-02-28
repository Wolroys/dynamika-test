package com.dynamika.controller;

import com.dynamika.dto.BookDto;
import com.dynamika.dto.UserDto;
import com.dynamika.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<BookDto>>> getAllBooks(){
        return bookService.findAll()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<BookDto>> getBookById(@PathVariable Long id){
        return bookService.findById(id)
                .thenApply(bookDto -> bookDto.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<BookDto>> createBook(@RequestBody BookDto bookDto){
        return bookService.create(bookDto)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<BookDto>> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto){
        return bookService.update(id, bookDto)
                .thenApply(book -> book.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteBook(@PathVariable Long id){
        return bookService.deleteBook(id)
                .thenApply(aBoolean -> aBoolean ? ResponseEntity.ok().<Void>build() : ResponseEntity.notFound().<Void>build());
    }

    @PostMapping("/{id}/take")
    public CompletableFuture<ResponseEntity<BookDto>> takeBook(@PathVariable Long id, @RequestBody UserDto userDto){
        return bookService.takeBook(id, userDto)
                .thenApply(bookDto -> bookDto != null ? ResponseEntity.ok(bookDto) : ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/release")
    public CompletableFuture<ResponseEntity<BookDto>> releaseBook(@PathVariable Long id){
        return bookService.releaseBook(id)
                .thenApply(bookDto -> bookDto != null ? ResponseEntity.ok(bookDto) : ResponseEntity.notFound().build());
    }
}

