package pl.kielce.tu.isi.springboothello.biz.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.kielce.tu.isi.springboothello.biz.enums.Genre;
import pl.kielce.tu.isi.springboothello.biz.model.Book;
import pl.kielce.tu.isi.springboothello.biz.model.BookComments;
import pl.kielce.tu.isi.springboothello.data.BookRepository;
import pl.kielce.tu.isi.springboothello.data.FileStorageRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Serwis obsługujący operacje związane z książkami w systemie bibliotecznym.
 */
@Service
@Log4j2
public class BookService {
    private final BookRepository bookRepository;
    private final FileStorageRepository fileStorageRepository;

    private BookCommentsService bookCommentsService;

    /**
     * Konstruktor klasy "BookService".
     *
     * @param bookRepository       Repozytorium książek.
     * @param fileStorageRepository Repozytorium przechowywania plików.
     * @param bookCommentsService  Serwis komentarzy do książek.
     */
    public BookService(BookRepository bookRepository, FileStorageRepository fileStorageRepository, BookCommentsService bookCommentsService) {
        this.bookRepository = bookRepository;
        this.fileStorageRepository = fileStorageRepository;
        this.bookCommentsService = bookCommentsService;
    }

    /**
     * Zapisuje nową książkę w bazie danych i przechowuje plik z okładką.
     *
     * @param book       Obiekt reprezentujący nową książkę.
     * @param photoStream Strumień wejściowy zawierający plik z okładką książki.
     * @return Zapisany obiekt książki.
     */
    @Transactional
    public Book save(Book book, InputStream photoStream) {
        Book savedBook = bookRepository.save(book);
        fileStorageRepository.save(book.getPhotoFilename(), photoStream);
        return savedBook;
    }

    /**
     * Pobiera książkę o podanym identyfikatorze.
     *
     * @param aLong Identyfikator książki do pobrania.
     * @return Obiekt Optional zawierający znalezioną książkę lub pusty Optional, jeśli książka nie istnieje.
     */
    public Optional<Book> findById(Long aLong) {
        return bookRepository.findById(aLong);
    }

    /**
     * Pobiera wszystkie książki dostępne w systemie bibliotecznym.
     *
     * @return Lista wszystkich książek.
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Usuwa książki o podanych identyfikatorach oraz związane z nimi pliki z okładkami.
     *
     * @param ids Kolekcja identyfikatorów książek do usunięcia.
     * @throws IOException Wyrzucane, jeśli wystąpi błąd podczas usuwania plików.
     */
    public void deleteAllById(Iterable<Long> ids) throws IOException {
        if (ids == null || !ids.iterator().hasNext()) {
            return;
        }

        Set<String> filenames = bookRepository.findFilenameByIds(ids);

        if (filenames != null && !filenames.isEmpty()) {
            fileStorageRepository.deleteAllByName(filenames);
        }
        bookRepository.deleteAllById(ids);
    }

    /**
     * Filtruje książki na podstawie kategorii.
     *
     * @param genre Wybrana kategoria książek.
     * @return Lista książek spełniających kryterium kategorii.
     */
    public List<Book> filtrBookByGenre(Genre genre) {
        Iterable<Book> allBooks = bookRepository.findAll();
        List<Book> filteredBooks = new ArrayList<>();

        for (Book book : allBooks) {
            if (book.getGenre() == genre) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }

    /**
     * Wyszukuje książki na podstawie słowa kluczowego i gatunku.
     *
     * @param keyword Słowo kluczowe, które może wystąpić w tytule lub autorze książki (ignorowane wielkości liter).
     * @param genre   Wybrana kategoria książki lub null, jeśli nie ma preferencji co do kategorii.
     * @return Lista znalezionych książek.
     */
    public List<Book> searchBooksByKeyWord(String keyword, Genre genre) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return bookRepository.findByWordKey(keyword, genre);
    }

    /**
     * Wyświetla 6 losowych książek.
     *
     * @return Lista 6 losowych książek.
     */
    public List<Book> showRandomBooks(){
        return bookRepository.sixRandomBooks();
    }

    /**
     * Pobiera komentarze do konkretnej książki na podstawie jej identyfikatora.
     *
     * @param bookId Identyfikator książki, dla której pobierane są komentarze.
     * @return Obiekt Optional zawierający listę komentarzy lub pusty Optional, jeśli książka nie istnieje.
     */
    public Optional<List<BookComments>> findBookCommentsById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            List<BookComments> comments = bookCommentsService.getCommentsByBookId(bookId);
            return Optional.of(comments);
        }
        return Optional.empty();
    }

}
