package com.example.guswn.workoutlog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Context context;
    EditText IIDtxt;
    EditText lPasswordtxt;
    Button Loginbtn;


    String ID;
    String PW;

    int a,b;
    int c=0;
    Gson gson;
    String strPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        IIDtxt = findViewById(R.id.IIDtxt);
        lPasswordtxt = findViewById(R.id.lPasswordtxt);
        Loginbtn = findViewById(R.id.Loginbtn);

        SharedPreferences appData = getSharedPreferences("register",0);
        strPerson = appData.getString("person","null");


        Log.e("??? 넘어온값 : 회원정보",strPerson);

        try{
            String result = "";
            JSONArray ja = new JSONArray(strPerson);
            for (int i = 0; i < ja.length(); i++){
                JSONObject order = ja.getJSONObject(i);
                result = "Email: " + order.getString("Email") + ", Id: " + order.getString("Id") +
                        ", Name: " + order.getString("Name") + ", Password: " + order.getString("Password")+"\n";
                Log.e("??? 풀어헤친 회원정보"+Integer.toString(i),result);
            }

        }
        catch (JSONException e){ ;}



        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(IIDtxt.getText().toString().equals("") || lPasswordtxt.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this,"이름과 비밀번호를 모두 입력하세요",Toast.LENGTH_SHORT).show();
                    Log.e("??? 로그인","이름과 비밀번호를 모두 입력하세요");
                }
                if(!IIDtxt.getText().toString().equals("") && !lPasswordtxt.getText().toString().equals("")) {

                    ID = IIDtxt.getText().toString();
                    PW = lPasswordtxt.getText().toString();


                    if(!strPerson.equals("null"))
                    {
                    try {
                        JSONArray ja = new JSONArray(strPerson);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject order = ja.getJSONObject(i);
                            if (ID.equals(order.getString("Id"))) {
                                if (PW.equals(order.getString("Password"))) {
                                    Log.e("??? 로그인", "로그인성공" + "id :" + ID + "pw :" + PW);
                                    a = 0;
                                    b = 0;
                                    c = 10;
                                    Intent intent = new Intent(LoginActivity.this, Main2Activity.class);

                                    SharedPreferences app = getSharedPreferences("회원정보", 0);
                                    SharedPreferences.Editor editor = app.edit();
                                    editor.putString("회원ID", ID);
                                    editor.putString("회원PW", PW);
                                    editor.apply();

                                    Log.e("??? abc", Integer.toString(a) + Integer.toString(b) + Integer.toString(c));
                                    intent.putExtra("Loginname", IIDtxt.getText().toString());
                                    startActivity(intent);
                                } else {
                                    //Toast.makeText(LoginActivity.this,"이름과 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                                    Log.e("??? 로그인", "이름과 비밀번호가 일치하지 않습니다" + Integer.toString(i));
                                    a = 10;
                                    b = 0;
                                }
                            } else {
//                                Toast.makeText(LoginActivity.this,"아이디와 비밀번호를 확인하세요",Toast.LENGTH_SHORT).show();
                                Log.e("??? 로그인", "존재하지 않는 아이디입니다." + Integer.toString(i));
                                b = 10;
                            }

                        }

                        if (c != 10) {
                            if (a == 10) {
                                Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                                Log.e("??? 로그인", "아이디와 비밀번호를 확인하세요==");
                                a = 0;
                            }
                            if (b == 10) {
                                Toast.makeText(LoginActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                Log.e("??? 로그인", "존재하지 않는 아이디입니다.==");
                                b = 0;
                            }
                            c = 0;
                        }
                        // Toast.makeText(LoginActivity.this,"아이디와 비밀번호를 확인하세요",Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        ;
                    }


                } else if(strPerson.equals("null")){
                        Toast.makeText(LoginActivity.this, "회원가입이 안된 기기입니다.", Toast.LENGTH_SHORT).show();
                        Log.e("??? 로그인", "회원가입한적이 없음");
                    }
                }
            }
        });



//
//        Intent intent = getIntent();
//        boolean kill = intent.getBooleanExtra("kill",false);
//
//        if(kill){
//            Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
//            intent1.putExtra("kill2",true);
//            startActivity(intent1);
//
//        }

    }
}
