package com.example.guswn.workoutlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Logs extends AppCompatActivity {

    MaterialCalendarView calendarView;
    Toolbar logstb;


    ArrayList<CompleteSetInfo> completeSetInfoArrayList = new ArrayList<>();
    ArrayList<String > Datalists = new ArrayList<>();
    String exercisename;
    String comName;
    String comData;
    String D = "";

    String DATE;
    String part2;
    ArrayList<StatisticsActivity_Info> statInfosList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        calendarView = findViewById(R.id.calendarView);
        logstb = findViewById(R.id.logstb);
        setSupportActionBar(logstb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("History");

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");


        DATE = date.format(today);
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017,1,1))
                .setMaximumDate(CalendarDay.from(2030,12,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        Log.e("??? DATEDTAE",DATE);
        
        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
               new OneDayDecorator());

        calendarView.setOnDateChangedListener(new OnDateSelectedListener(){
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Intent intent = new Intent(Logs.this,Logs_Daily.class);
                String year = Integer.toString(date.getYear());
                String month = Integer.toString(date.getMonth()+1);
                String day = Integer.toString(date.getDay());

                if(Integer.parseInt(day)<10){
                    day="0"+day;
                }
                if(Integer.parseInt(day)>=10){

                }
                Log.e("??? day fuckthatshhit",day);
                String today = year+"-"+month+"-"+day;//이게 선택한 날짜
                //Log.e("!!! 달력",""+date);
                Log.e("!!! 선택한 날짜 달력2",today);
               intent.putExtra("year",year);
               intent.putExtra("month",month);
               intent.putExtra("day",day);
               intent.putExtra("today",today);
               startActivity(intent);
            }


        });

        staistic();


    }

    public class SundayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SundayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }

    public class SaturdayDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }

    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date;

        public OneDayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.GREEN));
        }

        /**
         * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
         */
        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
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


    public  void  staistic(){
        SharedPreferences sp2 = getSharedPreferences(DATE+Main2Activity.Loginname,0);


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
//                                    logsDailyInfosList.add(new Logs_Daily_Info(part2,exercisename,D));

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
                                    editor.putString("통계"+DATE,A);
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

    }

}
