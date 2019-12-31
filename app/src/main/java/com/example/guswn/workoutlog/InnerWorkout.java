package com.example.guswn.workoutlog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InnerWorkout extends AppCompatActivity implements InnerWorkout_Adapter.InnerWorkoutRecyclerListener{

    Toolbar innerworkouttb;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton innerworkoutFloatingbtn;
    String name;
    Boolean isAdd;
    Boolean isStart = false;
    InnerWorkout_Info innerWorkout_info;
    ArrayList<InnerWorkout_Info> innerWorkoutInfos ;
    InnerWorkout_Adapter innerWorkoutAdapter;
    ArrayList<ExerciseInfo> GetList;

    int exerciseimg2;
    String exercisename2;
    String part2;
    String DATE;
    Boolean workStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner_workout);
        innerworkouttb = findViewById(R.id.innerworkouttb);
        innerworkoutFloatingbtn = findViewById(R.id.innerworkoutFloatingbtn);
        setSupportActionBar(innerworkouttb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DATE = date.format(today);


        innerWorkoutInfos = new ArrayList<>();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");// workout 게시물 제목
        Log.e("??? Innerworkout NAME",name);


        isAdd = intent.getBooleanExtra("isAdd",false);
        getSupportActionBar().setTitle(name);

        innerworkoutFloatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(InnerWorkout.this,Exercise.class);
                intent.putExtra("isAdd",true);//이 키값으로 리사이클러뷰의 체크박스를 visible / gone 할수 있다.
                intent.putExtra("WName",name);
                startActivity(intent);
            }
        });


        //Log.e("??? Innerworkout GETCONTEXT",""+getApplicationContext().toString());

        mRecyclerView = findViewById(R.id.innerworkoutRecyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        innerWorkoutAdapter = new InnerWorkout_Adapter(innerWorkoutInfos,this);

        innerWorkoutAdapter.setOnClickListenerInner(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(innerWorkoutAdapter);



        //innerWorkoutInfos.add(new InnerWorkout_Info(R.drawable.a_plank,"복근","plank"));

        //load();
        /** load*/
//        SharedPreferences kkk = getSharedPreferences("담긴운동파일"+name,0);
//        String a= kkk.getString("담긴운동들","null");
//        if(a.equals("null")){
//            innerWorkoutInfos = new ArrayList<>();
//        }else{
//            Type type = new TypeToken<ArrayList<ExerciseInfo>>(){}.getType();
//            Gson gson = new Gson();
//            GetList = gson.fromJson(a, type);
//            Log.e("!!! Innerworkoutload 로드된키리스트 GetList 사이즈",""+GetList.size());
//
//            for (int i = 0; i < GetList.size(); i++) {
//                Gson gson1 = new Gson();
//                try {
//                    JSONObject jsonObject = new JSONObject(gson1.toJson(GetList.get(i)));
//                     exerciseimg2 = jsonObject.getInt("exerciseimg");
//                     exercisename2 = jsonObject.getString("exercisename");
//                     part2 = jsonObject.getString("part");
//
//                     /****/
//                     SharedPreferences app = getSharedPreferences(name,0);
//                     String complete_setnum = app.getString("완료세트"+exercisename2+name,"fuck");
//                     Log.e("??? InnerWorkout complete_setnum 완료세트수 ",complete_setnum);
//                     if(complete_setnum != "fuck"){
//                         Boolean iscomp = app.getBoolean("완료"+exercisename2+Integer.parseInt(complete_setnum),false);
//                         Log.e("??? InnerWorkout iscomp 완료여부 ",""+iscomp);
//                         if(iscomp!=false){
//                             SharedPreferences app2 = getSharedPreferences("완료"+exercisename2,0);
//                             SharedPreferences.Editor editor = app2.edit();
//                             editor.putBoolean("완료됨",true);
//                             editor.apply();
//                         }
//                     }
//                     /***/ // 여기서 세트를 완료했는지 여부를 판가름한후
//                    //InnerWorkout_Adapter 에서 "완료"라는 텍스트뷰 비져블속성을 설정
//
//                    innerWorkoutInfos.add(new InnerWorkout_Info(exerciseimg2,part2,exercisename2));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        /** load*/



    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    ArrayList<String> CompleteWork ;

    public void load(){
        SharedPreferences kkk = getSharedPreferences("담긴운동파일"+name,0);
        String a= kkk.getString("담긴운동들","null");
        if(a.equals("null")){
            innerWorkoutInfos = new ArrayList<>();
        }else{
            Type type = new TypeToken<ArrayList<ExerciseInfo>>(){}.getType();
            Gson gson = new Gson();

            /**실험*/ CompleteWork = new ArrayList<>(); /**실험*/

            GetList = gson.fromJson(a, type);
            Log.e("!!! Innerworkoutload 로드된키리스트 GetList 사이즈",""+GetList.size());
            for (int i = 0; i < GetList.size(); i++) {
                Gson gson1 = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(gson1.toJson(GetList.get(i)));
                    exerciseimg2 = jsonObject.getInt("exerciseimg");
                    exercisename2 = jsonObject.getString("exercisename");
                    part2 = jsonObject.getString("part");

                    /****/
                    SharedPreferences app = getSharedPreferences(name,0);
                    String complete_setnum = app.getString("완료세트"+exercisename2+name,"fuck");
                    Log.e("??? InnerWorkout complete_setnum 완료세트 ","완료세트"+exercisename2+name);
                    Log.e("??? InnerWorkout complete_setnum 완료세트수 ",complete_setnum);
                    if(complete_setnum != "fuck"){
                        Boolean iscomp = app.getBoolean("완료"+exercisename2+Integer.toString(WorkoutStart.workoutstartsize-1),false);

                        Log.e("??? InnerWorkout iscomp 완료여부 ",""+iscomp);
                        if(iscomp!=false){
//                            SharedPreferences app2 = getSharedPreferences("완료"+exercisename2,0);
//                            SharedPreferences.Editor editor = app2.edit();
//                            editor.putBoolean("완료됨",true);
//                            editor.apply();
                            CompleteWork.add(exercisename2);
                        }
                    }


                    /***/ // 여기서 세트를 완료했는지 여부를 판가름한후
                    //InnerWorkout_Adapter 에서 "완료"라는 텍스트뷰 비져블속성을 설정

                    innerWorkoutInfos.add(new InnerWorkout_Info(exerciseimg2,part2,exercisename2));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            Log.e("??? CompleteWork 사이즈 / GetList 사이즈 ",""+CompleteWork.size()+"/"+GetList.size());
            //innerWorkoutInfos 사이즈는 onResume에서 계속 불어나니까 짜증나 그래서 그냥 원본이 들어있는 어레이 리스트 사이즈를 비교해
//            SharedPreferences app3 = getSharedPreferences("운동시작",0);
//           Boolean isStart = app3.getBoolean("운동시작여부",false);
           if(isStart!=false) {
               if (CompleteWork.size() == GetList.size()) {
                   Toast.makeText(InnerWorkout.this, "운동을 모두 완료하였습니다", Toast.LENGTH_LONG).show();
                   //완료된 횟수를 세자
                   //오늘 완료한 운동 횟수를 저장해 랭킹을 세우자
                   /***/
                   Log.e("??? COMPLETE WORKOUT getTimeOut",getTimeOut());
                   myTimer.removeCallbacksAndMessages(null);
                   /***/

               } else {
                   Toast.makeText(InnerWorkout.this, "완료한 운동 " + CompleteWork.size() + "/" + GetList.size(), Toast.LENGTH_SHORT).show();
               }
           }

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.workout_toolbar_menu,menu);
        return true;
    }

    Boolean BB=false;
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                if(workStart==false)
                {
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(InnerWorkout.this);
                    View mView = getLayoutInflater().inflate(R.layout.custom_dialog_add_workout,null);
                    Button nobtn =  mView.findViewById(R.id.add_nobtn);
                    Button yesbtn = mView.findViewById(R.id.add_yesbtn);
                    TextView title = mView.findViewById(R.id.textView23);
                    mBuilder.setView(mView);
                    title.setText("운동을 그만 하시겠습니까?");

                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    yesbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            SharedPreferences ap = getSharedPreferences(name,0);
                            SharedPreferences.Editor editor = ap.edit();
                            editor.clear().commit();

                            SharedPreferences ap2 = getSharedPreferences("완료"+name,0);
                            SharedPreferences.Editor editor2 = ap2.edit();
                            editor2.clear().commit();

                            SharedPreferences ap3 =getSharedPreferences("운동시작",0);
                            SharedPreferences.Editor editor3 = ap3.edit();
                            editor3.clear().commit();

//                            SharedPreferences ap4 =getSharedPreferences(DATE,0);
//                            SharedPreferences.Editor editor4 = ap4.edit();
//                            editor4.clear().commit();
//                            /**이거 삭제해야해 이부분은 그냥 실험을 위해 있는 거다*/

                            Toast.makeText(InnerWorkout.this,"운동 [ "+name+" ] 을 마쳤습니다",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InnerWorkout.this,Workout.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    nobtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                }else {
                    finish();
                }
                return true;

             case R.id.shareBtn:

                 if(BB==false) {
                     final AlertDialog.Builder mBuilder = new AlertDialog.Builder(InnerWorkout.this);
                     View mView = getLayoutInflater().inflate(R.layout.custom_dialog_add_workout, null);
                     Button nobtn = mView.findViewById(R.id.add_nobtn);
                     Button yesbtn = mView.findViewById(R.id.add_yesbtn);
                     TextView title = mView.findViewById(R.id.textView23);
                     mBuilder.setView(mView);
                     title.setText("운동을 시작 하시겠습니까?");

                     if(innerWorkoutInfos.size()!=0) {
                         final AlertDialog dialog = mBuilder.create();
                         dialog.show();
                         yesbtn.setOnClickListener(new View.OnClickListener() {
                             @SuppressLint("RestrictedApi")
                             @Override
                             public void onClick(View v) {
                                 workStart = false;
                                 Toast.makeText(InnerWorkout.this, "운동을 시작합니다", Toast.LENGTH_SHORT).show();
//                        getSupportActionBar().setTitle(name);
                                 myBaseTime = SystemClock.elapsedRealtime();
                                 myTimer.sendEmptyMessage(0);

                                 SharedPreferences a = getSharedPreferences("운동시작", 0);
                                 SharedPreferences.Editor editor = a.edit();
                                 editor.putBoolean("운동시작여부", true);
                                 editor.apply();
                                 isStart = true;


                                 // item.setVisible(false);
                                 item.setIcon(R.drawable.ic_done_all_black_24dp);


                                 BB = true;
                                 innerworkoutFloatingbtn.setVisibility(View.GONE);
                                 dialog.dismiss();
                             }
                         });
                         nobtn.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 dialog.dismiss();
                             }
                         });
                     }else {
                         Toast.makeText(InnerWorkout.this,"오늘 할 운동을 담으세요",Toast.LENGTH_SHORT).show();
                     }
                 }
                if(BB==true){
                    final AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(InnerWorkout.this);
                    View mView2= getLayoutInflater().inflate(R.layout.custom_dialog_add_workout,null);
                    Button nobtn2 =  mView2.findViewById(R.id.add_nobtn);
                    Button yesbtn2 = mView2.findViewById(R.id.add_yesbtn);
                    TextView title2 = mView2.findViewById(R.id.textView23);
                    mBuilder2.setView(mView2);
                    title2.setText("운동을 완료 하시겠습니까?");

                    final AlertDialog dialog2 = mBuilder2.create();
                    dialog2.show();
                    yesbtn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.dismiss();
                            SharedPreferences ap = getSharedPreferences(name,0);
                            SharedPreferences.Editor editor = ap.edit();
                            editor.clear().commit();

                            SharedPreferences ap2 = getSharedPreferences("완료"+name,0);
                            SharedPreferences.Editor editor2 = ap2.edit();
                            editor2.clear().commit();

                            SharedPreferences ap3 =getSharedPreferences("운동시작",0);
                            SharedPreferences.Editor editor3 = ap3.edit();
                            editor3.clear().commit();

                            Toast.makeText(InnerWorkout.this,"운동 [  "+name+"  ] 을 마쳤습니다",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InnerWorkout.this,Workout.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                           // BB=false;
                        }
                    });
                    nobtn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.dismiss();
                            //BB=false;
                        }
                    });
                }
                return  true;

            case R.id.doneBtn:

//                if(BB =true){
//                    item.setVisible(true);
//
//                    final AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(InnerWorkout.this);
//                    View mView2= getLayoutInflater().inflate(R.layout.custom_dialog_add_workout,null);
//                    Button nobtn2 =  mView2.findViewById(R.id.add_nobtn);
//                    Button yesbtn2 = mView2.findViewById(R.id.add_yesbtn);
//                    TextView title2 = mView2.findViewById(R.id.textView23);
//                    mBuilder2.setView(mView2);
//                    title2.setText("운동을 완료 하시겠습니까?");
//
//                    final AlertDialog dialog2 = mBuilder2.create();
//                    dialog2.show();
//                    yesbtn2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog2.dismiss();
//                        }
//                    });
//                    nobtn2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog2.dismiss();
//                        }
//                    });
//                }
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    Handler myTimer = new Handler(){
        public void handleMessage(Message msg){
            getSupportActionBar().setTitle(getTimeOut());

            myTimer.sendEmptyMessage(0);
        }
    };

    long myBaseTime;
    static String t;

    public String getTimeOut(){
        long now = SystemClock.elapsedRealtime();
        long outTime = now - myBaseTime;
        String easy_outTime = String.format("%02d : %02d", outTime/1000 / 60, (outTime/1000)%60);
        t=easy_outTime;
        return easy_outTime;
    }

    @Override
    public void onItemClicked(int position, View v) {
        if(workStart == true)
        {
            Log.e("??? workStart","true");
            Intent intent = new Intent(InnerWorkout.this, How_to_xx.class);
            intent.putExtra("exercisetxt", exercisename2);
//            intent.putExtra("운동부위", part2);
            intent.putExtra("운동부위", innerWorkoutInfos.get(position).getInn_part());
            intent.putExtra("운동부위position", Integer.toString(position));
            intent.putExtra("pic2", innerWorkoutInfos.get(position));
            intent.putExtra("isAdd", isAdd);
            intent.putExtra("isAdd2", false);
            intent.putExtra("Wname", name);
            startActivity(intent);
        }
        else if(workStart ==false)
        {
            Log.e("??? workStart","false");
            Intent intent = new Intent(InnerWorkout.this, WorkoutStart.class);
            intent.putExtra("package", innerWorkoutInfos.get(position));
            intent.putExtra("운동부위", innerWorkoutInfos.get(position).getInn_part());
            intent.putExtra("Wname", name);
            startActivity(intent);
        }
    }



    @Override
    public void onBackPressed() {
//  super.onBackPressed();  //이걸 없애야 백버튼 누르고 다이얼로그를 띄운다
        if(workStart==false){
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(InnerWorkout.this);
            View mView = getLayoutInflater().inflate(R.layout.custom_dialog_add_workout,null);
            Button nobtn =  mView.findViewById(R.id.add_nobtn);
            Button yesbtn = mView.findViewById(R.id.add_yesbtn);
            TextView title = mView.findViewById(R.id.textView23);
            mBuilder.setView(mView);
            title.setText("운동을 그만 하시겠습니까?");

            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            yesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    SharedPreferences ap = getSharedPreferences(name,0);
                    SharedPreferences.Editor editor = ap.edit();
                    editor.clear().commit();

                    SharedPreferences ap2 = getSharedPreferences("완료"+name,0);
                    SharedPreferences.Editor editor2 = ap2.edit();
                    editor2.clear().commit();

                    SharedPreferences ap3 =getSharedPreferences("운동시작",0);
                    SharedPreferences.Editor editor3 = ap3.edit();
                    editor3.clear().commit();

//                    SharedPreferences ap4 =getSharedPreferences(DATE,0);
//                    SharedPreferences.Editor editor4 = ap4.edit();
//                    editor4.clear().commit();

                    Toast.makeText(InnerWorkout.this,"운동 [ "+name+" ] 을 마쳤습니다",Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(InnerWorkout.this,Workout.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                }
            });
            nobtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }else {
            super.onBackPressed();
        }
    }


}
