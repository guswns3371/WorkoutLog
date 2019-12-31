package com.example.guswn.workoutlog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Boolean isAdd;
    SharedPreferences pref ;
    Context mContext;
    Gson gson;

    static ArrayList<ExerciseInfo> basket = new ArrayList<>();

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

        //  void onCardViewClicked(int position);
        ///내가 직접 구현해야할 함수들
        ///리사이클러뷰 아이템속 요소를 눌렀을떄 클릭리스너
    }

    private MyRecyclerViewClickListener2 mListener;

    public  void setOnClickListener (MyRecyclerViewClickListener2 listener){
        mListener = listener;
    }// 어댑터가 아닌 메인에서 이 리스너 함수를 사용하기위해서
    //////////////////////


    private ArrayList<ExerciseInfo> exerciseInfos;
    MyAdapter(ArrayList<ExerciseInfo> exerciseInfoArrayList , Context con)
    {
        this.exerciseInfos = exerciseInfoArrayList;
        this.mContext = con;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_content1,parent,false);
        return new MyViewHolder(v);
    }


   // int click;
    ArrayList<String> checked_workout = new ArrayList<>();// 체크된 운동 이름이 담긴다.
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        final int[] click = {0};
        final Context context = null;
        // 각각 리사이클러뷰 아이템에 대항하는 클릭 횟수를 지정하기위해서 배열로 한것이다.
        myViewHolder.exercisetxt.setText(exerciseInfos.get(position).getExercisename());
        //if(exerciseInfos.get(position).getExerciseimg() != R.drawable.ic_check_black_24dp) {
        myViewHolder.exerciseimg.setImageResource(exerciseInfos.get(position).getExerciseimg());
        myViewHolder.checkBox.setChecked(exerciseInfos.get(position).getIschecked());
        //myViewHolder.checkBox.setTag(exerciseInfos.get(position));
        Log.e("123test321",""+exerciseInfos.get(position).getIschecked()+position);
//        if(exerciseInfos.get(position).getIschecked()==true){
//
//            myViewHolder.checkBox.setChecked(true);
//        }else{
//
//            myViewHolder.checkBox.setChecked(false);
//        }
        /**
        if(mListener != null){
            //외부에서 내가 만든 리사이클러뷰 리스너를 연결 했다면
            final int pos = position;
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked2(pos);
                    //onItemClicked메소드는 내가 위에서 만든 인터페이스 함수이다.
                    // mListener2.onItemClicked(myViewHolder.getAdapterPosition()); 하고 같은 의미이다.
                }
            });

        }
         */

        SharedPreferences app = mContext.getSharedPreferences("좋아요체크"+exerciseInfos.get(position).getPart()+Main2Activity.Loginname,0);
        Boolean a = app.getBoolean(exerciseInfos.get(position).getExercisename(),false);
        if(a==true){
            myViewHolder.favimg.setVisibility(View.VISIBLE);
        }

        if(mListener != null){
        if(isAdd!=null && isAdd== true){
            myViewHolder.checkBox.setVisibility(View.VISIBLE);

                //외부에서 내가 만든 리사이클러뷰 리스너를 연결 했다면
                final int pos = position;
                myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onCheckBoxClicked(pos,v);
                        //onItemClicked메소드는 내가 위에서 만든 인터페이스 함수이다.
                        // mListener2.onItemClicked(myViewHolder.getAdapterPosition()); 하고 같은 의미이다.
                    }
                });

            }
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked2(position,v);
                }
            });

//            myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//
//                    if(isChecked) {
//                        myViewHolder.checkBox.setChecked(true);
//                        exerciseInfos.get(position).setIschecked(true);
//                        Log.e("test",""+exerciseInfos.get(position).getIschecked()+position);
//                    }
//                    else {
//                        myViewHolder.checkBox.setChecked(false);
//                        exerciseInfos.get(position).setIschecked(false);
//                        Log.e("test","zzz"+exerciseInfos.get(position).getIschecked()+position);
//                    }
//                }
//            });

//            myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   CheckBox cb = (CheckBox) v;
//
//                   exerciseInfos.get(position).setIschecked(cb.isChecked());
//                    Log.e("??? test","zzz"+exerciseInfos.get(position).getIschecked()+position);
//                    Log.e("??? MyAdapter getSharedPreferences",""+exerciseInfos.get(position).getPart());
//                    if(exerciseInfos.get(position).getIschecked()){
//
//                        switch (exerciseInfos.get(position).getPart()){
//                            case "복근":
//                                Log.e("??? MyAdapter getSharedPreferences","");
//                                SharedPreferences pref = PreferenceManager
//                                        .getDefaultSharedPreferences(mContext);
//                                String s = pref.getString("복근"+position,"null");
//                                Log.e("??? MyAdapter 복근",s);
//                                break;
//                            case "하체":
//                                break;
//                            case "어꺠":
//                                break;
//                            case "등":
//                                break;
//                            case "이두":
//                                break;
//                            case "가슴":
//                                break;
//                            case "삼두":
//                                break;
//                        }
//
//
//                    }else{
//                        basket.remove(exerciseInfos.get(position));
//
//                        //Log.e("test","KKKKKK  "+basket.size());
//                       for(int i=0; i<basket.size(); i++){
//                           Log.e("test","KKKKKK  "+basket.get(i).getExercisename()+i);
//                        }
//
//                        switch (exerciseInfos.get(position).getPart()){
//                            case "복근":
//                                break;
//                            case "하체":
//                                break;
//                            case "어꺠":
//                                break;
//                            case "등":
//                                break;
//                            case "이두":
//                                break;
//                            case "가슴":
//                                break;
//                            case "삼두":
//                                break;
//                        }
//                    }
//
//                   appData = context.getSharedPreferences("part",0);
//                    SharedPreferences.Editor editor = appData.edit();
//                  gson = new Gson();
//                   String s = gson.toJson(basket);
//
//                  editor.putBoolean(exerciseInfos.get(position).toString(),exerciseInfos.get(position).getIschecked());
//                   Log.e("담긴것",String.valueOf(appData.getBoolean(exerciseInfos.get(position).toString(),true)));
//                    editor.apply();
//
//                }
//            });// 이걸로 인해 체크박스 체크해도 다시 리사이클러뷰가 재생성되었을때 정보가 저장된다.
//
//



        }
        final int original_img = exerciseInfos.get(position).getExerciseimg();
        // 원본 이미지 파일을 파이널로 저장을 한다.
        //그래야 롱클릭 취소할떄 체크 이미지에서 원본 이미지로 돌아가야하기 때문이다.
//        myViewHolder.abscv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                   Context context = v.getContext();
//                   Intent intent = new Intent(context, How_to_xx.class);
//                   intent.putExtra("exercisetxt", myViewHolder.exercisetxt.getText().toString());
//                   intent.putExtra("pic",exerciseInfos.get(position));
//                   context.startActivity(intent);
//            }
//        });

        /**
        myViewHolder.abscv1.setOnLongClickListener(new View.OnLongClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onLongClick(View v) {

                   click[0]++;
                if(click[0] %2 !=0) {
                    Toast.makeText(myViewHolder.abscv1.getContext(), myViewHolder.exercisetxt.getText().toString() + " 선택 되었습니다."+position, Toast.LENGTH_SHORT).show();
                    myViewHolder.exerciseimg.setImageResource(R.drawable.ic_check_black_24dp);
                    //체크된 이미지로 바꾸어 보여주는 시점이다
                    exerciseInfos.get(position).setExerciseimg(R.drawable.ic_check_black_24dp);
                    //여기서 아이템의 이미지 파일이 완전히 바뀐후 저장된다.
                    checked_workout.add(myViewHolder.exercisetxt.getText().toString());
                }
                if(click[0] %2 ==0 && click[0] !=0)
                {
                    Toast.makeText(myViewHolder.abscv1.getContext(), myViewHolder.exercisetxt.getText().toString() + " 선택이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    myViewHolder.exerciseimg.setImageResource(original_img);
                    exerciseInfos.get(position).setExerciseimg(original_img);
                    //어레이 리스트에서 바로 이미지지를 바꿔주면 나중에 스크롤하고 리사이클러뷰 아이템이 갱신되어 나타날대 정보가 저장되어 나온다.
                    // 즉, 체크한 정보가 날아가지 않고 저장된 상태로 나온다는 이야기다
                    checked_workout.remove(myViewHolder.exercisetxt.getText().toString());
                }
                return true;//return ture 설정하면 Long클릭 후 클릭은 처리 안됨
            }
        });

         */
//        setAnimation(holder.itemView, position);
        setScaleAnimation(holder.itemView);
    }
    private final static int FADE_DURATION = 350; //FADE_DURATION in milliseconds
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



}
