package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.entities.*;
import com.softuni.springintroex.repositories.BookRepository;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.softuni.springintroex.constants.GlobalConstants.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.fileUtil = fileUtil;
    }


    @Override
    public void seedBooks() throws IOException {

        if (this.bookRepository.count() != 0) {
            return;
        }

        this.fileUtil
                .readFileContent(BOOKS_FILE_PATH)
                .forEach(r -> {

                    String[] params = r.split("\\s+");

                    Author author = this.getRandomAuthor();
                    EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate releaseDate = LocalDate.parse(params[1], formatter);
                    int copies = Integer.parseInt(params[2]);
                    BigDecimal price = new BigDecimal(params[3]);
                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];
                    String title = this.getTitle(params);

                    Set<Category> categories = this.getRandomCategories();

                    Book book = new Book();
                    book.setAuthor(author);
                    book.setEditionType(editionType);
                    book.setReleaseDate(releaseDate);
                    book.setCopies(copies);
                    book.setPrice(price);
                    book.setAgeRestriction(ageRestriction);
                    book.setTitle(title);
                    book.setCategories(categories);


                    this.bookRepository.saveAndFlush(book);
                });

    }

    @Override
    public List<Book> getAllBooksByAgeRestriction(String ageRestriction) {
        return this.bookRepository.findAllByAgeRestriction(
                AgeRestriction.valueOf(ageRestriction.toUpperCase()));
    }

    @Override
    public List<Book> getAllBooksByEditionTypeAndCopies(String editionType, int copies) {
        return this.bookRepository.
                findAllByEditionTypeAndCopiesLessThan(
                        EditionType.valueOf(
                                editionType.toUpperCase()), copies);
    }

    @Override
    public List<Book> getAllBooksByPriceLessThanOrPriceGreaterThan() {

        return this.bookRepository.
                findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40));
    }


    @Override
    public List<Book> getAllBooksByReleaseDateNotInYear(int year) {

        LocalDate before = LocalDate.of(year, 1, 1);
        LocalDate after = LocalDate.of(year, 12, 31);

        return this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(before, after);

    }

    @Override
    public List<Book> getAllByReleasedDate(String date) {

        LocalDate releaseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        return this.bookRepository.findAllByReleaseDateBefore(releaseDate);
    }

    @Override
    public List<Book> getAllByTitleContain(String contain) {

        return this.bookRepository.findAllByTitleContaining(contain);
    }

    @Override
    public List<Book> getBooksWithAuthorsNameStartingWith(String name) {

        return this.bookRepository.findAllByAuthor_LastNameStartingWith(name);
    }

    @Override
    public int getBookCountWithTitleLongerThan(int length) {

        return this.bookRepository.findBooksWithTitleLengthMoreThan(length).size();
    }

    @Override
    public int getTotalBookCopiesByAuthor(String fullName) {
        return this.bookRepository.findAllByCopiesByAuthor(fullName);
    }


    @Override
    public Book getBookByTitle(String title) {
        return this.bookRepository.findByTitle(title);
    }


    private Set<Category> getRandomCategories() {
        Set<Category> result = new HashSet<>();
        Random random = new Random();
        int bound = random.nextInt(3) + 1;


        for (int i = 1; i <= bound; i++) {
            int categoryId = random.nextInt(8) + 1;
            result.add(this.categoryService
                    .getCategoryById((long) categoryId));
        }
        return result;
    }

    private String getTitle(String[] params) {
        StringBuilder builder = new StringBuilder();
        for (int i = 5; i < params.length; i++) {
            builder.append(params[i]).append(" ");

        }

        return builder.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt(this.authorService.getAllAuthorsCount()) + 1;

        return this.authorService.findAuthorById((long) randomId);
    }
}
