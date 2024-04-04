package pl.kielce.tu.isi.springboothello.web.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Locale;

/**
 * Formatter służący do formatowania i parsowania obiektów klasy MultipartFile.
 */
@Component
public class MultipartFormatter implements Formatter<MultipartFile> {

    /**
     * Parsuje tekstową reprezentację pliku na obiekt MultipartFile.
     *
     * @param text   Tekstowa reprezentacja pliku.
     * @param locale Lokalizacja (nie używana w tym przypadku).
     * @return Obiekt MultipartFile, który reprezentuje przesłany plik.
     * @throws ParseException Wyjątek zgłaszany, gdy nie można sparsować tekstu na obiekt MultipartFile.
     */
    @Override
    public MultipartFile parse(String text, Locale locale) throws ParseException {
        return null;
    }

    /**
     * Formatuje obiekt MultipartFile na jego oryginalną nazwę pliku.
     *
     * @param object Obiekt MultipartFile, który ma być sformatowany.
     * @param locale Lokalizacja (nie używana w tym przypadku).
     * @return Oryginalna nazwa pliku z obiektu MultipartFile.
     */
    @Override
    public String print(MultipartFile object, Locale locale) {
        return object.getOriginalFilename();
    }
}
