package com.example.guswn.workoutlog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Custom_Dialog_Timer extends AppCompatActivity implements View.OnClickListener{
    private Context context;

    CheckBox vibratecheck;
    CheckBox beepcheck;
    CheckBox stopwatchcheck;
    Button startbtn;
    Button stopbtn;
    Button resetbtn;
    TextView timeshowtxt;
    Boolean bool = true;
    Boolean bool2 = true;
    Boolean bool3 = false;
    Boolean bool4 = false;

    String text,Str;
    SharedPreferences prefs;
    SharedPreferences.Editor ePref;
    int t,hour,minute,second;
    int a =0;
    int b= 0;

    EditText timetxt;
//    public Custom_Dialog_Timer ( Context context){
//        this.context= context;
//    }
    // 호출할 다이얼로그 함수를 정의한다.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom__dialog__timer);
        vibratecheck =findViewById(R.id.vibratecheck);
        beepcheck = findViewById(R.id.beepcheck);
        stopwatchcheck = findViewById(R.id.stopwatchcheck);

        timeshowtxt = findViewById(R.id.timeshowtxt);
        timetxt = findViewById(R.id.timetxt);

        startbtn = findViewById(R.id.startbtn);
        stopbtn = findViewById(R.id.stopbtn);
        resetbtn = findViewById(R.id.resetbtn);

        startbtn.setOnClickListener(this);
        stopbtn.setOnClickListener(this);
        resetbtn.setOnClickListener(this);

        vibratecheck.setOnClickListener(this);
        beepcheck.setOnClickListener(this);
        stopwatchcheck.setOnClickListener(this);
        timetxt.setOnClickListener(this);

        prefs = getSharedPreferences("Save", Activity.MODE_PRIVATE);
        t = prefs.getInt("t",t);
        sum();
      //  Str = String.format("%02d : %02d",minute,second);
        Str = String.format("%02d : %02d : %02d",hour,minute,second);
        timeshowtxt.setText(Str);

    }

    public  void sum(){
        hour =  t/3600;
        minute = (t%3600)/60;
        second = (t%3600)%60;
    }

    public class thread extends Thread {

        @Override
        public void run() {
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
                    //Str = String.format("%02d : %02d",minute,second);
                    Str = String.format("%02d : %02d : %02d",hour,minute,second);
                    timeshowtxt.setText(Str);


                }
            }
        }
    };


    public void completebeep(){

        if(a%2 !=0 && a!=0){
            Toast.makeText(Custom_Dialog_Timer.this,"타이머 완료 (소리)",Toast.LENGTH_SHORT).show();
        }

    }
    public void completevib(){


        if(b%2 !=0 && b!=0){
            Toast.makeText(Custom_Dialog_Timer.this,"타이머 완료 (진동)!!!!!!!!!!!!!!!!",Toast.LENGTH_SHORT).show();
        }
    }

    boolean threadisalive;
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.vibratecheck:
               // Toast.makeText(Custom_Dialog_Timer.this,"!!!!",Toast.LENGTH_SHORT).show();
                b++;
                //bool4 = true;
                break;

            case R.id.beepcheck:
//                Toast.makeText(Custom_Dialog_Timer.this,"!!!!",Toast.LENGTH_SHORT).show();

                a++;
              // bool3=true;
                break;

            case R.id.stopwatchcheck:
                Toast.makeText(Custom_Dialog_Timer.this,"!!!!",Toast.LENGTH_SHORT).show();
                break;

            case R.id.startbtn:
                bool = true;

                thread threadTest = new thread();
                threadTest.setDaemon(true);

                if(bool2){
                    threadTest.start();
                }

                bool2= false;
                break;

            case R.id.stopbtn:

                bool = false;
               bool2 =true;

                break;

            case R.id.resetbtn:
                bool = false;
                bool2= true;
                t=0;
                sum();
                //Str = String.format("%02d : %02d",minute,second);
                Str = String.format("%02d : %02d : %02d",hour,minute,second);
                timeshowtxt.setText(Str);

                break;

            case R.id.timetxt:

                try {
                    text = timetxt.getText().toString();
                    t = Integer.parseInt(text);
                    sum();
                  //  Str = String.format("%02d : %02d",minute,second);
                    Str = String.format("%02d : %02d : %02d",hour,minute,second);
                    timeshowtxt.setText(Str);
                    //bool2 =true;
                }catch (Exception e){

                }



                break;
        }



    }



    @Override

    protected void onDestroy() {
        super.onDestroy();

        bool = false;
        ePref = prefs.edit();
        ePref.putInt("t",t);
        ePref.commit();


    }
}
