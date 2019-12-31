package com.example.guswn.workoutlog;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchWorkout_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Boolean isAdd;
    SharedPreferences pref ;
    Context mContext;
    Gson gson;


    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView exercisetxt;
        ImageView exerciseimg;
        CardView abscv1;
        CheckBox checkBox;
        ImageView favimg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            exercisetxt = itemView.findViewById(R.id.exercisetxt);
            exerciseimg = itemView.findViewById(R.id.exerciseimg);
            abscv1 = itemView.findViewById(R.id.abscv1);
            checkBox = itemView.findViewById(R.id.checkBox);
            favimg = itemView.findViewById(R.id.favimg);

        }
    }
    public  interface  MyRecyclerViewClickListener2{
        void onItemClicked2(int position, View v);
        void onCheckBoxClicked(int position , View v);

    }

    private MyRecyclerViewClickListener2 mListener;

    public  void setOnClickListener (MyRecyclerViewClickListener2 listener){
        mListener = listener;
    }// 어댑터가 아닌 메인에서 이 리스너 함수를 사용하기위해서
    //////////////////////


    private ArrayList<ExerciseInfo> exerciseInfos;
    SearchWorkout_Adapter(ArrayList<ExerciseInfo> exerciseInfoArrayList , Context con)
    {
        this.exerciseInfos = exerciseInfoArrayList;
        this.mContext = con;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_content1,parent,false);
        return new MyAdapter.MyViewHolder(v);
    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyAdapter.MyViewHolder myViewHolder = (MyAdapter.MyViewHolder) holder;
        // 각각 리사이클러뷰 아이템에 대항하는 클릭 횟수를 지정하기위해서 배열로 한것이다.
        myViewHolder.exercisetxt.setText(exerciseInfos.get(position).getExercisename());
        myViewHolder.exerciseimg.setImageResource(exerciseInfos.get(position).getExerciseimg());
        myViewHolder.checkBox.setChecked(exerciseInfos.get(position).getIschecked());


        SharedPreferences app = mContext.getSharedPreferences("좋아요체크"+exerciseInfos.get(position).getPart(),0);
        Boolean a = app.getBoolean(exerciseInfos.get(position).getExercisename(),false);
//        if(a==true){
//            myViewHolder.favimg.setVisibility(View.VISIBLE);
//        }//리사이클러뷰 특성 땜시 하트가 지 맘대로 생겼다가 없어졌다가하네

        if(mListener != null){
            if(isAdd!=null && isAdd== true){
                myViewHolder.checkBox.setVisibility(View.VISIBLE);

                //외부에서 내가 만든 리사이클러뷰 리스너를 연결 했다면
                final int pos = position;
                myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onCheckBoxClicked(pos,v);

                    }
                });

            }
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked2(position,v);
                }
            });


        }

        //        setAnimation(holder.itemView, position);
        setScaleAnimation(holder.itemView);
    }
    private final static int FADE_DURATION = 150; //FADE_DURATION in milliseconds
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
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
        return exerciseInfos.size();
    }

    public void filterList(ArrayList<ExerciseInfo> filteredList){
        exerciseInfos = filteredList;
        notifyDataSetChanged();
    }
}
