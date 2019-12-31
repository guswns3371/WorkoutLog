package com.example.guswn.workoutlog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CommunityAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView community_name;
        TextView community_reply;
        TextView community_id;
        TextView community_views;
        TextView community_time;
        ImageButton community_morebtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            community_name = itemView.findViewById(R.id.community_name);
            community_reply = itemView.findViewById(R.id.community_reply);
            community_id = itemView.findViewById(R.id.community_id);
            community_views = itemView.findViewById(R.id.community_views);
            community_time = itemView.findViewById(R.id.community_time);
            community_morebtn = itemView.findViewById(R.id.community_morebtn);
        }
    }

    public  interface  MyRecyclerViewClickListenerC{
        void onItemClicked(int position);
        void onMoreButtonClicked(int position, View v);

        ///내가 직접 구현해야할 함수들
        ///리사이클러뷰 아이템속 요소를 눌렀을떄 클릭리스너
    }

    private MyRecyclerViewClickListenerC mListenerC;

    public  void setOnClickListener (MyRecyclerViewClickListenerC listener){
        mListenerC = listener;
    }// 어댑터가 아닌 메인에서 이 리스너 함수를 사용하기위해서


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.community_recycler_content,viewGroup,false);
        return new CommunityAdapter.MyViewHolder(v);
    }

    private  ArrayList<CommunityInfo> communityInfos;
    CommunityAdapter (ArrayList<CommunityInfo> communityInfoArrayList){
        this.communityInfos = communityInfoArrayList;
    }


    static int number;
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.community_name.setText(communityInfos.get(i).getName());
        myViewHolder.community_reply.setText(communityInfos.get(i).getReply());
        myViewHolder.community_id.setText(communityInfos.get(i).getId());
        myViewHolder.community_views.setText(communityInfos.get(i).getViews());
        myViewHolder.community_time.setText(communityInfos.get(i).getTime());

        number=i;
        Log.e("position //"+Integer.toString(i),Integer.toString(i));
        if(mListenerC != null){
            //외부에서 내가 만든 리사이클러뷰 리스너를 연결 했다면
            final int pos = i;
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListenerC.onItemClicked(pos);
                    //onItemClicked메소드는 내가 위에서 만든 인터페이스 함수이다.
                    // mListener2.onItemClicked(myViewHolder.getAdapterPosition()); 하고 같은 의미이다.
                }
            });
            myViewHolder.community_morebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListenerC.onMoreButtonClicked(pos,v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return communityInfos.size();
    }
}
