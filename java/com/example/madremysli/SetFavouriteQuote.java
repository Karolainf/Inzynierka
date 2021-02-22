package com.example.madremysli;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SetFavouriteQuote extends AppCompatActivity {

    private Button setQuote;
    private EditText editText;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_favourite_quote);

        setQuote = (Button) findViewById(R.id.setFavourite);
        editText = (EditText) findViewById(R.id.favouriteQuote);



        setQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    String myFavourite = editText.getText().toString().trim();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    // DatabaseReference reference = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ulubionyCytat");
                    Map<String, Object> userData = new HashMap<String, Object>();
                    userData.put("ulubionyCytat", myFavourite);
                    databaseReference.updateChildren(userData);
                } else if(Profile.getCurrentProfile() != null) {
                    String myFavourite = editText.getText().toString().trim();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId());
                    // DatabaseReference reference = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ulubionyCytat");
                    Map<String, Object> userData = new HashMap<String, Object>();
                    userData.put("ulubionyCytat", myFavourite);
                    databaseReference.updateChildren(userData);
                }
            }
        });
    }
}


