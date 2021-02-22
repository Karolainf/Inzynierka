package com.example.madremysli;

import android.widget.ToggleButton;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class QuoteStructure {

    private String autor;
    private String tresc;
    private String kategoria;
    private int polubienie;
    String klucz;
    String dataKeys;
    AdapterAllQuotes.MyViewHolder holder;
    ToggleButton toggleButton;
    Query query;
    DatabaseReference ref, reference, databaseReference, dataBase;


    public QuoteStructure() {
    }

    public QuoteStructure(String autor, String tresc, String kategoria, int polubienie, ToggleButton toggleButton, String klucz) {
        this.autor = autor;
        this.tresc = tresc;
        this.kategoria = kategoria;
        this.klucz = klucz;
        this.polubienie = polubienie;
        this.toggleButton = toggleButton;

    }

    public ToggleButton getToggleButton() {
        return toggleButton;
    }

    public void setToggleButton(ToggleButton toggleButton) {
        this.toggleButton = toggleButton;
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

    public int setPolubienie() {
        this.polubienie = polubienie;
        ref = FirebaseDatabase.getInstance().getReference().child("cytaty");
        ref.child("polubienie").setValue(++polubienie);
        return polubienie;
    }

    public String getKlucz() {
        return klucz;
    }

    public void setKlucz(String klucz) {
        this.klucz = klucz;
    }

    public int setToggleButtonLike(int star_like) {
        return R.drawable.star_like;
    }

    public int setToggleButtonDislike(int star_dislike) {
        return R.drawable.star_dislike;
    }
}
