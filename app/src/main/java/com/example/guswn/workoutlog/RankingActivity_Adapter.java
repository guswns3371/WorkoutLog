package com.example.guswn.workoutlog;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RankingActivity_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context mContext;

    public  static  class  MyViewHolder3 extends RecyclerView.ViewHolder{

        ImageView rank_img;
        TextView rank_idtxt;
        TextView rank_allnumtxt;

        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            rank_img = itemView.findViewById(R.id.rank_img);
            rank_idtxt = itemView.findViewById(R.id.rank_idtxt);
            rank_allnumtxt = itemView.findViewById(R.id.rank_allnumtxt);
        }
    }

    public  interface  MyRecyclerViewClickListener2{
        void onItemClicked2(int position, View v);

    }

    private MyRecyclerViewClickListener2 mListener;

    public  void setOnClickListener (MyRecyclerViewClickListener2 listener){
        mListener = listener;
    }// 어댑터가 아닌 메인에서 이 리스너 함수를 사용하기위해서
    //////////////////////

    private ArrayList<RankingActivity_Info> rankingActivityInfos;
    RankingActivity_Adapter(ArrayList<RankingActivity_Info> rankingActivityInfos1 , Context con)
    {
        this.rankingActivityInfos = rankingActivityInfos1;
        this.mContext = con;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_ranking_recycler_item,viewGroup,false);
        return new MyViewHolder3(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyViewHolder3 myViewHolder = (MyViewHolder3) viewHolder;
        myViewHolder.rank_img.setImageURI(Uri.parse(rankingActivityInfos.get(i).getRank_img()));
        myViewHolder.rank_allnumtxt.setText(rankingActivityInfos.get(i).getRank_allnum());
        myViewHolder.rank_idtxt.setText(rankingActivityInfos.get(i).getRank_id());

        if(mListener != null){
            //외부에서 내가 만든 리사이클러뷰 리스너를 연결 했다면
            final int pos = i;
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked2(pos,v);
                    //onItemClicked메소드는 내가 위에서 만든 인터페이스 함수이다.
                    // mListener2.onItemClicked(myViewHolder.getAdapterPosition()); 하고 같은 의미이다.
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return rankingActivityInfos.size();
    }
}
