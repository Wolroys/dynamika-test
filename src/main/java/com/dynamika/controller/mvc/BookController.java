package com.dynamika.controller.mvc;

import com.dynamika.dto.BookUserDto;
import com.dynamika.dto.UserDto;
import com.dynamika.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reading")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String getAllReadingBooks(Model model) {
        List<BookUserDto> readingBooks = bookService.findAllReadingBooks();
        model.addAttribute("readingBooks", readingBooks);
        return "reading/list";
    }

    @GetMapping("/take/{bookId}")
    public String takeBook(@PathVariable Long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "reading/take";
    }

    @PostMapping("/take/{bookId}")
    public String takeBook(@PathVariable Long bookId, @ModelAttribute("client") UserDto client) {
        bookService.takeBook(bookId, client);
        return "redirect:/reading";
    }
}
