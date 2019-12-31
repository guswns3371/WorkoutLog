package com.example.guswn.workoutlog;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter_Workout extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public class MyViewHolder2 extends RecyclerView.ViewHolder{

        CardView workoutcv;
        TextView worknametxt;
        ImageButton workmorebtn;
        TextView workdestxt;
        TextView worknumbertxt;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            workoutcv = itemView.findViewById(R.id.workoutcv);
            worknametxt = itemView.findViewById(R.id.worknametxt);
            workmorebtn = itemView.findViewById(R.id.workmorebtn);
            workdestxt = itemView.findViewById(R.id.workdestxt);
            worknumbertxt = itemView.findViewById(R.id.worknumbertxt);
        }
    }
/////////////////////////
    public  interface  MyRecyclerViewClickListener{
        void onItemClicked(int position, String name);
        void onMoreButtonClicked(int position, View v);
      //  void onCardViewClicked(int position);
        ///내가 직접 구현해야할 함수들
    ///리사이클러뷰 아이템속 요소를 눌렀을떄 클릭리스너
    }

    private MyRecyclerViewClickListener mListener;

    public  void setOnClickListener (MyRecyclerViewClickListener listener){
        mListener = listener;
    }// 어댑터가 아닌 메인에서 이 리스너 함수를 사용하기위해서
  //////////////////////




    private ArrayList<WorkoutInfo> workoutInfos;
    MyAdapter_Workout (ArrayList<WorkoutInfo> workoutInfoArrayList){
        this.workoutInfos = workoutInfoArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workout_recycler_content,viewGroup,false);
        return new MyAdapter_Workout.MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyAdapter_Workout.MyViewHolder2 myViewHolder = (MyAdapter_Workout.MyViewHolder2) viewHolder;
        myViewHolder.worknametxt.setText(workoutInfos.get(i).getWorkname());
        myViewHolder.workdestxt.setText(workoutInfos.get(i).getWorkdes());
        myViewHolder.worknumbertxt.setText(workoutInfos.get(i).getWorknumber());

        if(mListener != null){
            //외부에서 내가 만든 리사이클러뷰 리스너를 연결 했다면
            final int pos = i;
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(pos,workoutInfos.get(pos).getWorkname());
                    //onItemClicked메소드는 내가 위에서 만든 인터페이스 함수이다.
                    // mListener2.onItemClicked(myViewHolder.getAdapterPosition()); 하고 같은 의미이다.
                }
            });
            myViewHolder.workmorebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onMoreButtonClicked(pos,v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return workoutInfos.size();
    }
}
