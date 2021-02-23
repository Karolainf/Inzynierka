package com.example.madremysli;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class AddQuote extends AppCompatActivity {

    //String facebookUsername = Profile.getCurrentProfile().getName();
    //String username = FirebaseAuth.getInstance().getCurrentUser().toString();
   // Cytat cytat;
    EditText editText, editText1, editText2;
    Button button;
    String id;
    int like = 0;
    DatabaseReference databaseReference, ref, reference, dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("cytaty");
        ref = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            dataBase = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("dodane");
        } else if (Profile.getCurrentProfile() != null) {
            dataBase = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("dodane");
        }

        button = (Button) findViewById(R.id.buttonAdd);
        editText = (EditText) findViewById(R.id.editTextT);
        editText1 = (EditText) findViewById(R.id.editTextA);
        editText2 = (EditText) findViewById(R.id.editTextK);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuotes();
            }
        });


    }
    public void addQuotes() {
        String quoteName = editText.getText().toString();
        String quoteAuthor = editText1.getText().toString();
        String quoteCategory = editText2.getText().toString();
        String quoteKlucz;
        int quoteLike = like;

        if(Profile.getCurrentProfile() != null && !TextUtils.isEmpty(quoteName) && (!TextUtils.isEmpty(quoteAuthor)) && (!TextUtils.isEmpty(quoteCategory)))
        {
            id = databaseReference.push().getKey();

            quoteKlucz = id;

            AddQuoteHelper addQuoteHelper = new AddQuoteHelper(Profile.getCurrentProfile().getId(), quoteName, quoteAuthor, quoteCategory, quoteLike, quoteKlucz);

            // databaseReference.child(id).setValue(addQuoteHelper);

            //nadpisuje
            reference = ref.child(Profile.getCurrentProfile().getId()).child("dodane");

            Map<String, Object> userData = new HashMap<String, Object>();
            userData.put(id, 1);


            databaseReference.child(id).setValue(addQuoteHelper);
            dataBase.updateChildren(userData);

            editText.setText("");
            editText1.setText("");
            editText2.setText("");

            Toast.makeText(AddQuote.this, "Dodano nowy cytat!", Toast.LENGTH_LONG).show();

        }
        else if(FirebaseAuth.getInstance().getCurrentUser() != null && (!TextUtils.isEmpty(quoteName) && (!TextUtils.isEmpty(quoteAuthor)) && (!TextUtils.isEmpty(quoteCategory))))          {
            id = databaseReference.push().getKey();

            quoteKlucz = id;
            //nie wyswietla się nazwa użytkownika

            AddQuoteHelper addQuoteHelper = new AddQuoteHelper(FirebaseAuth.getInstance().getCurrentUser().getUid(), quoteName, quoteAuthor, quoteCategory, quoteLike, quoteKlucz);

           // databaseReference.child(id).setValue(addQuoteHelper);

            //nadpisuje
            reference = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("dodane");
            //Map<Object, String> userData = new HashMap<Object, String>();

            Map<String, Object> userData = new HashMap<String, Object>();
            // userData.put(id, "cytat");
            userData.put(id, 1);
            //   databaseReference =


            databaseReference.child(id).setValue(addQuoteHelper);
            dataBase.updateChildren(userData);

            editText.setText("");
            editText1.setText("");
            editText2.setText("");

            Toast.makeText(AddQuote.this, "Dodano nowy cytat!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(AddQuote.this, "Uzupełnij wszystkie dane!", Toast.LENGTH_LONG).show();
        }
    }
}
