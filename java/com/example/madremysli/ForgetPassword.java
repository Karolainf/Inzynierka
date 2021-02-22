package com.example.madremysli;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.opencensus.tags.Tag;

public class ForgetPassword extends AppCompatActivity {

    Button forgetPassword;
    EditText yourEmail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetPassword = (Button) findViewById(R.id.forgetPassword);
        yourEmail = (EditText) findViewById(R.id.yourEmail);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkEmailExist(yourEmail.getText().toString());
            }
        });
    }

    public String checkEmailExist(String email) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (yourEmail.getText().toString() != null) {
                    if (task.getResult().getSignInMethods().size() == 0) {
                        Toast.makeText(ForgetPassword.this, "Konto z podanym mailem nie istnieje!", Toast.LENGTH_LONG).show();
                    } else {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(yourEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ForgetPassword.this, "Wiadamość z instrukcją jak zresetować hasło została wysłana na podany adres e-mail", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgetPassword.this, Login.class));
                            }
                        });
                    }
                }
            }
        });
        return email;
    }

}
