package com.example.guswn.workoutlog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Logs_Daily_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView log_parttxt;
        TextView log_worknametxt;
        TextView log_datatxt;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            log_parttxt = itemView.findViewById(R.id.log_parttxt);
            log_worknametxt = itemView.findViewById(R.id.log_worknametxt);
            log_datatxt = itemView.findViewById(R.id.log_datatxt);
        }
    }

    private ArrayList<Logs_Daily_Info> logsDailyInfos;
    Logs_Daily_Adapter( ArrayList<Logs_Daily_Info> logsDailyInfos2){
        this.logsDailyInfos = logsDailyInfos2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_logs__daily_recycler_item,viewGroup,false);
        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Logs_Daily_Adapter.MyViewHolder2 myViewHolder = (Logs_Daily_Adapter.MyViewHolder2) viewHolder;
        myViewHolder.log_parttxt.setText(logsDailyInfos.get(i).getLpart());
        myViewHolder.log_worknametxt.setText(logsDailyInfos.get(i).getLname());
        myViewHolder.log_datatxt.setText(logsDailyInfos.get(i).getLdata());

    }

    @Override
    public int getItemCount() {
        return logsDailyInfos.size();
    }
}
