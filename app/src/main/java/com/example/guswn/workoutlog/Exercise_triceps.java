package com.example.guswn.workoutlog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Exercise_triceps extends AppCompatActivity implements MyAdapter.MyRecyclerViewClickListener2{
    MyAdapter myAdapter;
    Context Acontext;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Toolbar exercisenametb;
    CardView abscv1;
    FloatingActionButton floatingActionButton;
    Boolean isAdd;
    String Wname;
    ArrayList<ExerciseInfo> exerciseInfoArrayList =new ArrayList<>();
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);
        exercisenametb = findViewById(R.id.toolbar4);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setSupportActionBar(exercisenametb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        Intent intent= getIntent();
        String exercisename = intent.getStringExtra("title");
        isAdd = intent.getBooleanExtra("isAdd",false);
        Wname = intent.getStringExtra("WName");


        load2();
//        exerciseInfoArrayList = new ArrayList<>();
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Bench Press (Close Grip)",R.drawable.t_bench_press_close_grip,false,false));
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Dip",R.drawable.t_dip,false,false));
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Overhead Extension (Seated)",R.drawable.t_overhead_extension_seated,false,false));
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Overhead Extension (Standing)",R.drawable.t_overhead_extension_standing,false,false));
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Push-Down",R.drawable.t_push_down,false,false));
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Dumbbell)",R.drawable.t_tricpes_extension_dumbbell,false,false));
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Barbell)",R.drawable.t_tricpes_extension_barbell,false,false));
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Cable)",R.drawable.t_tricpes_extension_cable,false,false));
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Machine)",R.drawable.t_tricpes_extension_machine2,false,false));
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion Kneeling (Cable)",R.drawable.t_tricpes_extension_kneeling_cable,false,false));
//        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Kickback",R.drawable.t_triceps_kickback,false,false));

       // Log.e("??? Wname",Wname);
        for(int i =0 ; i<exerciseInfoArrayList.size(); i++) {

            SharedPreferences aa = getSharedPreferences("모든운동"+Wname, 0);
            SharedPreferences.Editor editor = aa.edit();
            Gson gson = new Gson();
            String s = gson.toJson( exerciseInfoArrayList.get(i));
            editor.putString("삼두사이즈",Integer.toString(exerciseInfoArrayList.size()));
            editor.putString("삼두"+Integer.toString(i),s);
            editor.apply();
        }

        /***/
        /***/
        for(int i =0 ; i<exerciseInfoArrayList.size(); i++) {
            SharedPreferences aa2 = getSharedPreferences("ALLWORKOUT", 0);
            SharedPreferences.Editor editor2 = aa2.edit();
            Gson gson = new Gson();
            String s = gson.toJson(exerciseInfoArrayList.get(i));
            editor2.putString("삼두사이즈",Integer.toString(exerciseInfoArrayList.size()));
            editor2.putString("삼두"+Integer.toString(i),s);
            /***/
            editor2.apply();
        }
        /***/
        /***/


        getSupportActionBar().setTitle("TRICEPS");

        myAdapter = new MyAdapter(exerciseInfoArrayList,this);
        myAdapter.isAdd=isAdd;
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setOnClickListener(this);
        if(isAdd){

            floatingActionButton = findViewById(R.id.floatingActionButton);
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Exercise_triceps.this);
                    View mView = getLayoutInflater().inflate(R.layout.custom_dialog_add_workout,null);
                    Button nobtn =  mView.findViewById(R.id.add_nobtn);
                    Button yesbtn = mView.findViewById(R.id.add_yesbtn);
                    mBuilder.setView(mView);

                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    yesbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences appp = getSharedPreferences("모든운동"+Wname,0);
                            String abs_size = appp.getString("복근사이즈","null");
                            String tricep_size = appp.getString("삼두사이즈","null");
                            String shoulder_size = appp.getString("어깨사이즈","null");
                            String leg_size = appp.getString("하체사이즈","null");
                            String chest_size = appp.getString("가슴사이즈","null");
                            String biceps_size = appp.getString("이두사이즈","null");
                            String back_size = appp.getString("등사이즈","null");

                            String[] part = {"복근","등","이두","가슴","하체","어깨","삼두"};
                            ArrayList<ExerciseInfo> DeliverList = new ArrayList<>();

                            for(int i =0; i<7; i++){
                                SharedPreferences APPDATA = getSharedPreferences("모든운동"+Wname,0);
                                String size =  APPDATA.getString(part[i]+"사이즈","null");
                                Log.e("??? "+part[i]+"사이즈 ",size);


                                for(int j =0; j<Integer.parseInt(size); j++){
                                    String val = APPDATA.getString(part[i]+Integer.toString(j),"null");
                                    Log.e("??? "+part[i]+Integer.toString(j)+"VALUE",val);
                                    if(!val.equals("null")){
                                        try {
                                            JSONObject jsonObject = new JSONObject(val);
                                            int exerciseimg = jsonObject.getInt("exerciseimg");
                                            String exercisename = jsonObject.getString("exercisename");
                                            Boolean isLiked = jsonObject.getBoolean("isLiked");
                                            Boolean ischecked = jsonObject.getBoolean("ischecked");
                                            String part2 = jsonObject.getString("part");

                                            if(ischecked){
                                                ExerciseInfo exerciseInfo = new ExerciseInfo(part2,exercisename,exerciseimg,ischecked,isLiked);
                                                DeliverList.add(exerciseInfo);
                                            }

                                        } catch (JSONException e) {
//                                           Log.e("??? 추가 버튼 누를때","뭐야 ㅅㅂ");
                                            Log.e("??? 추가 버튼 누를때","어레이구나");
                                            try {
                                                JSONArray jsonArray = new JSONArray(val);
                                                String a = jsonArray.getString(0);
                                                JSONObject jsonObject = new JSONObject(a);

                                                int exerciseimg = jsonObject.getInt("exerciseimg");
                                                String exercisename = jsonObject.getString("exercisename");
                                                Boolean isLiked = jsonObject.getBoolean("isLiked");
                                                Boolean ischecked = jsonObject.getBoolean("ischecked");
                                                String part2 = jsonObject.getString("part");

                                                if(ischecked){
                                                    ExerciseInfo exerciseInfo = new ExerciseInfo(part2,exercisename,exerciseimg,ischecked,isLiked);
                                                    DeliverList.add(exerciseInfo);
                                                }
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }
                                            e.printStackTrace();
                                        }

                                    }
                                }

                            }
                            Log.e("??? DeliverList 사이즈",DeliverList.size()+"");
                            SharedPreferences kkk = getSharedPreferences("담긴운동파일"+Wname,0);
                            SharedPreferences.Editor editor2 = kkk.edit();
                            Gson gson = new Gson();
                            String E = gson.toJson(DeliverList);
                            editor2.putString("담긴운동들",E);
                            editor2.apply();
                            Intent intent1 = new Intent(getApplicationContext(),Workout.class);
                            Toast.makeText(Exercise_triceps.this,"운동목록 : ["+Wname+"] 에 선택한 운동이 담겼습니다.",Toast.LENGTH_LONG).show();
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent1);

                        }
                    });
                    nobtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    {
                        floatingActionButton.show();
                    }

                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 ||dy<0 && floatingActionButton.isShown())
                    {
                        floatingActionButton.hide();
                    }
                }
            });
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked2(int position, View v) {

        Intent intent = new Intent(Exercise_triceps.this, How_to_xx.class);
        intent.putExtra("exercisetxt",exerciseInfoArrayList.get(position).getExercisename());
        intent.putExtra("운동부위",exerciseInfoArrayList.get(position).getPart());
        intent.putExtra("운동부위position",Integer.toString(position));
        intent.putExtra("pic",exerciseInfoArrayList.get(position));
        intent.putExtra("isAdd",isAdd);
        intent.putExtra("Exercise_xx",true);
        intent.putExtra("Wname",Wname);
        startActivity(intent);
    }
    /*****************************************************************************************************/
    public void load2(){
        SharedPreferences sss= getSharedPreferences("모든운동"+Wname,0);
        String kk = sss.getString("삼두0","null");
        Log.e("??? load2 삼두 정보",kk);
        if(kk.equals("null")){
            exerciseInfoArrayList = new ArrayList<>();
            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Bench Press (Close Grip)",R.drawable.t_bench_press_close_grip,false,false));
            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Dip",R.drawable.t_dip,false,false));
            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Overhead Extension (Seated)",R.drawable.t_overhead_extension_seated,false,false));
            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Overhead Extension (Standing)",R.drawable.t_overhead_extension_standing,false,false));
            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Push-Down",R.drawable.t_push_down,false,false));

            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Dumbbell)",R.drawable.t_tricpes_extension_dumbbell,false,false));
            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Barbell)",R.drawable.t_tricpes_extension_barbell,false,false));
            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Cable)",R.drawable.t_tricpes_extension_cable,false,false));
            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Machine)",R.drawable.t_tricpes_extension_machine2,false,false));
            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion Kneeling (Cable)",R.drawable.t_tricpes_extension_kneeling_cable,false,false));

            exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Kickback",R.drawable.t_triceps_kickback,false,false));
            SharedPreferences aa = getSharedPreferences("모든운동"+Wname, 0);
            SharedPreferences.Editor editor = aa.edit();
            editor.putString("삼두사이즈",Integer.toString(exerciseInfoArrayList.size()));
            editor.apply();
        }else {
            SharedPreferences sss2= getSharedPreferences("모든운동"+Wname,0);
            String  size = sss2.getString("삼두사이즈","null");
            Log.e("??? load2 삼두 사이즈 정보",size);
            for(int i=0; i<Integer.parseInt(size); i++){
                String val = sss2.getString("삼두"+Integer.toString(i),"null");
                Log.e("??? load2 각각 삼두 정보"+Integer.toString(i),val);
                try {
                    JSONObject jsonObject = new JSONObject(val);
                    String part = jsonObject.getString("part");
                    String exercisename = jsonObject.getString("exercisename");
                    String exerciseimg = jsonObject.getString("exerciseimg");
                    Boolean ischecked = jsonObject.getBoolean("ischecked");
                    Boolean isLiked = jsonObject.getBoolean("isLiked");
                    int img = Integer.parseInt(exerciseimg);
                    exerciseInfoArrayList.add(new ExerciseInfo(part,exercisename,img,ischecked,isLiked));
                } catch (JSONException e) {
                    Log.e("??? load2 각각 삼두 정보"+Integer.toString(i),"뭐지 ㅅㅂ???????????");

                    try {
                        JSONArray jsonArray = new JSONArray(val);
                        String a =jsonArray.getString(0);
                        JSONObject jsonObject = new JSONObject(a);
                        String part = jsonObject.getString("part");
                        String exercisename = jsonObject.getString("exercisename");
                        String exerciseimg = jsonObject.getString("exerciseimg");
                        Boolean ischecked = jsonObject.getBoolean("ischecked");
                        Boolean isLiked = jsonObject.getBoolean("isLiked");
                        int img = Integer.parseInt(exerciseimg);
                        exerciseInfoArrayList.add(new ExerciseInfo(part,exercisename,img,ischecked,isLiked));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onCheckBoxClicked(int position, View v) {

        CheckBox cb = (CheckBox) v;
        exerciseInfoArrayList.get(position).setIschecked(cb.isChecked());
        if(exerciseInfoArrayList.get(position).getIschecked())
        {// 체크 됬을 때
            Log.e("??? ISCHECKED T - 체크된 운동의 KEY"+position,""+exerciseInfoArrayList.get(position).getPart()+position);
            Log.e("??? ISCHECKED T"+position,""+exerciseInfoArrayList.get(position).getIschecked());

            SharedPreferences sss= getSharedPreferences("모든운동"+Wname,0);
            SharedPreferences.Editor editor1 = sss.edit();
            String a = sss.getString(exerciseInfoArrayList.get(position).getPart()+position,"null");
            Log.e("??? ISCHECKED T - 체크된 운동의 변경'전' 정보값"+position,a);

            try {
                JSONObject jsonObject = new JSONObject(a);
                String exerciseimg =  jsonObject.getString("exerciseimg");
                String exercisename = jsonObject.getString("exercisename");
                String part =  jsonObject.getString("part");
                ExerciseInfo exerciseInfo = new ExerciseInfo(part,exercisename,Integer.parseInt(exerciseimg),true,false);
                ArrayList<ExerciseInfo> list = new ArrayList<>();
                list.add(exerciseInfo);
                Gson gson = new Gson();
                String k = gson.toJson(list);


                editor1.putString(exerciseInfoArrayList.get(position).getPart()+position,k);
                editor1.apply();

            } catch (JSONException e) {
                try {
                    JSONArray jsonArray = new JSONArray(a);
                    String jj =jsonArray.getString(0);
                    JSONObject jsonObject = new JSONObject(jj);
                    String exerciseimg =  jsonObject.getString("exerciseimg");
                    String exercisename = jsonObject.getString("exercisename");
                    String part =  jsonObject.getString("part");
                    ExerciseInfo exerciseInfo = new ExerciseInfo(part,exercisename,Integer.parseInt(exerciseimg),true,false);
                    ArrayList<ExerciseInfo> list = new ArrayList<>();
                    list.add(exerciseInfo);
                    Gson gson = new Gson();
                    String k = gson.toJson(list);
                    editor1.putString(exerciseInfoArrayList.get(position).getPart()+position,k);
                    editor1.apply();
                } catch (JSONException e1) {
                    Log.e("??? ISCHECKED T - fuck3","뭐야 ㅅㅂ");
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            SharedPreferences aa = getSharedPreferences("모든운동"+Wname,0);
            String val = aa.getString(exerciseInfoArrayList.get(position).getPart()+position,"null");
            Log.e("??? ISCHECKED T - 체크된 운동의 변경'후''' 정보값"+position,val);

        }


        else if(!exerciseInfoArrayList.get(position).getIschecked())
        {//아직 체크 안됬을 때
            Log.e("??? ISCHECKED F - 체크취소된 운동의 KEY"+position,""+exerciseInfoArrayList.get(position).getPart()+position);
            Log.e("??? ISCHECKED F"+position,""+exerciseInfoArrayList.get(position).getIschecked());

            SharedPreferences sss2= getSharedPreferences("모든운동"+Wname,0);
            SharedPreferences.Editor editor2 = sss2.edit();
            String a = sss2.getString(exerciseInfoArrayList.get(position).getPart()+position,"null");
            Log.e("??? ISCHECKED F - 체크취소된 운동의 변경'전' 정보값"+position,a);


            try {
                JSONObject jsonObject = new JSONObject(a);
                String exerciseimg2 =  jsonObject.getString("exerciseimg");
                String exercisename2 = jsonObject.getString("exercisename");
                String part2 =  jsonObject.getString("part");
                ExerciseInfo exerciseInfo2 = new ExerciseInfo(part2,exercisename2,Integer.parseInt(exerciseimg2),false,false);
                ArrayList<ExerciseInfo> list2 = new ArrayList<>();
                list2.add(exerciseInfo2);
                Gson gson = new Gson();
                String k2 = gson.toJson(list2);
                editor2.putString(exerciseInfoArrayList.get(position).getPart()+position,k2);
                editor2.apply();

            } catch (JSONException e) {
                try {
                    JSONArray jsonArray = new JSONArray(a);
                    String jj =jsonArray.getString(0);
                    JSONObject jsonObject = new JSONObject(jj);
                    String exerciseimg =  jsonObject.getString("exerciseimg");
                    String exercisename = jsonObject.getString("exercisename");
                    String part =  jsonObject.getString("part");
                    ExerciseInfo exerciseInfo = new ExerciseInfo(part,exercisename,Integer.parseInt(exerciseimg),false,false);
                    ArrayList<ExerciseInfo> list = new ArrayList<>();
                    list.add(exerciseInfo);
                    Gson gson = new Gson();
                    String k = gson.toJson(list);
                    editor2.putString(exerciseInfoArrayList.get(position).getPart()+position,k);
                    editor2.apply();
                } catch (JSONException e1) {
                    Log.e("??? ISCHECKED F - fuck3","뭐야 ㅅㅂ");
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            SharedPreferences aa2 = getSharedPreferences("모든운동"+Wname,0);
            String val = aa2.getString(exerciseInfoArrayList.get(position).getPart()+position,"null");
            Log.e("??? ISCHECKED F - 체크취소된 운동의 변경'후''' 정보값"+position,val);

        }
    }
}
