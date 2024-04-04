package pl.kielce.tu.isi.springboothello.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.isi.springboothello.biz.model.UserData;

import java.util.Optional;

/**
 * Interfejs repozytorium do obsługi operacji na danych użytkowników w systemie bibliotecznym.
 */
@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {

    /**
     * Pobiera dane użytkownika na podstawie jego loginu.
     *
     * @param loginUser Login użytkownika do wyszukania.
     * @return Obiekt UserData reprezentujący znalezionego użytkownika lub pusty Optional, jeśli użytkownik o danym loginie nie istnieje.
     */
    Optional<UserData> findByLoginUser(String loginUser);
}
