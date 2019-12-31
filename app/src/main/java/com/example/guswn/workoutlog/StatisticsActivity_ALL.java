package com.example.guswn.workoutlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


import static com.example.guswn.workoutlog.Main2Activity.DayList;
import static com.example.guswn.workoutlog.StatisticsActivity.part;


public class StatisticsActivity_ALL extends AppCompatActivity {
    ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
    ArrayList<String> AllStatList = new ArrayList<>();
    PieChart pieChart;
    Toolbar stat_alltoolbar;
    String ST_name,ST_part,ST_reps;
    int[] allcount = new int[] {0,0,0,0,0,0,0};
    int todaynum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics__all);

        stat_alltoolbar = findViewById(R.id.stat_alltoolbar);
        setSupportActionBar(stat_alltoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
//        setSupportActionBar(stat_alltoolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
//        getSupportActionBar().setTitle("전체 운동 분석");

        pieChart = (PieChart)findViewById(R.id.piechart2);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        //pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);



        AllStastics();



        Description description = new Description();
        description.setText("전체 운동 횟수 : "+todaynum+" 회"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
    }

    public void AllStastics(){
        SharedPreferences AP = getSharedPreferences("통계들"+Main2Activity.Loginname,0);
//        /**test!!*/  SharedPreferences AP = getSharedPreferences("통계들",0);
        for(int b=0; b< DayList.size(); b++){
            String todayStat = AP.getString("통계"+ DayList.get(b),"null");
//            Log.e("??? DayList2 풀어헤친 날들",DayList.get(b));
            Log.e("??? DayList2 풀어헤친 날들 저장된값",DayList.get(b)+" / "+todayStat);
            if(todayStat !="null") {
                Type type = new TypeToken<ArrayList<StatisticsActivity_Info>>() {}.getType();
                Gson gson = new Gson();
                AllStatList = gson.fromJson(todayStat, type);
                Log.e("??? 오늘의 AllStatList SIZE", "" + AllStatList.size());
                //Log.e("??? 오늘의 AllStatList 정보들",""+StatList);
                for (int i = 0; i < AllStatList.size(); i++) {
                    Gson gson1 = new Gson();
                    try {
                        JSONObject jsonObject = new JSONObject(gson1.toJson(AllStatList.get(i)));
                        ST_name = jsonObject.getString("ST_name");
                        ST_part = jsonObject.getString("ST_part");
                        ST_reps = jsonObject.getString("ST_reps");
                        Log.e("??? 오늘 AllStatList 정보 " + DayList.get(b) + " [" + Integer.toString(i) + "]", ST_name + " / " + ST_part + " / " + ST_reps);
                        int a = Integer.parseInt(ST_reps);
                        //part = new String[] {"복근","등","이두","가슴","하체","어깨","삼두"};
//                    count = new int[] {0,0,0,0,0,0,0};
//                  yValues.add(new PieEntry((float)a,ST_name));
//                  yValues.add(new PieEntry((float)a,ST_part));
                        switch (ST_part){
                            case "복근":
                                allcount[0]+=Integer.parseInt(ST_reps);
                                break;
                            case "등":
                                allcount[1]+=Integer.parseInt(ST_reps);
                                break;
                            case "이두":
                                allcount[2]+=Integer.parseInt(ST_reps);
                                break;
                            case "가슴":
                                allcount[3]+=Integer.parseInt(ST_reps);
                                break;
                            case "하체":
                                allcount[4]+=Integer.parseInt(ST_reps);
                                break;
                            case "어깨":
                                allcount[5]+=Integer.parseInt(ST_reps);
                                break;
                            case "삼두":
                                allcount[6]+=Integer.parseInt(ST_reps);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

//        Statistics_Info statisticsInfo = new Statistics_Info( allcount[0], allcount[1], allcount[2], allcount[3], allcount[4], allcount[5], allcount[6]);
//        Gson gson = new Gson();
//        ArrayList<Statistics_Info> statisticsInfoArrayList = new ArrayList<>();
//        statisticsInfoArrayList.add(statisticsInfo);
//        String aa = gson.toJson(statisticsInfoArrayList);
//        SharedPreferences ap = getSharedPreferences("오늘운동횟수"+Main2Activity.Loginname,0);
//        SharedPreferences.Editor editor = ap.edit();
//        editor.putString("오늘운동한것들",aa);
//        editor.apply();

        for(int i=0; i<7; i++){
            if(allcount[i]!=0) {
                yValues.add(new PieEntry((float) allcount[i], part[i]));
                Log.e("???###!!! 전체 = 부위 / 횟수",part[i]+" / "+allcount[i]);
            }
        }
        todaynum = allcount[0]+allcount[1]+allcount[2]+allcount[3]+allcount[4]+allcount[5]+allcount[6];
        Log.e("??? todaynum",""+todaynum);


        Statistics_Info statisticsInfo = new Statistics_Info( allcount[0], allcount[1], allcount[2], allcount[3], allcount[4], allcount[5], allcount[6]);
        Gson gson = new Gson();
        ArrayList<Statistics_Info> statisticsInfoArrayList = new ArrayList<>();
        statisticsInfoArrayList.add(statisticsInfo);
        String aa = gson.toJson(statisticsInfoArrayList);
        SharedPreferences ap = getSharedPreferences("전체운동횟수"+Main2Activity.Loginname,0);
        SharedPreferences.Editor editor = ap.edit();
        editor.putString("전체운동한것들",aa);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.statistic_all_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.statistics_rank:
                Intent intent = new Intent(StatisticsActivity_ALL.this,RankingActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onOptionsItemSelected(final MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home://toolbar의 back키 눌렀을 때 동작
//                Log.e("??? 뭐야 왜 안돼 ???","뒤로가기가 안 되잖아");
//                finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
