package pl.kielce.tu.isi.springboothello.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kielce.tu.isi.springboothello.biz.model.Book;
import pl.kielce.tu.isi.springboothello.biz.model.OrderBooks;
import pl.kielce.tu.isi.springboothello.biz.model.UserData;
import pl.kielce.tu.isi.springboothello.biz.service.BookService;
import pl.kielce.tu.isi.springboothello.biz.service.OrderBooksService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Kontroler obsługujący operacje związane z koszykiem zakupowym.
 */
@Controller
@RequestMapping("/basket")
public class BasketController {
    private BookService bookService;
    private OrderBooksService orderBooksService;

    /**
     * Konstruktor klasy "BasketController".
     *
     * @param bookService        Serwis obsługujący operacje z książkami.
     * @param orderBooksService Serwis obsługujący operacje z zamówieniami książek.
     */
    public BasketController(BookService bookService, OrderBooksService orderBooksService) {
        this.bookService = bookService;
        this.orderBooksService = orderBooksService;
    }

    /**
     * Wyświetla stronę koszyka zakupowego.
     *
     * @param session Sesja HTTP.
     * @param model   Model danych do wyświetlenia na stronie.
     * @return Nazwa widoku strony koszyka zakupowego.
     */
    @GetMapping
    public String showPage(HttpSession session, Model model) {
        List<Long> basket = (List<Long>) session.getAttribute("basket");
        if (basket != null && !basket.isEmpty()) {
            List<Book> booksInBasket = basket.stream()
                    .map(bookService::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            model.addAttribute("booksInBasket", booksInBasket);
        }
        return "basket";
    }

    /**
     * Usuwa książkę z koszyka zakupowego.
     *
     * @param bookIdToRemove Identyfikator książki do usunięcia z koszyka.
     * @param session        Sesja HTTP.
     * @return Przekierowanie na stronę koszyka zakupowego.
     */
    @PostMapping(params = "removeFromBasket=true")
    public String removeFromBasket(@RequestParam("bookIdToRemove") Long bookIdToRemove, HttpSession session) {
        List<Long> basket = (List<Long>) session.getAttribute("basket");
        if (basket != null) {
            basket.remove(bookIdToRemove);
        }
        return "redirect:/basket";
    }

    /**
     * Realizuje proces wypożyczania książek z koszyka.
     *
     * @param session Sesja HTTP.
     * @return Przekierowanie na stronę koszyka zakupowego lub na stronę logowania, jeśli użytkownik nie jest zalogowany.
     */
    @PostMapping(params = "borrowBooks=true")
    public String borrowBooks(HttpSession session) {
        UserData loggedInUser = (UserData) session.getAttribute("currentUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        List<Long> basket = (List<Long>) session.getAttribute("basket");
        if (basket != null && !basket.isEmpty()) {
            for (Long bookId : basket) {
                OrderBooks order = new OrderBooks();
                order.setUserData(loggedInUser);
                order.setBook(bookService.findById(bookId).orElse(null));
                order.setStartDateOrder(LocalDate.now());
                order.setEndDateOrder(LocalDate.now().plusDays(14)); // 2 tygodnie od dziś
                order.setPaymentOrder(BigDecimal.ZERO);
                orderBooksService.save(order);
            }
            basket.clear();

            return "redirect:/basket";
        } else {
            return "redirect:/basket";
        }
    }
}
