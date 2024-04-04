//package pl.kielce.tu.isi.springboothello.biz.service;
// TODO
//
//import com.paypal.api.payments.*;
//import com.paypal.base.rest.APIContext;
//import com.paypal.base.rest.PayPalRESTException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Serwis do integracji z PayPal API, umożliwiający tworzenie i wykonanie płatności PayPal.
// */
//@Service
//public class PaypalService {
//    @Autowired
//    private APIContext apiContext;
//
//    /**
//     * Tworzy płatność PayPal.
//     *
//     * @param total       Całkowita kwota płatności.
//     * @param currency    Kod waluty "PLN".
//     * @param method      Metoda płatności, "paypal".
//     * @param intent      Intencja płatności, "wypożyczenie".
//     * @param description Opis transakcji.
//     * @param cancelUrl   URL do przekierowania użytkownika w przypadku anulowania płatności.
//     * @param successUrl  URL do przekierowania po udanej płatności.
//     * @return Obiekt Payment reprezentujący zainicjowaną płatność.
//     * @throws PayPalRESTException W przypadku błędów komunikacji z PayPal API.
//     */
//    public Payment createPayment(
//            Double total,
//            String currency,
//            String method,
//            String intent,
//            String description,
//            String cancelUrl,
//            String successUrl) throws PayPalRESTException{
//        BigDecimal roundedTotal = BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP);
//
//        Amount amount = new Amount();
//        amount.setCurrency(currency);
////        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
////        amount.setTotal(String.format("%.2f", total));
//        amount.setTotal(roundedTotal.toString());
//
//        Transaction transaction = new Transaction();
//        transaction.setDescription(description);
//        transaction.setAmount(amount);
//
//        List<Transaction> transactions = new ArrayList<>();
//        transactions.add(transaction);
//
//        Payer payer = new Payer();
//        payer.setPaymentMethod(method.toString());
//
//        Payment payment = new Payment();
//        payment.setIntent(intent.toString());
//        payment.setPayer(payer);
//        payment.setTransactions(transactions);
//        RedirectUrls redirectUrls = new RedirectUrls();
//        redirectUrls.setCancelUrl(cancelUrl);
//        redirectUrls.setReturnUrl(successUrl);
//        payment.setRedirectUrls(redirectUrls);
//
//        return payment.create(apiContext);
//    }
//
//    /**
//     * Wykonuje płatność PayPal na podstawie ID płatności i ID płatnika.
//     *
//     * @param paymentId ID płatności do wykonania.
//     * @param payerId   ID płatnika.
//     * @return Obiekt Payment reprezentujący wykonaną płatność.
//     * @throws PayPalRESTException W przypadku błędów komunikacji z PayPal API.
//     */
//    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
//        Payment payment = new Payment();
//        payment.setId(paymentId);
//        PaymentExecution paymentExecute = new PaymentExecution();
//        paymentExecute.setPayerId(payerId);
//        return payment.execute(apiContext, paymentExecute);
//    }
//
//}