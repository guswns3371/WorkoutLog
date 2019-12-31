package com.example.guswn.workoutlog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView main_part;
        TextView main_name;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            main_part = itemView.findViewById(R.id.main_part);
            main_name = itemView.findViewById(R.id.main_name);
        }
    }

    private ArrayList<Main2Activity_Info> main2ActivityInfos;
    Main2Activity_Adapter(ArrayList<Main2Activity_Info> main2ActivityInfos2){
        this.main2ActivityInfos = main2ActivityInfos2;
    }

    //////////////////////
    public  interface  Main2MyRecyclerViewClickListener{
        void onItemClicked(int position, View v);
        ///내가 직접 구현해야할 함수들
        ///리사이클러뷰 아이템속 요소를 눌렀을떄 클릭리스너
    }

    private Main2Activity_Adapter.Main2MyRecyclerViewClickListener mListener;

    public  void setOnClickListener (Main2Activity_Adapter.Main2MyRecyclerViewClickListener listener){
        mListener = listener;
    }// 어댑터가 아닌 메인에서 이 리스너 함수를 사용하기위해서

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main2activity_recycler_contents,viewGroup,false);
        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Main2Activity_Adapter.MyViewHolder2 myViewHolder = (Main2Activity_Adapter.MyViewHolder2) viewHolder;
        myViewHolder.main_name.setText(main2ActivityInfos.get(i).getMain_name());
        myViewHolder.main_part.setText(main2ActivityInfos.get(i).getMain_part());

        if(mListener!=null){
            final int pos = i;
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(pos,v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return main2ActivityInfos.size();
    }
}
