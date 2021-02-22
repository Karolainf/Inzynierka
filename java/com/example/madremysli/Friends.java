package com.example.madremysli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Friends extends AppCompatActivity{

    DatabaseReference reference, ref, databaseReference;
    ArrayList<FriendStructure> list;
    AdapterFriends adapterFriends;
    RecyclerView recyclerView;
    Query query, query2;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        reference = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");
        recyclerView = findViewById(R.id.recyclerView);
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            query = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("znajomi");
        } else if(Profile.getCurrentProfile() != null) {
            query = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("znajomi");
        }

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            query2 = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("znajomi");
        } else if(Profile.getCurrentProfile() != null) {
            query2 = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("znajomi");
        }

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("znajomi");
        }
        else if(Profile.getCurrentProfile() != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("znajomi");
        }

    }



    @Override
    protected void onStart()
    {
        super.onStart();
        if (reference != null) {

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                if(!reference.child("numer").equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                list.add(ds.getValue(FriendStructure.class));
                                //list.add(Picasso.with(AdapterFriends.class).load(dataSnapshot.getValue().toString()));
                        } else if (Profile.getCurrentProfile() != null) {
                                if(!reference.child("numer").equals(Profile.getCurrentProfile().getId()))
                                list.add(ds.getValue(FriendStructure.class));
                            }
                        }

                        final AdapterFriends adapterFriends = new AdapterFriends(list);
                        recyclerView.setAdapter(adapterFriends);

                        adapterFriends.setOnItemClickListener(new AdapterFriends.OnItemClickListener() {
                            @Override
                            public void onChangeClick(final int position) {


                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                if(!dataSnapshot.child(list.get(position).getNumer()).exists()) {

                                                    Map<String, Object> userData = new HashMap<>();

                                                    userData.put(list.get(position).getNumer(), list.get(position).getNumer());


                                                    databaseReference.updateChildren(userData);
                                                } else if(dataSnapshot.child(list.get(position).getNumer()).exists()) {
                                                    Map<String, Object> userData = new HashMap<>();

                                                    userData.put(list.get(position).getNumer(), null);


                                                    databaseReference.updateChildren(userData);
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                //  list.get(position).getToggleButton().setBackgroundResource(R.drawable.ic_tick);

                            }

                            @Override
                            public void toggleState(final int position) {





                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Friends.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
