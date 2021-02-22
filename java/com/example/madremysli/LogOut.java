package com.example.madremysli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogOut extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FacebookSdk facebookSdk;
    AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        Button buttonN = (Button) findViewById(R.id.bn);
        Button buttonT = (Button) findViewById(R.id.btAdd);

        firebaseAuth = FirebaseAuth.getInstance();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                updateWithToken(currentAccessToken);
            }
        };


        buttonN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent testactivty = new Intent(LogOut.this, Menu.class);
                startActivity(testactivty);
            }
        });

        buttonT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });
    }

    private void updateWithToken(final AccessToken currentAccessToken) {
        //   Intent intent = new Intent(Wyloguj.this, )
    }


    public void checkUser() {

        if((FirebaseAuth.getInstance().getCurrentUser()) != null)
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(LogOut.this, LoginForm.class);
            startActivity(intent);
        } else if (Profile.getCurrentProfile() != null)
        {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(LogOut.this, LoginForm.class);
            startActivity(intent);
        }
    }
}
