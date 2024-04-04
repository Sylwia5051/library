package pl.kielce.tu.isi.springboothello.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.kielce.tu.isi.springboothello.biz.model.OrderBooks;
import pl.kielce.tu.isi.springboothello.biz.model.UserData;
import pl.kielce.tu.isi.springboothello.biz.service.OrderBooksService;
import pl.kielce.tu.isi.springboothello.biz.service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * Kontroler obsługujący operacje związane z kontem użytkownika.
 */
@Controller
@RequestMapping("/userAccount")
public class UserAccountController {
    private UserService userService;
    private OrderBooksService orderBooksService;
//    private PaypalService paypalService;

    /**
     * Konstruktor klasy "UserAccountController".
     *
     * @param userService       Serwis obsługujący operacje na użytkownikach.
     * @param orderBooksService Serwis obsługujący operacje z wypożyczeniami książek.
//     * @param paypalService     Serwis obsługujący operacje związane z płatnościami PayPal.
     */
//    public UserAccountController(UserService userService, OrderBooksService orderBooksService, PaypalService paypalService) {
//        this.userService = userService;
//        this.orderBooksService = orderBooksService;
//        this.paypalService = paypalService;
//    }

    public UserAccountController(UserService userService, OrderBooksService orderBooksService) {
        this.userService = userService;
        this.orderBooksService = orderBooksService;
    }
    /**
     * Wyświetla stronę konta użytkownika z informacjami o wypożyczonych książkach.
     *
     * @param model   Model danych do przekazania na stronę.
     * @param session Sesja HTTP.
     * @return Nazwa widoku strony konta użytkownika.
     */
    @GetMapping
    public String showPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<OrderBooks> loans = orderBooksService.getUserLoans(userId);
        model.addAttribute("myLoans", loans);
        return "userAccount";
    }

    /**
     * Usuwa konto użytkownika i przekierowuje na stronę logowania.
     *
     * @param session Sesja HTTP.
     * @return Przekierowanie na stronę logowania po usunięciu konta użytkownika.
     */
    @PostMapping(params = "deleteUser=true")
    public String deleteUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            userService.deleteUser(userId);
        }
        session.invalidate();
        return "redirect:/login";
    }

    /**
     * Aktualizuje hasło użytkownika.
     *
     * @param session         Sesja HTTP.
     * @param model           Model danych do przekazania na stronę.
     * @param currentPassword Aktualne hasło użytkownika.
     * @param newPassword     Nowe hasło użytkownika.
     * @return Przekierowanie na stronę konta użytkownika.
     */
    @PostMapping(params = "saveNewPassword=true")
    public String updateUserPassword(HttpSession session, Model model,
                                     @RequestParam("passwordUser") String currentPassword,
                                     @RequestParam("newPassword") String newPassword) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
//        List<OrderBooks> loans = orderBooksService.getUserLoans(userId); //?
//        model.addAttribute("myLoans", loans);// ?

        UserData updatedUser = userService.updateUser(userId, currentPassword, newPassword);

        if (updatedUser == null) {
            return "redirect:/userAccount";
        } else {
            // Update succeeded
            return "redirect:/userAccount";
        }
    }

    /**
     * Przegląda listę wypożyczonych książek przez użytkownika.
     *
     * @param model   Model danych do przekazania na stronę.
     * @param session Sesja HTTP.
     * @return Nazwa widoku strony z listą wypożyczonych książek.
     */
    @PostMapping(params = "pageWithLoans=loansView")
    public String myLoansMode(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        List<OrderBooks> loans = orderBooksService.getUserLoans(userId);
        model.addAttribute("myLoans", loans);
        return "userAccount";
    }

    /**
     * Zwraca wypożyczoną książkę.
     *
     * @param loanId  Identyfikator wypożyczenia książki do zwrotu.
     * @param session Sesja HTTP.
     * @return Przekierowanie na stronę konta użytkownika.
     */
    @PostMapping(params = "returnBook=true")
    public String returnBook(@RequestParam("loanId") Long loanId, HttpSession session) {
        orderBooksService.returnBook(loanId);
        return "redirect:/userAccount";
    }

    /**
     * Płaci za opóźniony zwrot wypożyczonych książek.
     *
     * @param loanId        Identyfikator wypożyczenia książek do opłacenia.
     * @param session       Sesja HTTP.
     * @return Przekierowanie na stronę płatności PayPal.
     */
    @PostMapping(params = "payAmountDue=true")
    public String payAmount(@RequestParam("loanId") Long loanId, HttpSession session, RedirectAttributes redirectAttrs) {
        session.setAttribute("loanId", loanId);

        Optional<OrderBooks> loanOptional = orderBooksService.findById(loanId);
        if (!loanOptional.isPresent()) {
            redirectAttrs.addFlashAttribute("errorMessage", "Wypożyczenie o podanym ID nie istnieje.");
            return "redirect:/userAccount";
        }
        return "redirect:/paypal";

    }


}
