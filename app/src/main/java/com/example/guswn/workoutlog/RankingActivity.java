package com.example.guswn.workoutlog;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieEntry;
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
import static com.example.guswn.workoutlog.StatisticsActivity.part;


public class RankingActivity extends AppCompatActivity implements RankingActivity_Adapter.MyRecyclerViewClickListener2 {

    Toolbar ranking_toolbar;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RankingActivity_Adapter mAdapter;
    ArrayList<RankingActivity_Info> rankingActivityInfos;
    ArrayList<Statistics_Info> statisticsInfoArrayList;

    String strPerson;
    ArrayList<String> IDList = new ArrayList<>();
    String ID;
    int Stat_abs;
    int Stat_back;
    int Stat_biceps;
    int Stat_chest;
    int Stat_legs;
    int Stat_shoulder;
    int Stat_triceps;
    int allcountS;

    String ST_name, ST_part, ST_reps;
    ArrayList<String> AllStatList = new ArrayList<>();
    ArrayList<Rank_everycount> rankEverycountsList = new ArrayList<>();
    int[] allcount = new int[]{0, 0, 0, 0, 0, 0, 0};


    int mypos;
    ArrayList<CompleteSetInfo> completeSetInfoArrayList = new ArrayList<>();
    ArrayList<String> Datalists = new ArrayList<>();
    String exercisename;
    String part2;
    String comName;
    String comData;
    String D = "";
    String Strallcount = "0";
    ArrayList<StatisticsActivity_Info> statInfosList = new ArrayList<>();
    ArrayList<Logs_Daily_Info> logsDailyInfosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ranking_toolbar = findViewById(R.id.ranking_toolbar);

        mRecyclerView = findViewById(R.id.ranking_RV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        rankingActivityInfos = new ArrayList<>();

        mAdapter = new RankingActivity_Adapter(rankingActivityInfos, this);
        mAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        SharedPreferences appData = getSharedPreferences("register", 0);
        strPerson = appData.getString("person", "null");
        try {
            String result = "";
            JSONArray ja = new JSONArray(strPerson);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject order = ja.getJSONObject(i);
                result = "Email: " + order.getString("Email") + ", Id: " + order.getString("Id") +
                        ", Name: " + order.getString("Name") + ", Password: " + order.getString("Password") + "\n";
                Log.e("??? 풀어헤친 회원정보" + Integer.toString(i), result);
                ID = order.getString("Id");
                IDList.add(ID);
            }

        } catch (JSONException e) {
            ;
        }

        Statistics();
        AllStastics();

        for (int i = 0; i < IDList.size(); i++) {
            SharedPreferences ap = getSharedPreferences("전체운동횟수" + IDList.get(i), 0);
            String A = ap.getString("전체운동한것들", "null");
            if (A.equals("null")) {
                statisticsInfoArrayList = new ArrayList<>();
                Log.e("??? statisticsInfoArrayList fuck", "fuck");

            } else {
                Type type = new TypeToken<ArrayList<Statistics_Info>>() {
                }.getType();
                Gson gson = new Gson();
                statisticsInfoArrayList = gson.fromJson(A, type);
                Log.e("??? statisticsInfoArrayList", "" + statisticsInfoArrayList.get(0).getStat_abs());
                Stat_abs = statisticsInfoArrayList.get(0).getStat_abs();
                Stat_back = statisticsInfoArrayList.get(0).getStat_back();
                Stat_biceps = statisticsInfoArrayList.get(0).getStat_biceps();
                Stat_chest = statisticsInfoArrayList.get(0).getStat_chest();
                Stat_legs = statisticsInfoArrayList.get(0).getStat_legs();
                Stat_shoulder = statisticsInfoArrayList.get(0).getStat_shoulder();
                Stat_triceps = statisticsInfoArrayList.get(0).getStat_triceps();

                allcountS = Stat_abs + Stat_back + Stat_biceps + Stat_chest + Stat_legs + Stat_shoulder + Stat_triceps;
                Strallcount = Integer.toString(allcountS);
//                rankEverycountsList.add(new Rank_everycount(Stat_abs, Stat_back, Stat_biceps, Stat_chest, Stat_legs, Stat_shoulder, Stat_triceps));


            }


            SharedPreferences app3 = getSharedPreferences("사진", 0);
            String newimg = app3.getString("사진" + IDList.get(i), "null");
            Log.e("??? newimg", newimg);

            rankEverycountsList.add(new Rank_everycount(Stat_abs, Stat_back, Stat_biceps, Stat_chest, Stat_legs, Stat_shoulder, Stat_triceps));
            rankingActivityInfos.add(new RankingActivity_Info(newimg, "ID : " + IDList.get(i), "전체 운동횟수 : " + Strallcount));
            Strallcount = "0";
            if (IDList.get(i).equals(Main2Activity.Loginname)) {
                mypos = i;
            }

        }


        setSupportActionBar(ranking_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }


    int[] partcount = new int[]{0, 0, 0, 0, 0, 0, 0};
    int[] mycount = new int[]{0, 0, 0, 0, 0, 0, 0};
    int[] gap = new int[]{0, 0, 0, 0, 0, 0, 0};

    @Override
    public void onItemClicked2(int position, View v) {

        String name = rankingActivityInfos.get(position).getRank_id();
        String trim_name = name.replace("ID : ", "");

        partcount[0] = rankEverycountsList.get(position).getStat_abs();
        partcount[1] = rankEverycountsList.get(position).getStat_back();
        partcount[2] = rankEverycountsList.get(position).getStat_biceps();
        partcount[3] = rankEverycountsList.get(position).getStat_chest();
        partcount[4] = rankEverycountsList.get(position).getStat_legs();
        partcount[5] = rankEverycountsList.get(position).getStat_shoulder();
        partcount[6] = rankEverycountsList.get(position).getStat_triceps();

        Log.e("??? RNAKING all ", trim_name + " **/" + partcount[0] + "/" + partcount[1] + "/" + partcount[2]
                + "/" + partcount[3] + "/" + partcount[4] + "/" + partcount[5] + "/" + partcount[6]);
         Log.e("??? RNAKING my position",""+mypos);

        mycount[0] = rankEverycountsList.get(mypos).getStat_abs();
        mycount[1] = rankEverycountsList.get(mypos).getStat_back();
        mycount[2] = rankEverycountsList.get(mypos).getStat_biceps();
        mycount[3] = rankEverycountsList.get(mypos).getStat_chest();
        mycount[4] = rankEverycountsList.get(mypos).getStat_legs();
        mycount[5] = rankEverycountsList.get(mypos).getStat_shoulder();
        mycount[6] = rankEverycountsList.get(mypos).getStat_triceps();

        for (int i = 0; i < 7; i++) {
//            gap[i] = Math.abs(mycount[i]-partcount[i]);
            gap[i] = (mycount[i] - partcount[i]);
        }
        Log.e("??? RNAKING GAP ", trim_name + "/ " + Main2Activity.Loginname + " **/" + gap[0] + "/" + gap[1] + "/" + gap[2]
                + "/" + gap[3] + "/" + gap[4] + "/" + gap[5] + "/" + gap[6]);


        /**다이얼로그 띄우자*/
//        statdialog();
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RankingActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.ranking_dialog, null);
        final TextView NAME = mView.findViewById(R.id.rank_dialog_name);

        final TextView id1 = mView.findViewById(R.id.id1);
        final TextView id2 = mView.findViewById(R.id.id2);
        final TextView id3 = mView.findViewById(R.id.id3);
        final TextView id4 = mView.findViewById(R.id.id4);
        final TextView id5 = mView.findViewById(R.id.id5);
        final TextView id6 = mView.findViewById(R.id.id6);
        final TextView id7 = mView.findViewById(R.id.id7);

        final TextView m1 = mView.findViewById(R.id.m1);
        final TextView m2 = mView.findViewById(R.id.m2);
        final TextView m3 = mView.findViewById(R.id.m3);
        final TextView m4 = mView.findViewById(R.id.m4);
        final TextView m5 = mView.findViewById(R.id.m5);
        final TextView m6 = mView.findViewById(R.id.m6);
        final TextView m7 = mView.findViewById(R.id.m7);

        final TextView g1 = mView.findViewById(R.id.g1);
        final TextView g2 = mView.findViewById(R.id.g2);
        final TextView g3 = mView.findViewById(R.id.g3);
        final TextView g4 = mView.findViewById(R.id.g4);
        final TextView g5 = mView.findViewById(R.id.g5);
        final TextView g6 = mView.findViewById(R.id.g6);
        final TextView g7 = mView.findViewById(R.id.g7);


        NAME.setText(trim_name);

        id1.setText("" + partcount[0] + "번");
        id2.setText("" + partcount[1] + "번");
        id3.setText("" + partcount[2] + "번");
        id4.setText("" + partcount[3] + "번");
        id5.setText("" + partcount[4] + "번");
        id6.setText("" + partcount[5] + "번");
        id7.setText("" + partcount[6] + "번");

        m1.setText("" + mycount[0] + "번");
        m2.setText("" + mycount[1] + "번");
        m3.setText("" + mycount[2] + "번");
        m4.setText("" + mycount[3] + "번");
        m5.setText("" + mycount[4] + "번");
        m6.setText("" + mycount[5] + "번");
        m7.setText("" + mycount[6] + "번");

        if (gap[0] < 0) {
            g1.setTextColor(Color.RED);
        }
        if (gap[1] < 0) {
            g2.setTextColor(Color.RED);
        }
        if (gap[2] < 0) {
            g3.setTextColor(Color.RED);
        }
        if (gap[3] < 0) {
            g4.setTextColor(Color.RED);
        }
        if (gap[4] < 0) {
            g5.setTextColor(Color.RED);
        }
        if (gap[5] < 0) {
            g6.setTextColor(Color.RED);
        }
        if (gap[6] < 0) {
            g7.setTextColor(Color.RED);
        }
        g1.setText("" + gap[0] + "번");
        g2.setText("" + gap[1] + "번");
        g3.setText("" + gap[2] + "번");
        g4.setText("" + gap[3] + "번");
        g5.setText("" + gap[4] + "번");
        g6.setText("" + gap[5] + "번");
        g7.setText("" + gap[6] + "번");


//        Button nobtn =  mView.findViewById(R.id.nobtn);
//        Button yesbtn = mView.findViewById(R.id.yesbtn);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();


    }

    public void statdialog() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RankingActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.ranking_dialog, null);
        final TextView id1 = mView.findViewById(R.id.id1);
        final TextView id2 = mView.findViewById(R.id.id2);
        final TextView id3 = mView.findViewById(R.id.id3);
        final TextView id4 = mView.findViewById(R.id.id4);
        final TextView id5 = mView.findViewById(R.id.id5);
        final TextView id6 = mView.findViewById(R.id.id6);
        final TextView id7 = mView.findViewById(R.id.id7);

        final TextView m1 = mView.findViewById(R.id.m1);
        final TextView m2 = mView.findViewById(R.id.m2);
        final TextView m3 = mView.findViewById(R.id.m3);
        final TextView m4 = mView.findViewById(R.id.m4);
        final TextView m5 = mView.findViewById(R.id.m5);
        final TextView m6 = mView.findViewById(R.id.m6);
        final TextView m7 = mView.findViewById(R.id.m7);

        final TextView g1 = mView.findViewById(R.id.g1);
        final TextView g2 = mView.findViewById(R.id.g2);
        final TextView g3 = mView.findViewById(R.id.g3);
        final TextView g4 = mView.findViewById(R.id.g4);
        final TextView g5 = mView.findViewById(R.id.g5);
        final TextView g6 = mView.findViewById(R.id.g6);
        final TextView g7 = mView.findViewById(R.id.g7);

        id1.setText(partcount[0]);
        id2.setText(partcount[1]);
        id3.setText(partcount[2]);
        id4.setText(partcount[3]);
        id5.setText(partcount[4]);
        id6.setText(partcount[5]);
        id7.setText(partcount[6]);

        m1.setText(mycount[0]);
        m2.setText(mycount[1]);
        m3.setText(mycount[2]);
        m4.setText(mycount[3]);
        m5.setText(mycount[4]);
        m6.setText(mycount[5]);
        m7.setText(mycount[6]);

        g1.setText(gap[0]);
        g2.setText(gap[1]);
        g3.setText(gap[2]);
        g4.setText(gap[3]);
        g5.setText(gap[4]);
        g6.setText(gap[5]);
        g7.setText(gap[6]);


//        Button nobtn =  mView.findViewById(R.id.nobtn);
//        Button yesbtn = mView.findViewById(R.id.yesbtn);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
//        yesbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {}
//
//        });
//        nobtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void AllStastics() {
        SharedPreferences AP = getSharedPreferences("통계들" + Main2Activity.Loginname, 0);
//        /**test!!*/  SharedPreferences AP = getSharedPreferences("통계들",0);
        for (int b = 0; b < DayList.size(); b++) {
            String todayStat = AP.getString("통계" + DayList.get(b), "null");
            if (todayStat != "null") {
                Type type = new TypeToken<ArrayList<StatisticsActivity_Info>>() {
                }.getType();
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
                        switch (ST_part) {
                            case "복근":
                                allcount[0] += Integer.parseInt(ST_reps);
                                break;
                            case "등":
                                allcount[1] += Integer.parseInt(ST_reps);
                                break;
                            case "이두":
                                allcount[2] += Integer.parseInt(ST_reps);
                                break;
                            case "가슴":
                                allcount[3] += Integer.parseInt(ST_reps);
                                break;
                            case "하체":
                                allcount[4] += Integer.parseInt(ST_reps);
                                break;
                            case "어깨":
                                allcount[5] += Integer.parseInt(ST_reps);
                                break;
                            case "삼두":
                                allcount[6] += Integer.parseInt(ST_reps);
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
//        SharedPreferences ap = getSharedPreferences("전체운동횟수"+Main2Activity.Loginname,0);
//        SharedPreferences.Editor editor = ap.edit();
//        editor.putString("전체운동한것들",aa);
//        editor.apply();

        for (int i = 0; i < 7; i++) {
            if (allcount[i] != 0) {

                Log.e("???###!!! 전체 = 부위 / 횟수", part[i] + " / " + allcount[i]);
            }
        }
    }

    public void Statistics() {

        for (int AA = 0; AA < DayList.size(); AA++) {
            SharedPreferences sp2 = getSharedPreferences(DayList.get(AA) + Main2Activity.Loginname, 0);


            String[] part = {"복근", "등", "이두", "가슴", "하체", "어깨", "삼두"};
            for (int i = 0; i < 7; i++) {
                SharedPreferences APPDATA = getSharedPreferences("ALLWORKOUT", 0);
                String size = APPDATA.getString(part[i] + "사이즈", "null");
                if (!size.equals("null")) {
                    for (int j = 0; j < Integer.parseInt(size); j++) {
                        String val = APPDATA.getString(part[i] + Integer.toString(j), "null");
                        if (!val.equals("null")) {
                            try {
                                JSONObject jsonObject = new JSONObject(val);
                                exercisename = jsonObject.getString("exercisename");
                                part2 = jsonObject.getString("part");
                                String todayCom = sp2.getString("완료" + exercisename, "null");
                                if (todayCom == "null") {
                                    completeSetInfoArrayList = new ArrayList<>();
                                } else {
                                    Type type = new TypeToken<ArrayList<CompleteSetInfo>>() {
                                    }.getType();
                                    Gson gson = new Gson();
                                    completeSetInfoArrayList = gson.fromJson(todayCom, type);
                                    for (int kk = 0; kk < completeSetInfoArrayList.size(); kk++) {
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

                                            String AA2 = comName + "/ Data" + comData + " part/" + part2;
                                            //                    String data = comWeight+" kg x "+comReps+" reps";
                                            Log.e("???  LOG DAILY", AA2);

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
                                    String todaydata = sp2.getString("data" + exercisename, "null");
                                    Log.e("??? todaydata", todaydata);
                                    if (todaydata == "null") {
                                        Datalists = new ArrayList<>();
                                    } else {
                                        Type type2 = new TypeToken<ArrayList<String>>() {
                                        }.getType();
                                        Gson gson2 = new Gson();
                                        Datalists = gson2.fromJson(todaydata, type2);
                                        for (int a = 0; a < Datalists.size(); a++) {
                                            JSONArray jsonArray2 = new JSONArray(todaydata);
                                            String A = jsonArray2.getString(a);
                                            Log.e("??? Datalists" + a, "" + A);

                                            D += A + "\n";


                                        }
                                        Log.e("??? DDDDD " + exercisename + " " + part2 + " / Datalist SIZE", D + " / " + "" + Datalists.size());
                                        //Datalists.size()는 운동 한 세트수
//                                        logsDailyInfosList.add(new Logs_Daily_Info(part2, exercisename, D));

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
//                                        StatisticsActivity_Info statisticsInfo = new StatisticsActivity_Info(exercisename, part2, Integer.toString(Datalists.size()));
//                                        statInfosList.add(statisticsInfo);
//                                        SharedPreferences appdata = getSharedPreferences("통계들" + Main2Activity.Loginname, 0);
//                                        SharedPreferences.Editor editor = appdata.edit();
//                                        Gson gson1 = new Gson();
//                                        String A = gson1.toJson(statInfosList);
//                                        editor.putString("통계" + DayList.get(AA), A);
//                                        editor.apply();
                                        /** 통계 save*/

                                        /***/
                                        D = "";// 반드시 해줘야 다른 운동 세트 기록이 제대로 나온다
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

        }
    }


}
