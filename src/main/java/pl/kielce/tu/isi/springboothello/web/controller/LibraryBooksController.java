package pl.kielce.tu.isi.springboothello.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.isi.springboothello.biz.enums.Genre;
import pl.kielce.tu.isi.springboothello.biz.model.Book;
import pl.kielce.tu.isi.springboothello.biz.service.BookService;

import java.util.List;

/**
 * Kontroler obsługujący operacje związane z przeglądaniem i wybieraniem książek w bibliotece.
 */
@Controller
@RequestMapping("/libraryBooks")
@SessionAttributes({"loginUser", "passwordUser"})
public class LibraryBooksController {
    private BookService bookService;

    /**
     * Konstruktor klasy "LibraryBooksController".
     *
     * @param bookService Serwis obsługujący operacje z książkami.
     */
    public LibraryBooksController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Metoda modelująca atrybut "books" dla widoku.
     *
     * @return Lista wszystkich książek w bibliotece.
     */
    @ModelAttribute("books")
    public List<Book> getRooms() {
        return bookService.findAll();
    }

    /**
     * Wyświetla stronę przeglądania książek w bibliotece.
     *
     * @return Nazwa widoku strony przeglądania książek.
     */
    @GetMapping
    public String showBookPage() {
        return "libraryBooks";
    }

    /**
     * Obsługuje wybór gatunku książek oraz opcję wyszukiwania.
     *
     * @param position    Pozycja gatunku książek w liście dostępnych gatunków.
     * @param searchTerm  Szukane słowo kluczowe.
     * @param httpSession Sesja HTTP.
     * @param model       Model danych do przekazania na stronę.
     * @return Nazwa widoku strony przeglądania książek z wynikami wyszukiwania lub filtrowania.
     */
    @PostMapping(params = "confirmGenre=true")
    public String chooseGenre(@RequestParam(name = "position", required = false) Integer position,
                                               @RequestParam(name = "searchTerm", required = false) String searchTerm,
                                               HttpSession httpSession, Model model) {

        model.addAttribute("position", position);
        httpSession.setAttribute("position", position);
        model.addAttribute("searchTerm", searchTerm);
        httpSession.setAttribute("searchTerm", searchTerm);

        List<Book> books;
        Genre genre = null;

        if (position != null && position > 0) {
            int enumValue = position - 1;
            genre = Genre.values()[enumValue];
        }

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            books = bookService.searchBooksByKeyWord(searchTerm, genre);
        } else {
            if (genre != null) {
                books = bookService.filtrBookByGenre(genre);
            } else {
                books = bookService.findAll();
            }
        }

        model.addAttribute("books", books);

        return "libraryBooks";
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
    @PostMapping(params = "choose=true")
    public String chooseBook(@RequestParam(name = "selectedBookId", required = false) Long idBook, HttpSession session) {
        if (idBook != null) {
            session.setAttribute("selectedBookId", idBook);
            session.removeAttribute("selectedIdBooksShop");
            return "redirect:/bookDetails";
        }
        return "redirect:/libraryBooks";
    }

}
