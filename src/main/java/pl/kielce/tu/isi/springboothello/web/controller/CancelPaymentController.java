package pl.kielce.tu.isi.springboothello.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Kontroler obsługujący anulowanie płatności w aplikacji.
 */
@Controller
@RequestMapping("/cancel")
public class CancelPaymentController {

    /**
     * Wyświetla stronę informującą o anulowaniu płatności.
     *
     * @return Nazwa widoku strony anulowania płatności.
     */
    @GetMapping
    public String showPage(){
        return "cancel";
    }
}
