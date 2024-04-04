package pl.kielce.tu.isi.springboothello.biz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.isi.springboothello.biz.model.BookComments;
import pl.kielce.tu.isi.springboothello.data.BookCommentsRepository;

import java.util.List;

/**
 * Serwis obsługujący operacje związane z komentarzami do książek w systemie bibliotecznym.
 */
@Service
public class BookCommentsService {
    private BookCommentsRepository bookCommentsRepository;

    /**
     * Konstruktor serwisu "BookCommentsService".
     *
     * @param bookCommentsRepository Repozytorium komentarzy do książek.
     */
    public BookCommentsService(BookCommentsRepository bookCommentsRepository) {
        this.bookCommentsRepository = bookCommentsRepository;
    }

    /**
     * Pobiera wszystkie komentarze do książek.
     *
     * @return Lista wszystkich komentarzy do książek.
     */
    public List<BookComments> getAllComments() {
        return bookCommentsRepository.findAll();
    }

    /**
     * Pobiera komentarze przypisane do konkretnej książki na podstawie jej identyfikatora.
     *
     * @param bookId Identyfikator książki, dla której pobierane są komentarze.
     * @return Lista komentarzy przypisanych do książki.
     */
    public List<BookComments> getCommentsByBookId(Long bookId) {
        return bookCommentsRepository.findByBook_IdBook(bookId);
    }

    /**
     * Dodaje nowy komentarz do książki.
     *
     * @param bookComments Obiekt reprezentujący nowy komentarz.
     * @return Dodany komentarz.
     */
    @Transactional
    public BookComments addNewComment(BookComments bookComments) {
        return bookCommentsRepository.save(bookComments);
    }

}
