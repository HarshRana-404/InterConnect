package com.harsh.interconnect.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.harsh.interconnect.R;
import com.harsh.interconnect.fragments.MyInterests;
import com.harsh.interconnect.globaldata.GlobalData;
import com.harsh.interconnect.models.InterestModel;

import java.util.ArrayList;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ViewHolder> {

    ArrayList<InterestModel> alInterest;
    Context context;
    String currentInterest="";
    FirebaseFirestore fbStore = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public InterestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View peopleView = LayoutInflater.from(context).inflate(R.layout.interest_ui, parent, false);
        ViewHolder peopleViewHolder = new ViewHolder(peopleView);
        return peopleViewHolder;
    }
    public InterestsAdapter(Context context, ArrayList alInterest){
        this.context = context;
        this.alInterest = alInterest;
    }

    @Override
    public void onBindViewHolder(@NonNull InterestsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try{
            holder.tvInterest.setText(alInterest.get(position).getInterest());
            holder.ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        currentInterest = alInterest.get(position).getInterest();
                        fbStore.collection("people").document(GlobalData.fbAuth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                try{
                                    String fbInterests = documentSnapshot.getString("interests");
                                    fbInterests = fbInterests.replaceAll(currentInterest+"@", "");
                                    fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("interests", fbInterests);
                                    alInterest.remove(position);
                                    notifyDataSetChanged();
                                    MyInterests.refreshTitle();
                                } catch (Exception e) {
                                    Log.d("dalle", e+"");
                                }
                            }
                        });

                    } catch (Exception e) {
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return alInterest.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton ibDelete;
        TextView tvInterest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ibDelete = itemView.findViewById(R.id.ib_delete);
            tvInterest = itemView.findViewById(R.id.tv_interest);
        }
    }
}
