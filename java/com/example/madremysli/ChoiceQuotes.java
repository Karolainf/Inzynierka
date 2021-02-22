package com.example.madremysli;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ChoiceQuotes extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    DatabaseReference ref;
    ArrayList<ChoiceStructure> list;
    RecyclerView recyclerView;
    SearchView searchViewAuthors, searchViewCategories;
    Switch findQuote;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        ref = FirebaseDatabase.getInstance().getReference().child("cytaty");
        recyclerView = findViewById(R.id.rv);
        searchViewAuthors = findViewById(R.id.searchViewAuthors);
        searchViewCategories = findViewById(R.id.searchViewCategories);
        findQuote = (Switch) findViewById(R.id.findQ);
        textView = findViewById(R.id.sortBy);

        findQuote.setOnCheckedChangeListener(this);

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
                            list.add(ds.getValue(ChoiceStructure.class));
                        }
                        AdapterChoiceQuotes adapterChoiceQuotes = new AdapterChoiceQuotes(list);
                        recyclerView.setAdapter(adapterChoiceQuotes);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ChoiceQuotes.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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

        ArrayList<ChoiceStructure> myList = new ArrayList<>();
        for(ChoiceStructure object : list)
        {
            if(object.getAutor() != null && object.getAutor().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        AdapterChoiceQuotes adapterChoiceQuotes = new AdapterChoiceQuotes(myList);
        recyclerView.setAdapter(adapterChoiceQuotes);
    }

    private void searchCategories(String str)
    {
        ArrayList<ChoiceStructure> categoriesList = new ArrayList<>();
        for(ChoiceStructure object : list)
        {
            if(object.getKategoria() != null && object.getKategoria().toLowerCase().contains(str.toLowerCase()))
            {
                categoriesList.add(object);
            }
        }
        AdapterChoiceQuotes adapterChoiceQuotes = new AdapterChoiceQuotes(categoriesList);
        recyclerView.setAdapter(adapterChoiceQuotes);
    }

}
