//package pl.kielce.tu.isi.springboothello.web.controller;
// TODO
//
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Order;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import pl.kielce.tu.isi.springboothello.biz.model.OrderBooks;
//import pl.kielce.tu.isi.springboothello.biz.service.OrderBooksService;
//import pl.kielce.tu.isi.springboothello.biz.service.PaypalService;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
///**
// * Kontroler obsługujący płatności PayPal w aplikacji bibliotecznej.
// */
//@Controller
//@RequestMapping("/paypal")
//@SessionAttributes("loanId")
//public class PaypalController {
//    @Autowired
//    PaypalService service;
//
//    private OrderBooksService orderBooksService;
//
//    /**
//     * Konstruktor klasy "PaypalController".
//     *
//     * @param orderBooksService Serwis do obsługi zamówień książek.
//     */
//    public PaypalController(OrderBooksService orderBooksService) {
//        this.orderBooksService = orderBooksService;
//    }
//
//    public static final String SUCCESS_URL = "success";
//    public static final String CANCEL_URL = "cancel";
//
////    @GetMapping()
////    public String home() {
////        return "paypal";
////    }
//
//    /**
//     * Wyświetla stronę płatności PayPal.
//     *
//     * @param session Obiekt sesji HTTP.
//     * @param model   Model Spring MVC.
//     * @return Nazwa widoku.
//     */
//    @GetMapping()
//    public String showPaymentPage(HttpSession session, Model model) {
//        Long loanId = (Long) session.getAttribute("loanId");
//        if (loanId == null) {
//            model.addAttribute("errorMessage", "Nie znaleziono identyfikatora wypożyczenia.");
//            return "error";
//        }
//
//        Optional<OrderBooks> loanOptional = orderBooksService.findById(loanId);
//        if (loanOptional.isPresent()) {
//            OrderBooks loan = loanOptional.get();
//            model.addAttribute("loan", loan);
//            model.addAttribute("paymentOrder", loan.getPaymentOrder());
//            model.addAttribute("description", "Płatność za opóźnienie zwrotu wypożyczonej książki - " + loan.getBook().getBookTitle());
//            model.addAttribute("loanId", loan.getIdOrderBooks());
//        } else {
//            model.addAttribute("errorMessage", "Nie znaleziono wypożyczenia");
//            return "cancel";
//        }
//        return "paypal";
//    }
//
//    /**
//     * Rozpoczyna proces płatności PayPal.
//     *
//     * @param loanId        ID zamówienia książki.
//     * @param model         Model Spring MVC.
//     * @param session       Obiekt sesji HTTP.
//     * @param redirectAttrs Atrybuty przekierowania.
//     * @return URL przekierowania.
//     * @throws PayPalRESTException W przypadku błędów komunikacji z PayPal API.
//     */
//    @PostMapping(params = "payAmountDue=true")
//    public String payAmount(@RequestParam("loanId") Long loanId, Model model, HttpSession session, RedirectAttributes redirectAttrs) throws PayPalRESTException {
//        Optional<OrderBooks> loanOptional = orderBooksService.findById(loanId);
//
//        session.setAttribute("loanId", loanId);
//        model.addAttribute("loanId", loanId);
//
//        if (loanOptional.isPresent()) {
//            OrderBooks loan = loanOptional.get();
//            BigDecimal paymentAmount = loan.getPaymentOrder(); // Pobierz kwotę płatności
//            String description = "Library payment for book - " + loan.getBook().getBookTitle();
//            String cancelUrl = "http://localhost:8080/" + PaypalController.CANCEL_URL;
//            String successUrl = "http://localhost:8080/" + PaypalController.SUCCESS_URL;
//            // Utwórz płatność
//            Payment payment = service.createPayment(
//                    paymentAmount.doubleValue(),
//                    "PLN",
//                    "paypal",
//                    "sale",
//                    description,
//                    cancelUrl,
//                    successUrl
//            );
//            // Przekieruj na stronę PayPal
//            for (Links link : payment.getLinks()) {
//                if (link.getRel().equals("approval_url")) {
//                    return "redirect:" + link.getHref();
//                }
//            }
//        } else {
//            redirectAttrs.addFlashAttribute("errorMessage", "Loan not found");
//            return "redirect:/userAccount";
//        }
//        return "redirect:/";
//    }
//
//    /**
//     * Obsługuje pomyślne zakończenie płatności PayPal.
//     *
//     * @param paymentId ID płatności.
//     * @param payerId   ID płatnika.
//     * @return Nazwa widoku.
//     */
//    @GetMapping(value = SUCCESS_URL)
//    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
//        try {
//            Payment payment = service.executePayment(paymentId, payerId);
//            System.out.println(payment.toJSON());
//            if (payment.getState().equals("approved")) {
//                return "success";
//            }
//        } catch (PayPalRESTException e) {
//            System.out.println(e.getMessage());
//        }
//        return "redirect:/paypal";
//    }
//
//}
