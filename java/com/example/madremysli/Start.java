package com.example.madremysli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Start extends AppCompatActivity {

    private static int TIME_OUT = 2000; //Time to launch the another activity

    FirebaseAuth firebaseAuth;
   // FirebaseUser user = firebaseAuth.getCurrentUser();
  //  String facebookUser = Profile.getCurrentProfile().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final View myLayout = findViewById(R.id.start);

        firebaseAuth = FirebaseAuth.getInstance();

        checkIfLogged();
    }

    public void checkIfLogged() {
        if ((FirebaseAuth.getInstance().getCurrentUser()) != null) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent i = new Intent(Start.this, Menu.class);
                    startActivity(i);
                    finish();
                }
            }, TIME_OUT);
        } else if(Profile.getCurrentProfile() != null) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent i = new Intent(Start.this, Menu.class);
                    startActivity(i);
                    finish();
                }
            }, TIME_OUT);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent i = new Intent(Start.this, Choice.class);
                    startActivity(i);
                    finish();
                }
            }, TIME_OUT);
        }
    }
}
