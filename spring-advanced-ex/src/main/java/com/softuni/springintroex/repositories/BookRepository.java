package com.softuni.springintroex.repositories;


import com.softuni.springintroex.entities.AgeRestriction;
import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByTitleContaining(String contain);

    List<Book> findAllByAuthor_LastNameStartingWith(String name);

    @Query(value = "SELECT b FROM Book as b WHERE FUNCTION('CHAR_LENGTH', b.title) > :length")
    List<Book> findBooksWithTitleLengthMoreThan(@Param(value = "length") long length);


    @Query("SELECT sum(b.copies) FROM Book AS b WHERE concat(b.author.firstName, ' ', b.author.lastName) = :fullName")
    int findAllByCopiesByAuthor(@Param("fullName") String fullName);


    Book findByTitle(String title);
}


