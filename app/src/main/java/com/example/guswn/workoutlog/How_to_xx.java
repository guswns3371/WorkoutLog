package com.example.guswn.workoutlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class How_to_xx extends AppCompatActivity implements View.OnClickListener{

    TextView exercisetxt2;
    TextView exercise_description;

    Button googlebtn;
    ImageView how_to_gif;
    ImageButton timerbtn;
    ImageButton likedbtn;
    ImageButton addbtn;
    ImageButton youtubebtn;
    String part,position1,key;

    String Wname;
    String NAME;
    Boolean isLiked2,isLiked3,isLiked4;
    Boolean isAdd;
    Boolean isAdd2,search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_xx);

        exercisetxt2 = findViewById(R.id.exercisetxt2);
        youtubebtn = findViewById(R.id.youtubebtn);
        googlebtn = findViewById(R.id.googlebtn);
        how_to_gif = findViewById(R.id.how_to_gif);
        exercise_description = findViewById(R.id.exercise_description);
        timerbtn = findViewById(R.id.timerbtn);
        likedbtn = findViewById(R.id.likedbtn);
        addbtn = findViewById(R.id.addbtn);

        Intent intent = getIntent();
        //Exercise 클래스에서 받아오는 intent
        String exercisename = intent.getStringExtra("exercisetxt");
        isAdd = intent.getBooleanExtra("isAdd",false);
        isAdd2 = intent.getBooleanExtra("isAdd2",true);

        isLiked2 = intent.getBooleanExtra("isLiked2",false);
        isLiked3 = intent.getBooleanExtra("Exercise_xx",false);
        isLiked4 = intent.getBooleanExtra("Main2",true);

        search = intent.getBooleanExtra("Search",false);
        Wname = intent.getStringExtra("Wname");



        if(isAdd){
            if(isAdd2) {
                addbtn.setVisibility(View.VISIBLE);
            }
        }else {
            addbtn.setVisibility(View.GONE);
        }

        //이미지를 받아올때는 파싱해서 받아와야한다.
        //1.아이템클래스에서 implements Parcelable 해준다
        //2.이제 어댑터에서 리사이클러아이템 클릭시 여기로 인텐트가 넘오오는데
        //그떄 intent.putExtra("pic",exerciseInfos.get(position)); 해준다
        //여기서 이미지만 따로 보낼수 없으니 전체 정보를 보낸다
        //3.인텐트에 담긴 정보는 어레이 리스트정보이다.
        // 그래서 어레이 리스트 객체 exerciseInfo에 받은 인텐트 정보를 넗는다 =>  ExerciseInfo exerciseInfo = intent.getParcelableExtra("pic");
        //4.인텐트 정보가 담긴 어레이 리스트 객체에서 이미지정보를 추출하고 담는다 => int pic = exerciseInfo.getExerciseimg();
        //5.그리고나서  how_to_gif.setImageResource(pic); 이미지를 세팅 해주면 ㅇㅋ~


        ExerciseInfo exerciseInfo = intent.getParcelableExtra("pic");
        InnerWorkout_Info InnerWorkout_Info = intent.getParcelableExtra("pic2");
        LikedActivity_Info likedActivity_info = intent.getParcelableExtra("pic3");
        Main2Activity_Info main2Activity_Info = intent.getParcelableExtra("pic4");
          if(exerciseInfo!=null) {
              int pic = exerciseInfo.getExerciseimg();
              how_to_gif.setImageResource(pic);
              NAME =exerciseInfo.getExercisename();
              exercisetxt2.setText(NAME);
          }
          if(InnerWorkout_Info!=null) {
              int pic2 = InnerWorkout_Info.getInn_img();
              how_to_gif.setImageResource(pic2);
              NAME =InnerWorkout_Info.getInn_name();
              exercisetxt2.setText(NAME);
          }
          if(likedActivity_info !=null){
              int pic3 = likedActivity_info.getLiked_img();
              how_to_gif.setImageResource(pic3);
              NAME = likedActivity_info.getLiked_name();
              exercisetxt2.setText(NAME);
          }
          if(main2Activity_Info !=null){
              int pic4 = main2Activity_Info.getMain_img();
              how_to_gif.setImageResource(pic4);
              NAME = main2Activity_Info.getMain_name();
              exercisetxt2.setText(NAME);
              //likedbtn.setVisibility(View.GONE);
          }

        part = intent.getStringExtra("운동부위");
        SharedPreferences app2 =getSharedPreferences("좋아요체크"+part+Main2Activity.Loginname,0);
        Boolean isLiked = app2.getBoolean(NAME,false);

        Log.e("??? HowTOXX 부위 / 좋아요체크여부",part+" / "+isLiked);
        if(isLiked==false){
            likedbtn.setImageResource(R.drawable.ic_favorite_empty_24dp);
        }else if(isLiked==true){
            likedbtn.setImageResource(R.drawable.ic_favorite_full_24dp);
            L++;
        }


        Log.e("??? How_to_xx 운동부위",part);
        position1 = intent.getStringExtra("운동부위position");

        key = part+position1;
    //    Log.e("??? How_to_xx 운동부위 KEY",key);

//        SharedPreferences app = getSharedPreferences("모든운동",0);
//        String k = app.getString(key,"null");
//        try {
//            JSONObject jsonObject = new JSONObject(k);
//             isliked = jsonObject.getBoolean("ischecked");
//            if(isliked==false){
//                addbtn.setImageResource(R.drawable.ic_add_empty_24dp);
//            }else if(isliked==true){
//                addbtn.setImageResource(R.drawable.ic_add_full_24dp);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        youtubebtn.setOnClickListener(this);
        googlebtn.setOnClickListener(this);
        timerbtn.setOnClickListener(this);
        likedbtn.setOnClickListener(this);
        addbtn.setOnClickListener(this);
      //  Intent intent1 = getIntent();
     //   ExerciseInfo exerciseInfo = intent1.getParcelableExtra("picture");
     //   int imageRes = exerciseInfo.getExerciseimg();


//        likedbtn.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//
//                    case MotionEvent.ACTION_DOWN:
//                    {
//                        likedbtn.setImageResource(R.drawable.ic_favorite_full_24dp);
//                    }break;
//
//                    case MotionEvent.ACTION_UP:
//                    {
//                        likedbtn.setImageResource(R.drawable.ic_favorite_empty_24dp);
//                    }break;
//                }
//
//
//                return false;
//            }
//        });
//
//        addbtn.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//
//                    case MotionEvent.ACTION_DOWN:
//                    {
//                        addbtn.setImageResource(R.drawable.ic_add_empty_24dp);
//                    }break;
//
//                    case MotionEvent.ACTION_UP:
//                    {
//                        addbtn.setImageResource(R.drawable.ic_add_full_24dp);
//                    }break;
//                }
//
//
//                return false;
//            }
//        });



//        exercisetxt2.setText(exercisename);

        switch (exercisetxt2.getText().toString()){

            case "Bench Squat (Barbell)":

                exercise_description.setText("1. This exercise is best performed inside a squat rack for safety purposes.\n" +
                        "2. Then, set the bar on a rack that best matches your height.\n" +
                        "3. Hold on to the bar using both arms at each side \n" +
                        "   and lift it off the rack by first pushing with your legs \n" +
                        "   and at the same time straightening your torso.");

                break;
            case "Ab Rollout":

                exercise_description.setText("1. Hold the Ab Roller with both hands and kneel on the floor.\n" +
                        "2. Now place the ab roller on the floor in front of you \n" +
                        "   so that you are on all your hands and knees \n" +
                        "   (as in a kneeling push up position).\n" +
                        "3. Slowly roll the ab roller straight forward, \n" +
                        "stretching your body into a straight position.");

                break;
            case "Bench Press (Dumbbell)":

                exercise_description.setText("Dumbbell Bench Press Instructions\n" +
                        "1. Lie down on a flat bench with a dumbbell \n" +
                        "   in each hand resting on top of your thighs. ...\n" +
                        "2. Then, using your thighs to help raise the dumbbells up, \n" +
                        "   lift the dumbbells one at a time \n" +
                        "   so that you can hold them in front of you at shoulder width.");

                break;
            case "Bent-Over Crossover (Cable)":

                exercise_description.setText("Cable Crossover Instructions\n" +
                        "1. To get yourself into the starting position, \n" +
                        "   place the pulleys on a high position (above your head), \n" +
                        "   select the resistance to be used and hold the pulleys in each hand.\n" +
                        "2. Step forward in front of an imaginary straight line \n" +
                        "   between both pulleys while pulling your arms together in front of you.");

                break;
            case "Dips":

                exercise_description.setText("1. Setup. Grab the bars and jump up. ...\n" +
                        "2. Dip. Lower your body by bending your arms. ...\n" +
                        "3. Break Parallel. Go down until your shoulders are below your elbows at the bottom.\n" +
                        "4. Rise Up. Lift your body back up to the starting position by straightening your arms.\n" +
                        "5. Lockout. Balance yourself with your shoulders over your hands.");

                break;

                default:
                    exercise_description.setText("1. Hold the bar straight over your chest with a bend in your arms. \n" +
                            "   This will be your starting position. \n" +
                            "2. While keeping your arms in the bent arm position, \n" +
                            "   lower the weight slowly in an arc behind your head \n" +
                            "   while breathing in until you feel a stretch on the chest.");
                    break;
        }


        //if(exercisetxt2.getText().equals("Bench Squat (Barbell)"))


    }

    String PART;
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences app = getSharedPreferences("모든운동"+Wname,0);
        String k = app.getString(key,"null");
        Log.e("??? 해당 VALUE",k);
        try {
            JSONObject jsonObject = new JSONObject(k);
            Boolean ischecked = jsonObject.getBoolean("ischecked");
            PART = jsonObject.getString("part");
            if(ischecked==false){
                addbtn.setImageResource(R.drawable.ic_add_empty_24dp);
            }else if(ischecked==true){
                addbtn.setImageResource(R.drawable.ic_add_full_24dp);
            }

        } catch (JSONException e) {
            try {
                JSONArray jsonArray = new JSONArray(k);
                String kk = jsonArray.getString(0);
                JSONObject jsonObject =new JSONObject(kk);
                Boolean ischecked2 = jsonObject.getBoolean("ischecked");
                PART = jsonObject.getString("part");
                if(ischecked2==false){
                    addbtn.setImageResource(R.drawable.ic_add_empty_24dp);
                }else if(ischecked2==true){
                    addbtn.setImageResource(R.drawable.ic_add_full_24dp);
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
    }

    int L=0;
    int A=0;
    @Override
    public void onClick(View v) {
        Uri uri;
        Intent intent;

        switch (v.getId()){
            case R.id.youtubebtn:
                uri = Uri.parse("https://www.youtube.com/results?search_query= how to do "+exercisetxt2.getText().toString());
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;


            case R.id.googlebtn:
                uri= Uri.parse("https://www.google.co.kr/search?ei=7LStW9-gGMb88gX3g5W4DQ&q="+"how+"+"to+"+"do+"
                        +exercisetxt2.getText().toString()+"&oq="+"how+"+"to+"+"do+"
                        +exercisetxt2.getText().toString()+"&gs_l=psy-ab.3..0l10.4668.7086.0.7884.10.10.0.0.0.0.214.800.0j5j1.7.0....0...1c.1j4.64.psy-ab..3.7.921.6..35i39k1j0i67k1j0i3k1j0i131k1.126.ecnsa3Iysqk"
                );
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;

            case R.id.timerbtn:

                //intent = new Intent(How_to_xx.this,Custom_Dialog_Timer.class);
                intent = new Intent(How_to_xx.this,Timer_Fragment.class);
                startActivity(intent);
//                Custom_Dialog_Timer custom_dialog_timer = new Custom_Dialog_Timer(How_to_xx.this);
//
//                custom_dialog_timer.callFunction();
                break;// 이거 안해주니까 밑에 likedbtn 이 같이 눌림

            case R.id.likedbtn:

                ImageButton ib = (ImageButton) v;
//                Log.e("??? Howtoxx 좋아요 클릭여부1"+NAME,""+ib.isSelected());
//                Log.e("??? Howtoxx 좋아요 클릭여부2"+NAME,""+ib.isActivated());
//                Log.e("??? Howtoxx 좋아요 클릭여부3"+NAME,""+ib.isEnabled());
//                Log.e("??? Howtoxx 좋아요 클릭여부4"+NAME,""+ib.isPressed());
//                Log.e("??? Howtoxx 좋아요 클릭여부5"+NAME,""+ib.isFocused());
                if(isLiked4) {
                    if (L % 2 != 0) {
                        likedbtn.setImageResource(R.drawable.ic_favorite_empty_24dp);
                        Toast.makeText(How_to_xx.this, "좋아요 취소", Toast.LENGTH_SHORT).show();
                        Log.e("??? Howtoxx 좋아용 취소", NAME);
                        SharedPreferences app = getSharedPreferences("좋아요체크" + part+Main2Activity.Loginname, 0);
                        SharedPreferences.Editor editor = app.edit();
                        editor.putBoolean(NAME, false);
                        editor.apply();
                        if (isLiked2 == true) {

                        }
                    } else if (L % 2 == 0) {
                        likedbtn.setImageResource(R.drawable.ic_favorite_full_24dp);
                        Toast.makeText(How_to_xx.this, "좋아요", Toast.LENGTH_SHORT).show();
                        Log.e("??? Howtoxx 좋아용 체크", NAME);
                        SharedPreferences app2 = getSharedPreferences("좋아요체크" + part+Main2Activity.Loginname, 0);
                        SharedPreferences.Editor editor2 = app2.edit();
                        editor2.putBoolean(NAME, true);
                        editor2.apply();
                        if (isLiked2 == true) {

                        }
                    }

                    L++;
                }
                break;

            case R.id.addbtn:

                /**
                if(A%2 !=0)
                {
                    addbtn.setImageResource(R.drawable.ic_add_empty_24dp);
                    Toast.makeText(How_to_xx.this,"My Workout 에서 삭제되었습니다",Toast.LENGTH_SHORT).show();
                }
                else if(A%2==0) {
                    addbtn.setImageResource(R.drawable.ic_add_full_24dp);
                    Toast.makeText(How_to_xx.this,"My Workout 에 추가되었습니다",Toast.LENGTH_SHORT).show();
                }
                A++;
                 */

                break;

    }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isLiked2==true){
            Intent intent = new Intent(How_to_xx.this,LikedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

            if (isLiked3 == true) {
                if(isAdd==false) {
                Intent intent;
                switch (part) {
                    case "복근":
                        intent = new Intent(How_to_xx.this, Exercise_abs.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case "등":
                        intent = new Intent(How_to_xx.this, Exercise_back.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case "이두":
                        intent = new Intent(How_to_xx.this, Exercise_biceps.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case "가슴":
                        intent = new Intent(How_to_xx.this, Exercise_chest.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case "하체":
                        intent = new Intent(How_to_xx.this, Exercise_legs.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case "어깨":
                        intent = new Intent(How_to_xx.this, Exercise_shoulder.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case "삼두":
                        intent = new Intent(How_to_xx.this, Exercise_triceps.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }

            }
        }
        if(search){
//            Intent intent = new Intent(How_to_xx.this, SearchWorkout_Activity.class);
//            startActivity(intent);
        }
    }
}
