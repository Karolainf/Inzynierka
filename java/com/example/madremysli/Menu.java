package com.example.madremysli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Menu extends AppCompatActivity {

    private Button ranking;
    private Button myProfile;
    private Button quotes;
    private Button myQuotes;
    private Button znajomi;
    private Button wyloguj;
    DatabaseReference reference, ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ref = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");

        ranking = (Button) findViewById(R.id.ranking);
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRanking();
            }
        });

        myProfile = (Button) findViewById(R.id.profil);
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfile();
            }
        });

        quotes = (Button) findViewById(R.id.cytat);
        quotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuotes();
            }
        });

        myQuotes = (Button) findViewById(R.id.myQuotes);
        myQuotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyQuotes();
            }
        });

        znajomi = (Button) findViewById(R.id.znajomi);
        znajomi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFriends();
            }
        });

        wyloguj = (Button) findViewById(R.id.wyloguj);
        wyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openWyloguj();
            }
        });

    }

        public void openRanking() {
            Intent intent = new Intent(this, Ranking.class); //chcemy otworzyc klase Ranking
            startActivity(intent);
        }

    public void openMyProfile() {
        Intent intentp = new Intent(this, MyProfile.class); //chcemy otworzyc klase Profil
        startActivity(intentp);
    }

    public void openQuotes() {
        Intent intentc = new Intent(this, Quotes.class);
        startActivity(intentc);
    }

    public void openMyQuotes() {
        Intent intentm = new Intent(this, MyQuotes.class);
        startActivity(intentm);
    }

    public void openFriends() {
        Intent intentz = new Intent(this, Friends.class);
        startActivity(intentz);
    }

    public void openWyloguj() {
        Intent intentw = new Intent(this, LogOut.class);
        startActivity(intentw);
    }

}
