package pl.kielce.tu.isi.springboothello.biz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca komentarz do książki w systemie bibliotecznym.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BookComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComments;

    /**
     * Książka, do której przypisany jest ten komentarz.
     */
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    /**
     * Treść komentarza.
     */
    private String description;

    /**
     * Pobiera identyfikator komentarza.
     *
     * @return Identyfikator komentarza.
     */
    public Long getIdComments() {
        return idComments;
    }

    /**
     * Ustawia identyfikator komentarza.
     *
     * @param idComments Identyfikator komentarza.
     */
    public void setIdComments(Long idComments) {
        this.idComments = idComments;
    }

    /**
     * Pobiera książkę, do której przypisany jest ten komentarz.
     *
     * @return Książka, do której przypisany jest ten komentarz.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Ustawia książkę, do której przypisany jest ten komentarz.
     *
     * @param book Książka, do której przypisany jest ten komentarz.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Pobiera treść komentarza.
     *
     * @return Treść komentarza.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Ustawia treść komentarza.
     *
     * @param description Treść komentarza.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
