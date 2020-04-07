package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> getAllBooksByAgeRestriction(String ageRestriction);

    List<Book> getAllBooksByEditionTypeAndCopies(String editionType, int copies);

    List<Book> getAllBooksByPriceLessThanOrPriceGreaterThan();

    List<Book> getAllBooksByReleaseDateNotInYear(int year);

    List<Book> getAllByReleasedDate(String date);

    List<Book> getAllByTitleContain(String contain);

    List<Book> getBooksWithAuthorsNameStartingWith(String name);

    int getBookCountWithTitleLongerThan(int length);

    int getTotalBookCopiesByAuthor(String fullName);

    Book getBookByTitle(String title);






}
