package pl.kielce.tu.isi.springboothello.biz.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.isi.springboothello.biz.model.Book;
import pl.kielce.tu.isi.springboothello.biz.model.UserData;
import pl.kielce.tu.isi.springboothello.data.UserDataRepository;

import java.util.List;
import java.util.Optional;

/**
 * Serwis obsługujący operacje związane z użytkownikami w systemie bibliotecznym.
 */
@Component
public class UserService {
    private final UserDataRepository userDataRepository;

    /**
     * Konstruktor klasy "UserService".
     *
     * @param userDataRepository Repozytorium danych użytkowników.
     */
    @Autowired
    public UserService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    /**
     * Pobiera wszystkich użytkowników z systemu bibliotecznego.
     *
     * @return Lista wszystkich użytkowników.
     */
    public List<UserData> getAllUsers() {
        return userDataRepository.findAll();
    }

    /**
     * Dodaje nowego użytkownika do systemu bibliotecznego.
     *
     * @param userData Obiekt reprezentujący nowego użytkownika.
     * @return Zapisany obiekt użytkownika.
     */
    @Transactional
    public UserData addNewUser(UserData userData) {
        return userDataRepository.save(userData);
    }

    /**
     * Usuwa użytkownika o podanym identyfikatorze.
     *
     * @param id Identyfikator użytkownika do usunięcia.
     */
    public void deleteUser(Long id) {
        userDataRepository.deleteById(id);
    }

    /**
     * Aktualizuje hasło użytkownika.
     *
     * @param id              Identyfikator użytkownika, którego hasło ma być zaktualizowane.
     * @param currentPassword Obecne hasło użytkownika.
     * @param newPassword     Nowe hasło użytkownika.
     * @return Zaktualizowany obiekt użytkownika lub null, jeśli podane hasło jest niepoprawne lub użytkownik nie istnieje.
     */
    public UserData updateUser(Long id, String currentPassword, String newPassword) {
        Optional<UserData> optionalUserData = userDataRepository.findById(id);
        if (optionalUserData.isEmpty()) {
            return null;
        } else {
            UserData userData = optionalUserData.get();

            if (!userData.getPasswordUser().equals(currentPassword)) {
                return null;
            }

            userData.setPasswordUser(newPassword);
            return userDataRepository.save(userData);
        }
    }

    /**
     * Sprawdza dostępność adresu email w systemie.
     *
     * @param email Adres email do sprawdzenia.
     * @return true, jeśli adres email jest dostępny, false w przeciwnym razie.
     */
    public boolean isEmailAvailable(String email) {
        return !userDataRepository.findByLoginUser(email).isPresent();
    }

    /**
     * Pobiera użytkownika o podanym identyfikatorze.
     *
     * @param aLong Identyfikator użytkownika do pobrania.
     * @return Obiekt Optional zawierający znalezionego użytkownika lub pusty Optional, jeśli użytkownik nie istnieje.
     */
    public Optional<UserData> findById(Long aLong) {
        return userDataRepository.findById(aLong);
    }

}
