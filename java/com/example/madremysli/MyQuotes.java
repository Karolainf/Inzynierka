package com.example.madremysli;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyQuotes extends AppCompatActivity {

    DatabaseReference reference, ref, databaseReference, dataBase;
    ArrayList<MyQuoteStructure> list;
    ArrayList<Object> kejs;
    //ArrayList<String> keysss;
    RecyclerView recyclerView;
    Button button;
    Query query, query2, quer;
    AdapterMyQuotes adapterMyQuotes;
    ImageView usun;
    TextView textView;
    MyQuoteStructure myQuoteStructure;
    DatabaseReference dr;

    AddQuote addQuote;


    public void removeItem(int position) {
        list.remove(position);
        adapterMyQuotes.notifyItemRemoved(position);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quotes);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            dataBase = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("dodane");
        } else if(Profile.getCurrentProfile() != null) {
            dataBase = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("dodane");
        }

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            quer = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("dodane");
        } else if(Profile.getCurrentProfile() != null) {
            quer = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("dodane");
        }

        textView = findViewById(R.id.textView);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("cytaty");
        reference = FirebaseDatabase.getInstance().getReference().child("cytaty");
        ref = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");
        dr = FirebaseDatabase.getInstance().getReference();
        query = FirebaseDatabase.getInstance().getReference().child("cytaty");
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            query2 = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("dodane");
        } else if(Profile.getCurrentProfile() != null) {
            query2 = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("dodane");
        }
        kejs = new ArrayList<>();

        recyclerView = findViewById(R.id.rv);

        button = (Button) findViewById(R.id.buttonAddNew);
        usun = findViewById(R.id.delete);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddQuote();
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (reference != null) {

           reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            String firebaseAuth = FirebaseAuth.getInstance().getCurrentUser().getUid();
                           // if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                                //adapterMyQuotes.

                                list = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                                    Boolean currentUserr = ds.child("cytaty").child("uzytkownik").equals(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    if (ds.child("uzytkownik").getValue() != null && ds.child("uzytkownik").getValue().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                        // if (currentUserr.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) && FirebaseAuth.getInstance().getCurrentUser().getEmail() != null && currentUserr != null) {
                                        list.add(ds.getValue(MyQuoteStructure.class));
                                        textView.setVisibility(View.GONE);



                                    } else  {
                                        textView.setVisibility(View.VISIBLE);
                                    }
                                }

                                final AdapterMyQuotes adapterMyQuotes = new AdapterMyQuotes(list);
                                recyclerView.setAdapter(adapterMyQuotes);
                                adapterMyQuotes.setOnItemClickListener(new AdapterMyQuotes.OnItemClickListener() {
                                    @Override
                                    public void onDeleteClick(final int position) {

                                       query.addListenerForSingleValueEvent(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                               for(DataSnapshot ds: dataSnapshot.getChildren()) {



                                                       Map<String, Object> userData = new HashMap<>();

                                                       userData.put(list.get(position).getKlucz(), null);


                                                       dataBase.updateChildren(userData);
                                                       reference.updateChildren(userData);




                                               }
                                           }

                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                           }
                                       });



                                                adapterMyQuotes.notifyItemRemoved(position);
                                                adapterMyQuotes.notifyDataSetChanged();


                                    }

                                    @Override
                                    public void onFirstDelete(final int position) {

                                        quer.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot ds: dataSnapshot.getChildren()) {



                                                    Map<String, Object> userData = new HashMap<>();

                                                    userData.put(list.get(position - 1).getKlucz(), null);


                                                    dataBase.updateChildren(userData);
                                                    reference.updateChildren(userData);




                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        adapterMyQuotes.notifyItemRemoved(position - 1);
                                        adapterMyQuotes.notifyDataSetChanged();
                                    }

                                });
                        } else if (Profile.getCurrentProfile() != null) {


                                list = new ArrayList<>();
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    if (ds.child("uzytkownik").getValue() != null && ds.child("uzytkownik").getValue().equals(Profile.getCurrentProfile().getId())) {
                                        list.add(ds.getValue(MyQuoteStructure.class));
                                        textView.setVisibility(View.GONE);

                                    }else {
                                        textView.setVisibility(View.VISIBLE);
                                    }
                                }

                                final AdapterMyQuotes adapterMyQuotes = new AdapterMyQuotes(list);
                                recyclerView.setAdapter(adapterMyQuotes);
                                adapterMyQuotes.setOnItemClickListener(new AdapterMyQuotes.OnItemClickListener() {
                                    @Override
                                    public void onDeleteClick(final int position) {

                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot ds: dataSnapshot.getChildren()) {

                                                  //  if(list.get(position).getKlucz().equals(dataSnapshot.child("klucz").getValue())) {


                                                        Map<String, Object> userData = new HashMap<>();

                                                        userData.put(list.get(position - 1).getKlucz(), null);


                                                        dataBase.updateChildren(userData);
                                                        reference.updateChildren(userData);
                                                   // }





                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        adapterMyQuotes.notifyItemRemoved(position - 1);
                                        adapterMyQuotes.notifyDataSetChanged();


                                    }

                                    @Override
                                    public void onFirstDelete(final int position) {

                                    }

                                });
                           // }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MyQuotes.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void openAddQuote() {
        Intent intent = new Intent(this, AddQuote.class);
        startActivity(intent);
    }
}

