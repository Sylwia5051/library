package pl.kielce.tu.isi.springboothello.web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.kielce.tu.isi.springboothello.biz.model.Book;
import pl.kielce.tu.isi.springboothello.biz.model.BookComments;
import pl.kielce.tu.isi.springboothello.biz.service.BookCommentsService;
import pl.kielce.tu.isi.springboothello.biz.service.BookService;
import pl.kielce.tu.isi.springboothello.biz.service.OrderBooksService;
import pl.kielce.tu.isi.springboothello.data.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Kontroler obsługujący operacje związane z widokiem szczegółów książki.
 */
@Controller
@RequestMapping("/bookDetails")
@SessionAttributes({"selectedBookId","selectedIdBooksShop"})
public class BookDetailsController {
    private BookService bookService;
    private BookCommentsService bookCommentsService;
    private OrderBooksService orderBooksService;

    /**
     * Konstruktor klasy "BookDetailsController" inicjalizujący kontroler szczegółów książki.
     *
     * @param bookService          Serwis obsługujący operacje z książkami.
     * @param bookCommentsService  Serwis obsługujący operacje z komentarzami do książek.
     * @param orderBooksService    Serwis obsługujący operacje z wypożyczeniami książek.
     */
    public BookDetailsController(BookService bookService, BookCommentsService bookCommentsService,OrderBooksService orderBooksService) {
        this.bookService = bookService;
        this.bookCommentsService = bookCommentsService;
        this.orderBooksService=orderBooksService;
    }

    /**
     * Metoda wywoływana podczas inicjalizacji modelu. Pobiera dostępne książki do modelu.
     *
     * @return Lista dostępnych książek.
     */
    @ModelAttribute("books")
    public List<Book> getRooms() {
        return bookService.findAll();
    }

    /**
     * Wyświetla stronę szczegółów książki na podstawie identyfikatora książki pobranego z sesji użytkownika.
     * Jeśli identyfikator książki nie jest dostępny lub książka nie istnieje, użytkownik zostaje przekierowany na stronę biblioteki.
     *
     * @param session Sesja HTTP zawierająca identyfikator wybranej książki.
     * @param model   Model danych, do którego dodawane są informacje o wybranej książce, komentarzach oraz dostępności książki wypożyczenia.
     * @return Nazwa widoku strony szczegółów książki lub przekierowanie na stronę biblioteki w przypadku błędu.
     */
    @GetMapping
    public String showPage(HttpSession session, Model model) {
        Long bookId = (Long) session.getAttribute("selectedBookId");

        if (bookId != null) {
            Optional<Book> book = bookService.findById(bookId);
            if (book.isPresent()) {
                List<BookComments> comments = bookService.findBookCommentsById(bookId).orElseGet(ArrayList::new);
                boolean isOnLoan = orderBooksService.isBookOnLoan(bookId);
                model.addAttribute("book", book.get());
                model.addAttribute("comments", comments);
                model.addAttribute("isOnLoan", isOnLoan);
                return "bookDetails";
            }
        }
        session.removeAttribute("selectedBookId");
        return "library";
    }

    /**
     * Dodaje nowy komentarz do książki i przekierowuje użytkownika na stronę szczegółów książki.
     *
     * @param commentText Tekst nowego komentarza.
     * @param bookId      Identyfikator książki, do której dodawany jest komentarz.
     * @param session     Sesja HTTP.
     * @return Przekierowanie na stronę szczegółów książki.
     */
    @PostMapping(params = "addComment=true")
    public String addComments(@RequestParam("commentText") String commentText, @RequestParam("bookId") Long bookId, HttpSession session) {
        Optional<Book> book = bookService.findById(bookId);

        if (book.isPresent() && commentText != null && !commentText.trim().isEmpty()) {
            BookComments newComment = new BookComments();
            newComment.setBook(book.get());
            newComment.setDescription(commentText);
            bookCommentsService.addNewComment(newComment);

            session.setAttribute("selectedBookId", bookId);
            return "redirect:/bookDetails";
        } else {
            return "redirect:/bookDetails";
        }
    }

    /**
     * Dodaje książkę do koszyka zakupowego i przekierowuje użytkownika na stronę koszyka.
     *
     * @param bookId       Identyfikator książki, którą użytkownik chce dodać do koszyka.
     * @param session      Sesja HTTP.
     * @return Przekierowanie na stronę koszyka zakupowego.
     */
    @PostMapping(params = "orderBooks=true")
    public String addToBasket(@RequestParam("bookIdToOrder") Long bookId, HttpSession session) {
        List<Long> basket = (List<Long>) session.getAttribute("basket");

        if (basket == null) {
            basket = new ArrayList<>();
            session.setAttribute("basket", basket);
        }
        basket.add(bookId);
        return "redirect:/basket";
    }
}