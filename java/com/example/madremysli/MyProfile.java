package com.example.madremysli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MyProfile extends AppCompatActivity {

    DatabaseReference databaseReference, ref, reference, dataQuotes, dataFriends, dataFavourite, dataPhoto, dataName;
    DatabaseReference dataFriendsF, dataQuotesF, dataFavouriteF, dataPhotoF;
    private FirebaseAuth mAuth;
    Button changePicture, changeQuote;
    ImageView photoUser;
    String myFavourite;
    String myName;
    String downloadUrl;
    BitmapDrawable drawable;
    ProfilePictureView profilePictureView;
    String shared;
    SharedPreferences sharedPreferences;
    TextView textView, textViewQuote, favouriteQuote, quotesNumber, friendsNumber;
    StorageReference storageReference;
   // Profile profile = Profile.getCurrentProfile();
    //name = object.getString("name");
    //facebook_id = object.getString("id");
    //String facebookName = Profile.getCurrentProfile().getName();
    //String normalUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    private String username;
    private StorageReference storageRef, mStorageRef;
    //String userID = Profile.getCurrentProfile().getId();
    //Profile profile;
    //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("cytaty").child("uzytkownik");
    //child("uzytkownik").getValue(String.class).equals(username);

    int numberOfQuotes, numberOfFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mAuth = FirebaseAuth.getInstance();
//        username = Profile.getCurrentProfile().getName();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");
        storageReference = FirebaseStorage.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        textView = (TextView) findViewById(R.id.textViewName);
        textViewQuote = (TextView) findViewById(R.id.quotesNumber);
        profilePictureView = (ProfilePictureView) findViewById(R.id.facebookPhoto);
        changePicture = (Button) findViewById(R.id.changePhoto);
        photoUser = (ImageView) findViewById(R.id.userPhoto);
        changeQuote = (Button) findViewById(R.id.changeQuote);
        favouriteQuote = (TextView) findViewById(R.id.favouriteQuote);
        friendsNumber = (TextView) findViewById(R.id.friendsNumber);
        quotesNumber = (TextView) findViewById(R.id.quotesNumber);



        if(Profile.getCurrentProfile() != null) {
            profilePictureView.setVisibility(View.VISIBLE);
            textView.setText(Profile.getCurrentProfile().getFirstName());
            profilePictureView.setProfileId(Profile.getCurrentProfile().getId());

            dataQuotesF = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("dodane");
            dataFavouriteF = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("ulubionyCytat");
            dataFriendsF = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(Profile.getCurrentProfile().getId()).child("znajomi");


            dataFavouriteF.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null) {
                        myFavourite = dataSnapshot.getValue().toString();
                        favouriteQuote.setText(myFavourite);
                    } else {
                        favouriteQuote.setText(" ");
                        changeQuote.setText("USTAW ULUBIONY CYTAT");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            dataFriendsF.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataFriendsF != null) {
                        numberOfFriends = (int) dataSnapshot.getChildrenCount();
                        friendsNumber.setText(Integer.toString(numberOfFriends));
                    } else {
                        friendsNumber.setText("0");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            dataQuotesF.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataQuotesF != null) {
                        numberOfQuotes = (int) dataSnapshot.getChildrenCount();
                        quotesNumber.setText(Integer.toString(numberOfQuotes));
                    } else {
                        quotesNumber.setText("0");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if(FirebaseAuth.getInstance().getCurrentUser() != null) {

            //sharedPreferences.getString(photoUser);
            dataQuotes = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("dodane");
            dataFriends = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("znajomi");
            dataFavourite = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ulubionyCytat");
            dataPhoto = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("zdjecie");
            dataName = FirebaseDatabase.getInstance().getReference().child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("nazwa");


            dataName.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null) {
                        myName = dataSnapshot.getValue().toString();
                        textView.setText(myName);
                    } else {
                        textView.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            dataFavourite.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null) {
                        myFavourite = dataSnapshot.getValue().toString();
                        favouriteQuote.setText(myFavourite);
                    } else {
                        favouriteQuote.setText(" ");
                        changeQuote.setText("USTAW ULUBIONY CYTAT");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            dataQuotes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null) {
                        numberOfQuotes = (int) dataSnapshot.getChildrenCount();
                        quotesNumber.setText(Integer.toString(numberOfQuotes));
                    } else {
                        quotesNumber.setText("0");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            dataFriends.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null) {
                        numberOfFriends = (int) dataSnapshot.getChildrenCount();
                        friendsNumber.setText(Integer.toString(numberOfFriends));
                    } else {
                        friendsNumber.setText("0");
                    }
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            photoUser.setVisibility(View.VISIBLE);
            changePicture.setVisibility(View.VISIBLE);

        }


        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });

        changeQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuote();
            }
        });


        /*if(Profile.getCurrentProfile() != null) {
            dataPhotoF.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            photoUser.setVisibility(View.VISIBLE);
                            profilePictureView.setVisibility(View.GONE);
                           Picasso.with(MyProfile.this).load(dataSnapshot.getValue().toString()).fit().into(photoUser);
                        } else {
                            photoUser.setVisibility(View.GONE);
                            profilePictureView.setVisibility(View.VISIBLE);
                        }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }*/

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            dataPhoto.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //photoUser.setBackgroundResource(Integer.parseInt(dataSnapshot.getValue().toString()));

                        if (dataSnapshot.getValue() != null) {
                            Picasso.with(MyProfile.this).load(dataSnapshot.getValue().toString()).fit().into(photoUser);
                        }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Uri imageUri = data.getData();
                    photoUser.setImageURI(imageUri);
                    //String uriGet = sharedPreferences.getString("image", null);
                    //photoUser.setImageURI(Uri.parse(uriGet));
                    //String url = String.valueOf(mStorageRef.child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profilowe").child("zdjecie").getDownloadUrl());
                    uploadImage(imageUri);
                    sharedPreferences.edit().putString(shared, String.valueOf(imageUri)).apply();
                } /*else if(Profile.getCurrentProfile() != null) {
                    Uri imageUri = data.getData();
                    photoUser.setImageURI(imageUri);
                    //String uriGet = sharedPreferences.getString("image", null);
                    //photoUser.setImageURI(Uri.parse(uriGet));
                    //String url = String.valueOf(mStorageRef.child("uzytkownicy").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profilowe").child("zdjecie").getDownloadUrl());
                    uploadImageF(imageUri);
                    sharedPreferences.edit().putString(shared, String.valueOf(imageUri)).apply();
                }*/
            }
        }
    }


    private void uploadImage(final Uri uriImage) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            final StorageReference storage = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");
            storage.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String photou = uri.toString();
                                        DatabaseReference reference = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        Map<String, Object> userData = new HashMap<String, Object>();
                                        userData.put("zdjecie", photou);
                                        reference.updateChildren(userData);
                                        //Picasso.with(MyProfile.this).load(photou).fit().into(photoUser);


                                    }
                                });
                                downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();


                        }
                    });
                }
            });

        }
    }



    public void addQuote() {
        Intent intent = new Intent(this, SetFavouriteQuote.class);
        startActivity(intent);
    }

}
