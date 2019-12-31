package com.example.guswn.workoutlog;

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
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    EditText rUsernametxt;
    EditText rPasswordtxt;
    EditText rEmailtxt;
    EditText rIDtxt;
    EditText rEmailchecktxt;

    Button rIDcheckbtn;
    Button rEmailcheckbtn;
    Button Registerbtn;

    String username, id, password, email, emailcheck;
    Boolean saveEmailcheckdata, saveIDdata;
    //SharedPreferences appData,appData2;

    //SharedPreferences.Editor editor,editor2;
    Person person;
    Gson gson;
    //  String strPerson,strPerson2;
    ArrayList<Person> personArrayList = new ArrayList<>();

    final String TAG = "TAG";

    int a=0;
    int b=0;
    int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rUsernametxt = findViewById(R.id.rUsernametxt);
        rPasswordtxt = findViewById(R.id.rPasswordtxt);
        rEmailtxt = findViewById(R.id.rEmailtxt);
        rIDtxt = findViewById(R.id.rIDtxt);
        rEmailchecktxt = findViewById(R.id.rEmailchecktxt);

        rIDcheckbtn = findViewById(R.id.rIDcheckbtn);
        rEmailcheckbtn = findViewById(R.id.rEmailcheckbtn);
        Registerbtn = findViewById(R.id.Registerbtn);

        load();
        person = new Person();

        rIDcheckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                person.setName(rUsernametxt.getText().toString().trim());
                person.setId(rIDtxt.getText().toString().trim());
                Log.e("??? id",person.getId());
                Log.e("??? name",person.getName());
                id = rIDtxt.getText().toString().trim();
                // SharedPreferences appData = getSharedPreferences("register",0);
                //String strPerson = appData.getString("person","null");
                Gson gson= new Gson();
                String strPerson = gson.toJson(personArrayList);
                Log.e("??? 회원가입 id/name정보 (1)",strPerson);
                try{
                    JSONArray ja = new JSONArray(strPerson);
                    for (int i = 0; i < ja.length(); i++){
                        JSONObject order = ja.getJSONObject(i);

                        Log.e("??? 회원가입 중복확인 추출한 Id",order.getString("Id"));
                        Log.e("??? 회원가입 중복확인 비교할 Id",person.getId());
                        if(person.getId().equals(order.getString("Id"))){
                            b=10;
                            Log.e("??? 회원가입","이미 존재하는아이디 (1)");
                            //Toast.makeText(RegisterActivity.this, "이미 존재한 아이디입니다", Toast.LENGTH_SHORT).show();
                        }else if(!person.getId().equals(order.getString("Id"))) {
                            //Toast.makeText(RegisterActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                            Log.e("??? 회원가입","사용가능한 아이디 (1)");
                            a++;
                            c=11;
                        }
                    }

//                    if(b==10){
//                        Toast.makeText(RegisterActivity.this, "이미 존재한 아이디입니다", Toast.LENGTH_SHORT).show();
//                        Log.e("??? 회원가입","이미 존재하는아이디 (2)");
//                        b=0;
//                    }
//                    if(c==11 && b!=10){
//                        Toast.makeText(RegisterActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
//                        Log.e("??? 회원가입","사용가능한 아이디 (2)");
//                        c=0;
//                    }
                }
                catch (JSONException e){
                    Log.e("??? 뭐야 왜","안되잖아 ㅅㅂ 뭐야");
                }
                if(b==10){
                    Toast.makeText(RegisterActivity.this, "이미 존재한 아이디입니다", Toast.LENGTH_SHORT).show();
                    Log.e("??? 회원가입","이미 존재하는아이디 (2)");
                    b=0;
                }
                if(c==11 && b!=10){
                    Toast.makeText(RegisterActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                    Log.e("??? 회원가입","사용가능한 아이디 (2)");
                    c=0;
                }


            }
        });

        rEmailcheckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                person = new Person();
                person.setName(rUsernametxt.getText().toString().trim());
                person.setId(rIDtxt.getText().toString().trim());
                person.setEmail(rEmailtxt.getText().toString().trim());
                person.setPassword(rPasswordtxt.getText().toString().trim());
                //내가 만든 person 객체에 정보저장
//                personArrayList.add(person);
                Log.e("??? id2",person.getId());
                Log.e("??? name2",person.getName());
                Log.e("??? email2",person.getEmail());
                Log.e("??? password2",person.getPassword());

                if (!person.getEmail().isEmpty() && !person.getId().isEmpty() && !person.getName().isEmpty() && !person.getPassword().isEmpty()) {
                    SharedPreferences appData2 = getSharedPreferences("register",0);
                    String strPerson = appData2.getString("person","null");
                    if(!strPerson.equals("null"))  {//회원가입한적이 있는경우
                        if (a > 0) {//중복확인을 누른경우

                            if(b!=10) {// 중복확인을 눌렀는데, 존재하는 회원이 아니라면
                                try {
                                    personArrayList.add(person);
                                    save();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.e("??? 회원가입","회원가입성공");
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else {//중복확인을 눌렀는데, 존재하는 회원이라면
                                Toast.makeText(RegisterActivity.this, "아이디 중복확인이 필요합니다!!!", Toast.LENGTH_SHORT).show();
                                Log.e("??? 회원가입","중복확인을 눌렀는데, 존재하는 회원");
                                b=0;

                            }
                        } else if(a==0){//중복확인을 누르지 않은경우
                            Toast.makeText(RegisterActivity.this, "아이디 중복확인이 필요합니다", Toast.LENGTH_SHORT).show();
                            Log.e("??? 회원가입","중복확인을 누르지 않은경우");
                        }
                    }
                    else {//한번도 회원가입 한적 없을떄
                        try {
                            Log.e("??? 회원가입","회원가입이 처음인 기기");
                            personArrayList.add(person);
                            save();
                            Log.e("??? 회원가입","회원가입이 처음인 기기 - 회원가입성공");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent intent2 = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent2);
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "회원정보를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                    Log.e("??? 회원가입","회원정보를 모두 입력하지 않았다.");
                }
            }
        });
    }

    private  void load(){
        SharedPreferences  appData = getSharedPreferences("register", 0);
        gson = new Gson();
        String strPerson = appData.getString("person",null);
       // Log.e("??? 회원가입 id/name정보 (2)",strPerson);
        Type type = new TypeToken<ArrayList<Person>>(){}.getType();
        personArrayList = gson.fromJson(strPerson,type);
        if(personArrayList==null){
            personArrayList = new ArrayList<>();
        }
    }

    private void save() throws JSONException {
//        person = new Person();
//        person.setName(rUsernametxt.getText().toString().trim());
//        person.setId(rIDtxt.getText().toString().trim());
//        person.setEmail(rEmailtxt.getText().toString().trim());
//        person.setPassword(rPasswordtxt.getText().toString().trim());
//        //내가 만든 person 객체에 정보저장
//
//        personArrayList.add(person);

//        appData2 = getSharedPreferences("arraysize",0);
//        editor2 = appData2.edit();
//        editor2.putInt("size",personArrayList.size());
        //어레이리스트에 담는다

        SharedPreferences appData = getSharedPreferences("register", 0);
        //파일 이름을 register으로 지정해주고
        SharedPreferences.Editor  editor = appData.edit();
        //register 파일을 연다.
        gson = new Gson();
        String strPerson = gson.toJson(personArrayList);
        //쥐손으로 파싱
        editor.putString("person",strPerson);
        // editor.clear().commit();
        editor.apply();
        //apply가 commit보다 더 빠르게 저장된다. - 빠른이유
        //키는 person - value 값은 personArrayList의 정보가 담긴  String 객체 strPerson.
        //액티비티가 죽고 다시 시작되면 person키 에 새로운 정보가 덧씌어진다.

//        Map<String,?> keys = appData.getAll();
//        for(Map.Entry<String,?> entry : keys.entrySet()){
//            Log.e("fuckkk",entry.getKey() + ": " +
//                    entry.getValue().toString());
//        }
        //editor.commit();
        Log.e(TAG, appData.getString("person", "아무것도 업쇼어"));
    }
}

