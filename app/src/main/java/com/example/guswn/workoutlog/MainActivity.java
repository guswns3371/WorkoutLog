package com.example.guswn.workoutlog;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    MainActivity m_oMainActivity;
    Button mLoginbtn;
    TextView mRegistertxt;
    String NAME;
    String ID;
    String PW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_oMainActivity = this;

        mLoginbtn = findViewById(R.id.mLoginbtn);
        mRegistertxt = findViewById(R.id.mRegistertxt);
        mLoginbtn.setOnClickListener(this);
        mRegistertxt.setOnClickListener(this);

        SharedPreferences appdata = getSharedPreferences("오늘의운동",0);
        SharedPreferences.Editor editor = appdata.edit();
        if(!appdata.getString("오늘의운동들","null").equals("null")) {
            editor.remove("오늘의운동들");
            editor.apply();
        }

        checkVerify();
    }


    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId())
        {
            case R.id.mLoginbtn:
                 intent = new Intent(MainActivity.this,LoginActivity.class);

                startActivity(intent);

                break;

            case R.id.mRegistertxt:
                 intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }


    public void checkVerify()
    {
        if (
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                )
        {
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                // ...
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
        else
        {
            Log.e("??? MainActivity","뭐지?");
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1)
        {
            if (grantResults.length > 0)
            {
                for (int i=0; i<grantResults.length; ++i)
                {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    {
                        // 하나라도 거부한다면.
                        new AlertDialog.Builder(this).setTitle("알림").setMessage("권한을 허용해주셔야 앱을 이용할 수 있습니다.")
                                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        m_oMainActivity.finish();
                                    }
                                }).setNegativeButton("권한 설정", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                                getApplicationContext().startActivity(intent);
                            }
                        }).setCancelable(false).show();

                        return;
                    }
                }

            }
        }
    }



}
