package com.example.madremysli;

import android.widget.ToggleButton;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChoiceStructure {

    private String autor;
    private String tresc;
    private String kategoria;
    AdapterAllQuotes.MyViewHolder holder;
    DatabaseReference ref, reference, databaseReference;


    public ChoiceStructure() {
    }

    public ChoiceStructure(String autor, String tresc, String kategoria) {
        this.autor = autor;
        this.tresc = tresc;
        this.kategoria = kategoria;
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

}
