package pl.kielce.tu.isi.springboothello.biz.enums;

/**
 * Wyliczenie (enum) reprezentujące różne kategorie książek.
 */
public enum Genre {
    FOR_KIDS(1,"Dla dzieci"),
    KITCHEN(2,"Kuchnia i diety"),
    POETRY(3,"Poezja"),
    BIOGRAPHY(4,"Biografia"),
    BUSINESS(5,"Biznes, ekonomia, marketing"),
    FANTASY(6,"Fantastyka, horror"),
    HISTORY(7,"Historia"),
    DRAMA(8,"Dramat"),
    HEALTH(9,"Zdrowie, rodzina, związki"),
    COMIC_BOOK(10,"Komiks");

    private int position;
    private String text;

    /**
     * Konstruktor enuma Genre.
     *
     * @param position Numer porządkowy gatunku.
     * @param text     Tekstowy opis gatunku.
     */
    Genre(int position, String text) {
        this.position=position;
        this.text = text;
    }

    /**
     * Pobiera tekstowy opis gatunku.
     *
     * @return Tekstowy opis gatunku.
     */
    public String getText() {
        return text;
    }
}
