package com.example.guswn.workoutlog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Tab1_timer extends Fragment{

    Button starttimerbtn;
    Button pausebtn;
    Button resettimerbtn;
    NumberPicker picker_hour;
    NumberPicker picker_min;
    NumberPicker picker_sec;
    int hour_,min,sec;

    LinearLayout linear1;
    LinearLayout linear2;
    TextView hourtxt;
    TextView mintxt;
    TextView sectxt;
    TextView finaltimetxt;

    String text,Str;
    SharedPreferences prefs;
    SharedPreferences.Editor ePref;
    int hour,minute,second;

    static int t =0;
    Boolean bool = true;
    Boolean bool2 = true;

    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;

    static int cur_Status = Init;

    private boolean wheelScrolled = false;
    TimePicker timePicker;
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag1_timer_, container, false);

        picker_hour = rootView.findViewById(R.id.picker_hour);
        picker_min = rootView.findViewById(R.id.picker_min);
        picker_sec = rootView.findViewById(R.id.picker_sec);
        sectxt = rootView.findViewById(R.id.sectxt);
        mintxt = rootView.findViewById(R.id.mintxt);
        hourtxt = rootView.findViewById(R.id.hourtxt);
        finaltimetxt = rootView.findViewById(R.id.finaltimetxt);

        starttimerbtn = (Button) rootView.findViewById(R.id.starttimerbtn);
        pausebtn = rootView.findViewById(R.id.pausebtn);
        resettimerbtn = rootView.findViewById(R.id.resettimerbtn);
        linear1 = rootView.findViewById(R.id.linear1);
        linear2 = rootView.findViewById(R.id.linear2);


        picker_hour.setMaxValue(12);
        picker_hour.setMinValue(0);
        picker_hour.setWrapSelectorWheel(true);

        setTypeface(picker_hour,Typeface.MONOSPACE);

        if(t!=0) {
            SharedPreferences appdata = this.getActivity().getSharedPreferences("타이머", 0);
            SharedPreferences.Editor editor = appdata.edit();
            Date today = new Date();
            SimpleDateFormat time = new SimpleDateFormat("hhmmss");
            String TIME = time.format(today);
            editor.putString("들어온시간", TIME);
            editor.apply();
        }

       // hour = picker_hour.getValue();
       // hourtxt.setText(hour);
//        picker_hour.setOnScrollListener(new NumberPicker.OnScrollListener() {
//            @Override
//            public void onScrollStateChange(NumberPicker view, int scrollState) {
//                if(scrollState==SCROLL_STATE_IDLE) {
//                    hour_ = picker_hour.getValue();
//                    hourtxt.setText(Integer.toString(hour_));
//                    Log.e("??? zzzzz hour_", Integer.toString(hour_));
//                }
//            }
//        });
        picker_hour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                NumberPicker phour = (NumberPicker) picker;
                Log.e("??? oldval min",""+oldVal);
                Log.e("??? newVal min",""+newVal);
                hour_=newVal;
                hourtxt.setText(Integer.toString(newVal));

            }
        });







        picker_min.setMinValue(0);
        picker_min.setMaxValue(59);
        picker_min.setWrapSelectorWheel(true);
        setTypeface(picker_min,Typeface.MONOSPACE);

       // min = picker_min.getValue();
       // mintxt.setText(min);
//        picker_min.setOnScrollListener(new NumberPicker.OnScrollListener() {
//            @Override
//            public void onScrollStateChange(NumberPicker view, int scrollState) {
//                if(scrollState==SCROLL_STATE_IDLE) {
//                    NumberPicker pmin = (NumberPicker) view;
//                    min = pmin.getValue();
//                    mintxt.setText(Integer.toString(min));
//                    Log.e("??? zzzzz min", Integer.toString(min));
//                }
//            }
//        });
        picker_min.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                NumberPicker pmin = (NumberPicker) picker;
                Log.e("??? oldval min",""+oldVal);
                Log.e("??? newVal min",""+newVal);
                min=newVal;
                mintxt.setText(Integer.toString(newVal));

            }
        });






        picker_sec.setMinValue(0);
        picker_sec.setMaxValue(59);
        picker_sec.setWrapSelectorWheel(true);
        setTypeface(picker_sec,Typeface.MONOSPACE);

       // sec = picker_sec.getValue();
       // sectxt.setText(sec);
//        picker_sec.setOnScrollListener(new NumberPicker.OnScrollListener() {
//            @Override
//            public void onScrollStateChange(NumberPicker view, int scrollState) {
//                if(scrollState==SCROLL_STATE_IDLE) {
//                    sec = picker_sec.getValue();
//                    sectxt.setText(Integer.toString(sec));
//                    Log.e("??? zzzzz sec", Integer.toString(sec));
//                }
//            }
//        }); //  스크롤시 싱크가 안맞아

        picker_sec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                NumberPicker psec = (NumberPicker) picker;
                Log.e("??? oldval min",""+oldVal);
                Log.e("??? newVal min",""+newVal);
                sec = newVal;
                sectxt.setText(Integer.toString(newVal));

            }
        });
        prefs = getActivity().getSharedPreferences("Save", Activity.MODE_PRIVATE);
        t = prefs.getInt("t",t);

        Str = String.format("%02d : %02d : %02d",hour,minute,second);
        finaltimetxt.setText(Str);


        starttimerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(hour_+min+sec !=0) {
                    // setTime(v);
                    starttimerbtn.setVisibility(View.GONE);
                    pausebtn.setVisibility(View.VISIBLE);
                    resettimerbtn.setVisibility(View.VISIBLE);
                    linear1.setVisibility(View.GONE);
                    linear2.setVisibility(View.GONE);
                    finaltimetxt.setVisibility(View.VISIBLE);


                    try {
                        t = hour_ * 3600 + min * 60 + sec;
                        sum();
                        Str = String.format("%02d : %02d : %02d", hour, minute, second);
                        finaltimetxt.setText(Str);
                    } catch (Exception e) {
                    }


                    bool = true;

                    thread threadTest = new thread();
                    threadTest.setDaemon(true);

                    if (bool2) {
                        threadTest.start();

                    }

                    bool2 = false;

                    cur_Status=Run;
                }

            }
        });


        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (cur_Status){
                    case Run:
                        pausebtn.setText("RESUME");
                        pausebtn.setTextColor(Color.GREEN);
                        bool = false;
                        bool2 =true;
                        cur_Status = Pause;
                        break;

                    case Pause:
                        pausebtn.setText("PAUSE");
                        pausebtn.setTextColor(Color.WHITE);
                        bool = true;

                        thread threadTest = new thread();
                        threadTest.setDaemon(true);

                        if(bool2){
                            threadTest.start();
                        }

                        bool2= false;
                        cur_Status=Run;
                        break;

                    case Init:

                        break;
                }

            }
        });

        resettimerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starttimerbtn.setVisibility(View.VISIBLE);
                pausebtn.setVisibility(View.GONE);
                resettimerbtn.setVisibility(View.GONE);
                linear1.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.VISIBLE);
                finaltimetxt.setVisibility(View.GONE);

                bool = false;
                bool2= true;
                t=0;
                sum();
                //Str = String.format("%02d : %02d",minute,second);
                Str = String.format("%02d : %02d : %02d",hour,minute,second);
                finaltimetxt.setText(Str);
                hour_=0;
                min = 0;
                sec = 0;
                picker_hour.setValue(0);
                picker_min.setValue(0);
                picker_sec.setValue(0);
                cur_Status = Init;

            }
        });

        if(t != 0){
            if(cur_Status == Run) {
                SharedPreferences appdata2 = this.getActivity().getSharedPreferences("타이머", 0);
                String INTIME2 = appdata2.getString("들어온시간", "null");
                String OUTTIME2 = appdata2.getString("나간시간", "null");


                int INTIME = Integer.parseInt(INTIME2);
                int OUTTIME = Integer.parseInt(OUTTIME2);
                int term = INTIME - OUTTIME;

                Log.e("??? INTIME", "" + INTIME);
                Log.e("??? OUTTIME", "" + OUTTIME);
                Log.e("??? term", "" + term);

                t = t - term;

                starttimerbtn.setVisibility(View.GONE);
                pausebtn.setVisibility(View.VISIBLE);
                resettimerbtn.setVisibility(View.VISIBLE);
                linear1.setVisibility(View.GONE);
                linear2.setVisibility(View.GONE);
                finaltimetxt.setVisibility(View.VISIBLE);
                cur_Status = Run;
                try {
                    sum();
                    Str = String.format("%02d : %02d : %02d", hour, minute, second);
                    finaltimetxt.setText(Str);
                } catch (Exception e) {
                }


                bool = true;

                thread threadTest = new thread();
                threadTest.setDaemon(true);

                if (bool2) {
                    threadTest.start();

                }

                bool2 = false;
            }
            if(cur_Status==Pause){
                starttimerbtn.setVisibility(View.GONE);
                pausebtn.setVisibility(View.VISIBLE);
                resettimerbtn.setVisibility(View.VISIBLE);
                linear1.setVisibility(View.GONE);
                linear2.setVisibility(View.GONE);
                finaltimetxt.setVisibility(View.VISIBLE);
                pausebtn.setText("RESUME");
                pausebtn.setTextColor(Color.GREEN);
                bool = false;
                bool2 =true;
                try {
                    sum();
                    Str = String.format("%02d : %02d : %02d", hour, minute, second);
                    finaltimetxt.setText(Str);
                } catch (Exception e) {
                }

            }

        }













        return rootView;
    }


    public  void  setTypeface (NumberPicker picker , Typeface typeface){
        //주위의 숫자들을 보여주는 휠뷰
        try {
            Field field = NumberPicker.class.getDeclaredField("mSelectorWheelPaint");
            field.setAccessible(true);
            ((Paint)field.get(picker)).setTypeface(typeface);
            ((Paint)field.get(picker)).setTextSize(100);
            ((Paint)field.get(picker)).setColor(Color.GRAY);

        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }

        //선택된 숫자를 보여주는 TextView
        int count = picker.getChildCount();
        for(int i=0; i<count; i++){
            //자식뷰로 꺼내 폰트를 적용
            View view = picker.getChildAt(i);
            if(view instanceof TextView){
                ((TextView)view).setTypeface(typeface);
                ((TextView)view).setTextSize(30);
                ((TextView)view).setTextColor(Color.GRAY);
            }
        }
    }

    public class thread extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }//너무 빨리 시작되어서
            while(bool){
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(1000);
                }catch (Exception e){}
            }
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what ==0){

                if(t>0){

                    t--;
                    sum();
                    Str = String.format("%02d : %02d : %02d",hour,minute,second);

                    finaltimetxt.setText(Str);


                }
            }
        }
    };

    public  void sum(){
        hour =  t/3600;
        minute = (t%3600)/60;
        second = (t%3600)%60;
    }

    @Override

    public void onDestroy() {
        super.onDestroy();

        bool = false;
        ePref = prefs.edit();
        ePref.putInt("t",t);
        ePref.apply();


    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences appdata = this.getActivity().getSharedPreferences("타이머",0);
        SharedPreferences.Editor editor = appdata.edit();
        Date today = new Date();
        SimpleDateFormat time = new SimpleDateFormat("hhmmss");
        String TIME = time.format(today);
        editor.putString("나간시간",TIME);
        editor.apply();

    }



}
