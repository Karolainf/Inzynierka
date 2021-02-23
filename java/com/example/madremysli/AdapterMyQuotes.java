package com.example.madremysli;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterMyQuotes extends RecyclerView.Adapter<AdapterMyQuotes.MyViewHolder> {

    ArrayList<MyQuoteStructure> list;
    private OnItemClickListener mListener;
    FirebaseUser firebaseUser;
    DatabaseReference ref, reference;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdapterMyQuotes(ArrayList<MyQuoteStructure> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterMyQuotes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_setmyquotes, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        ref = FirebaseDatabase.getInstance().getReference().child("cytaty");
        reference = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        holder.autor.setText(list.get(position).getAutor());
        holder.tresc.setText(list.get(position).getTresc());
        holder.kategoria.setText(list.get(position).getKategoria());
        holder.polubienie.setText(list.get(position).getPolubienie());

    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView autor, tresc, kategoria, polubienie;
        ImageView usun;
        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            autor = itemView.findViewById(R.id.autor);
            tresc = itemView.findViewById(R.id.tresc);
            kategoria = itemView.findViewById(R.id.kategoria);
            polubienie = itemView.findViewById(R.id.polubienie);
            //edit = itemView.findViewById(R.id.edit);
            usun = itemView.findViewById(R.id.delete);


           usun.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(listener != null) {
                      // if(list.size() != 0) {
                       int position = getAdapterPosition();
                       if (position != RecyclerView.NO_POSITION) {


                               list.remove(list.get(position).getKlucz());
                               listener.onDeleteClick(position);
                               notifyItemRangeChanged(position, getItemCount());

                                 notifyDataSetChanged();

                       }


                   }
               }
           });

        }

    }
}
