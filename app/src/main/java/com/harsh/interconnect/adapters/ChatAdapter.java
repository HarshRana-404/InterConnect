package com.harsh.interconnect.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.harsh.interconnect.R;
import com.harsh.interconnect.fragments.MyInterests;
import com.harsh.interconnect.globaldata.GlobalData;
import com.harsh.interconnect.models.ChatModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<ChatModel> alChat;
    Context context;
    String dt[], dtPrev[], dtCurr[];
    String time;

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View peopleView = LayoutInflater.from(context).inflate(R.layout.chat_ui, parent, false);
        ViewHolder peopleViewHolder = new ViewHolder(peopleView);
        return peopleViewHolder;
    }
    public ChatAdapter(Context context, ArrayList alChat){
        this.context = context;
        this.alChat = alChat;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try{
            holder.tvMessage.setText(alChat.get(position).getMessage());
            dt = alChat.get(position).getDateTime().split("@");
            time = dt[1].substring(0, 5);
            holder.tvTime.setText(time);
            holder.llForDate.setVisibility(View.GONE);
            if(alChat.get(position).getSenderUID().equals(GlobalData.fbAuth.getUid())){
                holder.llChat.setGravity(Gravity.RIGHT);
                holder.llForTime.setGravity(Gravity.RIGHT);
            }else{
                holder.llChat.setGravity(Gravity.LEFT);
                holder.llForTime.setGravity(Gravity.LEFT);
            }
            if(alChat.size()!=1){
                if(position>0){
                    dtPrev = alChat.get(position-1).getDateTime().split("@");
                    dtCurr = alChat.get(position).getDateTime().split("@");
                    if(!dtPrev[0].equals(dtCurr[0])){
                        if(!GlobalData.alAddedDates.contains(dtCurr[0])){
                            holder.tvDate.setText(dtCurr[0]);
                            GlobalData.alAddedDates.add(dtCurr[0]);
                            holder.llForDate.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    holder.tvDate.setText(dtCurr[0]);
                    GlobalData.alAddedDates.add(dtCurr[0]);
                    holder.llForDate.setVisibility(View.VISIBLE);
                }
            }else{
                holder.tvDate.setText(dt[0]);
                GlobalData.alAddedDates.add(dt[0]);
                holder.llForDate.setVisibility(View.VISIBLE);
            }
            GlobalData.alAddedDates.clear();
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return alChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessage, tvTime, tvDate;
        LinearLayout llChat;
        LinearLayout llForTime;
        LinearLayout llForDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_chat_message);
            tvTime = itemView.findViewById(R.id.tv_chat_time);
            tvDate = itemView.findViewById(R.id.tv_chat_date);
            llChat = itemView.findViewById(R.id.ll_chat);
            llForTime = itemView.findViewById(R.id.ll_for_time);
            llForDate = itemView.findViewById(R.id.ll_for_date);
        }
    }
}
