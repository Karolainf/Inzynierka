package com.example.madremysli;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.Profile;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class MyQuoteStructure {

    private String autor;
    private String tresc;
    private String kategoria;
    String klucz;
    private int polubienie;
    private String uzytkownik;
    AdapterAllQuotes.MyViewHolder holder;
    ToggleButton delete, edit;
    ImageView dd;
    DatabaseReference ref, reff;
    DatabaseReference reference;
    DatabaseReference databaseReference, dr;
    Task<Void> dataRef;
    Query refere;


    public MyQuoteStructure() {
    }

    public MyQuoteStructure(String autor, String tresc, String kategoria, int polubienie, String uzytkownik, ToggleButton delete, ToggleButton edit, String klucz) {
        this.autor = autor;
        this.tresc = tresc;
        this.kategoria = kategoria;
        this.polubienie = polubienie;
        this.uzytkownik = uzytkownik;
        this.klucz = klucz;
        this.delete = delete;
        this.edit = edit;
    }

    public void changeTextt(String text) {
        tresc = text;
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

    public void setPolubienie(int polubienie) {
        this.polubienie = polubienie;
    }

    public String getKlucz() {
        return klucz;
    }

    public void setKlucz(String klucz) {
        this.klucz = klucz;
    }

    public String getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(String uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public ToggleButton getDelete() {
        return delete;
    }
}
