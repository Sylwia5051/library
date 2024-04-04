package pl.kielce.tu.isi.springboothello.web.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kielce.tu.isi.springboothello.biz.enums.Genre;
import pl.kielce.tu.isi.springboothello.biz.model.Book;
import pl.kielce.tu.isi.springboothello.biz.service.BookService;
import pl.kielce.tu.isi.springboothello.data.FileStorageRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Kontroler obsługujący operacje administracyjne na bibliotece.
 */
@Controller
@RequestMapping("/libraryAdministrator")
@Log4j2
public class LibraryAdminController {
    public static final String DISPO = """
            attachment; filename="%s"
            """;
    private BookService bookService;
    private FileStorageRepository fileStorageRepository;

    /**
     * Konstruktor klasy "LibraryAdminController".
     *
     * @param bookService           Serwis obsługujący operacje z książkami.
     * @param fileStorageRepository Repozytorium do przechowywania plików.
     */
    public LibraryAdminController(BookService bookService, FileStorageRepository fileStorageRepository) {
        this.bookService = bookService;
        this.fileStorageRepository = fileStorageRepository;
    }

    /**
     * Metoda modelująca atrybut "books" dla widoku.
     *
     * @return Lista wszystkich książek w bibliotece.
     */
    @ModelAttribute("books")
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    /**
     * Metoda modelująca atrybut "book" dla widoku.
     *
     * @return Nowa instancja klasy "Book".
     */
    @ModelAttribute
    public Book getBook() {
        return new Book();
    }

    /**
     * Wyświetla stronę zarządzania biblioteką.
     *
     * @return Nazwa widoku strony administracyjnej biblioteki.
     */
    @GetMapping
    public String showBookPage() {
        return "libraryAdministrator";
    }

    /**
     * Pobiera zasób graficzny (obraz) z repozytorium i zwraca go w odpowiedzi HTTP.
     *
     * @param resource Nazwa zasobu do pobrania.
     * @return Odpowiedź HTTP z zasobem graficznym (obrazem).
     */
    @GetMapping("/images/{resource}")
    public ResponseEntity<Resource> getResource(@PathVariable String resource) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, format(DISPO, resource))
                .body(fileStorageRepository.findByName(resource));
    }

    /**
     * Zapisuje informacje o nowej książce i jej okładce w bibliotece.
     *
     * @param book          Nowa książka do zapisania.
     * @param errors        Błędy walidacji danych książki.
     * @param multipartFile Plik zawierający okładkę książki.
     * @return Przekierowanie na stronę administracyjną biblioteki.
     * @throws IOException Wyjątek zgłaszany w przypadku problemów z zapisem plików.
     */
    @PostMapping
    public String saveBook(@Valid Book book, Errors errors, @RequestParam("photoFilename") MultipartFile multipartFile) throws IOException {
        try {
            fileStorageRepository.save(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
            bookService.save(book,multipartFile.getInputStream());
            return "redirect:libraryAdministrator";
        } catch (Exception e) {
            e.printStackTrace();
            return "libraryAdministrator";
        }
    }

    /**
     * Usuwa wybrane książki z biblioteki.
     *
     * @param selections Lista identyfikatorów książek do usunięcia.
     * @return Przekierowanie na stronę administracyjną biblioteki.
     * @throws IOException Wyjątek zgłaszany w przypadku problemów z usuwaniem plików.
     */
    @PostMapping(params = "delete=true")
    public String deleteBook(@RequestParam Optional<List<Long>> selections) throws IOException {
        log.info(selections);
        if(selections.isPresent()){
            bookService.deleteAllById(selections.get());
        }
        return "redirect:libraryAdministrator";
    }

    /**
     * Edytuje wybraną książkę w bibliotece.
     *
     * @param selections Lista identyfikatorów książek do edycji.
     * @param model      Model danych do przekazania na stronę.
     * @return Przekierowanie na stronę administracyjną biblioteki z danymi edytowanej książki.
     */
    @PostMapping(params = "edit=true")
    public String editBook(@RequestParam List<Long> selections, Model model){
        log.info(selections);
        if (selections != null) {
            Optional<Book> book = bookService.findById(selections.get(0));
            model.addAttribute("book",book);
        }
        return "libraryAdministrator";
    }

}
