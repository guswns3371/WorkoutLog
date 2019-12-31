package com.example.guswn.workoutlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchWorkout_Activity extends AppCompatActivity implements SearchWorkout_Adapter.MyRecyclerViewClickListener2{

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    SearchWorkout_Adapter mAdapter;
    ExerciseInfo searchInfo;
    ArrayList<ExerciseInfo> exerciseInfoArrayList;
    ArrayList<ExerciseInfo> filteredList;
    Toolbar searchtoolbar;
    FloatingActionButton floatingActionButton;
    EditText search_edittxt;


    Boolean isAdd;
    String Wname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_workout_);
        searchtoolbar = findViewById(R.id.searchtoolbar);
        setSupportActionBar(searchtoolbar);// 이걸 해줘야 에딧텍스트가 툴바 안으로 들어간다
        search_edittxt = findViewById(R.id.search_edittxt);
        search_edittxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        Intent intent =getIntent();
        isAdd = intent.getBooleanExtra("isAdd",false);
        Wname = intent.getStringExtra("WName");

        setSupportActionBar(searchtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);




//        SharedPreferences sss= getSharedPreferences("모든운동"+Wname,0);
//        String kk = sss.getString("복근0","null");
//        if(kk=="null") {
            exerciseInfoArrayList = new ArrayList<>();
            AllWorkOut();
//        }else {
//            exerciseInfoArrayList = new ArrayList<>();
//            load();
//        }


        mRecyclerView =findViewById(R.id.search_RV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SearchWorkout_Adapter(exerciseInfoArrayList,this);
        //mAdapter.isAdd=isAdd; //체크박스 보이게 하는 부분

        mAdapter.setOnClickListener(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        floatingActionButton = findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SearchWorkout_Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_add_workout, null);
                Button nobtn = mView.findViewById(R.id.add_nobtn);
                Button yesbtn = mView.findViewById(R.id.add_yesbtn);
                mBuilder.setView(mView);

                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences appp = getSharedPreferences("모든운동" + Wname, 0);
                        String abs_size = appp.getString("복근사이즈", "null");
                        String tricep_size = appp.getString("삼두사이즈", "null");
                        String shoulder_size = appp.getString("어깨사이즈", "null");
                        String leg_size = appp.getString("하체사이즈", "null");
                        String chest_size = appp.getString("가슴사이즈", "null");
                        String biceps_size = appp.getString("이두사이즈", "null");
                        String back_size = appp.getString("등사이즈", "null");

                        String[] part = {"복근", "등", "이두", "가슴", "하체", "어깨", "삼두"};
                        ArrayList<ExerciseInfo> DeliverList = new ArrayList<>();

                        for (int i = 0; i < 7; i++) {
                            SharedPreferences APPDATA = getSharedPreferences("모든운동" + Wname, 0);
                            String size = APPDATA.getString(part[i] + "사이즈", "null");
                            Log.e("??? " + part[i] + "사이즈 ", size);


                            for (int j = 0; j < Integer.parseInt(size); j++) {
                                String val = APPDATA.getString(part[i] + Integer.toString(j), "null");
                                Log.e("??? " + part[i] + Integer.toString(j) + "VALUE", val);
                                if (!val.equals("null")) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(val);
                                        int exerciseimg = jsonObject.getInt("exerciseimg");
                                        String exercisename = jsonObject.getString("exercisename");
                                        Boolean isLiked = jsonObject.getBoolean("isLiked");
                                        Boolean ischecked = jsonObject.getBoolean("ischecked");
                                        String part2 = jsonObject.getString("part");

                                        if (ischecked) {
                                            ExerciseInfo exerciseInfo = new ExerciseInfo(part2, exercisename, exerciseimg, ischecked, isLiked);
                                            DeliverList.add(exerciseInfo);
                                        }

                                    } catch (JSONException e) {
//                                           Log.e("??? 추가 버튼 누를때","뭐야 ㅅㅂ");
                                        Log.e("??? 추가 버튼 누를때", "어레이구나");
                                        try {
                                            JSONArray jsonArray = new JSONArray(val);
                                            String a = jsonArray.getString(0);
                                            JSONObject jsonObject = new JSONObject(a);

                                            int exerciseimg = jsonObject.getInt("exerciseimg");
                                            String exercisename = jsonObject.getString("exercisename");
                                            Boolean isLiked = jsonObject.getBoolean("isLiked");
                                            Boolean ischecked = jsonObject.getBoolean("ischecked");
                                            String part2 = jsonObject.getString("part");

                                            if (ischecked) {
                                                ExerciseInfo exerciseInfo = new ExerciseInfo(part2, exercisename, exerciseimg, ischecked, isLiked);
                                                DeliverList.add(exerciseInfo);
                                            }
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }
                                        e.printStackTrace();
                                    }

                                }
                            }

                        }
                        Log.e("??? DeliverList 사이즈", DeliverList.size() + "");
                        SharedPreferences kkk = getSharedPreferences("담긴운동파일" + Wname, 0);
                        SharedPreferences.Editor editor2 = kkk.edit();
                        Gson gson = new Gson();
                        String E = gson.toJson(DeliverList);
                        editor2.putString("담긴운동들", E);
                        editor2.apply();
                        Toast.makeText(SearchWorkout_Activity.this, "운동목록 : '" + Wname + "' 에 선택한 운동이 담겼습니다.", Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(getApplicationContext(), Workout.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);

                    }
                });
                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

        });
            }


    private void filter(String text){
         filteredList = new ArrayList<>();
        for( ExerciseInfo item : exerciseInfoArrayList){
            if(item.getExercisename().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    public void load(){

        String[] part = {"복근","등","이두","가슴","하체","어깨","삼두"};
        SharedPreferences sss2 = getSharedPreferences("모든운동" + Wname, 0);
        for (int a = 0 ; a<7; a++) {
           // SharedPreferences sss2 = getSharedPreferences("모든운동" + Wname, 0);
            String size = sss2.getString(part[a]+"사이즈", "null");
            Log.e("??? load2"+part[a] +" SIZE 정보", size);
            for (int i = 0; i < Integer.parseInt(size); i++) {
                String val = sss2.getString(part[a] + Integer.toString(i), "null");
                Log.e("??? load2 각각 "+part[a]+" 정보" + Integer.toString(i), val);
                try {
                    JSONObject jsonObject = new JSONObject(val);
                    String part2 = jsonObject.getString("part");
                    String exercisename = jsonObject.getString("exercisename");
                    String exerciseimg = jsonObject.getString("exerciseimg");
                    Boolean ischecked = jsonObject.getBoolean("ischecked");
                    Boolean isLiked = jsonObject.getBoolean("isLiked");
                    int img = Integer.parseInt(exerciseimg);
                    exerciseInfoArrayList.add(new ExerciseInfo(part2, exercisename, img, ischecked, isLiked));
                } catch (JSONException e) {
                    Log.e("??? load2 각각 "+part[a]+" 정보" + Integer.toString(i), "뭐지 ㅅㅂ???????????");

                    try {
                        JSONArray jsonArray = new JSONArray(val);
                        String a2 = jsonArray.getString(0);
                        JSONObject jsonObject = new JSONObject(a2);
                        String part3 = jsonObject.getString("part");
                        String exercisename = jsonObject.getString("exercisename");
                        String exerciseimg = jsonObject.getString("exerciseimg");
                        Boolean ischecked = jsonObject.getBoolean("ischecked");
                        Boolean isLiked = jsonObject.getBoolean("isLiked");
                        int img = Integer.parseInt(exerciseimg);
                        exerciseInfoArrayList.add(new ExerciseInfo(part3, exercisename, img, ischecked, isLiked));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
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

    @Override
    public void onItemClicked2(int position, View v) {

        if(filteredList == null) {
            Log.e("??? Search1",""+exerciseInfoArrayList.get(position).getExercisename());
            Intent intent = new Intent(SearchWorkout_Activity.this, How_to_xx.class);
            intent.putExtra("exercisetxt", exerciseInfoArrayList.get(position).getExercisename());
            intent.putExtra("운동부위", exerciseInfoArrayList.get(position).getPart());
            intent.putExtra("운동부위position", Integer.toString(position));
            intent.putExtra("pic", exerciseInfoArrayList.get(position));
            intent.putExtra("isAdd", isAdd);
            intent.putExtra("Search", true);
            intent.putExtra("Wname", Wname);
            startActivity(intent);
        }else {
            Log.e("??? Search2",""+filteredList.get(position).getExercisename());
            Intent intent = new Intent(SearchWorkout_Activity.this, How_to_xx.class);
            intent.putExtra("exercisetxt", filteredList.get(position).getExercisename());
            intent.putExtra("운동부위", filteredList.get(position).getPart());
            intent.putExtra("운동부위position", Integer.toString(position));
            intent.putExtra("pic", filteredList.get(position));
            intent.putExtra("isAdd", isAdd);
            intent.putExtra("Search", true);
            intent.putExtra("Wname", Wname);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckBoxClicked(int position, View v) {

        CheckBox cb = (CheckBox) v;
        if(filteredList != null) {
            if(filteredList.size() !=0) {
                filteredList.get(position).setIschecked(cb.isChecked());

                if (filteredList.get(position).getIschecked()) {// 체크 됬을 때
                    Log.e("??? ISCHECKED@ T - 체크된 운동의 KEY" + position, "" + filteredList.get(position).getPart() + position);
                    Log.e("??? ISCHECKED@ T" + position, "" + filteredList.get(position).getIschecked());

                    SharedPreferences sss = getSharedPreferences("모든운동" + Wname, 0);
                    SharedPreferences.Editor editor1 = sss.edit();
                    String a = sss.getString(filteredList.get(position).getPart() + position, "null");
                    Log.e("??? ISCHECKED@ T - 체크된 운동의 변경'전' 정보값" + position, a);

                    try {
                        JSONObject jsonObject = new JSONObject(a);
                        String exerciseimg = jsonObject.getString("exerciseimg");
                        String exercisename = jsonObject.getString("exercisename");
                        String part = jsonObject.getString("part");
                        ExerciseInfo exerciseInfo = new ExerciseInfo(part, exercisename, Integer.parseInt(exerciseimg), true, false);
                        ArrayList<ExerciseInfo> list = new ArrayList<>();
                        list.add(exerciseInfo);
                        Gson gson = new Gson();
                        String k = gson.toJson(list);


                        editor1.putString(filteredList.get(position).getPart() + position, k);
                        editor1.apply();

                    } catch (JSONException e) {
                        try {
                            JSONArray jsonArray = new JSONArray(a);
                            String jj = jsonArray.getString(0);
                            JSONObject jsonObject = new JSONObject(jj);
                            String exerciseimg = jsonObject.getString("exerciseimg");
                            String exercisename = jsonObject.getString("exercisename");
                            String part = jsonObject.getString("part");
                            ExerciseInfo exerciseInfo = new ExerciseInfo(part, exercisename, Integer.parseInt(exerciseimg), true, false);
                            ArrayList<ExerciseInfo> list = new ArrayList<>();
                            list.add(exerciseInfo);
                            Gson gson = new Gson();
                            String k = gson.toJson(list);
                            editor1.putString(filteredList.get(position).getPart() + position, k);
                            editor1.apply();
                        } catch (JSONException e1) {
                            Log.e("??? ISCHECKED@ T - fuck3", "뭐야 ㅅㅂ");
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    SharedPreferences aa = getSharedPreferences("모든운동" + Wname, 0);
                    String val = aa.getString(filteredList.get(position).getPart() + position, "null");
                    Log.e("??? ISCHECKED@ T - 체크된 운동의 변경'후''' 정보값" + position, val);

                } else if (!filteredList.get(position).getIschecked()) {//아직 체크 안됬을 때
                    Log.e("??? ISCHECKED@ F - 체크취소된 운동의 KEY" + position, "" + filteredList.get(position).getPart() + position);
                    Log.e("??? ISCHECKED@ F" + position, "" + filteredList.get(position).getIschecked());

                    SharedPreferences sss2 = getSharedPreferences("모든운동" + Wname, 0);
                    SharedPreferences.Editor editor2 = sss2.edit();
                    String a = sss2.getString(filteredList.get(position).getPart() + position, "null");
                    Log.e("??? ISCHECKED@ F - 체크취소된 운동의 변경'전' 정보값" + position, a);


                    try {
                        JSONObject jsonObject = new JSONObject(a);
                        String exerciseimg2 = jsonObject.getString("exerciseimg");
                        String exercisename2 = jsonObject.getString("exercisename");
                        String part2 = jsonObject.getString("part");
                        ExerciseInfo exerciseInfo2 = new ExerciseInfo(part2, exercisename2, Integer.parseInt(exerciseimg2), false, false);
                        ArrayList<ExerciseInfo> list2 = new ArrayList<>();
                        list2.add(exerciseInfo2);
                        Gson gson = new Gson();
                        String k2 = gson.toJson(list2);
                        editor2.putString(filteredList.get(position).getPart() + position, k2);
                        editor2.apply();

                    } catch (JSONException e) {
                        try {
                            JSONArray jsonArray = new JSONArray(a);
                            String jj = jsonArray.getString(0);
                            JSONObject jsonObject = new JSONObject(jj);
                            String exerciseimg = jsonObject.getString("exerciseimg");
                            String exercisename = jsonObject.getString("exercisename");
                            String part = jsonObject.getString("part");
                            ExerciseInfo exerciseInfo = new ExerciseInfo(part, exercisename, Integer.parseInt(exerciseimg), false, false);
                            ArrayList<ExerciseInfo> list = new ArrayList<>();
                            list.add(exerciseInfo);
                            Gson gson = new Gson();
                            String k = gson.toJson(list);
                            editor2.putString(filteredList.get(position).getPart() + position, k);
                            editor2.apply();
                        } catch (JSONException e1) {
                            Log.e("??? ISCHECKED@ F - fuck3", "뭐야 ㅅㅂ");
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    SharedPreferences aa2 = getSharedPreferences("모든운동" + Wname, 0);
                    String val = aa2.getString(filteredList.get(position).getPart() + position, "null");
                    Log.e("??? ISCHECKED@ F - 체크취소된 운동의 변경'후''' 정보값" + position, val);

                }
            }else {
                exerciseInfoArrayList.get(position).setIschecked(cb.isChecked());

                if (exerciseInfoArrayList.get(position).getIschecked()) {// 체크 됬을 때
                    Log.e("??? ISCHECKED T - 체크된 운동의 KEY" + position, "" + exerciseInfoArrayList.get(position).getPart() + position);
                    Log.e("??? ISCHECKED T" + position, "" + exerciseInfoArrayList.get(position).getIschecked());

                    SharedPreferences sss = getSharedPreferences("모든운동" + Wname, 0);
                    SharedPreferences.Editor editor1 = sss.edit();
                    String a = sss.getString(exerciseInfoArrayList.get(position).getPart() + position, "null");
                    Log.e("??? ISCHECKED T - 체크된 운동의 변경'전' 정보값" + position, a);

                    try {
                        JSONObject jsonObject = new JSONObject(a);
                        String exerciseimg = jsonObject.getString("exerciseimg");
                        String exercisename = jsonObject.getString("exercisename");
                        String part = jsonObject.getString("part");
                        ExerciseInfo exerciseInfo = new ExerciseInfo(part, exercisename, Integer.parseInt(exerciseimg), true, false);
                        ArrayList<ExerciseInfo> list = new ArrayList<>();
                        list.add(exerciseInfo);
                        Gson gson = new Gson();
                        String k = gson.toJson(list);


                        editor1.putString(exerciseInfoArrayList.get(position).getPart() + position, k);
                        editor1.apply();

                    } catch (JSONException e) {
                        try {
                            JSONArray jsonArray = new JSONArray(a);
                            String jj = jsonArray.getString(0);
                            JSONObject jsonObject = new JSONObject(jj);
                            String exerciseimg = jsonObject.getString("exerciseimg");
                            String exercisename = jsonObject.getString("exercisename");
                            String part = jsonObject.getString("part");
                            ExerciseInfo exerciseInfo = new ExerciseInfo(part, exercisename, Integer.parseInt(exerciseimg), true, false);
                            ArrayList<ExerciseInfo> list = new ArrayList<>();
                            list.add(exerciseInfo);
                            Gson gson = new Gson();
                            String k = gson.toJson(list);
                            editor1.putString(exerciseInfoArrayList.get(position).getPart() + position, k);
                            editor1.apply();
                        } catch (JSONException e1) {
                            Log.e("??? ISCHECKED T - fuck3", "뭐야 ㅅㅂ");
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    SharedPreferences aa = getSharedPreferences("모든운동" + Wname, 0);
                    String val = aa.getString(exerciseInfoArrayList.get(position).getPart() + position, "null");
                    Log.e("??? ISCHECKED T - 체크된 운동의 변경'후''' 정보값" + position, val);

                } else if (!exerciseInfoArrayList.get(position).getIschecked()) {//아직 체크 안됬을 때
                    Log.e("??? ISCHECKED F - 체크취소된 운동의 KEY" + position, "" + exerciseInfoArrayList.get(position).getPart() + position);
                    Log.e("??? ISCHECKED F" + position, "" + exerciseInfoArrayList.get(position).getIschecked());

                    SharedPreferences sss2 = getSharedPreferences("모든운동" + Wname, 0);
                    SharedPreferences.Editor editor2 = sss2.edit();
                    String a = sss2.getString(exerciseInfoArrayList.get(position).getPart() + position, "null");
                    Log.e("??? ISCHECKED F - 체크취소된 운동의 변경'전' 정보값" + position, a);


                    try {
                        JSONObject jsonObject = new JSONObject(a);
                        String exerciseimg2 = jsonObject.getString("exerciseimg");
                        String exercisename2 = jsonObject.getString("exercisename");
                        String part2 = jsonObject.getString("part");
                        ExerciseInfo exerciseInfo2 = new ExerciseInfo(part2, exercisename2, Integer.parseInt(exerciseimg2), false, false);
                        ArrayList<ExerciseInfo> list2 = new ArrayList<>();
                        list2.add(exerciseInfo2);
                        Gson gson = new Gson();
                        String k2 = gson.toJson(list2);
                        editor2.putString(exerciseInfoArrayList.get(position).getPart() + position, k2);
                        editor2.apply();

                    } catch (JSONException e) {
                        try {
                            JSONArray jsonArray = new JSONArray(a);
                            String jj = jsonArray.getString(0);
                            JSONObject jsonObject = new JSONObject(jj);
                            String exerciseimg = jsonObject.getString("exerciseimg");
                            String exercisename = jsonObject.getString("exercisename");
                            String part = jsonObject.getString("part");
                            ExerciseInfo exerciseInfo = new ExerciseInfo(part, exercisename, Integer.parseInt(exerciseimg), false, false);
                            ArrayList<ExerciseInfo> list = new ArrayList<>();
                            list.add(exerciseInfo);
                            Gson gson = new Gson();
                            String k = gson.toJson(list);
                            editor2.putString(exerciseInfoArrayList.get(position).getPart() + position, k);
                            editor2.apply();
                        } catch (JSONException e1) {
                            Log.e("??? ISCHECKED F - fuck3", "뭐야 ㅅㅂ");
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    SharedPreferences aa2 = getSharedPreferences("모든운동" + Wname, 0);
                    String val = aa2.getString(exerciseInfoArrayList.get(position).getPart() + position, "null");
                    Log.e("??? ISCHECKED F - 체크취소된 운동의 변경'후''' 정보값" + position, val);

                }
            }

        }

        else {
            exerciseInfoArrayList.get(position).setIschecked(cb.isChecked());

            if (exerciseInfoArrayList.get(position).getIschecked()) {// 체크 됬을 때
                Log.e("??? ISCHECKED T - 체크된 운동의 KEY" + position, "" + exerciseInfoArrayList.get(position).getPart() + position);
                Log.e("??? ISCHECKED T" + position, "" + exerciseInfoArrayList.get(position).getIschecked());

                SharedPreferences sss = getSharedPreferences("모든운동" + Wname, 0);
                SharedPreferences.Editor editor1 = sss.edit();
                String a = sss.getString(exerciseInfoArrayList.get(position).getPart() + position, "null");
                Log.e("??? ISCHECKED T - 체크된 운동의 변경'전' 정보값" + position, a);

                try {
                    JSONObject jsonObject = new JSONObject(a);
                    String exerciseimg = jsonObject.getString("exerciseimg");
                    String exercisename = jsonObject.getString("exercisename");
                    String part = jsonObject.getString("part");
                    ExerciseInfo exerciseInfo = new ExerciseInfo(part, exercisename, Integer.parseInt(exerciseimg), true, false);
                    ArrayList<ExerciseInfo> list = new ArrayList<>();
                    list.add(exerciseInfo);
                    Gson gson = new Gson();
                    String k = gson.toJson(list);


                    editor1.putString(exerciseInfoArrayList.get(position).getPart() + position, k);
                    editor1.apply();

                } catch (JSONException e) {
                    try {
                        JSONArray jsonArray = new JSONArray(a);
                        String jj = jsonArray.getString(0);
                        JSONObject jsonObject = new JSONObject(jj);
                        String exerciseimg = jsonObject.getString("exerciseimg");
                        String exercisename = jsonObject.getString("exercisename");
                        String part = jsonObject.getString("part");
                        ExerciseInfo exerciseInfo = new ExerciseInfo(part, exercisename, Integer.parseInt(exerciseimg), true, false);
                        ArrayList<ExerciseInfo> list = new ArrayList<>();
                        list.add(exerciseInfo);
                        Gson gson = new Gson();
                        String k = gson.toJson(list);
                        editor1.putString(exerciseInfoArrayList.get(position).getPart() + position, k);
                        editor1.apply();
                    } catch (JSONException e1) {
                        Log.e("??? ISCHECKED T - fuck3", "뭐야 ㅅㅂ");
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
                SharedPreferences aa = getSharedPreferences("모든운동" + Wname, 0);
                String val = aa.getString(exerciseInfoArrayList.get(position).getPart() + position, "null");
                Log.e("??? ISCHECKED T - 체크된 운동의 변경'후''' 정보값" + position, val);

            } else if (!exerciseInfoArrayList.get(position).getIschecked()) {//아직 체크 안됬을 때
                Log.e("??? ISCHECKED F - 체크취소된 운동의 KEY" + position, "" + exerciseInfoArrayList.get(position).getPart() + position);
                Log.e("??? ISCHECKED F" + position, "" + exerciseInfoArrayList.get(position).getIschecked());

                SharedPreferences sss2 = getSharedPreferences("모든운동" + Wname, 0);
                SharedPreferences.Editor editor2 = sss2.edit();
                String a = sss2.getString(exerciseInfoArrayList.get(position).getPart() + position, "null");
                Log.e("??? ISCHECKED F - 체크취소된 운동의 변경'전' 정보값" + position, a);


                try {
                    JSONObject jsonObject = new JSONObject(a);
                    String exerciseimg2 = jsonObject.getString("exerciseimg");
                    String exercisename2 = jsonObject.getString("exercisename");
                    String part2 = jsonObject.getString("part");
                    ExerciseInfo exerciseInfo2 = new ExerciseInfo(part2, exercisename2, Integer.parseInt(exerciseimg2), false, false);
                    ArrayList<ExerciseInfo> list2 = new ArrayList<>();
                    list2.add(exerciseInfo2);
                    Gson gson = new Gson();
                    String k2 = gson.toJson(list2);
                    editor2.putString(exerciseInfoArrayList.get(position).getPart() + position, k2);
                    editor2.apply();

                } catch (JSONException e) {
                    try {
                        JSONArray jsonArray = new JSONArray(a);
                        String jj = jsonArray.getString(0);
                        JSONObject jsonObject = new JSONObject(jj);
                        String exerciseimg = jsonObject.getString("exerciseimg");
                        String exercisename = jsonObject.getString("exercisename");
                        String part = jsonObject.getString("part");
                        ExerciseInfo exerciseInfo = new ExerciseInfo(part, exercisename, Integer.parseInt(exerciseimg), false, false);
                        ArrayList<ExerciseInfo> list = new ArrayList<>();
                        list.add(exerciseInfo);
                        Gson gson = new Gson();
                        String k = gson.toJson(list);
                        editor2.putString(exerciseInfoArrayList.get(position).getPart() + position, k);
                        editor2.apply();
                    } catch (JSONException e1) {
                        Log.e("??? ISCHECKED F - fuck3", "뭐야 ㅅㅂ");
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
                SharedPreferences aa2 = getSharedPreferences("모든운동" + Wname, 0);
                String val = aa2.getString(exerciseInfoArrayList.get(position).getPart() + position, "null");
                Log.e("??? ISCHECKED F - 체크취소된 운동의 변경'후''' 정보값" + position, val);

            }
        }
    }

    public void AllWorkOut(){
        exerciseInfoArrayList.add(new ExerciseInfo("이두","Concentration Curl",R.drawable.bi_concentraition_curl,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("이두","Curl (Barbell)",R.drawable.bi_curl_barbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("이두","Curl (Cable)",R.drawable.bi_curl_cable,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("이두","Curl (Dumbbell)",R.drawable.bi_curl_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("이두","Curl (EZ-Bar)",R.drawable.bi_curl_ez_bar,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("이두","Curl (Machine)",R.drawable.bi_curl_machine,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("이두","Drag Curl",R.drawable.bi_drag_curl,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("이두","Hammer Curl",R.drawable.bi_hammer_curl,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("이두","Hammer Curl (Cable)",R.drawable.bi_hammer_curl_cable,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("이두","Reverse Curl",R.drawable.bi_reverse_curl,false,false));

        exerciseInfoArrayList.add(new ExerciseInfo("복근","Ab Rollout",R.drawable.a_abs_rollout,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Air Bike",R.drawable.a_air_bike,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Crunch",R.drawable.a_crunch,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Crunch (Ball)",R.drawable.a_crunch_ball,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Crunch (Cross Body)",R.drawable.a_crunc_crossbody,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Crunch (Decline)",R.drawable.a_crunch_decline,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Leg Raise",R.drawable.a_leg_raise,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Plank",R.drawable.a_plank,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Side Bend (Dumbbell)",R.drawable.a_side_bend_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Side Plank",R.drawable.a_side_plank,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Side Plank Oblique Crunch",R.drawable.a_side_plank_oblique_crunch,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Sit-Up",R.drawable.a_sit_up,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("복근","Superman",R.drawable.a_superman,false,false));

        exerciseInfoArrayList.add(new ExerciseInfo("등","Bent Arm Pull-Over",R.drawable.ba_bent_over_row,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Bent-Over Row",R.drawable.ba_bent_over_row,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Chin up",R.drawable.ba_chin_up,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Deadlift (Barbell)",R.drawable.ba_deadlift_barbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Deadlift (Dumbbell)",R.drawable.ba_deadlifr_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Deadlift (Smith)",R.drawable.ba_deadliftsmith,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Good-Mornig",R.drawable.ba_good_morning,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Lat Pulldown",R.drawable.ba_latpulldown_closegrip,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Lat Pulldown (Close Grip)",R.drawable.ba_latpulldown_closegrip,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Lat Pulldown (Reverse Grip)",R.drawable.ba_latpulldown_reversegrip,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","One Arm Row (Dumbbell)",R.drawable.ba_onearmrow_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Pull-Over (Wide Grip)",R.drawable.ba_pullover_widegrip,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Pull-Up",R.drawable.ba_pull_up,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","Seated Row (Cable)",R.drawable.ba_seatedrowcable,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("등","T-Bar Row",R.drawable.ba_tbarrow,false,false));

        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Dips",R.drawable.c_dips,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Fly (Dumbbell)",R.drawable.c_fly_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Bench Press (Dumbbell)",R.drawable.c_bench_press_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Bench Press (Barbell)",R.drawable.c_bench_press_barbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Bent-Over Crossover (Cable)",R.drawable.c_bent_over_crossover_cable,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Chest Press (Machine)",R.drawable.c_chest_press_machine,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Crossover (Cable)",R.drawable.c_crossover_cable,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Decline Bench Press (Barbell)",R.drawable.c_decline_bench_press_barbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Decline Bench Press (Dumbbell)",R.drawable.c_decline_bench_press_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Decline Fly (Dumbbell)",R.drawable.c_decline_fly_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Incline Bench Press (Barbell)",R.drawable.c_incline_bench_press_barbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Incline Bench Press (Dumbbell)",R.drawable.c_incline_bench_press_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("가슴","Push-Up",R.drawable.c_push_up,false,false));

        exerciseInfoArrayList.add(new ExerciseInfo("하체","Hip Thrust (Barbell)", R.drawable.l_hip_thrust_barbell, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Kettlebell Swing", R.drawable.l_kettlebell_swing, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Kick-Back (One Leg)", R.drawable.l_kick_back_one_leg, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Bench Squat (Barbell)", R.drawable.l_bench_squat_barbell, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Bench Squat (Dumbbell)", R.drawable.l_bench_squat_dumbbell, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Front Squat", R.drawable.l_front_squat, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Leg Curl (Lying)", R.drawable.l_leg_curl_lying, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Leg Curl (Seated)", R.drawable.l_leg_curl_seated, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Leg Extension", R.drawable.l_leg_extension, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Leg Press", R.drawable.l_leg_press, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Lunge (Barbell)", R.drawable.l_lunge_barbell, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Lunge (Dumbbell)", R.drawable.l_lunge_dumbbell, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Romanian Deadlift", R.drawable.l_rumanian_deadlift, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Zercher Squat", R.drawable.l_zercher_squat, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Step Up (Barbell)", R.drawable.l_step_up_barbell, false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("하체","Step Up (Dumbbell)", R.drawable.l_step_up_dumbbell, false,false));

        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Arnold Press",R.drawable.s_arnold_press,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Cuban Press",R.drawable.c_cuban_press,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Front Raise (Barbell)",R.drawable.s_font_raise_barbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Front Raise (Dumbbell)",R.drawable.s_font_raise_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Front Raise (Cable)",R.drawable.s_font_raise_cable,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Lateral Raise",R.drawable.s_lateral_raise,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Raise (Dumbbell)",R.drawable.s_raise_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Reverse Fly (Cable)",R.drawable.s_reverse_fly_cable,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Shoulder Press (Barbell)",R.drawable.s_shoulder_press_barbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Shoulder Press (Dumbbell)",R.drawable.s_shoulder_press_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Shoulder Press (Stading)",R.drawable.s_shoulder_press_standing,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Shrug (Barbell)",R.drawable.s_shurg_barbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Shrug (Dumbbell)",R.drawable.s_shurg_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Shrug (Cable)",R.drawable.s_shurg_cable,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Shrug (Smith)",R.drawable.s_shurg_smith,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("어깨","Upright Row",R.drawable.s_upright_row,false,false));

        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Bench Press (Close Grip)",R.drawable.t_bench_press_close_grip,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Dip",R.drawable.t_dip,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Overhead Extension (Seated)",R.drawable.t_overhead_extension_seated,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Overhead Extension (Standing)",R.drawable.t_overhead_extension_standing,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Push-Down",R.drawable.t_push_down,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Dumbbell)",R.drawable.t_tricpes_extension_dumbbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Barbell)",R.drawable.t_tricpes_extension_barbell,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Cable)",R.drawable.t_tricpes_extension_cable,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion (Machine)",R.drawable.t_tricpes_extension_machine2,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Extenstion Kneeling (Cable)",R.drawable.t_tricpes_extension_kneeling_cable,false,false));
        exerciseInfoArrayList.add(new ExerciseInfo("삼두","Tricep Kickback",R.drawable.t_triceps_kickback,false,false));
    }
}

