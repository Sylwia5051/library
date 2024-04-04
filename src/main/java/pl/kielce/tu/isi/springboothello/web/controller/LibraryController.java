package pl.kielce.tu.isi.springboothello.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.isi.springboothello.biz.model.Book;
import pl.kielce.tu.isi.springboothello.biz.service.BookService;

import java.util.List;

/**
 * Kontroler obsługujący operacje związane z przeglądaniem i wybieraniem książek w bibliotece.
 */
@Controller
@RequestMapping("/library")
public class LibraryController {
    private BookService bookService;

    /**
     * Konstruktor klasy "LibraryController".
     *
     * @param bookService Serwis obsługujący operacje z książkami.
     */
    public LibraryController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Metoda modelująca atrybut "randomBooks" dla widoku.
     *
     * @return Lista losowo wybranych książek do wyświetlenia na stronie głównej biblioteki.
     */
    @ModelAttribute("randomBooks")
    public List<Book> randomBooks(){
        return bookService.showRandomBooks();
    }

    /**
     * Wyświetla stronę główną biblioteki.
     *
     * @return Nazwa widoku strony głównej biblioteki.
     */
    @GetMapping
    public String showPage(){
        return "library";
    }

    /**
     * Wylogowuje użytkownika i przekierowuje na stronę logowania.
     *
     * @param session Sesja HTTP.
     * @return Przekierowanie na stronę logowania.
     */
    @PostMapping(params = "logout=true")
    public String logoutUser(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }

    /**
     * Wybiera konkretną książkę w celu wyświetlenia jej szczegółów.
     *
     * @param idBook  Identyfikator wybranej książki.
     * @param session Sesja HTTP.
     * @return Przekierowanie na stronę ze szczegółami wybranej książki.
     */
    @PostMapping(params = "chooseBook=true")
    public String chooseProduct(@RequestParam(name = "selectedIdBooksShop", required = false) Long idBook, HttpSession session) {
        if (idBook != null) {
            session.setAttribute("selectedBookId", idBook);
            return "redirect:/bookDetails";
        }
        return "library";
    }

}
