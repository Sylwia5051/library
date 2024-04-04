package pl.kielce.tu.isi.springboothello.biz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Klasa reprezentująca zamówienie książek w systemie bibliotecznym.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrderBooks;

    /**
     * Książka, która jest częścią tego zamówienia.
     */
    @ManyToOne
    @JoinColumn(name = "bookOrder_id")
    private Book book;

    /**
     * Użytkownik, który złożył to zamówienie.
     */
    @ManyToOne
    @JoinColumn(name = "userOrder_id")
    private UserData userData;

    /**
     * Data rozpoczęcia zamówienia.
     */
    private LocalDate startDateOrder;

    /**
     * Data zakończenia zamówienia (przewidywana data zwrotu książki).
     */
    private LocalDate endDateOrder;

    /**
     * Kwota płatności za to zamówienie.
     */
    private BigDecimal paymentOrder;

    /**
     * Pobiera identyfikator zamówienia książek.
     *
     * @return Identyfikator zamówienia książek.
     */
    public Long getIdOrderBooks() {
        return idOrderBooks;
    }

    /**
     * Ustawia identyfikator zamówienia książek.
     *
     * @param idOrderBooks Identyfikator zamówienia książek.
     */
    public void setIdOrderBooks(Long idOrderBooks) {
        this.idOrderBooks = idOrderBooks;
    }

    /**
     * Pobiera książkę, która jest częścią tego zamówienia.
     *
     * @return Książka, która jest częścią tego zamówienia.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Ustawia książkę, która jest częścią tego zamówienia.
     *
     * @param book Książka, która jest częścią tego zamówienia.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Pobiera użytkownika, który złożył to zamówienie.
     *
     * @return Użytkownik, który złożył to zamówienie.
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     * Ustawia użytkownika, który złożył to zamówienie.
     *
     * @param userData Użytkownik, który złożył to zamówienie.
     */
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    /**
     * Pobiera datę rozpoczęcia zamówienia.
     *
     * @return Data rozpoczęcia zamówienia.
     */
    public LocalDate getStartDateOrder() {
        return startDateOrder;
    }

    /**
     * Ustawia datę rozpoczęcia zamówienia.
     *
     * @param startDateOrder Data rozpoczęcia zamówienia.
     */
    public void setStartDateOrder(LocalDate startDateOrder) {
        this.startDateOrder = startDateOrder;
    }

    /**
     * Pobiera datę zakończenia zamówienia (przewidywaną datę zwrotu książki).
     *
     * @return Data zakończenia zamówienia.
     */
    public LocalDate getEndDateOrder() {
        return endDateOrder;
    }

    /**
     * Ustawia datę zakończenia zamówienia (przewidywaną datę zwrotu książki).
     *
     * @param endDateOrder Data zakończenia zamówienia.
     */
    public void setEndDateOrder(LocalDate endDateOrder) {
        this.endDateOrder = endDateOrder;
    }

    /**
     * Pobiera kwotę płatności za to zamówienie.
     *
     * @return Kwota płatności za to zamówienie.
     */
    public BigDecimal getPaymentOrder() {
        return paymentOrder;
    }

    /**
     * Ustawia kwotę płatności za to zamówienie.
     *
     * @param paymentOrder Kwota płatności za to zamówienie.
     */
    public void setPaymentOrder(BigDecimal paymentOrder) {
        this.paymentOrder = paymentOrder;
    }
}
