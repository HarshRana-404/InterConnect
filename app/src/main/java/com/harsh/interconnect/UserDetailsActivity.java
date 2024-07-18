package com.harsh.interconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.harsh.interconnect.globaldata.GlobalData;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class UserDetailsActivity extends AppCompatActivity {

    TextView tvPersonName, tvPersonInterests, tvPersonGender, tvPersonEmail;
    ImageView ivPersonProfile;
    ImageButton ibBack;
    Button btnChat;
    ProgressBar pbPerson;
    FirebaseFirestore fbStore;
    StorageReference fbDatabase;
    String personUID="", personName="", personInterests="", personGender="", personEmail="";
    Boolean personHasProfile=false;
    public static Float profileRotation=0.f;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        pbPerson = findViewById(R.id.pb_person);
        tvPersonName = findViewById(R.id.tv_person_name);
        tvPersonInterests = findViewById(R.id.tv_person_interests);
        tvPersonEmail = findViewById(R.id.tv_person_email);
        tvPersonGender = findViewById(R.id.tv_person_gender);
        ivPersonProfile = findViewById(R.id.iv_person_profile);
        ibBack = findViewById(R.id.ib_person_back);
        btnChat = findViewById(R.id.btn_chat);
        fbStore = FirebaseFirestore.getInstance();
        try{

            ibBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            personUID = GlobalData.getDetailsForUID;
            Task<DocumentSnapshot> docRef = fbStore.collection("people").document(personUID).get();
            docRef.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    try{
                        pbPerson.setVisibility(View.GONE);
                        personName = documentSnapshot.getString("name");
                        personInterests = documentSnapshot.getString("interests");
                        personInterests = personInterests.replaceAll("@", ", ");
                        personInterests = personInterests.substring(0, personInterests.length()-2);
                        personGender = documentSnapshot.getString("gender");
                        if(personGender.equals("male")){
                            tvPersonGender.setText("Gender: Male");
                            tvPersonGender.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_male, 0, 0, 0);
                        }else if(personGender.equals("female")){
                            tvPersonGender.setText("Gender: Female");
                            tvPersonGender.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_female, 0, 0, 0);
                        }
                        personEmail = documentSnapshot.getString("email");
                        personHasProfile = Boolean.valueOf(documentSnapshot.getString("hasprofile"));
                        if(personHasProfile){
                            fbStore.collection("people").document(personUID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    profileRotation = Float.parseFloat(value.getString("profilerotation"));
                                }
                            });
                            fbDatabase = FirebaseStorage.getInstance().getReference("images/profile/"+personUID);
                            fbDatabase.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Picasso.get().load(task.getResult()).into(ivPersonProfile);
                                    if(profileRotation>0.f){
                                        ivPersonProfile.setRotation(profileRotation);
                                    }
                                }
                            });
                        }else{
//                            if(personGender.equals("male")){
//                                ivPersonProfile.setImageResource(R.drawable.ic_male);
//                            }else if(personGender.equals("female")) {
//                                ivPersonProfile.setImageResource(R.drawable.ic_female);
//                            }
                            ivPersonProfile.setImageResource(R.drawable.ic_person);
                        }
                        tvPersonName.setText(personName);
                        tvPersonInterests.setText("Interests: "+personInterests);
                        tvPersonEmail.setText("Email: "+personEmail);
                    } catch (Exception e) {
                        Toast.makeText(UserDetailsActivity.this, e+"", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserDetailsActivity.this, ChatActivity.class));
                }
            });

        } catch (Exception e) {
            Toast.makeText(UserDetailsActivity.this, e+"", Toast.LENGTH_SHORT).show();
        }

    }
}