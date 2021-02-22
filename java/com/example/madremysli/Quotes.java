package com.example.madremysli;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.SearchEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.app.SearchManager;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ToggleButton;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Quotes extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    DatabaseReference ref, reference, databaseReference;
    ArrayList<QuoteStructure> list;
    RecyclerView recyclerView;
    SearchView searchViewAuthors, searchViewCategories;
    Switch findQuote;
    TextView textView;
    Query query, query2, quer;
    int i = 0;
    ToggleButton toggleButton = AdapterAllQuotes.likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        this.i = AdapterAllQuotes.i;

        //toggleButton = findViewById(R.id.tb);

        ref = FirebaseDatabase.getInstance().getReference().child("cytaty");
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            reference = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("polubione");
        } else if(Profile.getCurrentProfile() != null) {
            reference = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("polubione");

        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");
        recyclerView = findViewById(R.id.rv);
        searchViewAuthors = findViewById(R.id.searchViewAuthors);
        searchViewCategories = findViewById(R.id.searchViewCategories);
        findQuote = (Switch) findViewById(R.id.findQ);
        toggleButton = findViewById(R.id.tb);
        textView = findViewById(R.id.sortBy);

        //toggleButton.setBackgroundResource(R.drawable.star_dislike);

//        toggleButton.setBackgroundResource(R.drawable.star_dislike);
        query = FirebaseDatabase.getInstance().getReference().child("cytaty");
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            quer = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("polubione");
        } else if(Profile.getCurrentProfile() != null) {
            quer = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("polubione");
        }

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            query2 = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("polubione");
        } else if(Profile.getCurrentProfile() != null) {
            query2 = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("polubione");
        }


        findQuote.setOnCheckedChangeListener(this);

       // i = 0;



    }

    @Override
    public  void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(findQuote.isChecked()) {
            textView.setText("Wyszukuj według autora");
            searchViewAuthors.setVisibility(View.VISIBLE);
            searchViewCategories.setVisibility(View.GONE);
        } else {
            textView.setText("Wyszukuj według kategorii");
            searchViewAuthors.setVisibility(View.GONE);
            searchViewCategories.setVisibility(View.VISIBLE);
        }
    }





    @Override
    protected void onStart()
    {
        super.onStart();
        if (ref != null) {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(QuoteStructure.class));
                          //  keyy = dataSnapshot.getRef().getKey();
                        }
                        final AdapterAllQuotes adapterAllQuotes = new AdapterAllQuotes(list);
                        recyclerView.setAdapter(adapterAllQuotes);

                        adapterAllQuotes.setOnItemClickListener(new AdapterAllQuotes.OnItemClickListener() {

                            @Override
                            public void onChangeClick(final int position) {
                               // if (i == 0) {

                                quer.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            if (dataSnapshot.child(list.get(position).getKlucz()).exists()) {
                                                //AdapterAllQuotes.likeButton.setBackgroundResource(R.drawable.star_dislike);
                                                Map<String, Object> userData = new HashMap<String, Object>();
                                                userData.put(list.get(position).getKlucz(), null);
                                                reference.updateChildren(userData);
                                            } else if (!dataSnapshot.child(list.get(position).getKlucz()).exists()) {
                                                //AdapterAllQuotes.likeButton.setBackgroundResource(R.drawable.star_like);
                                                Map<String, Object> userData = new HashMap<String, Object>();
                                                userData.put(list.get(position).getKlucz(), list.get(position).getKlucz());
                                                reference.updateChildren(userData);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                }

                            @Override
                            public void onUnlikeClick(final int position) {

                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                        for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                                            final String key = ds.getKey();

                                            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot datas) {
                                                    for (final DataSnapshot data : datas.getChildren()) {
                                                 if ((datas.child(list.get(position).getKlucz()).exists())) {
                                                        if (list.get(position).getKlucz().equals(ds.child("klucz").getValue())) {
                                                            if (!list.get(position).getPolubienie().equals("0")) {
                                                                Long count = (Long) ds.child("polubienie").getValue();
                                                                String valueKey = (String) ds.child("klucz").getValue();
                                                                Map<String, Object> userData = new HashMap<String, Object>();
                                                                userData.put("polubienie", --count);

                                                                list.get(position);
                                                                ref.child(key).updateChildren(userData);
                                                            }
                                                        }
                                                    } else if (!(datas.child(list.get(position).getKlucz()).exists())) {
                                                            if (list.get(position).getKlucz().equals(ds.child("klucz").getValue())) {
                                                                 if (!list.get(position).getKlucz().equals(data.getValue())) {
                                                                Long count = (Long) ds.child("polubienie").getValue();
                                                                Map<String, Object> userData = new HashMap<String, Object>();
                                                                userData.put("polubienie", ++count);


                                                                list.get(position);
                                                                ref.child(key).updateChildren(userData);

                                                                    }
                                                            }
                                                        }
                                                }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                            @Override
                            public void toggleState(final int position) {

                            }

                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Quotes.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (searchViewAuthors != null) {
            searchViewAuthors.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchAuthors(newText);
                    return true;
                }
            });
        }
        if (searchViewCategories != null)
        {
            searchViewCategories.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText)
                {
                    searchCategories(newText);
                    return true;
                }
            });
        }
    }

    private void searchAuthors(String str) {

        ArrayList<QuoteStructure> myList = new ArrayList<>();
        for(QuoteStructure object : list)
        {
            if(object.getAutor() != null && object.getAutor().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        AdapterAllQuotes adapterAllQuotes = new AdapterAllQuotes(myList);
        recyclerView.setAdapter(adapterAllQuotes);
    }

    private void searchCategories(String str)
    {
        ArrayList<QuoteStructure> categoriesList = new ArrayList<>();
        for(QuoteStructure object : list)
        {
            if(object.getKategoria() != null && object.getKategoria().toLowerCase().contains(str.toLowerCase()))
            {
                categoriesList.add(object);
            }
        }
        AdapterAllQuotes adapterAllQuotes = new AdapterAllQuotes(categoriesList);
        recyclerView.setAdapter(adapterAllQuotes);
    }

}

