package com.example.guswn.workoutlog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LikedActivity_Adapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context mContext;
    public class MyViewHolder_Liked extends RecyclerView.ViewHolder{

        ImageView likedimg;
        TextView likedtxt;
        ImageButton likedfavbtn;

        public MyViewHolder_Liked(@NonNull View itemView) {
            super(itemView);
            likedimg = itemView.findViewById(R.id.likedimg);
            likedtxt = itemView.findViewById(R.id.likedtxt);
            likedfavbtn = itemView.findViewById(R.id.likedfavbtn);
        }
    }

    //////////////////////////////////////////////////////////
    public interface LikedWorkoutRecyclerListener{
        void onItemClicked(int position, View v);
        void onFavButtonClicked(int position, View v);
    }

    private LikedWorkoutRecyclerListener MMlistener;

    public void setOnClickListenerLiked (LikedWorkoutRecyclerListener listener){
        MMlistener = listener;
    }

    //////////////////////////////////////////////////////////

    private ArrayList<LikedActivity_Info> likedActivityInfoArrayList;
    LikedActivity_Adapter(ArrayList<LikedActivity_Info> likedActivityInfos , Context con){
        this.likedActivityInfoArrayList = likedActivityInfos;
        this.mContext = con;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.acitivity_liked_recycler_content,viewGroup,false);
        return new MyViewHolder_Liked(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder_Liked myViewHolder = (MyViewHolder_Liked) viewHolder;
        myViewHolder.likedimg.setImageResource(likedActivityInfoArrayList.get(i).liked_img);
        myViewHolder.likedtxt.setText(likedActivityInfoArrayList.get(i).liked_name);

        if(MMlistener != null){
            final int pos = i;
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MMlistener.onItemClicked(pos,v);
                }
            });
            myViewHolder.likedfavbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MMlistener.onFavButtonClicked(pos,v);
                }
            });
        }
        //        setAnimation(viewHolder.itemView, position);
        setScaleAnimation(viewHolder.itemView);
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
        return likedActivityInfoArrayList.size();
    }
}
