package com.example.guswn.workoutlog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Community_writeActivity extends AppCompatActivity {

    Toolbar community_writetb;
    EditText com_idtxt;
    EditText com_pwtxt;
    EditText com_nametxt;
    EditText com_contenttxt;
    String id,pw,name,content;
    String DATE,TIME;
    SharedPreferences appData,appData2;
    SharedPreferences.Editor editor,editor2;
    Community_Contents_Info communityContentsInfo;
    ArrayList<Community_Contents_Info> community_contents_infos_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_write);

        community_writetb = findViewById(R.id.community_writetb);
        com_idtxt = findViewById(R.id.com_idtxt);
        com_pwtxt = findViewById(R.id.com_pwtxt);
        com_nametxt = findViewById(R.id.com_nametxt);
        com_contenttxt = findViewById(R.id.com_contenttxt);
        com_contenttxt.setHorizontallyScrolling(false);

        com_idtxt.setEnabled(false);
        com_pwtxt.setEnabled(false);
        setSupportActionBar(community_writetb);
        SharedPreferences app = getSharedPreferences("회원정보",0);
        String id = app.getString("회원ID",null);
        String pw = app.getString("회원PW",null);
        Log.e("??? id / pw",id+"/"+pw);
        if(id!=null && pw!=null){
            com_idtxt.setText(id);
            com_pwtxt.setText(pw);
        }
//        load();
//        load2();
        //onStart에서 해줌
        communityContentsInfo = new Community_Contents_Info();

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
        DATE = date.format(today);
        TIME = time.format(today);
    }

    @Override
    protected void onStart() {
        super.onStart();
        load();
        load2();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.community_write_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_community_write:
                //Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                id = com_idtxt.getText().toString();
                pw = com_pwtxt.getText().toString();
                name = com_nametxt.getText().toString();
                content = com_contenttxt.getText().toString();
                //TIME
                //id+pw+name+content
                communityContentsInfo.setId(id);
                communityContentsInfo.setPw(pw);
                communityContentsInfo.setContents(content);
                communityContentsInfo.setTitle(name);
                communityContentsInfo.setTime(TIME);
                communityContentsInfo.setReply("?");
                if(!communityContentsInfo.getId().isEmpty() && !communityContentsInfo.getContents().isEmpty()
                        && !communityContentsInfo.getPw().isEmpty() && !communityContentsInfo.getTitle().isEmpty()){
                    try {
                        community_contents_infos_list.add(communityContentsInfo);

                        save2();
                        save();

                        Intent intent = new Intent(Community_writeActivity.this,CommunityActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Community_writeActivity.this, "정보를 모두 입력해야합니다", Toast.LENGTH_SHORT).show();
                }

                return true;

            default:
                // Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }

    Gson gson;
    private  void load(){
        appData = getSharedPreferences("writing", 0);//writing 파일안에 writing_content키값이 존재
        gson = new Gson();
        String strPerson = appData.getString("writing_content",null);
        //writing_content키값은 community_contents_infos_list의 정보가담겨있다
        Type type = new TypeToken<ArrayList<Community_Contents_Info>>(){}.getType();
       // Log.e("??? ...",strPerson);
        community_contents_infos_list = gson.fromJson(strPerson, type);

        if(community_contents_infos_list==null){
            community_contents_infos_list = new ArrayList<>();
        }
    }
    private void save() throws JSONException {
        appData = getSharedPreferences("writing", 0);
        //파일 이름을 register으로 지정해주고.
        editor = appData.edit();
        //register 파일을 연다.
        gson = new Gson();
        String strPerson = gson.toJson(community_contents_infos_list);
        //쥐손으로 파싱.


        editor.putString("writing_content",strPerson);


        //키가 writing_content이고 value 는 쥐손으로 파싱한 스트링 값 strPerson 이 담긴다.
        //새롭게 save 할떄마다 strPerson에는 전에 있던 정보와 함께 추가된다.
        //strPerson에는 각각 게시글의 contents,id,pw,title 정보가 한꺼번에 저장되어있다
        //이정보를 CommunityActivity 클래스에서 열어야한다.


        //editor.clear().commit();
        editor.apply();
        Log.e("??? www",appData.getString("writing_content","널널널"));
    }


    private void load2(){
        appData2 = getSharedPreferences("writing", 0);
        String str2 = appData2.getString(id+pw+name+content+TIME,null);
        Gson gson2 = new Gson();
        for(int i =0 ; i<community_contents_infos_list.size(); i++) {
            String str = gson2.toJson(community_contents_infos_list.get(i));
            Log.e("??? write -load2-VALUE"+Integer.toString(i),str);
        }
//        if(str2 == null){//맨처음 로드 되었을때
//            community_contents_infos_list = new ArrayList<>();
//        }
    }

    private void save2()throws JSONException{
        appData2 = getSharedPreferences("writing", 0);
        editor2 = appData2.edit();
        Gson gson1 = new Gson();
        for(int i =0 ; i<community_contents_infos_list.size(); i++) {
            String str = gson1.toJson(community_contents_infos_list.get(i));

            editor2.putString(id + pw + name + content + TIME, str);

            Log.e("??? write-save2-VALUE"+Integer.toString(i),str);
        }
        //editor2.clear().commit();
       editor2.apply();
//
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("저장하지 않고 나가시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Community_writeActivity.this, CommunityActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
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
}
