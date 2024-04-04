package pl.kielce.tu.isi.springboothello.web.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.isi.springboothello.biz.model.UserData;
import pl.kielce.tu.isi.springboothello.biz.service.UserService;


/**
 * Kontroler obsługujący operacje związane z tworzeniem nowego konta użytkownika.
 */
@Controller
@RequestMapping("/createAccount")
@SessionAttributes({"email", "password", "age", "agreement"})
@Log4j2
public class CreateAccountController {
    private UserService userService;

    /**
     * Konstruktor klasy "CreateAccountController".
     *
     * @param userService Serwis obsługujący operacje z użytkownikami.
     */
    public CreateAccountController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Wyświetla stronę tworzenia nowego konta użytkownika.
     *
     * @return Nazwa widoku strony tworzenia nowego konta użytkownika.
     */
    @GetMapping
    public String showPage() {
        return "createAccount";
    }

    /**
     * Dodaje nowego użytkownika i przekierowuje go na stronę logowania.
     *
     * @param email          Adres e-mail użytkownika.
     * @param password       Hasło użytkownika.
     * @param haveEnoughAge  Informacja, czy użytkownik ma wystarczający wiek.
     * @param haveAgreement   Informacja, czy użytkownik zaakceptował regulamin.
     * @param httpSession    Sesja HTTP.
     * @param model          Model danych do wyświetlenia na stronie.
     * @param userData       Obiekt reprezentujący dane użytkownika.
     * @return Przekierowanie na stronę logowania lub z powrotem do formularza tworzenia konta w przypadku błędu.
     */
    @PostMapping(params = "confirmCreateAccount=true")
    public String addUser(@RequestParam(name = "email", required = false) String email,
                          @RequestParam("password") String password,
                          @RequestParam("age") Boolean haveEnoughAge,
                          @RequestParam("agreement") Boolean haveAgreement, HttpSession httpSession, Model model, UserData userData) {
        log.info(email);

        if (haveEnoughAge && haveAgreement) {
            if (userService.isEmailAvailable(email)) {
                userData.setLoginUser(email);
                userData.setPasswordUser(password);
                userService.addNewUser(userData);
                return "redirect:/login";
            } else {
                model.addAttribute("error", "E-mail już istnieje");
                return "createAccount";
            }
        }
        return "redirect:/createAccount";
    }


}
