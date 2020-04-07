package com.softuni.springintroex.controllers;

import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();


//////        ex1
//        List<Book> allBooksAfter2000 = this.bookService.getAllBooksAfter2000();
//        for (Book book : allBooksAfter2000) {
//            System.out.println(book.getTitle());
//        }


//        ex2
//        List<Book> books = this.bookService.getAllBooksBefore1990();
////        for (Book book : books) {
////            System.out.printf("%s %s%n",book.getAuthor().getFirstName(),book.getAuthor().getLastName());
////        }


////       ex3
//        this.authorService.findAllAuthorsByCountOfBooks()
//                .forEach(author -> {
//                    System.out.printf("%s %s % d%n",
//                            author.getFirstName(), author.getLastName(), author.getBooks().size());
//                });

//        ex4

//        bookService.getBooksByAuthor("George", "Powell")
//                .forEach(book -> System.out.printf("%s %s %s%n",
//                        book.getTitle(), book.getReleaseDate(), book.getCopies()));
    }
}
