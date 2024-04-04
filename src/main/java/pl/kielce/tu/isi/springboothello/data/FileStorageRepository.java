package pl.kielce.tu.isi.springboothello.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

/**
 * Klasa odpowiedzialna za operacje związane z przechowywaniem i obsługą plików w systemie.
 */
@Repository
public class FileStorageRepository {

    @Value("${STORAGE_FOLDER}")
    private String storageFolder;

    /**
     * Zapisuje przesłany plik do określonego folderu.
     *
     * @param originalFilename Nazwa oryginalna pliku.
     * @param inputStream      Strumień wejściowy zawierający plik do zapisu.
     */
    public void save(String originalFilename, InputStream inputStream){
        try {
            Path filePath = Path.of(storageFolder).resolve(originalFilename).normalize();
            Files.copy(inputStream,filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wyszukuje plik o podanej nazwie.
     *
     * @param filename Nazwa pliku do wyszukania.
     * @return Zasób (Resource) reprezentujący znaleziony plik lub null, jeśli plik nie istnieje.
     */
    public Resource findByName(String filename){
        try {
            Path filePath = Path.of(storageFolder).resolve(filename).normalize();
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Usuwa wszystkie pliki o podanych nazwach.
     *
     * @param filenames Zbiór nazw plików do usunięcia.
     * @throws IOException Wyrzucane, jeśli wystąpi błąd podczas usuwania plików.
     */
    public void deleteAllByName(Set<String> filenames) throws IOException {
        for (String filename : filenames) {
            Path filePath = Path.of(storageFolder).resolve(filename).normalize();
            Files.deleteIfExists(filePath);
        }
    }
}
