package com.harsh.interconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirestoreKt;
import com.google.firebase.firestore.QuerySnapshot;
import com.harsh.interconnect.fragments.MyAccount;
import com.harsh.interconnect.fragments.MyInterests;
import com.harsh.interconnect.fragments.People;
import com.harsh.interconnect.globaldata.GlobalData;
import com.harsh.interconnect.service.NewMessageService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavMain;

    FirebaseFirestore fbStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavMain = findViewById(R.id.main_bottom_nav);
        fbStore = FirebaseFirestore.getInstance();

//        fbStore.collection("people").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                List<DocumentSnapshot> people = value.getDocuments();
//                for(int i=0;i<people.size();i++){
//
//                }
//            }
//        });

        startForegroundService(new Intent(this, NewMessageService.class));
//        startActivity(new Intent(MainActivity.this, PaymentActivity.class));

        setFragment(new MyInterests());

        bottomNavMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.mi_my_interests){
                    setFragment(new MyInterests());
                }else if(item.getItemId() == R.id.mi_people){
                    setFragment(new People());
                }else if(item.getItemId() == R.id.mi_my_account){
                    setFragment(new MyAccount());
                }
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        bottomNavMain.setSelectedItemId(R.id.mi_my_interests);
    }

    public void setFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_frame_layout, fragment);
        ft.commit();
    }
}