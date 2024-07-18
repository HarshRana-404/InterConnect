package com.harsh.interconnect.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.harsh.interconnect.R;
import com.harsh.interconnect.UserDetailsActivity;
import com.harsh.interconnect.globaldata.GlobalData;
import com.harsh.interconnect.models.PeopleModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    ArrayList<PeopleModel> alPeople;
    Context context;
    FirebaseFirestore fbStore;
    Float profileRotation=0.f;

    @NonNull
    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View peopleView = LayoutInflater.from(context).inflate(R.layout.people_ui, parent, false);
        ViewHolder peopleViewHolder = new ViewHolder(peopleView);
        return peopleViewHolder;
    }
    public PeopleAdapter(Context context, ArrayList alPeople){
        this.context = context;
        this.alPeople = alPeople;
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try{
            holder.tvName.setText(alPeople.get(position).getName());
//            if(alPeople.get(position).getGender().equals("male")){
//                holder.ivGender.setImageResource(R.drawable.ic_male);
//            }else if(alPeople.get(position).getGender().equals("female")){
//                holder.ivGender.setImageResource(R.drawable.ic_female);
//            }
            if(alPeople.get(position).hasProfile()){
               try{
                   fbStore = FirebaseFirestore.getInstance();
                   holder.ivGender.setImageResource(R.drawable.ic_loading_fg);
                   StorageReference fbDatabase = FirebaseStorage.getInstance().getReference("images/profile/"+ alPeople.get(position).getUserID());
                   fbDatabase.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                       @Override
                       public void onComplete(@NonNull Task<Uri> task) {
                           if(task.isSuccessful()){
                               fbStore.collection("people").document(alPeople.get(position).getUserID()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                   @Override
                                   public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                       try{
                                           profileRotation = Float.parseFloat(value.getString("profilerotation"));
                                           if(profileRotation>0.f){
                                               holder.ivGender.setRotation(profileRotation);
                                           }
                                       } catch (Exception e) {
                                       }
                                   }
                               });
                               Picasso.get().load(task.getResult()).into(holder.ivGender);
                           }
                       }
                   });
               } catch (Exception e) {
               }
            }
            String interests = alPeople.get(position).getInterestsWithAt().replaceAll("@", ", ");
            interests = interests.substring(0, interests.length()-2);
            holder.tvInterests.setText(interests);
            holder.cvPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalData.getDetailsForUID = alPeople.get(position).getUserID();
                    context.startActivity(new Intent(context, UserDetailsActivity.class));
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return alPeople.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivGender;
        TextView tvName, tvInterests;
        CardView cvPerson;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGender = itemView.findViewById(R.id.iv_gender);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInterests = itemView.findViewById(R.id.tv_interests);
            cvPerson = itemView.findViewById(R.id.cv_person);
        }
    }
}
