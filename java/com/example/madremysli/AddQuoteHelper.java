package com.example.madremysli;

public class AddQuoteHelper {

    private String tresc;
    private String autor;
    private String kategoria;
    private String uzytkownik;
    private int polubienie;
    String klucz;

    public AddQuoteHelper() {
    }

    public AddQuoteHelper(String uzytkownik, String tresc, String autor, String kategoria, int polubienie, String klucz) {
        this.uzytkownik = uzytkownik;
        this.tresc = tresc;
        this.autor = autor;
        this.kategoria = kategoria;
        this.polubienie = 0;
        this.klucz = klucz;
    }

    public String getUzytkownik() {
        return uzytkownik;
    }

    public String getTresc() {
        return tresc;
    }

    public String getAutor() {
        return autor;
    }

    public String getKategoria() {
        return kategoria;
    }

    public int getPolubienie() {
        return polubienie;
    }

    public String getKlucz() {
        return klucz;
    }
}
