package com.harsh.interconnect.fragments;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.harsh.interconnect.R;
import com.harsh.interconnect.adapters.PeopleAdapter;
import com.harsh.interconnect.globaldata.GlobalData;
import com.harsh.interconnect.models.PeopleModel;

import java.util.ArrayList;
import java.util.List;

public class People extends Fragment {

    RecyclerView rvPeople;
    ArrayList<PeopleModel> alPeople = new ArrayList<>();
    PeopleAdapter adapterPeople;
    FirebaseFirestore fbStore;
    String uID = "", uState="", uCity="", uInterests="";
    Boolean uHasProfile = false;
    TextView tvPeopleTitle;
    ProgressBar pbPeople;

    public People() {
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
        View fragPeople = inflater.inflate(R.layout.fragment_people, container, false);

        tvPeopleTitle = fragPeople.findViewById(R.id.tv_people_title);
        pbPeople = fragPeople.findViewById(R.id.pb_people);
        rvPeople = fragPeople.findViewById(R.id.rv_people);
        rvPeople.setLayoutManager(new LinearLayoutManager(fragPeople.getContext()));
        fbStore = FirebaseFirestore.getInstance();

        try{
            DocumentReference userDoc = fbStore.collection("people").document(GlobalData.fbAuth.getUid());
            userDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    try{
                        uID = documentSnapshot.getString("uid");
                        uState = documentSnapshot.getString("state");
                        uCity = documentSnapshot.getString("city");
                        uHasProfile = Boolean.valueOf(documentSnapshot.getString("hasprofile"));
                        uInterests = documentSnapshot.getString("interests");
                        String interestsArray[] = uInterests.split("@");

                        fbStore.collection("people").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                try{
                                    alPeople.clear();
                                    QuerySnapshot snapshots = value;
                                    List<DocumentSnapshot> docs = snapshots.getDocuments();
                                    for(DocumentSnapshot doc : docs){
                                        if(doc.getString("interests")!=null && !doc.getString("interests").isEmpty() && !uInterests.isEmpty()){
                                            if(doc.getString("state").equals(uState) && doc.getString("city").equals(uCity) && !uID.equals(doc.getString("uid"))){
                                                if(interestsArray.length>0){
                                                    for(String interest : interestsArray){
                                                        if(doc.getString("interests").contains(interest)){
                                                            alPeople.add(new PeopleModel(doc.getString("uid"), doc.getString("name"), doc.getString("gender"), doc.getString("state"), doc.getString("city"), doc.getString("interests"), Boolean.valueOf(doc.getString("hasprofile"))));
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if(alPeople.size()==0){
                                        tvPeopleTitle.setText("There are no people in your city with your interest :(");
                                    }
                                    adapterPeople = new PeopleAdapter(fragPeople.getContext(), alPeople);
                                    rvPeople.setAdapter(adapterPeople);
                                    pbPeople.setVisibility(View.GONE);
                                    adapterPeople.notifyDataSetChanged();
                                } catch (Exception e) {
                                }
                            }
                        });
                    } catch (Exception e) {
                        pbPeople.setVisibility(View.GONE);
                        tvPeopleTitle.setText("There are no people in your city with your interest :(");
                    }
                }
            });
        } catch (Exception e) {

        }
        return fragPeople;
    }
}