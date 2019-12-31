package com.example.guswn.workoutlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Workout extends AppCompatActivity implements MyAdapter_Workout.MyRecyclerViewClickListener {

    ArrayList<WorkoutInfo> workoutInfos;
    RecyclerView mRecyclerView;
    Toolbar workouttoolbar;
    RecyclerView.LayoutManager mLayoutManager;

   ///////////////////////

   // RecyclerView.Adapter myAdapter_workout;  //이렇게 하면 어댑터속 내가 ㅁ나든 함수를 사용할수 없다
    MyAdapter_Workout myAdapter_workout;  //이렇게 해줘야 내가 만든 함수를 사용할수 있는것이다.
    ///////////////////////

    FloatingActionButton workoutfloatingbtn;
    String NAME;
    String DES;
    String KEY;
    String DATE,TIME,NOW;
    Workout_itemInfo workoutItemInfo ;
    ArrayList<Workout_itemInfo> workoutItemInfoArrayList = new ArrayList<>();
    ArrayList<String> workoutitemKeylist;// 이거는 키를 저장하는 어레이 리스트 , 로드 세이브 해줘야한다.
    ArrayList<String> remove_workoutitemKeylist = new ArrayList<>();// 삭제된 키가 담긴 어레이 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        workouttoolbar = findViewById(R.id.workouttoolbar);
        setSupportActionBar(workouttoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        workoutfloatingbtn = findViewById(R.id.workoutfloatingbtn);
        workoutInfos = new ArrayList<>();
        /***/
        workoutload();
        /***/
        workoutfloatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

////////////////////////////////////////////////////////////////////////////////////////////////////////////

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Workout.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_workout,null);
                final EditText workoutnametxt =  mView.findViewById(R.id.workoutnametxt);
                final EditText workdestxt =  mView.findViewById(R.id.workdestxt);
                Button nobtn =  mView.findViewById(R.id.nobtn);
                Button yesbtn = mView.findViewById(R.id.yesbtn);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date today = new Date();
                        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
                        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
                        DATE = date.format(today);
                        TIME = time.format(today);
                        NOW = DATE + TIME;
                        NAME = workoutnametxt.getText().toString();
                        DES = workdestxt.getText().toString();
                        KEY = NAME + DES + NOW;// workoutitemKeylist 에 담는다

                        Log.e("!!! 이미 존재하는 게시물", "" + alreeadyHaveit(NAME));
                        if (alreeadyHaveit(NAME).equals("최초")){
                            if (!NAME.equals("") && !DES.equals("") && !NAME.equals(null) && !DES.equals(null)
                                    && !workoutnametxt.getText().toString().equals(null) && !workdestxt.getText().toString().equals(null)) {
                                workoutItemInfo = new Workout_itemInfo();
                                workoutItemInfo.setwName(NAME);
                                workoutItemInfo.setwDes(DES);
                                workoutItemInfo.setwTime(NOW);

                                workoutItemInfoArrayList = new ArrayList<>();
                                Gson gson = new Gson();
                                workoutItemInfoArrayList.add(workoutItemInfo);
                                String a = gson.toJson(workoutItemInfoArrayList);
                                Log.e("!!! 생성된 VALUE정보", a);

                                /***/
                                // workoutitemKeylist = new ArrayList<>(); // 쉐프에 키 정보 다 삭제하고싶을때 하는장치다
                                /***/
                                Gson gson1 = new Gson();
                                workoutitemKeylist.add(KEY);// 이거 로드 세이브 해줘야하는 부분
                                String b = gson1.toJson(workoutitemKeylist);
                                Log.e("!!! 생성된 KEY정보", b);

                                SharedPreferences sp = getSharedPreferences("생성워크아웃"+Main2Activity.Loginname, 0);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString(KEY, a);
                                editor.putString("생성워크아웃KEY", b);// 이거 세이브 한다.
                                editor.apply();

                                insertItem(NAME, DES);
                                dialog.dismiss();
                            }else {
                                Toast.makeText(Workout.this,"정보를 모두 입력하세요",Toast.LENGTH_SHORT).show();
                            }
                    }else {
                            Toast.makeText(Workout.this,"이름이 중복된 게시물이 있습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
//                Custom_dialog_workout_ custom_dialog_workout = new Custom_dialog_workout_(Workout.this);
//                custom_dialog_workout.setCancelable(true);
//                custom_dialog_workout.getWindow().setGravity(Gravity.CENTER);
//                custom_dialog_workout.show();
//                val1 = custom_dialog_workout.getName_dialog();
//                val2 = custom_dialog_workout.getDes_dialog();
//                insertItem(val1,val2);
            }
        });


//
//        workoutInfos.add(new WorkoutInfo("Chest & Triceps & Abs","수업 끝난 날"));
//        workoutInfos.add(new WorkoutInfo("Back & Biceps & Abs","주말"));
//        workoutInfos.add(new WorkoutInfo("Shoulder & Legs","월 수 금"));

        mRecyclerView = findViewById(R.id.workoutRecyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        myAdapter_workout = new MyAdapter_Workout(workoutInfos);
        myAdapter_workout.setOnClickListener(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(myAdapter_workout);
        ////////////////////////////////////////////////
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    workoutfloatingbtn.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 ||dy<0 && workoutfloatingbtn.isShown())
                {
                    workoutfloatingbtn.hide();
                }
            }
        });
        //플로팅액션바 숨기기

    }

    public void insertItem(String name, String des){
        workoutInfos.add(new WorkoutInfo(name,des));
        myAdapter_workout.notifyDataSetChanged();
//        myAdapter_workout.notifyItemInserted(position);
    }

    public void removeItem(int position){
        workoutInfos.remove(position);
        myAdapter_workout.notifyDataSetChanged();
//        myAdapter_workout.notifyItemRemoved(position);
    }

    public void editItem(int position, String name, String des){
        workoutInfos.set(position,new WorkoutInfo(name,des));
        myAdapter_workout.notifyDataSetChanged();
    }

    public void workoutload(){
        SharedPreferences sp = getSharedPreferences("생성워크아웃"+Main2Activity.Loginname,0);
        String a = sp.getString("생성워크아웃KEY","null");

        Log.e("!!! workoutload 생성워크아웃KEY",a );
        if(a.equals("null")) {
            workoutitemKeylist =new ArrayList<>();
        }else {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            Gson gson = new Gson();
            workoutitemKeylist = gson.fromJson(a, type);
            Log.e("!!! workoutload 로드된키리스트 workoutitemKeylist 사이즈",""+workoutitemKeylist.size());

                for (int i = 0; i < workoutitemKeylist.size(); i++) {

                    SharedPreferences ss = getSharedPreferences("생성워크아웃"+Main2Activity.Loginname, 0);
                    String aa = ss.getString(workoutitemKeylist.get(i), "null");
                    Log.e("!!! workoutload 로드된키", workoutitemKeylist.get(i));
                    Log.e("!!! workoutload 로드된키에 해당하는 VALUE", aa);
                    if (!aa.equals("null")) {
                        try {
                            JSONArray jsonArray = new JSONArray(aa);
                            String k = jsonArray.getString(0);
                            JSONObject jsonObject = new JSONObject(k);

                            String wName = jsonObject.getString("wName");
                            String wDes = jsonObject.getString("wDes");
                            String wTime = jsonObject.getString("wTime");
                            workoutInfos.add(new WorkoutInfo(wName, wDes));
                        } catch (JSONException e) {
                            Log.e("!!! workoutload 로드된키??", "뭐지 ㅅㅂ?" + Integer.toString(i));
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("!!! workoutload 삭제될 키" + Integer.toString(i), workoutitemKeylist.get(i));
                        remove_workoutitemKeylist.add(workoutitemKeylist.get(i));
                    }

            }
        }
        if(workoutitemKeylist.size()!=0) {

        for(int j = 0 ; j<remove_workoutitemKeylist.size(); j++)
        {
           //; Log.e("!!! workoutload 삭제된 키"+Integer.toString(j),workoutitemKeylist.get(j));
            workoutitemKeylist.remove(remove_workoutitemKeylist.get(j));
        }

            for (int i = 0; i < workoutitemKeylist.size(); i++) {
                Log.e("!!! workoutload 남아있는 키" + Integer.toString(i), workoutitemKeylist.get(i));
            }

        }

    }

    String wName2;
    String Q;
    int A,B;
    public String alreeadyHaveit(String name){
        SharedPreferences sp = getSharedPreferences("생성워크아웃"+Main2Activity.Loginname,0);
        String a = sp.getString("생성워크아웃KEY","null");

        if(a.equals("null")) {

        }else {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            Gson gson = new Gson();
            workoutitemKeylist = gson.fromJson(a, type);
            if(workoutitemKeylist!=null) {
                for (int i = 0; i < workoutitemKeylist.size(); i++) {
                    SharedPreferences ss = getSharedPreferences("생성워크아웃"+Main2Activity.Loginname, 0);
                    String aa = ss.getString(workoutitemKeylist.get(i), "null");
                    Log.e("!!! workoutload 로드된키", workoutitemKeylist.get(i));
                    Log.e("!!! workoutload 로드된키에 해당하는 VALUE", aa);
                    if (!aa.equals("null")) {
                        try {
                            JSONArray jsonArray = new JSONArray(aa);
                            String k = jsonArray.getString(0);
                            JSONObject jsonObject = new JSONObject(k);
                            wName2 = jsonObject.getString("wName");
                            if (wName2.equals(name)) {
                                A++;
                                Log.e("!!! workout 중복된게시물", "있습니다");
                            } else if (!wName2.equals(name)) {

                                Log.e("!!! workout 중복된게시물", "없**습니다");
                            }
                        } catch (JSONException e) {
                            Log.e("!!! workoutload 로드된키??", "뭐지 ㅅㅂ?" + Integer.toString(i));
                            e.printStackTrace();
                        }

                    } else {
                        // Log.e("!!! workout 중복된게시물","있습니다");
                    }
                }
            }
        }

        if(A>0){
            Q="중복";
        }else {
            Q="최초";
        }
        A=0;

        return Q;

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

    String WNAME;
    @Override
    public void onItemClicked(int position, String name) {
          WNAME =name;
//        Toast.makeText(this,"아이템"+position,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Workout.this,InnerWorkout.class);

        intent.putExtra("isAdd",true);//이 키값으로 리사이클러뷰의 체크박스를 visible / gone 할수 있다.
        intent.putExtra("name",name);
        startActivity(intent);
    }

    @Override
    public void onMoreButtonClicked(final int position ,  View v) {

        final PopupMenu p = new PopupMenu(getApplicationContext(),v);
        //저 v를 가져오기 위해선
        // 어밷벝의 인터페이스 메소드 onMoreButtonClicked의 파라미터값을 더 줘야 한다.
        getMenuInflater().inflate(R.menu.workout_menu,p.getMenu());

        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.edititem)
                {
                    Toast.makeText(Workout.this,"수정"+position,Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Workout.this);
                    View mView = getLayoutInflater().inflate(R.layout.custom_dialog_workout,null);
                    final EditText workoutnametxt =  mView.findViewById(R.id.workoutnametxt);
                    final EditText workdestxt =  mView.findViewById(R.id.workdestxt);
                    TextView titletxt = mView.findViewById(R.id.titletxt);
                    titletxt.setText("Edit Workout");
                    Button nobtn =  mView.findViewById(R.id.nobtn);
                    Button yesbtn = mView.findViewById(R.id.yesbtn);

//                    workoutnametxt.setHint(workoutInfos.get(position).workname.toString());
//                    workdestxt.setHint(workoutInfos.get(position).workdes.toString());

                    workoutnametxt.setText(workoutInfos.get(position).workname.toString());
                    workdestxt.setText(workoutInfos.get(position).workdes.toString());
                    // 수정 할때 기존에 입력 된 텍스트가 나오도록
                    nobtn.setText("Cancel");
                    yesbtn.setText("OK");



                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    yesbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           if(alreeadyHaveit(workoutnametxt.getText().toString()).equals("최초")) {
                               if (!workoutnametxt.getText().toString().isEmpty() && !workdestxt.getText().toString().isEmpty()) {

                                   try {
                                       SharedPreferences sss = getSharedPreferences("생성워크아웃"+Main2Activity.Loginname, 0);
                                       SharedPreferences.Editor editor = sss.edit();
                                       String aa = sss.getString(workoutitemKeylist.get(position), "null");
                                       Log.e("!!! 수정할 게시판 KEY",workoutitemKeylist.get(position));
                                       Log.e("!!! 수정할 게시판 정보",aa);

                                       workoutItemInfo = new Workout_itemInfo();
                                       workoutItemInfo.setwName(workoutnametxt.getText().toString());
                                       workoutItemInfo.setwDes(workdestxt.getText().toString());
                                       JSONArray jsonArray = new JSONArray(aa);
                                       String k = jsonArray.getString(0);
                                       JSONObject jsonObject = new JSONObject(k);
                                       String TIME = jsonObject.getString("wTime");
                                       workoutItemInfo.setwTime(TIME);

                                       Gson gson = new Gson();
                                       workoutItemInfoArrayList = new ArrayList<>();
                                       workoutItemInfoArrayList.add(workoutItemInfo);
                                       String h = gson.toJson(workoutItemInfoArrayList);

                                       editor.putString(workoutitemKeylist.get(position), h);
                                       editor.apply();

                                       editItem(position, workoutnametxt.getText().toString(), workdestxt.getText().toString());
                                       dialog.dismiss();

                                   } catch (JSONException e) {
                                       Log.e("!!! 게시물 수정","뭐지 ㅅㅂ?");
                                       e.printStackTrace();
                                   }
                               } else {
                                   Toast.makeText(Workout.this, "정보를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                               }
                           }else {
                               Toast.makeText(Workout.this, "이름이 중복된 게시물이 있습니다", Toast.LENGTH_SHORT).show();

                           }
                        }
                    });
                    nobtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                }
                if(item.getItemId()==R.id.deleteitem)
                {
                    Toast.makeText(Workout.this,"삭제"+position,Toast.LENGTH_SHORT).show();
                  // v.animate().setDuration(500).x(-v.getWidth()).alpha(0f);

                    SharedPreferences sss= getSharedPreferences("생성워크아웃"+Main2Activity.Loginname,0);
                    SharedPreferences.Editor editor = sss.edit();
                    String h = sss.getString(workoutitemKeylist.get(position),"null");
                    Log.e("!!! 삭제될 KEY정보",workoutitemKeylist.get(position));
                    Log.e("!!! 삭제될 KEY정보에 해당하는 VALUE",h);

                    editor.remove(workoutitemKeylist.get(position));
                    Log.e("!!! 삭제전 workoutitemKeylist 사이즈",workoutitemKeylist.size()+"");
                    workoutitemKeylist.remove(position);
                   Gson gson = new Gson();
                   String aa = gson.toJson(workoutitemKeylist);
                   editor.putString("생성워크아웃KEY",aa);
                   editor.apply();

                    removeItem(position);
                    Log.e("!!! 삭제후 workoutitemKeylist 사이즈",workoutitemKeylist.size()+"");
                    SharedPreferences sss3= getSharedPreferences("생성워크아웃"+Main2Activity.Loginname,0);
                    for(int i =0 ; i<workoutitemKeylist.size(); i++){
                        Log.e("!!! 남아있는 KEY에해당하는 VALUE"+Integer.toString(i),sss3.getString(workoutitemKeylist.get(i),"삭제됨"));
                        Log.e("!!! 남아있는 KEY"+Integer.toString(i),workoutitemKeylist.get(i));
                    }

                    SharedPreferences pp = getSharedPreferences("모든운동"+WNAME, 0);
                    Log.e("??? WORKOUT 삭제될 게시물 KEY","모든운동"+WNAME);
                    Log.e("??? WORKOUT 삭제",pp.getString("복근0","null"));
                    SharedPreferences.Editor editorpp = pp.edit();
                    editorpp.clear();
                    editorpp.commit();

                    SharedPreferences kkk = getSharedPreferences("담긴운동파일"+WNAME,0);
                    SharedPreferences.Editor ee = kkk.edit();
                    ee.clear();
                    ee.commit();
                }
                return false;
            }
        });
        p.show();


    }
}
