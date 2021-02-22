package com.example.madremysli;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.List;

public class QuoteRanking {

    private String autor;
    private String tresc;
    private String kategoria;
    private int polubienie;

    DatabaseReference ref;

    public QuoteRanking() {
    }

    public QuoteRanking(String autor, String tresc, String kategoria,  int polubienie) {
        this.autor = autor;
        this.tresc = tresc;
        this.kategoria = kategoria;
        this.polubienie = polubienie;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public String getPolubienie() {
        return String.valueOf(polubienie);
    }

    public void setPolubienie(int polubienie)
    {
        this.polubienie = polubienie;
    }
}

