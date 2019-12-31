package com.example.guswn.workoutlog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;


import static com.example.guswn.workoutlog.StatisticsActivity.part;
import static java.security.AccessController.getContext;

public class Main2Activity extends AppCompatActivity implements  View.OnClickListener {
//사진으로 전송시 되돌려 받을 번호

    static final int REQUEST_PICTURE=1;

//앨범으로 전송시 돌려받을 번호

    static final int REQUEST_PHOTO_ALBUM=2;
    /***/
    static ArrayList<String> DayList = new ArrayList<>();
    String  DATE;

    ///


    ArrayList<String> AllStatList = new ArrayList<>();
    String ST_name,ST_part,ST_reps;
    int[] allcount = new int[] {0,0,0,0,0,0,0};
    /***/

    ImageView goalimg;
    Button goalbtn;
    Button photobtn;
    Toolbar myToolbar;
   // Dialog dialog;
    TextView today_wo1;
    TextView usernametxt;
    String wo_name="";
   // Button  button;
    String ID;
    String PW;
    String NAME;

    Context mContext;
    static String Loginname;
    static String Password;

    ArrayList<String> itemname = new ArrayList<>();
    Context context;
    ArrayList<String> workoutname = new ArrayList<>();

    Handler mHandler;


    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    ArrayList<Main2Activity_Info> main2ActivityInfos =  new ArrayList<>();
    Main2Activity_Adapter main2ActivityAdapter;
    private static int displayedposition = 0;



    Boolean zero = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         today_wo1 = findViewById(R.id.today_wo1);
        usernametxt = findViewById(R.id.usernametxt);
        myToolbar = (Toolbar) findViewById(R.id.myToolbar);



        goalimg = findViewById(R.id.goalimg);

        goalbtn = findViewById(R.id.goalbtn);
        photobtn = findViewById(R.id.photobtn);

         mHandler = new Handler();
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        DATE = date.format(today);


         SharedPreferences app2 = getSharedPreferences("회원정보",0);
         app2.getString("회원ID","null");
         Loginname = app2.getString("회원ID","null");
         Password = app2.getString("회원PW","null");

         usernametxt.setText(Loginname+"'s WANNABE BODY");

        loadImg();
        workoutdialog();



//        SharedPreferences app = getSharedPreferences("회원사진"+Loginname,0);
//        SharedPreferences.Editor editor= app.edit();
//        editor.putString("회원사진정보","null");
//        editor.putString("사진유형","null");
//        editor.apply();// 아무것도 없을떄
        /**실험*/

        goalbtn.setOnClickListener(this);
        photobtn.setOnClickListener(this);
        goalimg.setOnClickListener(this);
//        goalimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main2Activity.this);
//                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_add_workout,null);
//                Button nobtn =  mView.findViewById(R.id.add_nobtn);
//                Button yesbtn = mView.findViewById(R.id.add_yesbtn);
//                TextView title = mView.findViewById(R.id.textView23);
//                mBuilder.setView(mView);
//                title.setText("이미지를 변경하시겠습니까?");
//
//                yesbtn.setText("카메라");
//                nobtn.setText("갤러리");
//                final AlertDialog dialog = mBuilder.create();
//                dialog.show();
//                //카메라
//                yesbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        sendTakePhotoIntent();
//                    }
//                });
//                //갤러리
//                nobtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        photoAlbum();
//                    }
//                });
//                return false;
//            }
//        });
      //  button.setOnClickListener(this);

        setSupportActionBar(myToolbar);

        mRecyclerView = findViewById(R.id.main2_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        main2ActivityAdapter = new Main2Activity_Adapter(main2ActivityInfos);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setAdapter(main2ActivityAdapter);
        main2ActivityAdapter.setOnClickListener(new Main2Activity_Adapter.Main2MyRecyclerViewClickListener() {
            @Override
            public void onItemClicked(int position, View v) {
                if(zero) {
                    Intent intent = new Intent(Main2Activity.this, How_to_xx.class);
                    intent.putExtra("pic4", main2ActivityInfos.get(position));
                    intent.putExtra("운동부위", main2ActivityInfos.get(position).getMain_part());
                    intent.putExtra("Main2", false);
                    startActivity(intent);
                }
            }

        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("??? onScrollStateChanged",""+newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                displayedposition = llm.findFirstVisibleItemPosition();
                Log.e("??? displayedposition","displayedposition"+displayedposition);
                Log.e("??? onScrolled","dx"+dx+"/"+"dy"+dy);
            }
        });

//        Log.e("??? main2ActivityInfos.size",""+main2ActivityInfos.size());
//        for (int i =0; i<1000000000*main2ActivityInfos.size();i++){
//            if(i%100000000==0){
//                final int finalI = i;
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRecyclerView.smoothScrollBy(300,0);
//                        mRecyclerView.smoothScrollToPosition(finalI);
//                    }
//                },200);
//            }
//        }



        Intent intent = getIntent();
        getSupportActionBar().setTitle("Home");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent;
                switch (item.getItemId()) {
                    case R.id.action_history:
                        Toast.makeText(Main2Activity.this, "History", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Main2Activity.this,Logs.class);
                        startActivity(intent);
                        break;
                    case R.id.action_workout:
                        Toast.makeText(Main2Activity.this, "Workout", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Main2Activity.this,Workout.class);
                        int a=0;
                        for (String work_name : workoutname)
                        {
                            intent.putExtra(Integer.toString(a)+"2",work_name);
                            a++;
                        }
                        intent.putExtra("size2",Integer.toString(workoutname.size()));
                        startActivity(intent);

                        break;
                    case R.id.action_exercise:
                        Toast.makeText(Main2Activity.this, "Exercise", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Main2Activity.this,Exercise.class);
                        int i=0;
                        for (String work_name : workoutname)
                        {
                            intent.putExtra(Integer.toString(i),work_name);
                            i++;
                        }
                        intent.putExtra("size",Integer.toString(workoutname.size()));

                        startActivity(intent);
                        break;

                    case R.id.action_community:
                        Toast.makeText(Main2Activity.this, "Community", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Main2Activity.this,CommunityActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.action_timer:
                        Toast.makeText(Main2Activity.this, "Rank", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Main2Activity.this,Timer_Fragment.class);
//                        intent = new Intent(Main2Activity.this,RankingActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        AllDayList();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences appdata = getSharedPreferences("오늘의운동",0);
        SharedPreferences.Editor editor = appdata.edit();
        editor.remove("오늘의운동들");
        editor.apply();
        Log.e("!!! onDestroy","onDestroy");
    }
    private void workoutdialog() {
        final CharSequence[] items= {"ABS","BACK","BICEPS","CHEST","TRICEPS","SHOULDER","LEGS"};
        final boolean[] checkedItems = { false, false, false, false, false, false, false };


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("오늘 운동할 부위를 선택하세요")
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        checkedItems[which] = isChecked;
                        int cnt =0;
                        for (int i = 0; i <checkedItems.length; i++) {
                            if (checkedItems[i]) {

                                // wo_name += items[i].toString()+"\n";
                                cnt++;
                            }
                        }
                        if (cnt > 3) {
                            Toast.makeText(Main2Activity.this, "최대 3개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                            checkedItems[which] = false;
                            ((AlertDialog) dialog).getListView().setItemChecked(which, false);
                        }
                    }
                })
//                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(getApplicationContext(),items[which]+"를 선택 하셨습니다.",Toast.LENGTH_SHORT).show();
//                    }
//                })
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // today_wo1.setText(wo_name);

                        for (int i = 0; i <checkedItems.length; i++) {

                            if(checkedItems[i]==true)
                            {
                                wo_name += items[i].toString()+"\n";
                                workoutname.add(items[i].toString());

                            }
                        }

                        today_wo1.setText(wo_name);

                        SharedPreferences aaa= getSharedPreferences("오늘의운동",0);
                        SharedPreferences.Editor editor = aaa.edit();
                        Gson gson = new Gson();
                        String k = gson.toJson(workoutname);
                        editor.putString("오늘의운동들",k);
                        editor.apply();

//                        SharedPreferences appdata = getSharedPreferences("오늘의운동",0);
//                        String kk = appdata.getString("오늘의운동들","null");
//                        Log.e("??? 오늘의운동들",kk);
//                        if(!kk.equals("null")){
//                            try {
//                                JSONArray jsonArray =new JSONArray(kk);
//                                for(int i = 0; i<jsonArray.length(); i++){
//                                    String exercisename;
//                                    String name = (String) jsonArray.get(i);// 운동 부위
//                                    Log.e("??? 오늘의운동들 name",name);
//                                    switch (name){
//                                        case "ABS":
//                                            name = "복근";
//                                            break;
//                                        case "BACK":
//                                            name = "등";
//                                            break;
//                                        case "BICEPS":
//                                            name = "이두";
//                                            break;
//                                        case "CHEST":
//                                            name = "가슴";
//                                            break;
//                                        case "TRICEPS":
//                                            name = "삼두";
//                                            break;
//                                        case "SHOULDER":
//                                            name = "어깨";
//                                            break;
//                                        case "LEGS":
//                                            name = "하체";
//                                            break;
//
//                                    }
//
//                                    SharedPreferences APPDATA =getSharedPreferences("ALLWORKOUT",0);
//                                    String size = APPDATA.getString(name + "사이즈", "null");
//                                    Log.e("??? ALLWORKOUT size",size);
//                                    if(!size.equals("null")) {
//                                        for (int j = 0; j < Integer.parseInt(size); j++) {
//                                            String val = APPDATA.getString(name + Integer.toString(j), "null");
//                                            Log.e("??? ALLWORKOUT val"+name + Integer.toString(j),val);
//                                            if (!val.equals("null")) {
//                                                JSONObject jsonObject = new JSONObject(val);
//                                                exercisename = jsonObject.getString("exercisename");
//                                                SharedPreferences sp = getSharedPreferences("좋아요체크"+name+Main2Activity.Loginname,0);
//                                                Boolean b = sp.getBoolean(exercisename, false);
//                                                if (b == true) {
//                                                    String part = jsonObject.getString("part");
//                                                    int img = jsonObject.getInt("exerciseimg");
//                                                    main2ActivityInfos.add(new Main2Activity_Info(img, exercisename, part));
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                Log.e("??? MAIN2","뭐야 ㅅㅂ");
//                                e.printStackTrace();
//                            }
//                        }



                        likedworkoutList();
                        Toast.makeText(Main2Activity.this,"환영합니다 "+Loginname+" 님",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        Animation anim = AnimationUtils.loadAnimation
                                (getApplicationContext(), // 현재화면 제어권자
                                        R.anim.alpha_anim);      // 에니메이션 설정한 파일
                        goalimg.startAnimation(anim);

                        Animation anim2 = AnimationUtils.loadAnimation
                                (getApplicationContext(), // 현재화면 제어권자
                                        R.anim.twinkle_anim);      // 에니메이션 설정한 파일
                        usernametxt.startAnimation(anim2);

                    }
                });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {// 화면 회전시 데이터 유지 하기위한 메소드

        outState.putString("today_workout",wo_name);
        super.onSaveInstanceState(outState);//항상 호출을 해줘야함
    }

    public void likedworkoutList(){
        SharedPreferences appdata = getSharedPreferences("오늘의운동",0);
        String kk = appdata.getString("오늘의운동들","null");
        Log.e("!!! 오늘의운동들",kk);
        if(!kk.equals("null")){
            try {
                JSONArray jsonArray =new JSONArray(kk);
                for(int i = 0; i<jsonArray.length(); i++){
                    String exercisename;
                    String name = (String) jsonArray.get(i);// 운동 부위
                    //Log.e("!!! 오늘의운동들 name",name);
                    switch (name){
                        case "ABS":
                            name = "복근";
                            break;
                        case "BACK":
                            name = "등";
                            break;
                        case "BICEPS":
                            name = "이두";
                            break;
                        case "CHEST":
                            name = "가슴";
                            break;
                        case "TRICEPS":
                            name = "삼두";
                            break;
                        case "SHOULDER":
                            name = "어깨";
                            break;
                        case "LEGS":
                            name = "하체";
                            break;

                    }
                    Log.e("!!! 오늘의운동들 name",name);
                    SharedPreferences APPDATA =getSharedPreferences("ALLWORKOUT",0);
                    String size = APPDATA.getString(name + "사이즈", "null");
                    //Log.e("!!! ALLWORKOUT size",size);
                    if(!size.equals("null")) {
                        for (int j = 0; j < Integer.parseInt(size); j++) {
                            String val = APPDATA.getString(name + Integer.toString(j), "null");
                            //Log.e("!!! ALLWORKOUT val"+name + Integer.toString(j),val);
                            if (!val.equals("null")) {
                                JSONObject jsonObject = new JSONObject(val);
                                exercisename = jsonObject.getString("exercisename");
                                SharedPreferences sp = getSharedPreferences("좋아요체크"+name+Main2Activity.Loginname,0);
                                Boolean b = sp.getBoolean(exercisename, false);

                                if (b == true) {

                                    String part = jsonObject.getString("part");
                                    int img = jsonObject.getInt("exerciseimg");

                                    Log.e("!!! "+exercisename+"/"+part+" 좋아요체크",""+b);
                                    main2ActivityInfos.add(new Main2Activity_Info(img, exercisename, part));
                                }
                            }
                        }
//                        if(main2ActivityInfos.size()==0){
//                            main2ActivityInfos.add(new Main2Activity_Info(0, "", "좋아요된 운동이 없습니다"));
//                        }
                    }
                }
            } catch (JSONException e) {
                Log.e("!!! MAIN2","뭐야 ㅅㅂ");
                e.printStackTrace();
            }
        }
        Log.e("!!! main2ActivityInfos SIZE",""+main2ActivityInfos.size());
        if(main2ActivityInfos.size()==0){
            main2ActivityInfos.add(new Main2Activity_Info(0, "", "좋아요된 운동이 없습니다"));
            zero =false;
        }
        if(main2ActivityInfos.size()!=0) {
            task = new MyAsync();
            task.execute(main2ActivityInfos.size());
        }
    }

    private  MyAsync task;
    class MyAsync extends AsyncTask<Integer,Integer,Integer>{
        boolean resume = true;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            int counter = 0;
            while (resume) {
                publishProgress(counter);
                resume = (counter++ != integers[0]);
                try {
                    // --- put here any time expensive code --
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mRecyclerView.smoothScrollBy(300, 0);
            mRecyclerView.smoothScrollToPosition(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {// 화면 회전시 데이터 유지 하기위한 메소드
        // 이 메소드를 사용하지 않으려면
        // onCreate() 함수에서 if(savedInstanceState != null) 조건 내용안에 밑에 코드를 넣어줘야한다.
        super.onRestoreInstanceState(savedInstanceState);

        wo_name = savedInstanceState.getString("today_workout");
        today_wo1.setText(wo_name);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("종료 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //finish();
                    //  ActivitiyCompat.finishAffinity(Main2Activity.this);

//                        Intent intent = new Intent(Main2Activity.this,LoginActivity.class);
//                        intent.putExtra("kill",true);
//                        startActivity(intent);
//                        Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                        finish();
                        //moveTaskToBack(true);//완전히 종료한건 아니다. 홈화면으로 돌아가는 것 뿐
                        //finishAndRemoveTask ();
                        SharedPreferences appdata = getSharedPreferences("오늘의운동",0);
                        SharedPreferences.Editor editor = appdata.edit();
                     editor.clear().commit();
//                        editor.putString("오늘의운동들","삭제");
//                        editor.apply();
                        Log.e("!!! finishAffinity","finishAffinity");

                        ActivityCompat.finishAffinity(Main2Activity.this);
                        System.runFinalization();
                        System.exit(0);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_settings:
                //Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Main2Activity.this,SettingActivity.class);


                startActivity(intent);

                return true;

                default:
                   // Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                    return super.onOptionsItemSelected(item);


        }

    }


    @Override
    public void onClick(View v) {


        switch (v.getId())
        {
            case R.id.goalbtn://사진불러오기
                photoAlbum();

                break;

            case R.id.photobtn://사진찍기
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,111);
                sendTakePhotoIntent();
                break;

            case R.id.goalimg:
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main2Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_dialog_add_workout,null);
                Button nobtn =  mView.findViewById(R.id.add_nobtn);
                Button yesbtn = mView.findViewById(R.id.add_yesbtn);
                TextView title = mView.findViewById(R.id.textView23);
                mBuilder.setView(mView);
                title.setText("이미지를 변경하시겠습니까?");


                final AlertDialog dialog = mBuilder.create();
                dialog.show();
               //갤러리
                yesbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        photoAlbum();

                    }
                });
                //카메라
                nobtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                       // sendTakePhotoIntent();
                    }
                });
                break;
        }
    }


    void photoAlbum(){
//        Intent intent = new Intent(Intent.ACTION_PICK); // 이걸로 하면 앱을 끄고 다시 켰을때 이미지불러오기 시 퍼미션 디나니얼나옴
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent,REQUEST_PHOTO_ALBUM);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if(requestCode==REQUEST_IMAGE_CAPTURE){//사진찍기
               //goalimg.setImageBitmap(loadPicture());
                if(data!=null) {
                    /**
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    goalimg.setImageBitmap(imageBitmap);
                     */
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
                    ExifInterface exif = null;

                    try {
                        exif = new ExifInterface(imageFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int exifOrientation;
                    int exifDegree;

                    if (exif != null) {
                        //exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees( ExifInterface.ORIENTATION_ROTATE_90);
                    } else {
                        exifDegree = 0;
                    }

                    ((ImageView)findViewById(R.id.goalimg)).setImageBitmap(rotate(bitmap, exifDegree));
                }
                else if (data == null){
                    //Toast.makeText(Main2Activity.this,"사진찍기 취소",Toast.LENGTH_SHORT).show();
                }

            }
            if(requestCode==REQUEST_PHOTO_ALBUM){//사진불러오기

                if(data!=null) {
                    SharedPreferences app2 = getSharedPreferences("회원사진"+Loginname,0);
                    SharedPreferences.Editor editor2= app2.edit();
                    editor2.putString("회원사진정보",data.getData().toString());
                    editor2.putString("사진유형","갤러리");
                    editor2.apply();
                    Log.e("??? 사진가져오기 ImageURI",data.getData().toString());


                    SharedPreferences app3 = getSharedPreferences("사진",0);
                    SharedPreferences.Editor editor3 =app3.edit();
                    editor3.putString("사진"+Loginname,data.getData().toString());
                    editor3.apply();
                    goalimg.setImageURI(data.getData());
                    Log.e("??? 사진불러오기 loadImg setImageURI",""+data.getData());

                }
                else if (data == null){
                  //  Toast.makeText(Main2Activity.this,"사진가져오기 취소",Toast.LENGTH_SHORT).show();

                }
            }


    }
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void sendTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                Log.e("??? 사진찍기 photoUri",photoUri.toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        SharedPreferences app = getSharedPreferences("회원사진"+Loginname,0);
        SharedPreferences.Editor editor= app.edit();
        editor.putString("회원사진정보",imageFilePath);
        editor.putString("사진유형","카메라");
        editor.apply();

        SharedPreferences app3 = getSharedPreferences("사진",0);
        SharedPreferences.Editor editor3 =app3.edit();
        editor3.putString("사진"+Loginname,imageFilePath);
        editor3.apply();
        Log.e("??? 사진찍기 loadImg setImageURI",""+imageFilePath);
        return image;
    }

    public void loadImg(){
        SharedPreferences app3 = getSharedPreferences("사진",0);
        String a =app3.getString("사진"+Loginname,"null");
        Log.e("??? loadImg",""+Uri.parse(a));
        if(a!="null"){

            ((ImageView)findViewById(R.id.goalimg)).setImageURI(Uri.parse(a));
            SharedPreferences aaa =getSharedPreferences("회원사진"+Loginname,0);
            SharedPreferences.Editor eee= aaa.edit();
            eee.putString("회원사진정보",a);
            eee.putString("사진유형","갤러리");
//            eee.putString("PIC",a);
            eee.apply();
        }else {
            ((ImageView)findViewById(R.id.goalimg)).setImageResource(R.drawable.ic_plus_24dp);
        }
    }
//
//    public void  Statistics() {
//
//        for (int AA = 0; AA < DayList.size(); AA++) {
//            SharedPreferences sp2 = getSharedPreferences(DayList.get(AA) + Main2Activity.Loginname, 0);
//
//
//            String[] part = {"복근", "등", "이두", "가슴", "하체", "어깨", "삼두"};
//            for (int i = 0; i < 7; i++) {
//                SharedPreferences APPDATA = getSharedPreferences("ALLWORKOUT", 0);
//                String size = APPDATA.getString(part[i] + "사이즈", "null");
//                if (!size.equals("null")) {
//                    for (int j = 0; j < Integer.parseInt(size); j++) {
//                        String val = APPDATA.getString(part[i] + Integer.toString(j), "null");
//                        if (!val.equals("null")) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(val);
//                                exercisename = jsonObject.getString("exercisename");
//                                part2 = jsonObject.getString("part");
//                                String todayCom = sp2.getString("완료" + exercisename, "null");
//                                if (todayCom == "null") {
//                                    completeSetInfoArrayList = new ArrayList<>();
//                                } else {
//                                    Type type = new TypeToken<ArrayList<CompleteSetInfo>>() {
//                                    }.getType();
//                                    Gson gson = new Gson();
//                                    completeSetInfoArrayList = gson.fromJson(todayCom, type);
//                                    for (int kk = 0; kk < completeSetInfoArrayList.size(); kk++) {
//                                        try {
//                                            JSONArray jsonArray = new JSONArray(todayCom);
//                                            String k = jsonArray.getString(kk);
//                                            JSONObject Object = new JSONObject(k);
//                                            //                    String comSet = Object.getString("comSet");
//                                            //                    String comWeight = Object.getString("comWeight");
//                                            //                    String comReps = Object.getString("comReps");
//
//
//                                            comData = Object.getString("comData");
//                                            comName = Object.getString("comName");
//                                            String comTime = Object.getString("comTime");
//
//                                            String AA2 = comName + "/ Data" + comData + " part/" + part2;
//                                            //                    String data = comWeight+" kg x "+comReps+" reps";
//                                            Log.e("???  LOG DAILY", AA2);
//
//                                            //                    String todaydata = sp2.getString("data"+exercisename,"null");
//                                            //                    Log.e("??? todaydata",todaydata);
//                                            //                    if(todaydata =="null"){
//                                            //                        Datalists = new ArrayList<>();
//                                            //                    }else {
//                                            //                        Type type2 = new TypeToken<ArrayList<String>>(){}.getType();
//                                            //                        Gson gson2 = new Gson();
//                                            //                        Datalists = gson2.fromJson(todaydata,type2);
//                                            //                        for(int a =0; a<completeSetInfoArrayList.size(); a++){
//                                            //                            JSONArray jsonArray2 = new JSONArray(todaydata);
//                                            //                            String A = jsonArray2.getString(a);
//                                            //                            Log.e("??? Datalists"+a,""+A);
//                                            //
//                                            //                        }
//                                            //                    }
//
//
//                                            //logsDailyInfosList.add(new Logs_Daily_Info(part2,comName,comData));
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                    String todaydata = sp2.getString("data" + exercisename, "null");
//                                    Log.e("??? todaydata", todaydata);
//                                    if (todaydata == "null") {
//                                        Datalists = new ArrayList<>();
//                                    } else {
//                                        Type type2 = new TypeToken<ArrayList<String>>() {
//                                        }.getType();
//                                        Gson gson2 = new Gson();
//                                        Datalists = gson2.fromJson(todaydata, type2);
//                                        for (int a = 0; a < Datalists.size(); a++) {
//                                            JSONArray jsonArray2 = new JSONArray(todaydata);
//                                            String A = jsonArray2.getString(a);
//                                            Log.e("??? Datalists" + a, "" + A);
//
//                                            D += A + "\n";
//
//
//                                        }
//                                        Log.e("??? DDDDD " + exercisename + " " + part2 + " / Datalist SIZE", D + " / " + "" + Datalists.size());
//                                        //Datalists.size()는 운동 한 세트수
////                                        logsDailyInfosList.add(new Logs_Daily_Info(part2, exercisename, D));
//
//                                        /** 통계 load*/
////                                                                       SharedPreferences appdata2= getSharedPreferences("통계들",0);
////                                                                       String B = appdata2.getString("통계"+selectedDay,"null");
////                                                                       if(B=="null"){
////                                                                           statInfosList = new ArrayList<>();
////                                                                       }else {
////                                                                           Type type3 = new TypeToken<ArrayList<StatisticsActivity_Info>>(){}.getType();
////                                                                           Gson gson3 = new Gson();
////                                                                           statInfosList = gson3.fromJson(B,type);
////                                                                       }
//                                        /** 통계 load*/ //세이브만 해줘야 한다 로드를 해주면 매번 들어올때마다 계속 기록들이 쌓인다.
//
//
//                                        /** 통계 save*/
//                                        StatisticsActivity_Info statisticsInfo = new StatisticsActivity_Info(exercisename, part2, Integer.toString(Datalists.size()));
//                                        statInfosList.add(statisticsInfo);
//                                        SharedPreferences appdata = getSharedPreferences("통계들" + Main2Activity.Loginname, 0);
//                                        SharedPreferences.Editor editor = appdata.edit();
//                                        Gson gson1 = new Gson();
//                                        String A = gson1.toJson(statInfosList);
//                                        editor.putString("통계" + DayList.get(AA), A);
//                                        editor.apply();
//                                        /** 통계 save*/
//
//                                        /***/
//                                        D = "";// 반드시 해줘야 다른 운동 세트 기록이 제대로 나온다
//                                        /***/
//
//
//                                        /*************************************************************************/
////                                                                       StatisticsList.add(exercisename);
////                                                                       //오늘한 운동 목록들이 담겨있다
////                                                                       Gson gson1 = new Gson();
////                                                                       String list = gson1.toJson(StatisticsList);
////                                                                       SharedPreferences AP = getSharedPreferences("통계",0);
////                                                                       SharedPreferences.Editor editor = AP.edit();
////                                                                       editor.putString("통계"+selectedDay,list);
////                                                                       editor.apply();
////                                                                       Log.e("??? StatisticsList 내용",""+StatisticsList);
////                                                                       Log.e("??? logsDailyInfosList 사이즈", ""+logsDailyInfosList.size());
//                                        /*************************************************************************/
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//    }

    public void AllDayList(){
        /************************************************************************************************************/
        //날짜 저장 DayList
        /** selectedDay  load*/
        SharedPreferences SPP2 = getSharedPreferences("통계날짜들",0);
        String k = SPP2.getString("통계날짜","null");
        Log.e("??? DayList 통계날짜",k);
        if(k=="null"){
            DayList = new ArrayList<>();
        }else {
            Type type3 = new TypeToken<ArrayList<String>>(){}.getType();
            Gson gson3 = new Gson();
            DayList =  gson3.fromJson(k,type3);
            Log.e("??? DayList SIZE",""+DayList.size());
        }
        /** selectedDay  load*/

        /** selectedDay= save*/
        SharedPreferences SPP = getSharedPreferences("통계날짜들",0);
        SharedPreferences.Editor editorr = SPP.edit();
        if(DayList.size()>0) {
            Boolean isSame = false;
            Log.e("??? DayList SIZE >0",".");
            for (int i=0; i<DayList.size(); i++){
                if(DayList.get(i).equals(DATE)){
//                   DayList.add(selectedDay);
//                   Gson gson1 = new Gson();
//                   String aaa = gson1.toJson(DayList);
//                   editor.putString("통계날짜",aaa);
//                   editor.apply();
                    isSame = true;
                    Log.e("??? DayList isSame ",""+isSame);
                }
            }
            Log.e("??? DayList FINAL isSame ",""+isSame);
            if(isSame==false){
                DayList.add(DATE);
                Gson gson12 = new Gson();
                String aaa = gson12.toJson(DayList);
                editorr.putString("통계날짜",aaa);
                editorr.apply();

                SharedPreferences ppp= getSharedPreferences("통계날짜들",0);
                String ll =ppp.getString("통계날짜","null");
                Log.e("??? DayList isSame false 저장 결과",ll);
            }

        }else {
            Log.e("??? DayList SIZE ==0",".");
            DayList.add(DATE);
            Gson gson12 = new Gson();
            String aaa = gson12.toJson(DayList);
            editorr.putString("통계날짜",aaa);
            editorr.apply();
        }
        /** selectedDay= save*/
        /************************************************************************************************************/
    }
    public void AllStastics(){
        SharedPreferences AP = getSharedPreferences("통계들"+Main2Activity.Loginname,0);
//        /**test!!*/  SharedPreferences AP = getSharedPreferences("통계들",0);
        for(int b=0; b< DayList.size(); b++){
            String todayStat = AP.getString("통계"+ DayList.get(b),"null");
//            Log.e("??? DayList2 풀어헤친 날들",DayList.get(b));
            Log.e("??? DayList2 풀어헤친 날들 저장된값",DayList.get(b)+" / "+todayStat);
            if(todayStat !="null") {
                Type type = new TypeToken<ArrayList<StatisticsActivity_Info>>() {}.getType();
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
                        switch (ST_part){
                            case "복근":
                                allcount[0]+=Integer.parseInt(ST_reps);
                                break;
                            case "등":
                                allcount[1]+=Integer.parseInt(ST_reps);
                                break;
                            case "이두":
                                allcount[2]+=Integer.parseInt(ST_reps);
                                break;
                            case "가슴":
                                allcount[3]+=Integer.parseInt(ST_reps);
                                break;
                            case "하체":
                                allcount[4]+=Integer.parseInt(ST_reps);
                                break;
                            case "어깨":
                                allcount[5]+=Integer.parseInt(ST_reps);
                                break;
                            case "삼두":
                                allcount[6]+=Integer.parseInt(ST_reps);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }


        for(int i=0; i<7; i++){
            if(allcount[i]!=0) {


            }
        }




        Statistics_Info statisticsInfo = new Statistics_Info( allcount[0], allcount[1], allcount[2], allcount[3], allcount[4], allcount[5], allcount[6]);
        Gson gson = new Gson();
        ArrayList<Statistics_Info> statisticsInfoArrayList = new ArrayList<>();
        statisticsInfoArrayList.add(statisticsInfo);
        String aa = gson.toJson(statisticsInfoArrayList);
        SharedPreferences ap = getSharedPreferences("전체운동횟수"+Main2Activity.Loginname,0);
        SharedPreferences.Editor editor = ap.edit();
        editor.putString("전체운동한것들",aa);
        editor.apply();
    }
}
