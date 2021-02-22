package com.example.madremysli;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterChoiceQuotes extends RecyclerView.Adapter<AdapterChoiceQuotes.MyViewHolder> {

    ArrayList<ChoiceStructure> list;
    FirebaseUser firebaseUser;
    DatabaseReference ref, reference;
    // String key = ref.getKey();
    //  int value = Integer.parseInt(key);
    public AdapterChoiceQuotes(ArrayList<ChoiceStructure> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterChoiceQuotes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_setchoicequotes, parent, false);
        return new AdapterChoiceQuotes.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterChoiceQuotes.MyViewHolder holder, final int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        holder.tresc.setText(list.get(position).getTresc());
        holder.autor.setText(list.get(position).getAutor());
        holder.kategoria.setText(list.get(position).getKategoria());

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tresc, autor, kategoria;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tresc = itemView.findViewById(R.id.tresc);
            autor = itemView.findViewById(R.id.autor);
            kategoria = itemView.findViewById(R.id.kategoria);
        }

    }
}
