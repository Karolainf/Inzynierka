package com.example.madremysli;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.RelativeDateTimeFormatter;
import android.provider.ContactsContract;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAllQuotes extends RecyclerView.Adapter<AdapterAllQuotes.MyViewHolder> {

    ArrayList<QuoteStructure> list;
    FirebaseUser firebaseUser;
    private OnItemClickListener mListener;
    DatabaseReference ref, reference, databaseReference, dataBase;
    private static boolean isClickedDislike = false;
    private static boolean isClickedLike = false;
    boolean clicked = true;
    public static int position;
    Quotes quotes;
    Query query, query2;
    public static ToggleButton likeButton;

    private GestureDetector gestureDetector;
    //public static ToggleButton likeButton;
    //public static int position;
    //public static OnItemClickListener listener;

    public static int i = 0;

    boolean [] state;

    public interface OnItemClickListener {
        void onChangeClick(int position);
        void onUnlikeClick(int position);
        void toggleState(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        //listener.toggleState(position);
        mListener = listener;
    }


    public AdapterAllQuotes(ArrayList<QuoteStructure> list) {
        state = new boolean[list.size()];
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_setallquotes, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        holder.tresc.setText(list.get(position).getTresc());
        holder.autor.setText(list.get(position).getAutor());
        holder.kategoria.setText(list.get(position).getKategoria());
        holder.polubienie.setText(list.get(position).getPolubienie());

        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSn : dataSnapshot.getChildren()) {
                    if ((dataSnapshot.child(list.get(position).getKlucz()).exists())) {
                        holder.likeButton.setBackgroundResource(R.drawable.star_like);
                    } else if (!(dataSnapshot.child(list.get(position).getKlucz()).exists())) {
                        holder.likeButton.setBackgroundResource(R.drawable.star_dislike);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tresc, autor, kategoria, polubienie, klucz;
        // ImageButton like, dislike;
        ToggleButton likeButton;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                query2 = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("polubione");
            } else if(Profile.getCurrentProfile() != null) {
                query2 = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("polubione");
            }
            tresc = itemView.findViewById(R.id.tresc);
            autor = itemView.findViewById(R.id.autor);
            kategoria = itemView.findViewById(R.id.kategoria);
            polubienie = itemView.findViewById(R.id.polubienie);

            likeButton = itemView.findViewById(R.id.tb);



            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clicked) {
                        if (listener != null) {

                                final int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {

                                    //likeButton.setBackgroundResource(R.drawable.star_like);
                                    listener.onChangeClick(position);
                                    listener.onUnlikeClick(position);
                                   // listener.toggleState(position);



                                }
                        }
                    } else {
                        if (listener != null) {
                        final int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                           // likeButton.setBackgroundResource(R.drawable.star_like);



                        }
                        }
                    }
                        }
            });


            }
        }


}

