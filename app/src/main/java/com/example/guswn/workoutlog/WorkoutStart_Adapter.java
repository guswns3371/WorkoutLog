package com.example.guswn.workoutlog;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkoutStart_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int LAYOUT_ONE= 0;
    private static final int LAYOUT_TWO= 1;
//    static ArrayList<String> DayList = new ArrayList<>();
    Context mContext;
    int total_types;

    String key;
    public  class  ViewHolderOne extends RecyclerView.ViewHolder{//workout_start_type1.xml

        TextView workstart_part;
        TextView workstart_name;
        ImageView workstart_img;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            workstart_part = itemView.findViewById(R.id.workstart_part);
            workstart_name = itemView.findViewById(R.id.workstart_name);
            workstart_img = itemView.findViewById(R.id.workstart_img);

        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder{//workout_start_type2.xml

        TextView workstart_setnumtxt;

        ImageView workstart_weight_minus;
        ImageView workstart_weight_plus;

        ImageView workstart_reps_minus;
        ImageView workstart_reps_plus;

        EditText workstart_weight_edittxt;
        EditText workstart_reps_edit_txt;

        ImageButton workstart_morebtn;
        Button workstart_complete_btn;

        TextView workstart_setnumB;
        TextView workstart_weight_repsB;

        CardView workout_start_cvA;
        CardView workout_start_cvB;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            workstart_setnumtxt = itemView.findViewById(R.id.workstart_setnumtxt);

            workstart_weight_minus = itemView.findViewById(R.id.workstart_weight_minus);
            workstart_weight_plus = itemView.findViewById(R.id.workstart_weight_plus);

            workstart_reps_plus = itemView.findViewById(R.id.workstart_reps_plus);
            workstart_reps_minus = itemView.findViewById(R.id.workstart_reps_minus);

            workstart_weight_edittxt = itemView.findViewById(R.id.workstart_weight_edittxt);
            workstart_reps_edit_txt = itemView.findViewById(R.id.workstart_reps_edit_txt);

            workstart_morebtn = itemView.findViewById(R.id.workstart_morebtn);
            workstart_complete_btn= itemView.findViewById(R.id.workstart_complete_btn);

            workout_start_cvA = itemView.findViewById(R.id.workout_start_cvA);
            workout_start_cvB = itemView.findViewById(R.id.workout_start_cvB);

            workstart_setnumB= itemView.findViewById(R.id.workstart_setnumB);
            workstart_weight_repsB= itemView.findViewById(R.id.workstart_weight_repsB);
        }
    }

    public interface WorkoutStartRecyclerClickListner{
        void onMoreButtonClicked(int position , View v);
//        void onCompleteButtonClicked(int position , View v);
//        void onPlusMinusButtonClicked1(int position, View v);
//        void onPlusMinusButtonClicked2(int position, View v);
//        void onPlusMinusButtonClicked3(int position, View v);
//        void onPlusMinusButtonClicked4(int position, View v);
    }

    private WorkoutStartRecyclerClickListner WSclickListner;
    public void setOnClickListener_WorkoutStart(WorkoutStartRecyclerClickListner a){
        WSclickListner = a;
    }



    String name;// 운동이름
    private ArrayList<WorkoutStart_Info> workoutStartInfos;
    WorkoutStart_Adapter (ArrayList<WorkoutStart_Info> workoutStartInfos2 , Context context,String key2,String name2){
        this.workoutStartInfos = workoutStartInfos2;
        this.mContext = context;
        this.key = key2;// Workout 게시물 이름
        this.name = name2;//운동이름
        total_types = workoutStartInfos2.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =null;
        RecyclerView.ViewHolder viewHolder = null;
        if(i==LAYOUT_ONE)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workout_start_type1,viewGroup,false);
            viewHolder = new ViewHolderOne(view);
        }
        else
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workout_start_type2,viewGroup,false);
            viewHolder= new ViewHolderTwo(view);
        }

        return viewHolder;
    }

    double num1=0;
    int num2=0;
    String completeData,datas;
    String DATE;
    ArrayList<CompleteSetInfo> completeSetInfosList = new ArrayList<>();
    ArrayList<String > datalist = new ArrayList<>();

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        WorkoutStart_Info object = workoutStartInfos.get(i);
        if(object != null){
            switch (object.type){
                case LAYOUT_ONE:
                    ((ViewHolderOne)viewHolder).workstart_part.setText(object.ws_part);
                    ((ViewHolderOne)viewHolder).workstart_name.setText(object.ws_name);
                    ((ViewHolderOne)viewHolder).workstart_img.setImageResource(object.ws_img);
                    break;
                case LAYOUT_TWO:

                    ((ViewHolderTwo)viewHolder).workstart_setnumtxt.setText("Set "+i);
                   // ((ViewHolderTwo)viewHolder).workstart_weight_edittxt.setText(Integer.toString(num1));
                   // ((ViewHolderTwo)viewHolder).workstart_reps_edit_txt.setText(object.ws_reps);
                    /***/
                    SharedPreferences app = mContext.getSharedPreferences(key,0);
                   Boolean iscom = app.getBoolean("완료"+name+i,false);
                   Log.e("??? WorkStart onBindViewHolder ITEM 세트 완료여부"+i,""+iscom);
                   if(iscom !=false)
                   {
                       String comp_weight = app.getString("완료무게" + name + i, "null");
                       String comp_reps = app.getString("완료횟수" + name + i, "null");
                       Log.e("??? WorkStart onBindViewHolder 완료무게 / 완료횟수" + i, comp_weight + "/" + comp_reps);
                       if (comp_reps != "null" && comp_weight != "null")
                       {
                           ((ViewHolderTwo) viewHolder).workout_start_cvA.setVisibility(View.GONE);
                           ((ViewHolderTwo) viewHolder).workstart_setnumB.setText("Set " + i);
                           ((ViewHolderTwo) viewHolder).workstart_weight_repsB.setText(comp_weight + " kg x " + comp_reps + " reps");
                           ((ViewHolderTwo) viewHolder).workout_start_cvB.setVisibility(View.VISIBLE);
                       }
                   }


                    ((ViewHolderTwo)viewHolder).workstart_weight_minus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(num1!=0) {
                                num1-=2.5;
                                ((ViewHolderTwo)viewHolder).workstart_weight_edittxt.setText(Double.toString(num1));
                                Log.e("??? num1",""+num1);
                                Log.e("??? position",""+i);
                            }
                        }
                    });
                    ((ViewHolderTwo)viewHolder).workstart_weight_plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            num1+=2.5;
                            ((ViewHolderTwo)viewHolder).workstart_weight_edittxt.setText(Double.toString(num1));
                            Log.e("??? num1",""+num1);
                            Log.e("??? position",""+i);
                        }
                    });
                    ((ViewHolderTwo)viewHolder).workstart_reps_minus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(num2!=0) {
                                num2--;
                                ((ViewHolderTwo) viewHolder).workstart_reps_edit_txt.setText(Integer.toString(num2));
                                Log.e("??? position",""+i);
                            }
                        }
                    });
                    ((ViewHolderTwo)viewHolder).workstart_reps_plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            num2++;
                            ((ViewHolderTwo)viewHolder).workstart_reps_edit_txt.setText(Integer.toString(num2));
                            Log.e("??? position",""+i);
                        }
                    });
                    ((ViewHolderTwo)viewHolder).workstart_complete_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!((ViewHolderTwo)viewHolder).workstart_weight_edittxt.getText().toString().isEmpty() &&
                                    !((ViewHolderTwo)viewHolder).workstart_reps_edit_txt.getText().toString().isEmpty())
                            {
                                ((ViewHolderTwo) viewHolder).workout_start_cvA.setVisibility(View.GONE);
                                ((ViewHolderTwo) viewHolder).workstart_weight_repsB.setText(((ViewHolderTwo) viewHolder).workstart_weight_edittxt.getText().toString() + " kg x " +
                                        ((ViewHolderTwo) viewHolder).workstart_reps_edit_txt.getText().toString() + " reps");
                                ((ViewHolderTwo) viewHolder).workstart_setnumB.setText("Set " + i);
                                ((ViewHolderTwo) viewHolder).workout_start_cvB.setVisibility(View.VISIBLE);

                                Log.e("??? WorkoutStart adapter KEY",key);
                                SharedPreferences sp = mContext.getSharedPreferences(key,0);
                                // key 는 workout 게시물이름 + name은 특정 선택한 운동명

                                SharedPreferences.Editor editor = sp.edit();
                                editor.putBoolean("완료"+name+i,true);
                                editor.putString("완료세트"+name+key,Integer.toString(i));
                                editor.putString("완료무게"+name+i,((ViewHolderTwo)viewHolder).workstart_weight_edittxt.getText().toString());
                                editor.putString("완료횟수"+name+i,((ViewHolderTwo)viewHolder).workstart_reps_edit_txt.getText().toString());
                                editor.apply();

                                SharedPreferences sp1 = mContext.getSharedPreferences(key,0);
                                Log.e("??? 저장된건가? "+"완료"+i,""+sp1.getBoolean("완료"+name+i,false));
                                Log.e("??? 저장된건가? "+"완료세트"+name+key,""+sp1.getString("완료세트"+name+key,"fuck"));
                                Log.e("??? 저장된건가? "+"완료무게"+i,""+sp1.getString("완료무게"+name+i,"fuck"));
                                Log.e("??? 저장된건가? "+"완료횟수"+i,""+sp1.getString("완료횟수"+name+i,"fuck"));

                                /*****************************************************************************************************/
                                //달력에 뿌릴 데이터
                                Date today = new Date();
                                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

                                DATE = date.format(today);
                                Log.e("??? DATE",DATE);

                                /**load 하는 부분*/
                                SharedPreferences sp3 = mContext.getSharedPreferences(DATE+Main2Activity.Loginname,0);
                                String A = sp3.getString("완료"+name,"null");
                                if(A=="null"){
                                    completeSetInfosList = new ArrayList<>();
                                }else {
                                    Type type = new TypeToken<ArrayList<CompleteSetInfo>>(){}.getType();
                                    Gson gson = new Gson();
                                    completeSetInfosList = gson.fromJson(A,type);

                                }
                                /**load 하는 부분*/

                                /**save 하는 부분*/
                                SharedPreferences sp2 = mContext.getSharedPreferences(DATE+Main2Activity.Loginname,0);
                                // key 는 workout 게시물이름 + name은 특정 선택한 운동명
                                SharedPreferences.Editor editor2 = sp2.edit();

                                String data =  ((ViewHolderTwo)viewHolder).workstart_weight_edittxt.getText().toString()+" kg x "+
                                        ((ViewHolderTwo)viewHolder).workstart_reps_edit_txt.getText().toString()+" reps";


//                                CompleteSetInfo completeSetInfo = new CompleteSetInfo(name,Integer.toString(i),
//                                        ((ViewHolderTwo)viewHolder).workstart_weight_edittxt.getText().toString(),
//                                        ((ViewHolderTwo)viewHolder).workstart_reps_edit_txt.getText().toString(),DATE);
                                CompleteSetInfo completeSetInfo = new CompleteSetInfo(name,data,DATE);
                                completeSetInfosList.add(completeSetInfo);
                                Gson gson = new Gson();
                                completeData = gson.toJson(completeSetInfosList);
                                editor2.putString("완료"+name,completeData);
                                editor2.apply();
                                /**save 하는 부분*/
                                /*****************************************************************************************************/

                                //달력에 뿌릴 데이터 - 2
                                /**데이터 load 하는 부분*/
                               // SharedPreferences sp5 = mContext.getSharedPreferences(DATE+Main2Activity.Loginname,0);
                                String A2 = sp3.getString("data"+name,"null");
                                if(A2=="null"){
                                    datalist = new ArrayList<>();
                                }else {
                                    Type type3 = new TypeToken<ArrayList<String>>(){}.getType();
                                    Gson gson3 = new Gson();
                                    datalist = gson3.fromJson(A2,type3);

                                }
                                /**데이터 load 하는 부분*/

                                /**데이터 save 하는 부분*/
                                SharedPreferences sp4 = mContext.getSharedPreferences(DATE+Main2Activity.Loginname,0);
                                SharedPreferences.Editor editor4 = sp4.edit();
                                datalist.add(data);
                                Gson gson1 = new Gson();
                                datas = gson1.toJson(datalist);
                                editor4.putString("data"+name,datas);
                                editor4.apply();
                                /**데이터 save 하는 부분*/

                                /*****************************************************************************************************/
//                                //다시해보자
//                                SharedPreferences kk = mContext.getSharedPreferences(key+name,0);//key = 게시물 이름
//                                SharedPreferences.Editor editor3 = kk.edit();
//                                CompleteSetInfo completeSetInfo2 = new CompleteSetInfo(name,Integer.toString(i),
//                                        ((ViewHolderTwo)viewHolder).workstart_weight_edittxt.getText().toString(),
//                                        ((ViewHolderTwo)viewHolder).workstart_reps_edit_txt.getText().toString(),DATE);
//                                completeSetInfosList2.add(completeSetInfo2);
//                                Gson gson2 = new Gson();
//                                completeData2 = gson2.toJson(completeSetInfosList2);
//                                editor3.putString("완료2"+name+i,completeData2);
//                                editor3.apply();
                                /*****************************************************************************************************/
                                //통계를 내보자
                                /*****************************************************************************************************/





//                                /************************************************************************************************************/
//                                //날짜 저장 DayList
//                                /** selectedDay  load*/
//                                SharedPreferences SPP2 = mContext.getSharedPreferences("통계날짜들",0);
//                                String k = SPP2.getString("통계날짜","null");
//                                Log.e("??? DayList 통계날짜",k);
//                                if(k=="null"){
//                                    DayList = new ArrayList<>();
//                                }else {
//                                    Type type3 = new TypeToken<ArrayList<String>>(){}.getType();
//                                    Gson gson3 = new Gson();
//                                    DayList =  gson3.fromJson(k,type3);
//                                    Log.e("??? DayList SIZE",""+DayList.size());
//                                }
//                                /** selectedDay  load*/
//
//                                /** selectedDay= save*/
//                                SharedPreferences SPP = mContext.getSharedPreferences("통계날짜들",0);
//                                SharedPreferences.Editor editorr = SPP.edit();
//                                if(DayList.size()>0) {
//                                    Boolean isSame = false;
//                                    Log.e("??? DayList SIZE >0",".");
//                                    for (int i=0; i<DayList.size(); i++){
//                                        if(DayList.get(i).equals(DATE)){
////                   DayList.add(selectedDay);
////                   Gson gson1 = new Gson();
////                   String aaa = gson1.toJson(DayList);
////                   editor.putString("통계날짜",aaa);
////                   editor.apply();
//                                            isSame = true;
//                                            Log.e("??? DayList isSame ",""+isSame);
//                                        }
//                                    }
//                                    Log.e("??? DayList FINAL isSame ",""+isSame);
//                                    if(isSame==false){
//                                        DayList.add(DATE);
//                                        Gson gson12 = new Gson();
//                                        String aaa = gson12.toJson(DayList);
//                                        editorr.putString("통계날짜",aaa);
//                                        editorr.apply();
//
//                                        SharedPreferences ppp= mContext.getSharedPreferences("통계날짜들",0);
//                                        String ll =ppp.getString("통계날짜","null");
//                                        Log.e("??? DayList isSame false 저장 결과",ll);
//                                    }
//
//                                }else {
//                                    Log.e("??? DayList SIZE ==0",".");
//                                    DayList.add(DATE);
//                                    Gson gson12 = new Gson();
//                                    String aaa = gson12.toJson(DayList);
//                                    editorr.putString("통계날짜",aaa);
//                                    editorr.apply();
//                                }
//                                /** selectedDay= save*/
//                                /************************************************************************************************************/
                            }
                        }
                    });







                    if(WSclickListner != null){
                        //외부에서 내가 만든 리사이클러뷰 리스너를 연결 했다면
                        final int pos = i;

                        ((ViewHolderTwo)viewHolder).workstart_morebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WSclickListner.onMoreButtonClicked(pos,v);
                            }
                        });
//                        ((ViewHolderTwo)viewHolder).workstart_complete_btn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                WSclickListner.onCompleteButtonClicked(pos,v);
//                            }
//                        });

//


//                        ((ViewHolderTwo)viewHolder).workstart_weight_minus.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                WSclickListner.onPlusMinusButtonClicked1(pos,v);
//                            }
//                        });
//                        ((ViewHolderTwo)viewHolder).workstart_weight_plus.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                WSclickListner.onPlusMinusButtonClicked2(pos,v);
//                            }
//                        });
//                        ((ViewHolderTwo)viewHolder).workstart_reps_minus.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                WSclickListner.onPlusMinusButtonClicked3(pos,v);
//                            }
//                        });
//                        ((ViewHolderTwo)viewHolder).workstart_reps_plus.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                WSclickListner.onPlusMinusButtonClicked4(pos,v);
//                            }
//                        });
                    }
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return workoutStartInfos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return LAYOUT_ONE;
        else
            return LAYOUT_TWO;
    }
}
