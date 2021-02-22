package com.example.madremysli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    TextView loginStatusText;
    CallbackManager callbackManager;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference, ref;
    ProfileTracker profileTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); //it initializes itself
        setContentView(R.layout.activity_main);
        loginButton = (LoginButton)findViewById(R.id.lb);
        callbackManager = CallbackManager.Factory.create();

        ref = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        this.stopTracking();
                        Profile.setCurrentProfile(currentProfile);
                        Profile profile1 = Profile.getCurrentProfile();
                        reference = ref.child(profile1.getId());
                        Map<String, Object> userData = new HashMap<String, Object>();
                        userData.put("nazwa", profile1.getFirstName());
                        reference.updateChildren(userData);
                    }
                };
                profileTracker.startTracking();
                //What happens when the user logs in.

                //if(Profile.getCurrentProfile() != null) {

              //  }
                // loginStatusText.setText("Login Sucessfull \n" + loginResult.getAccessToken().getUserId() + "\n" +loginResult.getAccessToken().getToken());
                Intent testactivty = new Intent(MainActivity.this, Menu.class);
                startActivity(testactivty);


            }

            @Override
            public void onCancel() {

                loginStatusText.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
