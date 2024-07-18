package com.harsh.interconnect.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.harsh.interconnect.R;
import com.harsh.interconnect.globaldata.GlobalData;
import com.squareup.picasso.Picasso;

public class ProfileEditorActivity extends AppCompatActivity {

    ImageView ivProfileEdit;
    Button btnRotate, btnSetProfile, btnProfileCancel;

    StorageReference fbDatabase;
    FirebaseFirestore fbStore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editor);

        ivProfileEdit = findViewById(R.id.iv_profile_edit);
        btnRotate = findViewById(R.id.btn_profile_rotate);
        btnSetProfile = findViewById(R.id.btn_profile_set);
        btnProfileCancel = findViewById(R.id.btn_profile_cancel);

        try{
//            Bundle bn = getIntent().getBundleExtra("bundle");
//            if(bn.getString("profileUri")!=null){
//                ivProfileEdit.setImageURI(Uri.parse(bn.getString("profileUri")));
//            }

            fbStore = FirebaseFirestore.getInstance();
            fbDatabase = FirebaseStorage.getInstance().getReference("images/profile/"+ GlobalData.fbAuth.getUid());
            fbDatabase.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Picasso.get().load(task.getResult()).into(ivProfileEdit);
                    }
                }
            });
        } catch (Exception e) {

        }

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivProfileEdit.setRotation(ivProfileEdit.getRotation()+90);
            }
        });
        btnSetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("profilerotation", String.valueOf((ivProfileEdit.getRotation()%360))).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        onBackPressed();
                    }
                });
            }
        });
        btnProfileCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    removeProfileImage();
                } catch (Exception e) {

                }
            }
        });

    }
    public void removeProfileImage(){
        try{
            fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("hasprofile", "false").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    fbDatabase.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ProfileEditorActivity.this, "Profile upload cancelled!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });
                }
            });
        } catch (Exception e) {}
    }
}