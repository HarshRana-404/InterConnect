package com.harsh.interconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotListenOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.harsh.interconnect.adapters.ChatAdapter;
import com.harsh.interconnect.globaldata.GlobalData;
import com.harsh.interconnect.models.ChatModel;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    ImageButton ibBack, ibSend;
    ImageView ivProfile;
    EditText etMessage;
    TextView tvName;
    RecyclerView rvChats;
    ProgressBar pbChat;
    FirebaseFirestore fbStore;
    String oppositeUID, oppositeUName;
    Boolean oppositeHasProfile;
    StorageReference fbDatabase;

    ArrayList<ChatModel> alChat = new ArrayList<>();
    ChatAdapter adapterChat;
    String chatSenderID="", chatMessage="", chatDateTime="";
    String myUID="";
    HashMap<String, String> hmChat;
    Boolean myIDFirst = false;

    Float profileRotation=0.f;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rvChats = findViewById(R.id.rv_chats);
        pbChat = findViewById(R.id.pb_chat);
        ibBack = findViewById(R.id.ib_chat_back);
        ibSend = findViewById(R.id.ib_chat_send);
        ivProfile = findViewById(R.id.iv_chat_profile);
        etMessage = findViewById(R.id.et_chat_message);
        tvName = findViewById(R.id.tv_chat_name);
        fbStore = FirebaseFirestore.getInstance();
        hmChat = new HashMap<String, String>();
        try{
            rvChats.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
            adapterChat = new ChatAdapter(ChatActivity.this, alChat);
            oppositeUID = GlobalData.getDetailsForUID;
            myUID = GlobalData.fbAuth.getUid();
            Task<DocumentSnapshot> doc = fbStore.collection("people").document(oppositeUID).get();
            doc.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint("UseCompatLoadingForDrawables")
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    try{
                        oppositeUName = documentSnapshot.getString("name");
                        oppositeHasProfile = Boolean.valueOf(documentSnapshot.getString("hasprofile"));
                        if(oppositeHasProfile){
                            fbStore = FirebaseFirestore.getInstance();
                            fbStore.collection("people").document(oppositeUID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    profileRotation = Float.parseFloat(value.getString("profilerotation"));
                                }
                            });
                            fbDatabase = FirebaseStorage.getInstance().getReference("/images/profile/"+oppositeUID);
                            fbDatabase.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    try {
                                        Picasso.get().load(task.getResult()).into(ivProfile);
                                        if(profileRotation>0.f){
                                            ivProfile.setRotation(profileRotation);
                                        }
                                    } catch (Exception e) {

                                    }
                                }
                            });
                        }else{
                            ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
                        }
                        tvName.setText(oppositeUName);
                    } catch (Exception e) {
                    }
                }
            });
            ibBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            ibSend.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    try{
                        if(!etMessage.getText().toString().trim().isEmpty()){
                            View view = getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss", Locale.getDefault());
                            String dateTime = sdf.format(Calendar.getInstance().getTime());
                            hmChat.put("sender", GlobalData.fbAuth.getUid());
                            hmChat.put("message", etMessage.getText().toString());
                            hmChat.put("datetime", dateTime);
                            fbStore.collection("chat").document(getUUIDForCurrentChat()).collection("chats").document().set(hmChat);
                            etMessage.setText("");
                        }else{
                            etMessage.setText("");
                        }
                    } catch (Exception e) {}
                }
            });
            fbStore.collection("chat").document(getUUIDForCurrentChat()).collection("chats").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    Task<QuerySnapshot> qs = fbStore.collection("chat").document(getUUIDForCurrentChat()).collection("chats").get();
                    qs.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots.size()==0){
                                try{
                                    Task<QuerySnapshot> qs = fbStore.collection("chat").document(getUUIDForCurrentChat()).collection("chats").get();
                                    qs.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if(queryDocumentSnapshots.size()==0){
                                                try{
                                                    HashMap hm = new HashMap();
                                                    fbStore.collection("chat").document(getUUIDForCurrentChat()).set(hm);
                                                    fbStore.collection("chat").document(getUUIDForCurrentChat()).collection("chats").document("srid").set(hm);
                                                    myIDFirst=true;
                                                    loadPreviousChats();
                                                } catch (Exception e) {}
                                            }else{
                                                try{
                                                    myIDFirst = false;
                                                    loadPreviousChats();
                                                } catch (Exception e) {}
                                            }
                                        }
                                    });

                                } catch (Exception e) {}
                            }else{
                                myIDFirst=true;
                                loadPreviousChats();
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {}

    }
    public void loadPreviousChats(){
        try{
            pbChat.setVisibility(View.VISIBLE);
            Task<QuerySnapshot> querySnap = fbStore.collection("chat").get();
            querySnap.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    try{
                        alChat.clear();
                        for(DocumentSnapshot doc : queryDocumentSnapshots){
                            if(myIDFirst){
                                if(doc.getId().equals(getUUIDForCurrentChat())){
                                    Task<QuerySnapshot> querySnap = fbStore.collection("chat").document(doc.getId()).collection("chats").get();
                                    querySnap.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            try{
                                                for(DocumentSnapshot doc : queryDocumentSnapshots){
                                                    if(!doc.getId().equals("srid")){
                                                        chatSenderID = doc.getString("sender");
                                                        chatMessage = doc.getString("message");
                                                        chatDateTime = doc.getString("datetime");
                                                        alChat.add(new ChatModel(chatMessage, chatSenderID, chatDateTime));
                                                    }
                                                }
                                                pbChat.setVisibility(View.GONE);
                                                reSortArrayList();
                                            } catch (Exception e) {}
                                        }
                                    });
                                }
                            }else{
                                if(doc.getId().equals(getUUIDForCurrentChat())){
                                    Task<QuerySnapshot> querySnap = fbStore.collection("chat").document(doc.getId()).collection("chats").get();
                                    querySnap.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for(DocumentSnapshot doc : queryDocumentSnapshots){
                                                if(!doc.getId().equals("srid")){
                                                    chatSenderID = doc.getString("sender");
                                                    chatMessage = doc.getString("message");
                                                    chatDateTime = doc.getString("datetime");
                                                    alChat.add(new ChatModel(chatMessage, chatSenderID, chatDateTime));
                                                }
                                            }
                                            pbChat.setVisibility(View.GONE);
                                            reSortArrayList();
                                        }
                                    });
                                }
                            }
                        }
                    } catch (Exception e) {}
                }
            });
        } catch (Exception e) {}
    }

    @SuppressLint("NotifyDataSetChanged")
    public void reSortArrayList() {
        String dt[], dtMn[], date="";
        alChat.sort(Comparator.comparing(ChatModel::getDateTime));
//        for(int i=0;i<alChat.size()-1;i++){
//            Log.d("dalle", "After sort: " + alChat.get(i).getDateTime() + " : "+alChat.get(i).getMessage());
//            if(alChat.get(i).getDateTime().equals(alChat.get(i+1).getDateTime())){
//                alChat.remove(i);
//            }
//        }
        for(int i=0;i<alChat.size();i++){
            dt = alChat.get(i).getDateTime().split("-");
            dtMn = dt[2].split("@");
            date = dtMn[0]+"-"+dt[1]+"-"+dt[0];
            alChat.set(i, new ChatModel(alChat.get(i).getMessage(), alChat.get(i).getSenderUID(), date+"@"+dtMn[1]));
        }
        rvChats.setAdapter(adapterChat);
        adapterChat.notifyDataSetChanged();
        rvChats.scrollToPosition(alChat.size() - 1);
    }
    public String getUUIDForCurrentChat(){
        UUID uuid = null;
        String combinedSortedUIDS = getCombinedSortedUIDs();
        try{
            uuid = UUID.nameUUIDFromBytes(combinedSortedUIDS.getBytes());
        } catch (Exception e) {}
        return uuid.toString();
    }
    public String getCombinedSortedUIDs(){
        String combinedUIDs = myUID+oppositeUID;
        char charCombinedUIDs[] = combinedUIDs.toCharArray();
        ArrayList<Integer> alNum = new ArrayList<>();
        ArrayList<Character> alAlpha = new ArrayList<>();
        String strAlpha="", strNum="";

        for(char ch : charCombinedUIDs){
            try{
                alNum.add(Integer.parseInt(String.valueOf(ch)));
            } catch (Exception e) {
                try{
                    alAlpha.add(ch);
                } catch (Exception ex) {}
            }
        }

        alNum.sort(Comparator.naturalOrder());
        alAlpha.sort(Comparator.naturalOrder());

        for(Integer in : alNum){
            strNum+=in;
        }
        for(Character ch : alAlpha){
            strAlpha+=ch;
        }
        combinedUIDs = strAlpha+strNum;

        return combinedUIDs;
    }

}