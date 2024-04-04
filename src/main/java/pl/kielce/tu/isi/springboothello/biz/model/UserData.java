package pl.kielce.tu.isi.springboothello.biz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.kielce.tu.isi.springboothello.biz.model.Book;

import java.util.List;

/**
 * Klasa reprezentująca dane użytkownika w systemie.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    /**
     * Login użytkownika (unikalny).
     */
    @NotEmpty(message = "Login jest wymagany")
    @Column(name = "login_user", nullable = false, unique = true)
    private String loginUser;

    /**
     * Hasło użytkownika.
     */
    @NotEmpty(message = "Hasło jest wymagane")
    @Column(name = "password_user", nullable = false)
    private String passwordUser;

    /**
     * Pobiera identyfikator użytkownika.
     *
     * @return Identyfikator użytkownika.
     */
    public Long getIdUser() {
        return idUser;
    }

    /**
     * Ustawia identyfikator użytkownika.
     *
     * @param idUser Identyfikator użytkownika.
     */
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    /**
     * Pobiera login użytkownika.
     *
     * @return Login użytkownika.
     */
    public String getLoginUser() {
        return loginUser;
    }

    /**
     * Ustawia login użytkownika.
     *
     * @param loginUser Login użytkownika.
     */
    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    /**
     * Pobiera hasło użytkownika.
     *
     * @return Hasło użytkownika.
     */
    public String getPasswordUser() {
        return passwordUser;
    }

    /**
     * Ustawia hasło użytkownika.
     *
     * @param passwordUser Hasło użytkownika.
     */
    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }
}



