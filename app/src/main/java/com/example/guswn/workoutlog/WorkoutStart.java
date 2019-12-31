package com.example.guswn.workoutlog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkoutStart extends AppCompatActivity implements WorkoutStart_Adapter.WorkoutStartRecyclerClickListner{

    String NAME;
    String DATE;
    int IMG;
    String KEY;// 워크아웃스타트정보를 저장하는 쉐프의 키 값이다.
    String PART,NAME2;
    Toolbar workoutToolbar;
    ArrayList<WorkoutStart_Info> workoutStartInfos;
    RecyclerView MRecylcerview;
    RecyclerView.LayoutManager MLayoutManager;
    WorkoutStart_Adapter workoutStartAdapter;

    static int workoutstartsize;

    Boolean isComplete;
    String Complete_setnum,Complete_weight,Complete_reps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_start);

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DATE = date.format(today);

        Intent intent = getIntent();
        InnerWorkout_Info innerWorkoutInfo = intent.getParcelableExtra("package");
        IMG = innerWorkoutInfo.getInn_img();
        PART = innerWorkoutInfo.getInn_part();
        NAME2 = innerWorkoutInfo.getInn_name();


        NAME = intent.getStringExtra("Wname");
        workoutToolbar = findViewById(R.id.workoutToolbar);

        /***/ Log.e("??? workoutstart NAME / NAME2",NAME+"/"+NAME2);
        //NAME 은 게시물 이름 , NAME2는 운동이름
        KEY = NAME+NAME2; /** 중요! */

        workoutStartInfos = new ArrayList<>();
        MRecylcerview = findViewById(R.id.workoutRecycler);
        MRecylcerview.setHasFixedSize(true);
        MLayoutManager = new LinearLayoutManager(this);
        workoutStartAdapter = new WorkoutStart_Adapter(workoutStartInfos,this,NAME,NAME2);
        MRecylcerview.setLayoutManager(MLayoutManager);
        MRecylcerview.setAdapter(workoutStartAdapter);
        workoutStartAdapter.setOnClickListener_WorkoutStart(this);
        setSupportActionBar(workoutToolbar);
        getSupportActionBar().setTitle(" "+NAME2);

        SharedPreferences ap = getSharedPreferences(KEY,0);


        workoutStartInfos.add(new WorkoutStart_Info(WorkoutStart_Info.A_TYPE,IMG,PART,NAME2,"",""));
        /***********************************************************************************************************/
        workoutStartInfos.add(new WorkoutStart_Info(WorkoutStart_Info.B_TYPE,IMG,PART,NAME2,"",""));
        workoutStartInfos.add(new WorkoutStart_Info(WorkoutStart_Info.B_TYPE,IMG,PART,NAME2,"",""));
        workoutStartInfos.add(new WorkoutStart_Info(WorkoutStart_Info.B_TYPE,IMG,PART,NAME2,"",""));
        workoutStartInfos.add(new WorkoutStart_Info(WorkoutStart_Info.B_TYPE,IMG,PART,NAME2,"",""));
        workoutStartInfos.add(new WorkoutStart_Info(WorkoutStart_Info.B_TYPE,IMG,PART,NAME2,"",""));
        workoutstartsize = workoutStartInfos.size();
    }

    public void insertItem3(int type, int ws_img, String ws_part, String ws_name, String ws_wight, String ws_reps){
        workoutStartInfos.add(new WorkoutStart_Info(type,ws_img,ws_part,ws_name,ws_wight,ws_reps));
        workoutStartAdapter.notifyDataSetChanged();
    }

    public void removeItem3(int position){
        workoutStartInfos.remove(position);
        workoutStartAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.workoutstar_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: //toolbar의 back키 눌렀을 때 동작

                return true;

            case R.id.addbtn:

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(WorkoutStart.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_add_workout,null);
                Button nobtn =  mView.findViewById(R.id.add_nobtn);
                Button yesbtn = mView.findViewById(R.id.add_yesbtn);
                TextView title = mView.findViewById(R.id.textView23);
                mBuilder.setView(mView);
                title.setText("세트를 추가 하시겠습니까?");

                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(View v) {
                        insertItem3(WorkoutStart_Info.B_TYPE,0,"","","","");
                        Toast.makeText(WorkoutStart.this,"세트가 추가되었습니다",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences a= getSharedPreferences(DATE,0);
                        if(a.getString("완료"+NAME2,"null")!= "null"){
                            Log.e("??? completeData2",a.getString("완료"+NAME2,"null"));
                            //Log.e("??? completeData", completeData);
                        }else {
                            Log.e("??? completeData3","null 이네 ㅅㅂ");
                        }
                        dialog.dismiss();
                    }
                });
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
    /****************************************************************************/
    @Override
    public void onMoreButtonClicked(final int position, View v) {

        final PopupMenu p = new PopupMenu(getApplicationContext(),v);
        //저 v를 가져오기 위해선
        // 어밷벝의 인터페이스 메소드 onMoreButtonClicked의 파라미터값을 더 줘야 한다.
        getMenuInflater().inflate(R.menu.workout_menu,p.getMenu());

        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.edititem)
                {

                }
                if(item.getItemId()==R.id.deleteitem)
                {
                    removeItem3(position);
                }
                return false;
            }
        });
        p.show();


    }

//    @Override
//    public void onCompleteButtonClicked(int position, View v) {
//
//    }
    /**************************************************************************/

    /*************************************************************************/
//    @Override
//    public void onPlusMinusButtonClicked1(int position, View v) {//weight_minus
//
//    }
//
//    @Override
//    public void onPlusMinusButtonClicked2(int position, View v) {//weight_plus
//
//    }
//
//    @Override
//    public void onPlusMinusButtonClicked3(int position, View v) {//reps_minus
//
//    }
//
//    @Override
//    public void onPlusMinusButtonClicked4(int position, View v) {//reps_plus
//
//    }
    /***********************************************************************/

}
