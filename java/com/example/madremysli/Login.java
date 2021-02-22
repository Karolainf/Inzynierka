package com.example.madremysli;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    TextView textView, textView2;
    Button button;
    EditText editText1, editText2;

    DatabaseReference ref, reference;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = (TextView) findViewById(R.id.textRegister);
        button = (Button) findViewById(R.id.buttonLogin);
        editText1 = (EditText) findViewById(R.id.editMailLogin);
        editText2 = (EditText) findViewById(R.id.editPasswordLogin);
        textView2 = (TextView) findViewById(R.id.forgetPassword);
        FirebaseAuthInvalidUserException invalidEmail;
        ref = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");

        firebaseAuth = FirebaseAuth.getInstance();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(editText1.getText().toString(), editText2.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FriendStructure friend = new FriendStructure();
                                if(task.isSuccessful()) {
                                    if(firebaseAuth.getCurrentUser().isEmailVerified()) {
                                        startActivity(new Intent(Login.this, Menu.class));
                                    }else {
                                        Toast.makeText(Login.this, "Zweryfikuj swój adres email!", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Nieprawidłowy adres e-mail lub hasło!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
              //  login(editText1.toString(), editText2.toString());
            }
        });

    }
}
