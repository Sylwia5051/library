package pl.kielce.tu.isi.springboothello.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.isi.springboothello.biz.model.OrderBooks;
import pl.kielce.tu.isi.springboothello.biz.model.UserData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfejs repozytorium do obsługi operacji na zamówieniach książek w systemie bibliotecznym.
 */
@Repository
public interface OrderBooksRepository extends JpaRepository<OrderBooks,Long> {

    /**
     * Pobiera listę zamówień książek przypisanych do określonego użytkownika.
     *
     * @param userData Obiekt użytkownika, dla którego pobierane są zamówienia.
     * @return Lista zamówień książek przypisanych do użytkownika.
     */
    List<OrderBooks> findByUserData(Optional<UserData> userData);

    /**
     * Pobiera listę zamówień książek, których identyfikator książki jest równy podanemu identyfikatorowi,
     * a data zakończenia wypożyczenia jest późniejsza niż określona data.
     *
     * @param idBook   Identyfikator książki, dla której pobierane są zamówienia.
     * @param endDate  Data zakończenia wypożyczenia, która jest brana pod uwagę.
     * @return Lista zamówień książek spełniających kryteria wyszukiwania.
     */
    List<OrderBooks> findByBook_IdBookAndEndDateOrderGreaterThan(Long idBook, LocalDate endDate);
}
