package com.example.guswn.workoutlog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Community_reply_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int LAYOUT_ONE= 0;
    private static final int LAYOUT_TWO= 1;
    Context mContext;
    int total_types;

    public  class  ViewHolderOne extends RecyclerView.ViewHolder{//community_inner_content.xml

        TextView B_reply_title;
        TextView B_reply_id;
        TextView B_reply_viewcount;
        TextView B_reply_replycount;
        TextView B_reply_contents;
        TextView B_reply_time;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            B_reply_title = itemView.findViewById(R.id.B_reply_title);
            B_reply_id = itemView.findViewById(R.id.B_reply_id);
            B_reply_viewcount = itemView.findViewById(R.id.B_reply_viewcount);
            B_reply_replycount = itemView.findViewById(R.id.B_reply_replycount);
            B_reply_contents = itemView.findViewById(R.id.B_reply_contents);
            B_reply_time = itemView.findViewById(R.id.B_reply_time);
        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder{//community_reply_content.xml

        ImageView reply_img;

        TextView reply_id;
        TextView reply_time;
        TextView reply_contents;
        TextView reply_likecounttxt;
        TextView reply_replybtntxt; // 이건 버튼역할
        ImageButton reply_settingbtn;
        ImageView reply_likebtn;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            reply_img = itemView.findViewById(R.id.reply_img);
            reply_id = itemView.findViewById(R.id.reply_id);
            reply_time = itemView.findViewById(R.id.reply_time);
            reply_contents = itemView.findViewById(R.id.reply_contents);
            reply_likecounttxt = itemView.findViewById(R.id.reply_likecounttxt);
            reply_replybtntxt = itemView.findViewById(R.id.reply_replybtntxt);
            reply_settingbtn = itemView.findViewById(R.id.reply_settingbtn);
            reply_likebtn = itemView.findViewById(R.id.reply_likebtn);
        }
    }

    public interface ReplyRecyclerClickListner{
        void onMoreButtonClicked(int position , View v);
        void onLikeButtonClicked(int position , View v);
        void onReplyReplyButtonClicked(int position , View v);
    }

    private ReplyRecyclerClickListner RRclickListner;
    public void setOnClickListener_reply(ReplyRecyclerClickListner a){
        RRclickListner = a;
    }



    private ArrayList<Communit_reply_Info> communitReplyInfos;
    Community_reply_Adapter (ArrayList<Communit_reply_Info> communit_reply_infos , Context context){
        this.communitReplyInfos = communit_reply_infos;
        this.mContext = context;
        total_types = communit_reply_infos.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =null;
        RecyclerView.ViewHolder viewHolder = null;
        if(i==LAYOUT_ONE)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.community_inner_content,viewGroup,false);
            viewHolder = new ViewHolderOne(view);
        }
        else
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.community_reply_content,viewGroup,false);
            viewHolder= new ViewHolderTwo(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Communit_reply_Info object = communitReplyInfos.get(i);
        if(object != null){
            switch (object.type){
                case LAYOUT_ONE:
                    ((ViewHolderOne)viewHolder).B_reply_title.setText(object.rTitle);
                    ((ViewHolderOne)viewHolder).B_reply_id.setText(object.rid);
                    ((ViewHolderOne)viewHolder).B_reply_time.setText(object.rtime);
                    //((ViewHolderOne)viewHolder).B_reply_replycount.setText(object.rReplycount);
//                    ((ViewHolderOne)viewHolder).B_reply_viewcount.setText(object.rViewcount);
                    ((ViewHolderOne)viewHolder).B_reply_viewcount.setText("조회수 :");
                    ((ViewHolderOne)viewHolder).B_reply_contents.setText(object.rcontents);
                   // Log.e("??? 댓글 onBindViewHolder 포지션",)
                    ((ViewHolderOne)viewHolder).B_reply_replycount.setText("댓글 :"+Integer.toString(communitReplyInfos.size()-1));
                    //object.setrReplycount(Integer.toString(communitReplyInfos.size()-1));

                    SharedPreferences sp2 = mContext.getSharedPreferences("originalkey",0);
                    String key = sp2.getString("goodolkey","null");

                    SharedPreferences ap = mContext.getSharedPreferences("댓글수",0);
                    SharedPreferences.Editor editor = ap.edit();
                    editor.putString("댓글count"+key,Integer.toString(communitReplyInfos.size()-1));
                    editor.apply();

                    break;
                case LAYOUT_TWO:
                    ((ViewHolderTwo)viewHolder).reply_img.setImageURI(Uri.parse(object.rimage));
//                    ((ViewHolderTwo)viewHolder).reply_img.setBackground(new ShapeDrawable(new OvalShape()));
//                    ((ViewHolderTwo)viewHolder).reply_img.setClipToOutline(true);

                    ((ViewHolderTwo)viewHolder).reply_id.setText(object.rid);
                    ((ViewHolderTwo)viewHolder).reply_time.setText(object.rtime);
                    ((ViewHolderTwo)viewHolder).reply_contents.setText(object.rcontents);
                    ((ViewHolderTwo)viewHolder).reply_likecounttxt.setText(object.rlikecount);

                    if(RRclickListner != null){
                        //외부에서 내가 만든 리사이클러뷰 리스너를 연결 했다면
                        final int pos = i;

                        ((ViewHolderTwo)viewHolder).reply_settingbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RRclickListner.onMoreButtonClicked(pos,v);
                            }
                        });
                        ((ViewHolderTwo)viewHolder).reply_likebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RRclickListner.onLikeButtonClicked(pos,v);
                            }
                        });
                        ((ViewHolderTwo)viewHolder).reply_replybtntxt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RRclickListner.onReplyReplyButtonClicked(pos,v);
                            }
                        });
                    }
                    break;
            }
        }


    }

    @Override
    public int getItemCount() {
        return communitReplyInfos.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position==0)
            return LAYOUT_ONE;
        else
            return LAYOUT_TWO;
    }
}
