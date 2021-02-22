package com.example.madremysli;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.MyViewHolder> {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("cytaty").child("polubienie");//.orderByChild();

    ArrayList<QuoteRanking> list;
    public AdapterRanking(ArrayList<QuoteRanking> list)
    {
            this.list = list;
    }

    @NonNull
    @Override
    public AdapterRanking.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_setrankingquotes, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRanking.MyViewHolder holder, int position) {
        holder.tresc.setText(list.get(position).getTresc());
        holder.autor.setText(list.get(position).getAutor());
        holder.kategoria.setText(list.get(position).getKategoria());
        holder.polubienie.setText(list.get(position).getPolubienie());

//        linearLayoutManager.setReverseLayout(true);

         // Collections.sort();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tresc, autor, kategoria, polubienie;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tresc = itemView.findViewById(R.id.tresc);
            autor = itemView.findViewById(R.id.autor);
            kategoria = itemView.findViewById(R.id.kategoria);
            polubienie = itemView.findViewById(R.id.polubienie);
        }
    }
}
