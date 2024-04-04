package pl.kielce.tu.isi.springboothello.biz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.kielce.tu.isi.springboothello.biz.enums.Genre;

/**
 * Klasa reprezentująca książkę w systemie bibliotecznym.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBook;

    /**
     * Tytuł książki.
     */
    @NotEmpty(message = "Nazwa jest wymagana")
    private String bookTitle;

    /**
     * Imię autora książki.
     */
    @NotEmpty(message = "kategoria jest wymagana")
    private String firstNameAuthor;

    /**
     * Nazwisko autora książki.
     */
    @NotEmpty(message = "kategoria jest wymagana")
    private String lastNameAuthor;

    /**
     * Katergoria książki.
     */
    private Genre genre;

    /**
     * Nazwa pliku z zdjęciem okładki książki.
     */
    private String photoFilename;

    /**
     * Pobiera identyfikator książki.
     *
     * @return Identyfikator książki.
     */
    public Long getIdBook() {
        return idBook;
    }

    /**
     * Ustawia identyfikator książki.
     *
     * @param idBook Identyfikator książki.
     */
    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    /**
     * Pobiera tytuł książki.
     *
     * @return Tytuł książki.
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * Ustawia tytuł książki.
     *
     * @param bookTitle Tytuł książki.
     */
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    /**
     * Pobiera imię autora książki.
     *
     * @return Imię autora książki.
     */
    public String getFirstNameAuthor() {
        return firstNameAuthor;
    }

    /**
     * Ustawia imię autora książki.
     *
     * @param firstNameAuthor Imię autora książki.
     */
    public void setFirstNameAuthor(String firstNameAuthor) {
        this.firstNameAuthor = firstNameAuthor;
    }

    /**
     * Pobiera nazwisko autora książki.
     *
     * @return Nazwisko autora książki.
     */
    public String getLastNameAuthor() {
        return lastNameAuthor;
    }

    /**
     * Ustawia nazwisko autora książki.
     *
     * @param lastNameAuthor Nazwisko autora książki.
     */
    public void setLastNameAuthor(String lastNameAuthor) {
        this.lastNameAuthor = lastNameAuthor;
    }

    /**
     * Pobiera kategorię książki.
     *
     * @return kategoria książki.
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * Ustawia kategorię książki.
     *
     * @param genre kategoria książki.
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * Pobiera nazwę pliku z zdjęciem okładki książki.
     *
     * @return Nazwa pliku z zdjęciem okładki książki.
     */
    public String getPhotoFilename() {
        return photoFilename;
    }

    /**
     * Ustawia nazwę pliku z zdjęciem okładki książki.
     *
     * @param photoFilename Nazwa pliku z zdjęciem okładki książki.
     */
    public void setPhotoFilename(String photoFilename) {
        this.photoFilename = photoFilename;
    }
}
