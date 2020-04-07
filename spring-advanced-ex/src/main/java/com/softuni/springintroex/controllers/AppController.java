package com.softuni.springintroex.controllers;

import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {

        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();


//        1. Books Titles by Age Restriction
//        System.out.println("Enter ageRestriction:");
//        this.bookService.
//                getAllBooksByAgeRestriction(this.bufferedReader.readLine())
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);

//        2. Golden Books
//        this.bookService.
//                getAllBooksByEditionTypeAndCopies("gold", 5000)
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);

////      3. Books by Price
//        this.bookService
//                .getAllBooksByPriceLessThanOrPriceGreaterThan()
//                .forEach(b ->
//                        System.out.printf("%s - $%.2f%n", b.getTitle(), b.getPrice()));


//        4. Not Released Books
//        System.out.println("Enter year:");
//        this.bookService
//                .getAllBooksByReleaseDateNotInYear(Integer.parseInt(this.bufferedReader.readLine()))
//                .forEach(b -> {
//                    System.out.printf("%s%n",b.getTitle());
//                });

//        5. Books Released Before Date
//        System.out.println("Enter year:");
//        this.bookService
//                .getAllByReleasedDate(this.bufferedReader.readLine())
//                .forEach(b -> {
//                    System.out.printf("%s %s %.2f%n", b.getTitle(), b.getEditionType(), b.getPrice());
//                });


//        6. Authors Search
//        System.out.println("Enter input:");
//        this.authorService.getAuthorsByFirstNameEndsWith(this.bufferedReader.readLine())
//                .forEach(a -> {
//                    System.out.printf("%s %s%n", a.getFirstName(), a.getLastName());
//                });

//        7. Books Search
//        System.out.println("Enter string:");
//        this.bookService
//                .getAllByTitleContain(this.bufferedReader.readLine())
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);

//        8. Book Titles Search
//        System.out.println("Enter string:");
//        this.bookService
//               .getBooksWithAuthorsNameStartingWith(this.bufferedReader.readLine())
//                .forEach(b ->
//                        System.out.printf("%s (%s %s)%n",
//                                b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()));


//        9. Count Books
//        System.out.println("Enter number:");
//        int number = Integer.parseInt(this.bufferedReader.readLine());
//        int output = this.bookService.
//                getBookCountWithTitleLongerThan(number);
//        System.out.printf("There are %d books with longer title than %d symbols", output, number);

//        10. Total Book Copies
//        System.out.println("Enter authorName:");
//
//        String name = this.bufferedReader.readLine();
//        int output = this.bookService.getTotalBookCopiesByAuthor(name);
//        System.out.printf("%s - %d%n", name, output);


//        11. Reduced Book
//        System.out.println("Enter bookTitle:");
//        Book reducedBook = this.bookService.getBookByTitle(bufferedReader.readLine());
//        if (reducedBook == null) {
//            System.out.println("No such book!");
//        } else {
//            System.out.printf("%s %s %s %.2f%n",
//                    reducedBook.getTitle(), reducedBook.getEditionType(),
//                    reducedBook.getAgeRestriction(), reducedBook.getPrice());
//        }

    }

}

