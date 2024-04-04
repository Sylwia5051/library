package pl.kielce.tu.isi.springboothello.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kielce.tu.isi.springboothello.biz.model.OrderBooks;
import pl.kielce.tu.isi.springboothello.biz.model.UserData;
import pl.kielce.tu.isi.springboothello.data.OrderBooksRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Serwis obsługujący operacje związane z wypożyczeniami książek w systemie bibliotecznym.
 */
@Service
public class OrderBooksService {
    private OrderBooksRepository orderBooksRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    /**
     * Konstruktor klasy "OrderBooksService".
     *
     * @param orderBooksRepository Repozytorium wypożyczeń książek.
     * @param bookService          Serwis książek.
     * @param userService          Serwis użytkowników.
     */
    public OrderBooksService(OrderBooksRepository orderBooksRepository, BookService bookService, UserService userService) {
        this.orderBooksRepository = orderBooksRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    /**
     * Zapisuje nowe wypożyczenie książki.
     *
     * @param orderBooks Obiekt reprezentujący nowe wypożyczenie książki.
     * @return Zapisany obiekt wypożyczenia.
     */
    @Transactional
    public OrderBooks save(OrderBooks orderBooks){
        return orderBooksRepository.save(orderBooks);
    }

    /**
     * Pobiera listę wypożyczeń użytkownika na podstawie jego identyfikatora.
     *
     * @param userId Identyfikator użytkownika, dla którego pobierane są wypożyczenia.
     * @return Lista wypożyczeń użytkownika.
     */
    public List<OrderBooks> getUserLoans(Long userId) {
        Optional<UserData> userData = userService.findById(userId);
        return orderBooksRepository.findByUserData(userData);
    }

    /**
     * Pobiera wypożyczenie książki o podanym identyfikatorze.
     *
     * @param aLong Identyfikator wypożyczenia do pobrania.
     * @return Obiekt Optional zawierający znalezione wypożyczenie lub pusty Optional, jeśli wypożyczenie nie istnieje.
     */
    public Optional<OrderBooks> findById(Long aLong){
        return orderBooksRepository.findById(aLong);
    }

    /**
     * Sprawdza, czy książka o podanym identyfikatorze jest aktualnie wypożyczona.
     *
     * @param bookId Identyfikator książki, którą chcemy sprawdzić.
     * @return True, jeśli książka jest aktualnie wypożyczona; False, jeśli książka nie jest wypożyczona.
     */
    public boolean isBookOnLoan(Long bookId) {
        List<OrderBooks> activeOrders = orderBooksRepository.findByBook_IdBookAndEndDateOrderGreaterThan(bookId, LocalDate.now());
        return !activeOrders.isEmpty();
    }

    /**
     * Aktualizuje dane wypożyczenia książki podczas jej zwrotu.
     *
     * @param loanId Identyfikator wypożyczenia do zaktualizowania.
     */
    @Transactional
    public void returnBook(Long loanId) {
        Optional<OrderBooks> loanOptional = orderBooksRepository.findById(loanId);

        loanOptional.ifPresent(loan -> {
            LocalDate now = LocalDate.now();
            if (now.isAfter(loan.getEndDateOrder())) {
                long daysLate = ChronoUnit.DAYS.between(loan.getEndDateOrder(), now);
                BigDecimal penalty = BigDecimal.valueOf(daysLate).multiply(new BigDecimal("5.00"));
                loan.setPaymentOrder(penalty);
            }
            loan.setEndDateOrder(now);
            orderBooksRepository.save(loan);
        });
    }
}
