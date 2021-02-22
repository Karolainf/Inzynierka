package com.example.madremysli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Ranking extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<QuoteRanking> list;
    RecyclerView recyclerView;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ref = FirebaseDatabase.getInstance().getReference().child("cytaty");
        query = FirebaseDatabase.getInstance().getReference().child("cytaty").orderByChild("polubienie");

        recyclerView = findViewById(R.id.rv);


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (query != null) {

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                    {

                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(QuoteRanking.class));
                        }
                        AdapterRanking adapterRanking = new AdapterRanking(list);
                        Collections.reverse(list);
                        recyclerView.setAdapter(adapterRanking);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Ranking.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}