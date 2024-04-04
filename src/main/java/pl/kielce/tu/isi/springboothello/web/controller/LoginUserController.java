package pl.kielce.tu.isi.springboothello.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.isi.springboothello.biz.model.UserData;
import pl.kielce.tu.isi.springboothello.biz.service.LoginService;
import java.util.List;

/**
 * Kontroler obsługujący operacje logowania użytkowników do systemu.
 */
@Controller
@RequestMapping("/login")
@SessionAttributes({"loginUser", "passwordUser"})
public class LoginUserController {
    private LoginService loginService;

    /**
     * Konstruktor klasy "LoginUserController".
     *
     * @param loginService Serwis obsługujący operacje logowania.
     */
    public LoginUserController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Wyświetla stronę logowania.
     *
     * @return Nazwa widoku strony logowania.
     */
    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    /**
     * Loguje użytkownika do systemu na podstawie przesłanych danych.
     *
     * @param emailUser    Adres e-mail użytkownika.
     * @param passwordUser Hasło użytkownika.
     * @param session      Sesja HTTP.
     * @return Przekierowanie na stronę po zalogowaniu lub stronę logowania w przypadku nieudanego logowania.
     */
    @PostMapping(params = "loginUser=true")
    public String loginUser(@RequestParam("emailUser") String emailUser,
                            @RequestParam("passwordUser") String passwordUser,
                            HttpSession session) {
        UserData loginAttempt  = new UserData();
        loginAttempt .setLoginUser(emailUser);
        loginAttempt .setPasswordUser(passwordUser);

        UserData loggedInUser = loginService.checkLoginData(loginAttempt);

        if (loggedInUser != null) {
            // Użytkownik zalogowany pomyślnie
            session.setAttribute("currentUser", loggedInUser);
            session.setAttribute("userId",loggedInUser.getIdUser());
            return "redirect:/libraryBooks";
        } else {
            // Logowanie nieudane
            return "redirect:/login";
        }
    }

}
