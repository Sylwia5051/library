package pl.kielce.tu.isi.springboothello.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.isi.springboothello.biz.model.Book;
import pl.kielce.tu.isi.springboothello.biz.model.BookComments;

import java.util.List;

/**
 * Interfejs repozytorium do obsługi operacji na komentarzach do książek.
 */
@Repository
public interface BookCommentsRepository extends JpaRepository<BookComments, Long> {

    /**
     * Pobiera listę komentarzy przypisanych do konkretnej książki.
     *
     * @param bookId Identyfikator książki, dla której pobierane są komentarze.
     * @return Lista komentarzy przypisanych do książki.
     */
    List<BookComments> findByBook_IdBook(Long bookId);
}
