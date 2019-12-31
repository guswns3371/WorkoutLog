package com.example.guswn.workoutlog;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class InnerWorkout_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public class MyViewHolder_inner extends RecyclerView.ViewHolder{

        TextView inner_completetxt;

        ImageView inner_image;
        TextView inner_parttxt;
        TextView inner_nametxt;
        public MyViewHolder_inner(@NonNull View itemView) {
            super(itemView);
            inner_completetxt = itemView.findViewById(R.id.inner_completetxt);
            inner_image = itemView.findViewById(R.id.inner_image);
            inner_parttxt = itemView.findViewById(R.id.inner_parttxt);
            inner_nametxt = itemView.findViewById(R.id.inner_nametxt);
        }
    }

    //////////////////////////////////////////////////////////
    public interface InnerWorkoutRecyclerListener{
        void onItemClicked(int position, View v);

    }

    private InnerWorkoutRecyclerListener Mlistener;

    public void setOnClickListenerInner (InnerWorkoutRecyclerListener listener){
        Mlistener = listener;
    }
    //////////////////////////////////////////////////////////

    Context mContext;
    private ArrayList<InnerWorkout_Info> innerWorkoutInfos;
    InnerWorkout_Adapter(ArrayList<InnerWorkout_Info> innerWorkoutInfosList, Context context){
        this.innerWorkoutInfos = innerWorkoutInfosList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inner_workout_recycler_content,viewGroup,false);
        return new MyViewHolder_inner(v);
    }

    Boolean[] isComplete = {};
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder_inner myViewHolder = (MyViewHolder_inner) viewHolder;
        myViewHolder.inner_image.setImageResource(innerWorkoutInfos.get(i).getInn_img());
        myViewHolder.inner_nametxt.setText(innerWorkoutInfos.get(i).getInn_name());
        myViewHolder.inner_parttxt.setText(innerWorkoutInfos.get(i).getInn_part());

        //운동시작후 세트 완료된 운동 옆에 "완료" 텍스트뷰를 보이게 하는 과정이다.
//        SharedPreferences app3 = mContext.getSharedPreferences("운동시작",0);
//        Boolean isStart = app3.getBoolean("운동시작여부",false);
//        Log.e("??? 운동시작여부","q"+isStart);
//        if(isStart!=false) {
//            SharedPreferences app2 = mContext.getSharedPreferences("완료" + innerWorkoutInfos.get(i).getInn_name(), 0);
//
//            Boolean iscom = app2.getBoolean("완료됨", false);
//            Log.e("??? 완료"+ innerWorkoutInfos.get(i).getInn_name(),"완료됨 여부"+iscom);
//            if (iscom != false) {
//                myViewHolder.inner_completetxt.setVisibility(View.VISIBLE);
//            }
//        }
        //운동시작후 세트 완료된 운동 옆에 "완료" 텍스트뷰를 보이게 하는 과정이다.

//        if(isComplete.length>0) {
//            if (isComplete[i] == true) {
//                myViewHolder.inner_completetxt.setVisibility(View.VISIBLE);
//            }
//        }

        if(Mlistener != null){
            final int pos = i;
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Mlistener.onItemClicked(pos,v);
                }
            });
        }
//        setAnimation(viewHolder.itemView, position);
        setScaleAnimation(viewHolder.itemView);
    }
    private final static int FADE_DURATION = 200; //FADE_DURATION in milliseconds
    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.5f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {

        return innerWorkoutInfos.size();
    }
}
