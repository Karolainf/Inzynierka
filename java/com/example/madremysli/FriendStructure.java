package com.example.madremysli;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FriendStructure {

    private String nazwa;
    private String ulubionyCytat;
    String obserwuj;
    String numer;
    ImageView zdjecie;
    AdapterFriends.MyViewHolder holder;
    ToggleButton toggleButton; //dodawanie znajomych
    DatabaseReference reference, ref;


    public FriendStructure() {
    }

    public FriendStructure( String nazwa, String ulubionyCytat, ToggleButton toggleButton, String numer, String obserwuj) {
       // this.zdjecie = zdjecie;
        this.nazwa = nazwa;
        this.ulubionyCytat = ulubionyCytat;
        this.toggleButton = toggleButton;
        this.numer = numer;
        this.obserwuj = obserwuj;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getUlubionyCytat() {
        return ulubionyCytat;
    }

    public void setUlubionyCytat(String ulubionyCytat) {
        this.ulubionyCytat = ulubionyCytat;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public String getObserwuj() {
        return obserwuj;
    }

    public void setObserwuj(String obserwuj) {
        this.obserwuj = obserwuj;
    }

    /* public ImageView getZdjecie() {
        return zdjecie;
    }

   public void setZdjecie(ImageView zdjecie) {
        this.zdjecie = zdjecie;
    }*/

    public ToggleButton getToggleButton() {
        return toggleButton;
    }

    public void setToggleButton(ToggleButton toggleButton) {
        this.toggleButton = toggleButton;
    }

    public String sendInvitation(int position) {
       // ref = FirebaseDatabase.getInstance().getReference().child("cytaty");
        //String key = ref.getKey();
        //  String key = ref.getKey();
//        value = Integer.parseInt(key);

        //zmiana  w bazie danych
       // ref.child(String.valueOf(position)).child("polubienie").setValue(++polubienie);


        //ref.child(key).child("polubienie").setValue(++polubienie);
        //ref.child("polubienie").setValue(++polubienie);
        //System.out.println(key);
        return String.valueOf(++position);
    }

    public String cancelInvitation(int position)
    {

        ref = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("znajomi");
        //  String key = ref.getKey();
        //  value = Integer.parseInt(key);
        //String key = ref.getKey();
        //ref.child("polubienie").setValue(--polubienie);
        //ref.child("polubienie").getKey())
        // if(polubienie != 0) {
        // ref.child(key).child("polubienie").setValue(--polubienie);
      //  ref.child(String.valueOf(position)).child("polubienie").setValue(--polubienie);
        // }
        return String.valueOf(--position);
    }

    public int setButtonStart(int position) {
        //  toggleButton.setBackgroundResource(position);
        return R.drawable.ic_add;
    }

    public int setButtonSent(int position) {
        // holder.toggleButton.setBackgroundResource(position);
        return R.drawable.ic_tick;
    }

}
