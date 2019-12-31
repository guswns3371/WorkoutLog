package com.example.guswn.workoutlog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class Tab2_stopwatch extends Fragment implements OnClickListener{

    //Button pausebtn;
    Button startbtn;
    Button recordbtn;
    TextView timviewtxt;
    TextView recordtxt;
    ScrollView scrollview;


    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;

    static int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    int myCount=1;
    long myBaseTime;
    long myPauseTime;

    static String t;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag2_stopwatch_, container, false);
        //pausebtn = rootView.findViewById(R.id.pausebtn);
        startbtn = rootView.findViewById(R.id.startbtn);
        recordbtn = rootView.findViewById(R.id.recordbtn);
        timviewtxt = rootView.findViewById(R.id.timviewtxt);
        recordtxt = rootView.findViewById(R.id.recordtxt);
        scrollview = rootView.findViewById(R.id.scrollview);

        recordtxt.setMovementMethod(ScrollingMovementMethod.getInstance());
        startbtn.setOnClickListener(this);
        recordbtn.setOnClickListener(this);

        if(t!=null){
            if(cur_Status==Run){

            }if(cur_Status==Pause){

            }if(cur_Status==Init){

            }
        }


        return rootView;
    }


    Handler myTimer = new Handler(){
      public void handleMessage(Message msg){
          timviewtxt.setText(getTimeOut());

          myTimer.sendEmptyMessage(0);
      }
    };


    public String getTimeOut(){
        long now = SystemClock.elapsedRealtime();
        long outTime = now - myBaseTime;
        String easy_outTime = String.format("%02d : %02d : %02d", outTime/1000 / 60, (outTime/1000)%60,(outTime%1000)/10);
        t=easy_outTime;
        return easy_outTime;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startbtn:

                switch (cur_Status){
                    case Init:
                        myBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(myBaseTime);
                        startbtn.setText("PAUSE"); //버튼의 문자"시작"을 "멈춤"으로 변경
                        recordbtn.setEnabled(true); //기록버튼 활성
                        myTimer.sendEmptyMessage(0);//myTimer이라는 핸들러를 빈 메세지를 보내서 호출
                        cur_Status=Run; //현재상태를 런상태로 변경
                        break;
                    case Run:
                        myTimer.removeMessages(0);
                        myPauseTime = SystemClock.elapsedRealtime();
                        startbtn.setText("START");
                        recordbtn.setText("RESET");
                        cur_Status =Pause;
                        break;
                    case Pause:
                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now - myPauseTime);
                        startbtn.setText("STOP");
                        recordbtn.setText("RECORD");
                        cur_Status = Run;
                        break;
                }
                break;

            case R.id.recordbtn:

                switch (cur_Status){

                    case Run:
                        String str = recordtxt.getText().toString();
                        str += String.format("%d. %s\n",myCount,getTimeOut());
                        recordtxt.setText(str);
                        scrollview.post(new Runnable() {
                            @Override public void run() {
                                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                        myCount++;
                        break;
                    case Pause:
                        myTimer.removeMessages(0);

                        startbtn.setText("START");
                        recordbtn.setText("RECORD");
                        timviewtxt.setText("00 : 00 : 00");

                        cur_Status = Init;
                        myCount = 1;
                        recordtxt.setText("");
                        recordbtn.setEnabled(false);
                        break;
                }
                break;
        }
    }
}

