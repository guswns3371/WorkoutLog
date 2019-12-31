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

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Community_Edit extends AppCompatActivity {
    Toolbar community_writetb2;
    EditText com_nametxt2;
    EditText com_contenttxt2;
    String key;
    String DATE,TIME;
    String edittitle,editcontents;
    String id,pw;

    Community_Contents_Info EditcommunityContentsInfo = new Community_Contents_Info();
    ArrayList<Community_Contents_Info> Editlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_edit);
        com_nametxt2 = findViewById(R.id.com_nametxt2);
        com_contenttxt2 = findViewById(R.id.com_contenttxt2);
        community_writetb2 = findViewById(R.id.community_writetb2);
        setSupportActionBar(community_writetb2);

        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
        DATE = date.format(today);
        TIME = time.format(today);


        Intent intent = getIntent();
        key = intent.getStringExtra("key값");// 수정할 정보의 키 값을 갖고
        Log.e("??? 수정할 정보의 KEY값",key);


        SharedPreferences sp = getSharedPreferences("writing",0);
        String val =sp.getString(key,null);
        try {
            JSONObject jo = new JSONObject(val);
            String title,contents,time2;
            title =jo.getString("title");
            contents = jo.getString("contents");
            time2 = jo.getString("time");
            id= jo.getString("id");
            pw = jo.getString("pw");
            if(title.contains("#")){
                title= title.replaceAll("# ", "");
            }
            Log.e("??? 변경전 정보","contents ="+contents+"/ id ="+id+"/ pw ="+pw+"/ time ="+time2+"/ title="+title);
            com_nametxt2.setText(title);
            com_contenttxt2.setText(contents);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                //수정이 완료되었을때 누른다.
                edittitle="# "+com_nametxt2.getText().toString();
                editcontents=com_contenttxt2.getText().toString();
                EditcommunityContentsInfo.setTime(DATE);
                EditcommunityContentsInfo.setTitle(edittitle);
                EditcommunityContentsInfo.setContents(editcontents);
                EditcommunityContentsInfo.setId(id);
                EditcommunityContentsInfo.setPw(pw);
                Editlist.add(EditcommunityContentsInfo);
                Gson gson = new Gson();
                String editStr = gson.toJson(EditcommunityContentsInfo);
                Log.e("??? 변경된 정보",editStr);

                SharedPreferences sp2 = getSharedPreferences("writing",0);
                SharedPreferences.Editor editor2 = sp2.edit();
                editor2.putString(key,editStr);
                editor2.apply();
                Intent intent = new Intent(Community_Edit.this,CommunityActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;

            default:
                // Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("수정하지 않고 나가시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
