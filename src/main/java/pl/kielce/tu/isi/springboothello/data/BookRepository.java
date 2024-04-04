package pl.kielce.tu.isi.springboothello.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.isi.springboothello.biz.enums.Genre;
import pl.kielce.tu.isi.springboothello.biz.model.Book;

import java.util.List;
import java.util.Set;

/**
 * Interfejs repozytorium do obsługi operacji na książkach w systemie bibliotecznym.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Pobiera nazwy plików zdjęć dla książek o określonych identyfikatorach.
     *
     * @param ids Kolekcja identyfikatorów książek.
     * @return Zbiór nazw plików zdjęć książek.
     */
    @Query(nativeQuery = true, value = "select photo_filename from book where id_book in :ids")
    public Set<String> findFilenameByIds(@Param("ids") Iterable<Long> ids);

    /**
     * Pobiera wszystkie książki dostępne w systemie bibliotecznym.
     *
     * @return Lista wszystkich książek.
     */
    List<Book> findAll();

    /**
     * Wyszukuje książki na podstawie słowa kluczowego i gatunku.
     *
     * @param keyword Słowo kluczowe, które może wystąpić w tytule lub autorze książki (ignorowane wielkości liter).
     * @param genre   Wybrany gatunek książki lub null, jeśli nie ma preferencji co do gatunku.
     * @return Lista znalezionych książek.
     */
    @Query("SELECT b FROM Book b WHERE " +
            "(LOWER(b.bookTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.firstNameAuthor) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.lastNameAuthor) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:genre IS NULL OR b.genre = :genre)")
    List<Book> findByWordKey(@Param("keyword") String keyword, @Param("genre") Genre genre);

    /**
     * Pobiera 6 losowych książek z bazy danych.
     *
     * @return Lista 6 losowych książek.
     */
    @Query(nativeQuery = true, value = "SELECT * FROM Book ORDER BY RANDOM() LIMIT 6")
    List<Book> sixRandomBooks();

}
