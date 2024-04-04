package pl.kielce.tu.isi.springboothello.biz.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.kielce.tu.isi.springboothello.biz.model.UserData;
import pl.kielce.tu.isi.springboothello.data.UserDataRepository;

import java.util.List;
import java.util.Optional;

/**
 * Serwis obsługujący operacje związane z logowaniem i użytkownikami w systemie bibliotecznym.
 */
@Service
public class LoginService {
    private final UserDataRepository userDataRepository;

    /**
     * Konstruktor klasy "LoginService".
     *
     * @param userDataRepository Repozytorium danych użytkowników.
     */
    @Autowired
    public LoginService(UserDataRepository userDataRepository) {
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
     * Sprawdza dane logowania użytkownika w systemie bibliotecznym.
     *
     * @param userData Obiekt reprezentujący dane logowania użytkownika (login i hasło).
     * @return Obiekt UserData reprezentujący zalogowanego użytkownika lub null, jeśli dane logowania są niepoprawne.
     */
    public UserData checkLoginData(UserData userData) {
        Optional<UserData> optionalUserData = userDataRepository.findByLoginUser(userData.getLoginUser());

        if (optionalUserData.isPresent() && optionalUserData.get().getPasswordUser().equals(userData.getPasswordUser())) {
            return optionalUserData.get();
        } else {
            return null;
        }
    }

}
