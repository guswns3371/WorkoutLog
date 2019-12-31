package com.example.guswn.workoutlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Logs_Daily extends AppCompatActivity {

//    TextView yeartxt;
//    TextView monthtxt;
//    TextView daytxt;

    String part2;
    Toolbar logdailytb;
    CompleteSetInfo completeSetInfo;
    ArrayList<CompleteSetInfo> completeSetInfoArrayList = new ArrayList<>();
    ArrayList<String > Datalists = new ArrayList<>();
    String exercisename;
    String comName;
    String comData;
    String D = "";
    String selectedDay;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Logs_Daily_Adapter dailyAdapter;
    ArrayList<Logs_Daily_Info> logsDailyInfosList = new ArrayList<>();


    ArrayList<String> StatisticsList = new ArrayList<>();
    ArrayList<StatisticsActivity_Info> statInfosList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs__daily);

//        yeartxt = findViewById(R.id.yeartxt);
//        monthtxt = findViewById(R.id.monthtxt);
//        daytxt = findViewById(R.id.daytxt);
        logdailytb = findViewById(R.id.logdailytb);
        mRecyclerView = findViewById(R.id.logRecyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        dailyAdapter = new Logs_Daily_Adapter(logsDailyInfosList);
        //dailyAdapter.setOnClickListener(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(dailyAdapter);


        setSupportActionBar(logdailytb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);


        Intent intent= getIntent();
        String year = intent.getStringExtra("year");
        String month = intent.getStringExtra("month");
        String day = intent.getStringExtra("day");
        selectedDay = intent.getStringExtra("today");
       /** selectedDay = 달력에서 선택한 날짜이다. */


        getSupportActionBar().setTitle(month+". "+day+". "+year);

        SharedPreferences sp2 = getSharedPreferences(selectedDay+Main2Activity.Loginname,0);


        String[] part = {"복근","등","이두","가슴","하체","어깨","삼두"};
        for(int i =0; i<7; i++) {
            SharedPreferences APPDATA = getSharedPreferences("ALLWORKOUT", 0);
            String size = APPDATA.getString(part[i] + "사이즈", "null");
            if(!size.equals("null")) {
                for (int j = 0; j < Integer.parseInt(size); j++) {
                    String val = APPDATA.getString(part[i] + Integer.toString(j), "null");
                    if (!val.equals("null")) {
                        try {
                            JSONObject jsonObject = new JSONObject(val);
                             exercisename = jsonObject.getString("exercisename");
                             part2 = jsonObject.getString("part");
                             String todayCom = sp2.getString("완료"+exercisename,"null");
                                       if(todayCom=="null"){
                                         completeSetInfoArrayList = new ArrayList<>();
                                        }else {
                                            Type type = new TypeToken<ArrayList<CompleteSetInfo>>(){}.getType();
                                            Gson gson = new Gson();
                                            completeSetInfoArrayList = gson.fromJson(todayCom,type);
                                            for(int kk =0; kk<completeSetInfoArrayList.size(); kk++){
                                                try {
                                                    JSONArray jsonArray = new JSONArray(todayCom);
                                                    String k = jsonArray.getString(kk);
                                                    JSONObject Object = new JSONObject(k);
                                //                    String comSet = Object.getString("comSet");
                                //                    String comWeight = Object.getString("comWeight");
                                //                    String comReps = Object.getString("comReps");


                                                     comData = Object.getString("comData");
                                                     comName = Object.getString("comName");
                                                    String comTime = Object.getString("comTime");

                                                    String AA= comName+"/ Data"+comData+" part/"+part2;
                                //                    String data = comWeight+" kg x "+comReps+" reps";
                                                    Log.e("???  LOG DAILY",AA);

                                //                    String todaydata = sp2.getString("data"+exercisename,"null");
                                //                    Log.e("??? todaydata",todaydata);
                                //                    if(todaydata =="null"){
                                //                        Datalists = new ArrayList<>();
                                //                    }else {
                                //                        Type type2 = new TypeToken<ArrayList<String>>(){}.getType();
                                //                        Gson gson2 = new Gson();
                                //                        Datalists = gson2.fromJson(todaydata,type2);
                                //                        for(int a =0; a<completeSetInfoArrayList.size(); a++){
                                //                            JSONArray jsonArray2 = new JSONArray(todaydata);
                                //                            String A = jsonArray2.getString(a);
                                //                            Log.e("??? Datalists"+a,""+A);
                                //
                                //                        }
                                //                    }


                                                         //logsDailyInfosList.add(new Logs_Daily_Info(part2,comName,comData));

                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                                   String todaydata = sp2.getString("data"+exercisename,"null");
                                                                   Log.e("??? todaydata",todaydata);
                                                                   if(todaydata =="null"){
                                                                       Datalists = new ArrayList<>();
                                                                   }else {
                                                                       Type type2 = new TypeToken<ArrayList<String>>(){}.getType();
                                                                       Gson gson2 = new Gson();
                                                                       Datalists = gson2.fromJson(todaydata,type2);
                                                                       for(int a =0; a<Datalists.size(); a++){
                                                                           JSONArray jsonArray2 = new JSONArray(todaydata);
                                                                           String A = jsonArray2.getString(a);
                                                                           Log.e("??? Datalists"+a,""+A);

                                                                            D += A+"\n";


                                                                       }
                                                                       Log.e("??? DDDDD "+exercisename+" "+part2+" / Datalist SIZE",D+" / "+""+Datalists.size());
                                                                       //Datalists.size()는 운동 한 세트수
                                                                       logsDailyInfosList.add(new Logs_Daily_Info(part2,exercisename,D));

                                                                       /** 통계 load*/
//                                                                       SharedPreferences appdata2= getSharedPreferences("통계들",0);
//                                                                       String B = appdata2.getString("통계"+selectedDay,"null");
//                                                                       if(B=="null"){
//                                                                           statInfosList = new ArrayList<>();
//                                                                       }else {
//                                                                           Type type3 = new TypeToken<ArrayList<StatisticsActivity_Info>>(){}.getType();
//                                                                           Gson gson3 = new Gson();
//                                                                           statInfosList = gson3.fromJson(B,type);
//                                                                       }
                                                                       /** 통계 load*/ //세이브만 해줘야 한다 로드를 해주면 매번 들어올때마다 계속 기록들이 쌓인다.


                                                                       /** 통계 save*/
                                                                       StatisticsActivity_Info statisticsInfo = new StatisticsActivity_Info(exercisename,part2,Integer.toString(Datalists.size()));
                                                                       statInfosList.add(statisticsInfo);
                                                                       SharedPreferences appdata= getSharedPreferences("통계들"+Main2Activity.Loginname,0);
                                                                       SharedPreferences.Editor editor = appdata.edit();
                                                                       Gson gson1 = new Gson();
                                                                       String A = gson1.toJson(statInfosList);
                                                                       editor.putString("통계"+selectedDay,A);
                                                                       editor.apply();
                                                                       /** 통계 save*/

                                                                       /***/
                                                                       D="";// 반드시 해줘야 다른 운동 세트 기록이 제대로 나온다
                                                                       /***/








                                                                       /*************************************************************************/
//                                                                       StatisticsList.add(exercisename);
//                                                                       //오늘한 운동 목록들이 담겨있다
//                                                                       Gson gson1 = new Gson();
//                                                                       String list = gson1.toJson(StatisticsList);
//                                                                       SharedPreferences AP = getSharedPreferences("통계",0);
//                                                                       SharedPreferences.Editor editor = AP.edit();
//                                                                       editor.putString("통계"+selectedDay,list);
//                                                                       editor.apply();
//                                                                       Log.e("??? StatisticsList 내용",""+StatisticsList);
//                                                                       Log.e("??? logsDailyInfosList 사이즈", ""+logsDailyInfosList.size());
                                                                       /*************************************************************************/
                                                                   }
                                                                }
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }

        //String todayCom = sp2.getString("완료","null");
//        Log.e("??? todayCom",todayCom);
//        if(todayCom=="null"){
//            completeSetInfoArrayList = new ArrayList<>();
//        }else {
//            Type type = new TypeToken<ArrayList<CompleteSetInfo>>(){}.getType();
//            Gson gson = new Gson();
//            completeSetInfoArrayList = gson.fromJson(todayCom,type);
//            for(int j =0; j<completeSetInfoArrayList.size(); j++){
//                try {
//                    JSONArray jsonArray = new JSONArray(todayCom);
//                    String k = jsonArray.getString(j);
//                    JSONObject jsonObject = new JSONObject(k);
//                    String comSet = jsonObject.getString("comSet");
//                    String comWeight = jsonObject.getString("comWeight");
//                    String comReps = jsonObject.getString("comReps");
//                    String comName = jsonObject.getString("comName");
//                    String comTime = jsonObject.getString("comTime");
//
//                    String AA= comName+"/ set"+comSet+" Weight/"+comWeight+" Reps/"+comReps;
//                    Log.e("???  LOG DAILY",AA);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }



//        yeartxt.setText(year);
//        monthtxt.setText(month);
//        daytxt.setText(day);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.log_daily_toolbar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.deletebtn:

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Logs_Daily.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_add_workout,null);
                Button nobtn =  mView.findViewById(R.id.add_nobtn);
                Button yesbtn = mView.findViewById(R.id.add_yesbtn);
                TextView title = mView.findViewById(R.id.textView23);
                mBuilder.setView(mView);
                title.setText("운동 기록을 삭제하시겠습니까?\n(나의 운동통계 결과에 영향을 줄 수 있습니다.)");

                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                       SharedPreferences ap4 =getSharedPreferences(selectedDay+Main2Activity.Loginname,0);
                       SharedPreferences.Editor editor4 = ap4.edit();
                       editor4.clear().commit();

                        Toast.makeText(Logs_Daily.this,"운동 기록을 삭제했습니다",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Logs_Daily.this,Logs.class);
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
                return true;

            case R.id.staticsticBtn:
                Toast.makeText(Logs_Daily.this, "Statistics", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Logs_Daily.this,StatisticsActivity.class);
                intent.putExtra("선택한날",selectedDay);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
