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

    public boolean setDelete(final int position) {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {

            reff = FirebaseDatabase.getInstance().getReference().child("cytaty");
            dr = FirebaseDatabase.getInstance().getReference().child("cytaty").child(reff.getKey());
            ref = FirebaseDatabase.getInstance().getReference();
            reference = ref.child("cytaty").child(String.valueOf(position)).child("uzytkownik");
            databaseReference = FirebaseDatabase.getInstance().getReference();
            dataRef = (databaseReference.child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("dodane").child(String.valueOf(position))).removeValue();


            getKlucz();
          //  String dss = dr.getKey();
         //   dr.child(dss).setValue("aaaa");

            //dr.child(dss).removeValue();
            //String dddd = dr.child("klucz").getKey();
          //  dr.child(dddd).setValue("aaaaa");


            refere = reff;

            refere.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        if(key.equals(ds.child("klucz").getValue()) && FirebaseAuth.getInstance().getCurrentUser().getUid().equals(ds.child("uzytkownik").getValue())) {
                            Map<String, Object> userData = new HashMap<String, Object>();
                           // userData.put("tresc", "aaaaaa");
                            dr.child(key).child("klucz").removeValue();
                            //dr.child(key).setValue("aAAAAAAAAAAA");
                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





        } else if(Profile.getCurrentProfile() != null) {
            ref = FirebaseDatabase.getInstance().getReference();
            reference = ref.child("cytaty").child(String.valueOf(position)).child("uzytkownik");
            //databaseReference = FirebaseDatabase.getInstance().getReference();
            //dataRef = (databaseReference.child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("dodane").child(String.valueOf(position))).updateChildren(null);

        }
        return true;
    }

    public ToggleButton getEdit() {
        return edit;
    }

    public boolean setEdit(int position) {
        this.edit = edit;
        return true;
    }
}
