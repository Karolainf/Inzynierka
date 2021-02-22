package com.example.madremysli;

//import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {

    Button bRegister;
    EditText eName, eMail, ePassword, eRepeatPassword;
    TextView textView;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, ref, reference;
    //FirebaseUser user;

    CheckBox checkBox, checkBox1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();

        bRegister = (Button) findViewById(R.id.buttonRegister);
        eName = (EditText) findViewById(R.id.editName);
        eMail = (EditText) findViewById(R.id.editMail);
        ePassword = (EditText) findViewById(R.id.editPassword);
        eRepeatPassword = (EditText) findViewById(R.id.editRepeatPassword);
        checkBox = (CheckBox) findViewById(R.id.checkPassword);
        checkBox1 = (CheckBox) findViewById(R.id.checkRepeatPassword);
        textView = (TextView) findViewById(R.id.textLogin);

        progressDialog = new ProgressDialog(this);
        ref = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    ePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    eRepeatPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    eRepeatPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    //upload data to database

                    String userEmail = eMail.getText().toString().trim();
                    String userPassword = ePassword.getText().toString().trim();
                    final String userName = eName.getText().toString().trim();
                    //final String displayName;
                    //String d = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    //FirebaseUser user = firebaseAuth.getCurrentUser();

                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                                Toast.makeText(Registration.this, "Rejestracja udana!", Toast.LENGTH_LONG).show();
                                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
                                                FirebaseAuth.getInstance().getCurrentUser().updateProfile(userProfileChangeRequest);
                                                reference = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                Map<String, String> userData = new HashMap<String, String>();
                                                String numer = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                userData.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                                userData.put("id", numer);
                                                userData.put("nazwa", eName.getText().toString());
                                                reference.setValue(userData);
                                                //reference.setValue(userData);

                                                //Map<String, String> userrData = new HashMap<String, String>();
                                                //userrData.put("nazwa", eName.getText().toString());
                                                //reference.setValue(userrData);

                                                eName.setText("");
                                                eMail.setText("");
                                                ePassword.setText("");
                                                eRepeatPassword.setText("");

                                                startActivity(new Intent(Registration.this, Login.class));
                                        } else {
                                            Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validate() {
        Boolean result = false;

        String username = eName.getText().toString();
        String email = eMail.getText().toString();
        String password = ePassword.getText().toString();
        String repeatPassword = eRepeatPassword.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "(/^(?=.*\\d)(?=.*[A-Z])([@$%&#])[0-9a-zA-Z]{4,}$/)";


        /*(/^
        (?=.*\d)                should contain at least one digit
        (?=.*[@$%&#])           should contain at least one special char
        (?=.*[A-Z])             should contain at least one upper case
        [a-zA-Z0-9]{4,}         should contain at least 8 from the mentioned characters
        $/)
         */



        if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !repeatPassword.isEmpty() && email.matches(emailPattern) && repeatPassword.equals(password)) {
            result = true;
            //wszystko jest uzupelnione
        } else if (!email.matches(emailPattern) && !email.isEmpty()){
            eMail.setError("Wpisz poprawną formę e-maila!");
            eMail.requestFocus();
            //mail nie pasuje do formy maila
            //Toast.makeText(this, "Wpisz poprawną formę e-maila!", Toast.LENGTH_SHORT).show();
        } else if(!repeatPassword.equals(password) && !password.isEmpty()) {
            //haslo jest uzupelnione, a powtorzone haslo nie jest takie samo jak haslo
            eRepeatPassword.setError("Wpisz takie samo hasło!");
            eRepeatPassword.requestFocus();
            //Toast.makeText(this, "Wpisz takie samo hasło!", Toast.LENGTH_SHORT).show();
        }
         else {
             eName.setError("Uzupełnij nazwę użytkownika");
             eMail.setError("Uzupełnij e-maila!");
             ePassword.setError("Uzupełnij hasło!");
             eRepeatPassword.setError("Powtórz hasło!");
             eName.requestFocus();
             eMail.requestFocus();
             ePassword.requestFocus();
             eRepeatPassword.requestFocus();
          //  Toast.makeText(this, "Uzupełnij wszystkie dane", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public String checkEmailExist(final String myEmail) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.fetchSignInMethodsForEmail(myEmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if(eMail.getText().toString() != null) {
                    if(task.getResult().getSignInMethods().size() == 0) {
                        Toast.makeText(Registration.this, "Konto z podanym adresem e-mail nie istnieje!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Registration.this, "Konto z podanym adresem e-mail już istnieje!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return myEmail;
    }



}
