package com.harsh.interconnect.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.harsh.interconnect.ChatActivity;
import com.harsh.interconnect.R;
import com.harsh.interconnect.adapters.InterestsAdapter;
import com.harsh.interconnect.globaldata.GlobalData;
import com.harsh.interconnect.models.InterestModel;

import java.util.ArrayList;

public class MyInterests extends Fragment {

    String interests[] = {"Select an interest", "Acting", "Baking", "Badminton", "Basketball", "Bike riding", "Bird watching", "Board games", "Boxing", "Carrom", "Car driving", "Chess", "Cooking", "Creative writing", "Cricket", "Crocheting", "Dancing", "Drawing", "FieldHockey", "Fishing", "Football", "Gardening", "Geocaching", "Glassblowing", "Hiking", "Horseback riding", "Jewelry making", "Kabaddi", "Kho-kho", "Knitting", "Learning a new language", "Meditation", "Metal detecting", "Metalworking", "Model building", "Origami", "Painting", "Photography", "Playing a musical instrument", "Playing sports", "Playing trivia", "Playing video games", "Pottery", "Puzzles", "Reading", "Rock climbing", "Running", "Sailing", "Scrapbooking", "Sewing", "Singing", "Snooker", "Sudoku", "Surfing", "Swimming", "Taking online courses", "Taking a cooking class", "Taking a dance class", "Tennis", "Travel", "Volunteering", "Woodworking", "Yoga", "Wrestling"};
    Spinner spInterests;
    Button btnAddAsInterest;
    ProgressBar pbMyInterests;
    FirebaseFirestore fbStore;
    String fbInterests;
    RecyclerView rvInterests;
    public static TextView tvInterestTitle;
    InterestsAdapter adapterInterests;
    public static ArrayList<InterestModel> alInterest = new ArrayList<>();
    public MyInterests() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragMyInterests = inflater.inflate(R.layout.fragment_my_interests, container, false);

        fbStore = FirebaseFirestore.getInstance();
        tvInterestTitle = fragMyInterests.findViewById(R.id.tv_interest_title);
        pbMyInterests = fragMyInterests.findViewById(R.id.pb_my_interests);
        spInterests = fragMyInterests.findViewById(R.id.sp_interests);
        btnAddAsInterest = fragMyInterests.findViewById(R.id.btn_add_as_interest);
        rvInterests = fragMyInterests.findViewById(R.id.rv_interests);
        adapterInterests = new InterestsAdapter(fragMyInterests.getContext(), alInterest);
        rvInterests.setLayoutManager(new LinearLayoutManager(fragMyInterests.getContext()));
        rvInterests.setAdapter(adapterInterests);

        refreshInterests();

        spInterests.setAdapter(new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_list_item_1, interests));

        spInterests.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    btnAddAsInterest.setVisibility(View.VISIBLE);
                }else{
                    btnAddAsInterest.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        btnAddAsInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    fbStore.collection("people").document(GlobalData.fbAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            fbInterests = documentSnapshot.getString("interests");
                            if(fbInterests==null){
                                fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("interests", spInterests.getSelectedItem().toString()+"@");
                            }else{
                                if(!fbInterests.contains(spInterests.getSelectedItem().toString())) {
                                    fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("interests", fbInterests+spInterests.getSelectedItem().toString()+"@");
                                }else{
                                    Toast.makeText(fragMyInterests.getContext(), "Interest already added!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            refreshInterests();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return fragMyInterests;
    }
    public void refreshInterests(){
        try{
            Task<DocumentSnapshot> doc = fbStore.collection("people").document(GlobalData.fbAuth.getUid()).get();
            doc.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    try{
                        alInterest.clear();
                        String interests = documentSnapshot.getString("interests");
                        if(interests==null || interests.isEmpty()){
                            tvInterestTitle.setText("You don't have any Interests yet!");
                            pbMyInterests.setVisibility(View.GONE);
                        }else{
                            tvInterestTitle.setText("My Interests:");
                            String interestArray[] = interests.split("@");
                            for(String interest : interestArray){
                                alInterest.add(new InterestModel(interest));
                            }
                        }
                        pbMyInterests.setVisibility(View.GONE);
                        adapterInterests.notifyDataSetChanged();
                    } catch (Exception e) {}
                }
            });
        } catch (Exception e) {
        }
    }
    public static void refreshTitle(){
        if(alInterest.size()==0){
            tvInterestTitle.setText("You don't have any Interests yet!");
        }
    }
}