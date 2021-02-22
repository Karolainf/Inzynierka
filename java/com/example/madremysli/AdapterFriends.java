package com.example.madremysli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFriends extends RecyclerView.Adapter<AdapterFriends.MyViewHolder> {

    ArrayList<FriendStructure> list;
    FirebaseUser firebaseUser;
    DatabaseReference ref, reference;
    private OnItemClickListener mListener;

    Query query2;

    int i = 0;

    boolean clicked = true;

    public interface OnItemClickListener {
        void onChangeClick(int position);
        void toggleState(int position);

    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public AdapterFriends(ArrayList<FriendStructure> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_set_friends, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        ref = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        holder.nazwa.setText(list.get(position).getNazwa());
        holder.ulubionyCytat.setText(list.get(position).getUlubionyCytat());

        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (dataSnapshot.child(list.get(position).getNumer()).exists()) {
                        holder.toggleButton.setBackgroundResource(R.drawable.ic_tick);
                        holder.obserwuj.setText("Obserwujesz!");
                    } else if (!dataSnapshot.child(list.get(position).getNumer()).exists()) {
                        holder.toggleButton.setBackgroundResource(R.drawable.ic_add);
                        holder.obserwuj.setText("Zaobserwuj!");
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
        TextView nazwa, ulubionyCytat, numer;
        ToggleButton toggleButton;
        TextView obserwuj;


        // ImageButton like, dislike;
       // Button button;
       // ImageView zdjecie;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                query2 = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("znajomi");
            } else if(Profile.getCurrentProfile() != null) {
                query2 = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("znajomi");
            }

            nazwa = itemView.findViewById(R.id.nazwa);
            ulubionyCytat = itemView.findViewById(R.id.ulubionyCytat);
            toggleButton = itemView.findViewById(R.id.tb);
            obserwuj = itemView.findViewById(R.id.dodaj);



            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clicked) {
                        if (listener != null) {


                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {

                                    listener.onChangeClick(position);

                                }

                        }
                    }
                }
            });



        }

    }
}
