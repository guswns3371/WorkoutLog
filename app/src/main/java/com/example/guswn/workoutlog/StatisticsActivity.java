package com.example.guswn.workoutlog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.guswn.workoutlog.Main2Activity.DayList;


public class StatisticsActivity extends AppCompatActivity {

    Toolbar stat_toolbar;
    PieChart pieChart;
    Button stat_allbtn;
    String ST_name;
    String ST_part;
    String ST_reps;

    String ST2_name;
    String ST2_reps;
    String DATE;
    int todaynum;
    String selectedDay;
    ArrayList<String> StatList = new ArrayList<>();
    ArrayList<String> AllStatList = new ArrayList<>();
//    static ArrayList<String> DayList = new ArrayList<>();

    ArrayList<Statistics_Another_Info> statisticsAnotherInfosList;
    Statistics_Another_Info statisticsAnotherInfo;
    String allData;
    int[] count = new int[] {0,0,0,0,0,0,0};
    int[] allcount = new int[] {0,0,0,0,0,0,0};
    static String[]  part = new String[] {"복근","등","이두","가슴","하체","어깨","삼두"};
    ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        stat_toolbar = findViewById(R.id.stat_toolbar);
        pieChart = (PieChart)findViewById(R.id.piechart);
        stat_allbtn = findViewById(R.id.stat_allbtn);
        stat_allbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this,StatisticsActivity_ALL.class);
                startActivity(intent);
            }
        });

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DATE = date.format(today);



        Intent intent = getIntent();
        selectedDay = intent.getStringExtra("선택한날");
        Log.e("??? DATE / selectedDay",DATE+"/"+selectedDay);
//        if(!DATE .equals(selectedDay) ){
//            stat_allbtn.setVisibility(View.GONE);
//        }// 오늘 날짜에서만 전체 통계 볼수 있도록





        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        //pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);





        /************************************************************************************************************/
        //날짜 저장 DayList
//        /** selectedDay  load*/
//        SharedPreferences SPP2 = getSharedPreferences("통계날짜들",0);
//        String k = SPP2.getString("통계날짜","null");
//        Log.e("??? DayList 통계날짜",k);
//        if(k=="null"){
//            DayList = new ArrayList<>();
//        }else {
//            Type type3 = new TypeToken<ArrayList<String>>(){}.getType();
//            Gson gson3 = new Gson();
//            DayList =  gson3.fromJson(k,type3);
//            Log.e("??? DayList SIZE",""+DayList.size());
//        }
//        /** selectedDay  load*/
//
//        /** selectedDay= save*/
//        SharedPreferences SPP = getSharedPreferences("통계날짜들",0);
//        SharedPreferences.Editor editor = SPP.edit();
//        if(DayList.size()>0) {
//            Boolean isSame = false;
//            Log.e("??? DayList SIZE >0",".");
//            for (int i=0; i<DayList.size(); i++){
//                if(DayList.get(i).equals(selectedDay)){
////                   DayList.add(selectedDay);
////                   Gson gson1 = new Gson();
////                   String aaa = gson1.toJson(DayList);
////                   editor.putString("통계날짜",aaa);
////                   editor.apply();
//                    isSame = true;
//                    Log.e("??? DayList isSame ",""+isSame);
//                }
//            }
//            Log.e("??? DayList FINAL isSame ",""+isSame);
//            if(isSame==false){
//                   DayList.add(selectedDay);
//                   Gson gson1 = new Gson();
//                   String aaa = gson1.toJson(DayList);
//                   editor.putString("통계날짜",aaa);
//                   editor.apply();
//
//                   SharedPreferences ppp= getSharedPreferences("통계날짜들",0);
//                   String ll =ppp.getString("통계날짜","null");
//                   Log.e("??? DayList isSame false 저장 결과",ll);
//            }
//
//        }else {
//            Log.e("??? DayList SIZE ==0",".");
//            DayList.add(selectedDay);
//            Gson gson1 = new Gson();
//            String aaa = gson1.toJson(DayList);
//            editor.putString("통계날짜",aaa);
//            editor.apply();
//        }
        /** selectedDay= save*/
        /************************************************************************************************************/

        setSupportActionBar(stat_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("운동 분석");


        /************************************************************************************************************/
        //오늘의 운동분석 StatList
        SharedPreferences AP = getSharedPreferences("통계들"+Main2Activity.Loginname,0); //이걸로 바꿔야한다!!!!!!!꼭!!!!!!!!
//       /**test!!!*/ SharedPreferences AP = getSharedPreferences("통계들",0);
        String todayStat = AP.getString("통계"+selectedDay,"null");
        Log.e("??? fuck"," "+todayStat);
        if(todayStat !="null") {
            Type type = new TypeToken<ArrayList<StatisticsActivity_Info>>() {}.getType();
            Gson gson = new Gson();
            StatList = gson.fromJson(todayStat, type);
            Log.e("??? 오늘의 StatList SIZE", "" + StatList.size());
            //Log.e("??? 오늘의 StatList 정보들",""+StatList);
            for (int i = 0; i < StatList.size(); i++) {
                Gson gson1 = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(gson1.toJson(StatList.get(i)));
                    ST_name = jsonObject.getString("ST_name");
                    ST_part = jsonObject.getString("ST_part");
                    ST_reps = jsonObject.getString("ST_reps");
                    Log.e("??? 오늘 StatList 정보 " + selectedDay + " [" + Integer.toString(i) + "]", ST_name + " / " + ST_part + " / " + ST_reps);
                    int a = Integer.parseInt(ST_reps);
                    //part = new String[] {"복근","등","이두","가슴","하체","어깨","삼두"};
//                    count = new int[] {0,0,0,0,0,0,0};
//                  yValues.add(new PieEntry((float)a,ST_name));
//                  yValues.add(new PieEntry((float)a,ST_part));
                    switch (ST_part){
                        case "복근":
                            count[0]+=Integer.parseInt(ST_reps);
                            break;
                        case "등":
                            count[1]+=Integer.parseInt(ST_reps);
                            break;
                        case "이두":
                            count[2]+=Integer.parseInt(ST_reps);
                            break;
                        case "가슴":
                            count[3]+=Integer.parseInt(ST_reps);
                            break;
                        case "하체":
                            count[4]+=Integer.parseInt(ST_reps);
                            break;
                        case "어깨":
                            count[5]+=Integer.parseInt(ST_reps);
                            break;
                        case "삼두":
                            count[6]+=Integer.parseInt(ST_reps);
                            break;
                    }

                    /************************************************************************/
                    //이제 전체 통계를 위해 여태동안 했던 운동들 다 합치기
                    /** 합치기 load*/

                    /** 합치기 load*/

                    /** 합치기 save*/

                    /** 합치기 save*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            for(int i=0; i<7; i++){
                if(count[i]!=0) {
                    yValues.add(new PieEntry((float) count[i], part[i]));
                    Log.e("### 오늘 = 부위 / 횟수",selectedDay+" / "+part[i]+" / "+count[i]);
                }
            }
            todaynum = count[0] + count[1] + count[2] + count[3] + count[4] + count[5] + count[6];
            Log.e("??? todaynum",""+todaynum);

        }
        //오늘의 운동분석 StatList
        /***********************************************************************************************************/

//        /***********************************************************************************************************/
//        // 전체 운동분석 AllStatList - load
//        SharedPreferences SSS = getSharedPreferences("전체통계",0);
//        String KKK = SSS.getString("전체통계들","null");
//        Log.e("??? statisticsAnotherInfosList 전체통계들",KKK);
//        if(KKK=="null"){
//            statisticsAnotherInfosList = new ArrayList<>();
//        }else {
//            Type type3 = new TypeToken<ArrayList<Statistics_Another_Info>>(){}.getType();
//            Gson gson3 = new Gson();
//            statisticsAnotherInfosList =  gson3.fromJson(KKK,type3);
//            Log.e("??? statisticsAnotherInfosList SIZE",""+statisticsAnotherInfosList.size());
//        }
//
//        // 전체 운동분석 AllStatList - load
//        /***********************************************************************************************************/

        /***********************************************************************************************************/
        // 전체 운동분석 AllStatList - save

        // 전체 운동분석 AllStatList - save
        /***********************************************************************************************************/


        /************************************* MPChart**********************************************/
//        pieChart.setUsePercentValues(true);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(5,10,5,5);
//
//        pieChart.setDragDecelerationFrictionCoef(0.95f);
//
//        pieChart.setDrawHoleEnabled(false);
//        pieChart.setHoleColor(Color.WHITE);
//        pieChart.setTransparentCircleRadius(61f);
//
//        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
//
//        yValues.add(new PieEntry(34f,"Japen"));
//        yValues.add(new PieEntry(23f,"USA"));
//        yValues.add(new PieEntry(14f,"UK"));
//        yValues.add(new PieEntry(35f,"India"));
//        yValues.add(new PieEntry(40f,"Russia"));
//        yValues.add(new PieEntry(40f,"Korea"));
//
        Description description = new Description();
        description.setText(selectedDay+" 운동 횟수 : "+todaynum+" 회"); //라벨
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



//        AllStastics();//전체통계

    }

    public void AllStastics(){
        SharedPreferences APR = getSharedPreferences("통계들"+Main2Activity.Loginname,0);
//      /**test!!*/  SharedPreferences AP = getSharedPreferences("통계들",0);
        for(int b=0; b< DayList.size(); b++){
            String todayStat = APR.getString("통계"+DayList.get(b),"null");
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
        for(int i=0; i<7; i++){
            if(allcount[i]!=0) {
//                yValues.add(new PieEntry((float) allcount[i], part[i]));
                Log.e("???###!!! 전체 = 부위 / 횟수",part[i]+" / "+allcount[i]);
            }
        }

    }




    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작
                    finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
