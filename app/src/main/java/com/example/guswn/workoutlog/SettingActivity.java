package com.example.guswn.workoutlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar settingtb;
    TextView profileedittxt;
    TextView contacttxt;
    CardView facebookcv;
    CardView twittercv;
    CardView logoutcv;
    CardView telcv;
    CardView locationcv;
    String ID;
    String PW;
    String NAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent =getIntent();


        settingtb = (Toolbar) findViewById(R.id.settingtb);

        profileedittxt = findViewById(R.id.profileedittxt);
        contacttxt = findViewById(R.id.contacttxt);
        facebookcv = findViewById(R.id.facebookcv);
        twittercv = findViewById(R.id.twittercv);
        logoutcv = findViewById(R.id.logoutcv);
        telcv = findViewById(R.id.telcv);
        locationcv = findViewById(R.id.locationcv);

        profileedittxt.setOnClickListener(this);
        contacttxt.setOnClickListener(this);
        facebookcv.setOnClickListener(this);
        twittercv.setOnClickListener(this);
        logoutcv.setOnClickListener(this);
        telcv.setOnClickListener(this);
        locationcv.setOnClickListener(this);



        setSupportActionBar(settingtb);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        //import android.support.v7.widget.Toolbar; 해줘야함
        //까다로운 새끼
        getSupportActionBar().setTitle("Settings");

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
    public void onClick(View v) {
        Intent intent;
        Uri uri;
        switch (v.getId()){
            case R.id.profileedittxt:
                intent = new Intent(SettingActivity.this,EditProfileActivity.class);

                startActivity(intent);

                break;
            case R.id.contacttxt:
                intent = new Intent(Intent.ACTION_SEND);

                try {
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"stayfitapp@gmail.com", "guswns3371@naver.com"});
                    //intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<font color = '#ff0000'>이메일 내용</font>"));
                    intent.setType("text/html");//"text/plain" MIME type
                    intent.setPackage("com.google.android.gm");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Help / Support / Other");
                    intent.putExtra(Intent.EXTRA_TEXT, "이메일 내용");
                   // Toast.makeText(SettingActivity.this, "됐어1", Toast.LENGTH_SHORT).show();

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        //startActivity()를 통해서 암시적인 인텐트를 보낼 때, 시스템 자체에서 그 인텐트를 처리 할 수 있는 어플리케이션이 없을 수 있다
                        //그래서 이 구문이 필요하다.
                        //resolveActivity()를 호출해서,
                        //해당 인텐트를 받을 수 있는 컴포넌트의 존재 유무를 확인해야 한다.

                        startActivity(intent);
                        //Toast.makeText(SettingActivity.this, "됐어2", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                   // Toast.makeText(SettingActivity.this,"됐어3",Toast.LENGTH_SHORT).show();
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"stayfitapp@gmail.com"});

                    startActivity(Intent.createChooser(intent,"send mail"));

                }


                break;
            case R.id.facebookcv:
                uri= Uri.parse("https://www.facebook.com/profile.php?id=100028963730142");// https://www.naver.com 를 입력해야 한다
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

                break;
            case R.id.twittercv:
                uri= Uri.parse("https://twitter.com/guswns3371");// https://www.naver.com 를 입력해야 한다
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

                break;
            case R.id.logoutcv:

                intent = new Intent(SettingActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.telcv:
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:02 1234 5678")));
                break;

            case R.id.locationcv:

                uri= Uri.parse("https://www.google.co.kr/maps/place/%ED%8C%80%EB%85%B8%EB%B0%94/@37.482991,126.972043,17z/data=!3m1!4b1!4m5!3m4!1s0x357ca03512ae7029:0xd800940224261675!8m2!3d37.482991!4d126.9742317?hl=ko");
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;

        }

    }
}
