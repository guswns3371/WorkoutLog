package com.example.guswn.workoutlog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class Exercise extends AppCompatActivity implements View.OnClickListener{
    Boolean isAdd;
    String size2,size3;
    Button absbtn;
    Button backbtn;
    Button bicepsbtn;
    Button chestbtn;
    Button tricepsbtn;
    Button shoulderbtn;
    Button legbtn;
    Toolbar exercise_tb;

    String WName;
    ArrayList<String> workoutname = new ArrayList<>();
    ArrayList<String> workoutname2 = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        absbtn = findViewById(R.id.ABS);
        backbtn = findViewById(R.id.BACK);
        bicepsbtn = findViewById(R.id.BICEPS);
        chestbtn = findViewById(R.id.CHEST);
        tricepsbtn = findViewById(R.id.TRICEPS);
        shoulderbtn = findViewById(R.id.SHOULDER);
        legbtn = findViewById(R.id.LEGS);
        exercise_tb = findViewById(R.id.exercise_tb);
        setSupportActionBar(exercise_tb);

        absbtn.setOnClickListener(this);
        backbtn.setOnClickListener(this);
        bicepsbtn.setOnClickListener(this);
        chestbtn.setOnClickListener(this);
        tricepsbtn.setOnClickListener(this);
        shoulderbtn.setOnClickListener(this);
        legbtn.setOnClickListener(this);


        Intent intent = getIntent();
         size2 = intent.getStringExtra("size");
        // size3 = intent.getStringExtra("size2");
         isAdd = intent.getBooleanExtra("isAdd",false);
        WName = intent.getStringExtra("WName");
         // Workout 액티비티에서 플로팅버튼을 누를때 받는 값이다
        if(size2!=null) {
            for (int i = 0; i < Integer.parseInt(size2); i++) {
            workoutname.add(intent.getStringExtra(Integer.toString(i)));
            }
         }
        /***/
        SharedPreferences aa = getSharedPreferences("모든운동"+WName, 0);
        SharedPreferences.Editor editor = aa.edit();
        editor.putString("복근사이즈",Integer.toString(13));
        editor.putString("삼두사이즈",Integer.toString(11));
        editor.putString("어깨사이즈",Integer.toString(16));
        editor.putString("하체사이즈",Integer.toString(16));
        editor.putString("가슴사이즈",Integer.toString(13));
        editor.putString("이두사이즈",Integer.toString(10));
        editor.putString("등사이즈",Integer.toString(15));
        editor.apply();
        /***/


        CharSequence[] items= {"ABS","BACK","BICEPS","CHEST","TRICEPS","SHOULDER","LEGS"};

        for(int i = 0 ; i<items.length ; i++)
        {
            for(int j = 0 ; j<workoutname.size() ; j++)
            if(workoutname.get(j).equals(items[i]))
            {
                if(items[i].equals("ABS"))
                {
                    absbtn.setBackgroundColor(Color.YELLOW);
                    //absbtn.setBackgroundColor(R.color.colorYellow); 이거로 하면 안됨
                }
                if(items[i].equals("BACK"))
                {
                    backbtn.setBackgroundColor(Color.YELLOW);
                }
                if(items[i].equals("BICEPS"))
                {
                    bicepsbtn.setBackgroundColor(Color.YELLOW);
                }
                if(items[i].equals("CHEST"))
                {
                    chestbtn.setBackgroundColor(Color.YELLOW);
                }
                if(items[i].equals("TRICEPS"))
                {
                    tricepsbtn.setBackgroundColor(Color.YELLOW);
                }
                if(items[i].equals("SHOULDER"))
                {
                    shoulderbtn.setBackgroundColor(Color.YELLOW);
                }
                if(items[i].equals("LEGS"))
                {
                    legbtn.setBackgroundColor(Color.YELLOW);
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.exercise_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.favBtn:
                Log.e("??? 좋아요 리스트 버튼","fuckyeah");
                Intent intent = new Intent(Exercise.this,LikedActivity.class);
                startActivity(intent);
                return  true;

            case R.id.searchBtn:
                Log.e("??? 검색  버튼","fuckyass");
                Intent intent1 = new Intent(Exercise.this,SearchWorkout_Activity.class);
                intent1.putExtra("isAdd",isAdd);
                intent1.putExtra("WName",WName);
                startActivity(intent1);
                return true;
                default:


                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){

            case R.id.ABS:
                intent = new Intent(Exercise.this,Exercise_abs.class);
                intent.putExtra("title",absbtn.getText());
                intent.putExtra("isAdd",isAdd);
                intent.putExtra("WName",WName);
                startActivity(intent);
                break;
            case R.id.BACK:
                intent = new Intent(Exercise.this,Exercise_back.class);
                intent.putExtra("title",backbtn.getText());
                intent.putExtra("isAdd",isAdd);
                intent.putExtra("WName",WName);
                startActivity(intent);
                break;
            case R.id.BICEPS:
                intent = new Intent(Exercise.this,Exercise_biceps.class);
                intent.putExtra("title",bicepsbtn.getText());
                intent.putExtra("isAdd",isAdd);
                intent.putExtra("WName",WName);
                startActivity(intent);
                break;
            case R.id.CHEST:
                intent = new Intent(Exercise.this,Exercise_chest.class);
                intent.putExtra("title",chestbtn.getText());
                intent.putExtra("isAdd",isAdd);
                intent.putExtra("WName",WName);
                startActivity(intent);
                break;
            case R.id.TRICEPS:
                intent = new Intent(Exercise.this,Exercise_triceps.class);
                intent.putExtra("title",tricepsbtn.getText());
                intent.putExtra("isAdd",isAdd);
                intent.putExtra("WName",WName);
                startActivity(intent);
                break;
            case R.id.SHOULDER:
                intent = new Intent(Exercise.this,Exercise_shoulder.class);
                intent.putExtra("title",shoulderbtn.getText());
                intent.putExtra("isAdd",isAdd);
                intent.putExtra("WName",WName);
                startActivity(intent);
                break;
            case R.id.LEGS:
                intent = new Intent(Exercise.this,Exercise_legs.class);
                intent.putExtra("title",legbtn.getText());
                intent.putExtra("isAdd",isAdd);
                intent.putExtra("WName",WName);
                startActivity(intent);
                break;
        }
    }


}
