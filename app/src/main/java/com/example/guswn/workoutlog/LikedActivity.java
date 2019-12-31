package com.example.guswn.workoutlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LikedActivity extends AppCompatActivity implements LikedActivity_Adapter.LikedWorkoutRecyclerListener{

    Toolbar likedtoolbar;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    LikedActivity_Adapter mAdapter;
    ArrayList<LikedActivity_Info> likedActivityInfos = new ArrayList<>();
    IsLikeInfo isLikeInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked);
        likedtoolbar = findViewById(R.id.likedtoolbar);
        mRecyclerView = findViewById(R.id.likedrecyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LikedActivity_Adapter(likedActivityInfos,this);
        mAdapter.setOnClickListenerLiked(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        setSupportActionBar(likedtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Favorite");



//        likedActivityInfos.add(new LikedActivity_Info(R.drawable.a_abs_rollout,"Ab Rollout"));
//        likedActivityInfos.add(new LikedActivity_Info(R.drawable.a_air_bike,"Air Bike"));
//        likedActivityInfos.add(new LikedActivity_Info(R.drawable.a_abs_rollout,"Ab Rollout"));
//        likedActivityInfos.add(new LikedActivity_Info(R.drawable.a_abs_rollout,"Ab Rollout"));




    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] part = {"복근","등","이두","가슴","하체","어깨","삼두"};
        //likedActivityInfos = new ArrayList<>();
        for(int i =0; i<7; i++) {
            SharedPreferences APPDATA = getSharedPreferences("ALLWORKOUT", 0);
            String size = APPDATA.getString(part[i] + "사이즈", "null");
            Log.e("??? " + part[i] + "사이즈 ", size);
            if(!size.equals("null")) {
                for (int j = 0; j < Integer.parseInt(size); j++) {
                    String val = APPDATA.getString(part[i] + Integer.toString(j), "null");
                    if (!val.equals("null")) {
                        try {
                            JSONObject jsonObject = new JSONObject(val);
                            String exercisename = jsonObject.getString("exercisename");
                            int exerciseimg = jsonObject.getInt("exerciseimg");
                            String part2 = jsonObject.getString("part");
                            Log.e("??? 운동부위", "" + part2);

                            SharedPreferences app2 = getSharedPreferences("좋아요체크" + part[i]+Main2Activity.Loginname, 0);
                            Boolean isLiked = app2.getBoolean(exercisename, false);
                            if (isLiked == true) {
                                Log.e("??? 좋아용 체크된 운동 try", exercisename + "/" + part2);
                                likedActivityInfos.add(new LikedActivity_Info(exerciseimg, exercisename, part2));
                                //mAdapter.notifyDataSetChanged();
                            } else {
                                Log.e("??? 좋아용 체크 삭제 운동 try", exercisename);
                            }
                        } catch (JSONException e) {
                            try {
                                JSONArray jsonArray = new JSONArray(val);
                                String a = jsonArray.getString(0);
                                JSONObject jsonObject = new JSONObject(a);
                                String exercisename = jsonObject.getString("exercisename");
                                int exerciseimg = jsonObject.getInt("exerciseimg");
                                String part2 = jsonObject.getString("part");
                                SharedPreferences app2 = getSharedPreferences("좋아요체크" + part[i]+Main2Activity.Loginname, 0);
                                Boolean isLiked = app2.getBoolean(exercisename, false);
                                if (isLiked == true) {
                                    Log.e("??? 좋아용 체크된 운동 catch", exercisename + "/" + part2);
                                    likedActivityInfos.add(new LikedActivity_Info(exerciseimg, exercisename, part2));
                                    // mAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e1) {
                                Log.e("??? 좋아용 체크된 운동 catch2", "2????");
                                e1.printStackTrace();
                            }
                            Log.e("??? 좋아용 체크된 운동 catch3", "3????");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void removeItem(int position){
        likedActivityInfos.remove(position);
        mAdapter.notifyDataSetChanged();
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



    ////////////////////////////////////////////////////////
    //인터페이스 리스너//
    @Override
    public void onItemClicked(int position, View v) {
        Intent intent = new Intent(LikedActivity.this, How_to_xx.class);
        intent.putExtra("pic3",likedActivityInfos.get(position));
        intent.putExtra("운동부위",likedActivityInfos.get(position).getLiked_part());
        intent.putExtra("isLiked2",true);
        startActivity(intent);
    }

    @Override
    public void onFavButtonClicked(int position, View v) {

        SharedPreferences app2 =getSharedPreferences("좋아요체크"+likedActivityInfos.get(position).getLiked_part()+Main2Activity.Loginname,0);
        String likedName =likedActivityInfos.get(position).getLiked_name();
        Log.e("??? 선택한 좋아요 운동 이름",likedName);
        SharedPreferences.Editor editor = app2.edit();
        editor.putBoolean(likedName,false);
        editor.apply();

        removeItem(position);

    }

}
