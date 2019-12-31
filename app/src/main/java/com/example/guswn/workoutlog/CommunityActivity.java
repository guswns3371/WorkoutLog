package com.example.guswn.workoutlog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity implements CommunityAdapter.MyRecyclerViewClickListenerC {
    ArrayList<CommunityInfo> communityInfos = new ArrayList<>();
    Toolbar communitytb;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CommunityAdapter communityAdapter;
    ArrayList<String> list = new ArrayList<>();
    String strCommunity;
    SharedPreferences appData,appData2;
    SharedPreferences.Editor editor,editor2;
    ArrayList<String> keylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        communitytb = findViewById(R.id.communitytb);
//        SharedPreferences appData = getSharedPreferences("writing",0);
//        String strCommunity = appData.getString("writing_content","?");
//        //writing 파일은 Community_writeActivity 클래스에서 저장한 파일이다.
//        //writing 파일을 열어 그곳에 있는 writing_content의 벨류 값을 strCommunity에 저장한다.
//        //strCommunity에는 여러 게시글의 정보가 JsonArray 형태로 있다.(즉 ,여러개의 JsonObject가 저장되어있다)
        //실험

        //list = strCommunity.split()
        // Log.e("??? ///",strCommunity);

       // Toast.makeText(CommunityActivity.this,"onCreate",Toast.LENGTH_SHORT).show();
        load();
        // communityInfos = new ArrayList<>();
        //communityInfos.add(new CommunityInfo("name","reply","id","views","time"));

       makeKeylist();
        //밑에주석과 같은 코드의 메소드
//        for(int k =0; k<keylist.size(); k++){
//            String key = keylist.get(k);
//            String strValue = appData.getString(key,null);
//
//            if(strValue !=null) {
//                Log.e("??? /...KEY"+Integer.toString(k),key);
//                Log.e("??? /.,VALUE"+Integer.toString(k),strValue);
//                //JSONObject jsonObject = null;
//                try {
//                    JSONObject jsonObject = new JSONObject(strValue);
//                    String title2 = jsonObject.getString("title");
//                    String id2 = jsonObject.getString("id");
//                    String pw2 = jsonObject.getString("pw");
//                    String contents2 = jsonObject.getString("contents");
//                    String time2 = "";
//                    if (!jsonObject.getString("time").isEmpty()) {
//                        time2 = jsonObject.getString("time");
//                    }
//                    Log.e("??? MYKEY",id2+"/"+pw2+"/"+contents2+"/"+title2+"/"+time2);
//                    communityInfos.add(new CommunityInfo(title2, "reply개수", id2, "views 개수", time2));
//                    //insertItem(title2,"reply개수2",id2,"views 개수2",time2);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }

        mRecyclerView = findViewById(R.id.communityRV);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        communityAdapter = new CommunityAdapter(communityInfos);
        communityAdapter.setOnClickListener(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(communityAdapter);


//        if(strCommunity!=null){
//            try{
//                String result = "";
//                JSONArray ja = new JSONArray(strCommunity);
//                for (int i = 0; i < ja.length(); i++){
//                    JSONObject order = ja.getJSONObject(i);
//                    //이를  JSONArray ja = new JSONArray(strCommunity);으로 제이슨 어레이에 넣어
//                    // 반복문을 이용하여  JSONObject order = ja.getJSONObject(i);처럼 제이슨 오브젝트에 제이슨어레이[i]값을 넣는다.
//                    Log.e("??? ,,,",order.toString());
//                    list.add(order.toString());
//                    //order.toString()는 제이슨 오브젝트이다. 이를 어레이리스트 list에 담는다.(list는 String을 담는 어레이 리스트)
//                    //이제 밑으로 가봐봐
//
////                result = "title: " + order.getString("title") + ", id: " + order.getString("id") +
////                        ", pw: " + order.getString("pw") + ", contents: " + order.getString("contents")+
////                        ", time: " + order.getString("time")+"\n";
////                Log.e("??? KKK"+Integer.toString(i),result);
//                    String title = order.getString("title");
//                    String id = order.getString("id");
//                    String pw = order.getString("pw");
//                    String contents = order.getString("contents");
//                    String time="";
//                    if(!order.getString("time").isEmpty()) {
//                        time = order.getString("time");
//                    }
//                    Log.e("??? ###",id+"/"+pw+"/"+contents+"/"+title+"/"+time);
//                    communityInfos.add(new CommunityInfo(title,"reply개수",id,"views 개수",time));
//                    //insertItem(title,"reply개수",id,"views 개수",time);
//                }
//
//            }
//            catch (JSONException e){ ;}
//
//
//            SharedPreferences appData2 = getSharedPreferences("writing_content2",0);
//            SharedPreferences.Editor editor2 = appData2.edit();
//            //여기서 writing_content2라는 파일을 만들고 연다
//            //이 파일은 밑에 초록색 주석있는 곳에서 다시 열것이다.
//            for(int j =0 ; j<list.size(); j++){
//
//                editor2.putString(Integer.toString(j),list.get(j));
//                //어레이리스트 list의 인덱스값 i 를 키값으로 정하고
//                // 벨류값에 list.get(j) 정보를 넣는다.
//                Log.e("??? >>>",list.get(j));
//
//            }editor2.apply();
//            //그리고 저장한다. 이거 안해주는 실수 하지마 ㅄ아 좀 이딴거에 실수 하지마
//            //이 저장된 값은 밑에서 예쁘게 포장한다. 밑으로 가봐 초록색 주석있는곳으로
//        }
//
//        /**  이제 어레이 리스트 list 로 잘 조절하면 된다.
//         * list에 각각 인덱스에맞게 게시물이 정보가 담긴다.
//         * 로드와 세이브로 리스트의 크기는 알아서 조절된다.*/


//        save();

        setSupportActionBar(communitytb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);


//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavbar_community);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Intent intent;
//                switch (item.getItemId()) {
//                    case R.id.btm_search:
//                       // Toast.makeText(CommunityActivity.this, "History", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.btm_write:
//                        save();
//                       // Toast.makeText(CommunityActivity.this, "Workout", Toast.LENGTH_SHORT).show();
//                        intent = new Intent(CommunityActivity.this,Community_writeActivity.class);
//                        ////////////////////////////////////
//                        //intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        ////////////////////////////////////
//                        startActivity(intent);
//                        break;
//                }
//                return true;
//            }
//        });

    }



    public void insertItem(String name, String reply, String id, String views, String time){
        communityInfos.add(new CommunityInfo(name,reply,id,views,time));
        communityAdapter.notifyDataSetChanged();
    }

    public void removeItem(int position){
        communityInfos.remove(position);
        communityAdapter.notifyDataSetChanged();
    }

    public  void editItem(int position, String name, String reply, String id, String views, String time){
        communityInfos.set(position,new CommunityInfo(name,reply,id,views,time));
        communityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
      //  Toast.makeText(CommunityActivity.this,"onStart",Toast.LENGTH_SHORT).show();
        //load();
       // makeKeylist();

//        load();
//
//        SharedPreferences app = getSharedPreferences("writing",0);
//        for(int k =0; k<keylist.size(); k++){
//            String key = keylist.get(k);
//            String strValue = app.getString(key,null);
//
//            if(strValue !=null) {
//                Log.e("??? /., KEY"+Integer.toString(k),key);
//                Log.e("??? /.,VALUE"+Integer.toString(k),strValue);
//                //JSONObject jsonObject = null;
//                try {
//                    JSONObject jsonObject = new JSONObject(strValue);
//                    String title2 = jsonObject.getString("title");
//                    String id2 = jsonObject.getString("id");
//                    String pw2 = jsonObject.getString("pw");
//                    String contents2 = jsonObject.getString("contents");
//                    String time2 = "";
//                    if (!jsonObject.getString("time").isEmpty()) {
//                        time2 = jsonObject.getString("time");
//                    }
//                    Log.e("??? VALUE//"+Integer.toString(k),id2+"/"+pw2+"/"+contents2+"/"+title2+"/"+time2);
//                    communityInfos.add(new CommunityInfo(title2, "reply개수", id2, "views 개수", time2));
//                    //insertItem(title2,"reply개수2",id2,"views 개수2",time2);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.community_activity_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.community_main_write:
                save();
                Intent intent = new Intent(CommunityActivity.this,Community_writeActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(int position) {
       // Toast.makeText(this,"아이템"+position,Toast.LENGTH_SHORT).show();
        /**       SharedPreferences data = getSharedPreferences("content",0);
         SharedPreferences.Editor editor = data.edit();
         Gson gson = new Gson();
         String strPerson = gson.toJson(communityInfos.get(position));
         //쥐손으로 파싱
         editor.putString(communityInfos.get(position).getId(),strPerson);
         editor.apply();
         */


//        //여기!!
//        SharedPreferences data = getSharedPreferences("writing_content2",0);
//        //writing_content2 파일을 연다.
//
//        String s =data.getString(Integer.toString(position),"없어");
//
//        //writing_content2 파일에는 키가 어레이리스트인덱스 i 이고 벨류가 인덱스 i 에 해당한 정보이다.
//        //즉, 0 - list.get(0) , 1 - list.get(1) ... 이런식이다. 진짜 대박 고생했어 현준아 ㅠㅠ
//        //대신 이함수는 리사이클러뷰의 포지션값에 해당하는 키 에 저장된 벨류값을 꺼낸다.
//        // 다시말해 특정 리사이클러뷰 아이템에 저장된 정보를 스트링 변수 s 에 저장한다는 의미다.
//        Log.e("??? MMM",s);


       SharedPreferences sp = getSharedPreferences("writing",0);
       String k= sp.getString(keylist.get(position),null);
       if(k!=null){
           try {
               JSONObject jo = new JSONObject(k);
               String id, title, content,time,pw;
               id = jo.getString("id");
               title = jo.getString("title");
               content = jo.getString("contents");
               time = jo.getString("time");
               pw =jo.getString("pw");
               Intent intent = new Intent(CommunityActivity.this,Community_Inner.class);
              // intent.putExtra("positon_content",s);
               intent.putExtra("id",id);
               intent.putExtra("title",title);
               intent.putExtra("content",content);
               intent.putExtra("time",time);
               intent.putExtra("pw",pw);

               intent.putExtra("goodkey",keylist.get(position));
              // Toast.makeText(CommunityActivity.this,keylist.get(position)+"/"+position,Toast.LENGTH_SHORT).show();
               //이제 스트링 변수 s 를 Community_Inner 액티비티에 인텐트로 전달한다.
               startActivity(intent);
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }

//
//        Intent intent = new Intent(CommunityActivity.this,Community_Inner.class);
//        intent.putExtra("positon_content",s);
//        intent.putExtra("id",id)
//        Toast.makeText(CommunityActivity.this,keylist.get(position)+"/"+position,Toast.LENGTH_SHORT).show();
//        //이제 스트링 변수 s 를 Community_Inner 액티비티에 인텐트로 전달한다.
//        startActivity(intent);

    }

    @Override
    public void onMoreButtonClicked(final int position, View v) {
        final PopupMenu p = new PopupMenu(getApplicationContext(),v);
        //저 v를 가져오기 위해선
        // 어밷벝의 인터페이스 메소드 onMoreButtonClicked의 파라미터값을 더 줘야 한다.
        getMenuInflater().inflate(R.menu.workout_menu,p.getMenu());

        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.edititem)
                {
                    Log.e("??? 이것의 KEY값은" , keylist.get(position));
                    Log.e("??? 이것의 POSITION 값은" ,Integer.toString(position));
                }
                if(item.getItemId()==R.id.deleteitem)
                {
                    Toast.makeText(CommunityActivity.this,"삭제"+position,Toast.LENGTH_SHORT).show();
                    Log.e("??? 삭제하기!" , keylist.get(position));

                    SharedPreferences data = getSharedPreferences("writing",0);
                    SharedPreferences.Editor editor = data.edit();
                    editor.remove(keylist.get(position));
                    editor.apply();
                    keylist.remove(position);// 키값이 든 어레이 리스트 삭제 근데 반영이 안됨 -> 한번 static으로?
                    removeItem(position);// 리사이클러뷰 삭제


//                    list.remove(position);
//                    appData2 = getSharedPreferences("writing",0);
//                    editor2 = appData2.edit();
//                    Gson gson = new Gson();
//                    String newContent = gson.toJson(list);
//                    editor2.putString("writing_content",newContent);
//                    editor2.apply();
//                    //여기서 원본 을 바꿔야함.
//
//                    save();
                }
                return false;
            }
        });
        p.show();

    }



    public void load(){
        appData = getSharedPreferences("writing",0);
        strCommunity = appData.getString("writing_content",null);
       // Log.e("??? 222",strCommunity);
        //writing 파일은 Community_writeActivity 클래스에서 저장한 파일이다.
        //writing 파일을 열어 그곳에 있는 writing_content의 벨류 값을 strCommunity에 저장한다.
        //strCommunity에는 여러 게시글의 정보가 JsonArray 형태로 있다.(즉 ,여러개의 JsonObject가 저장되어있다)
        if(strCommunity==null){
            communityInfos = new ArrayList<>();
            Log.e("??? 888","strCommunity==null");
        } else if(strCommunity!=null ){
            try{
                String result = "";
                JSONArray ja = new JSONArray(strCommunity);
                for (int i = 0; i < ja.length(); i++){
                    JSONObject order = ja.getJSONObject(i);
                    //이를  JSONArray ja = new JSONArray(strCommunity);으로 제이슨 어레이에 넣어
                    // 반복문을 이용하여  JSONObject order = ja.getJSONObject(i);처럼 제이슨 오브젝트에 제이슨어레이[i]값을 넣는다.
                    Log.e("??? ,,,"+Integer.toString(i),order.toString());
                    list.add(order.toString());

                    //order.toString()는 제이슨 오브젝트이다. 이를 어레이리스트 list에 담는다.(list는 String을 담는 어레이 리스트)
                    //이제 밑으로 가봐봐

//                result = "title: " + order.getString("title") + ", id: " + order.getString("id") +
//                        ", pw: " + order.getString("pw") + ", contents: " + order.getString("contents")+
//                        ", time: " + order.getString("time")+"\n";
//                Log.e("??? KKK"+Integer.toString(i),result);
                    String title = order.getString("title");
                    String id = order.getString("id");
                    String pw = order.getString("pw");
                    String contents = order.getString("contents");
                    String time = order.getString("time");

                    Log.e("??? MAIN KEY",id+"/"+pw+"/"+title+"/"+contents+"/"+time);

                    String s =appData.getString(id+pw+title+contents+time,null);
                    if(s!=null) {
                        keylist.add(id + pw + title + contents + time);
                    }
                    Log.e("??? <><>< KEYSIZE",Integer.toString(keylist.size()));
                    /** ////////////////////////////////////////////////////////// */

                   // String string = appData.getString(id+pw+title+contents+time,null);
/**
                    if(string !=null) { Log.e("??? /.,mnbvc",string);
                        JSONObject jsonObject = new JSONObject(string);
                        String title2 = jsonObject.getString("title");
                        String id2 = jsonObject.getString("id");
                        String pw2 = jsonObject.getString("pw");
                        String contents2 = jsonObject.getString("contents");
                        String time2 = "";
                        if (!jsonObject.getString("time").isEmpty()) {
                            time2 = jsonObject.getString("time");
                        }
                        Log.e("??? qweqrty",id2+"/"+pw2+"/"+contents2+"/"+title2+"/"+time2);
                        communityInfos.add(new CommunityInfo(title2, "reply개수", id2, "views 개수", time2));
                        //insertItem(title2,"reply개수2",id2,"views 개수2",time2);
                    }
*/
                    //communityInfos.add(new CommunityInfo(title,"reply개수",id,"views 개수",time));
                    //insertItem(title,"reply개수",id,"views 개수",time);
                }

            }
            catch (JSONException e){ ;}


            SharedPreferences appData2 = getSharedPreferences("writing_content2",0);
            SharedPreferences.Editor editor2 = appData2.edit();
            //여기서 writing_content2라는 파일을 만들고 연다
            //이 파일은 밑에 초록색 주석있는 곳에서 다시 열것이다.
            for(int j =0 ; j<list.size(); j++){

                editor2.putString(Integer.toString(j),list.get(j));
                //어레이리스트 list의 인덱스값 i 를 키값으로 정하고
                // 벨류값에 list.get(j) 정보를 넣는다.
                Log.e("??? >>>",list.get(j));
                Log.e("??? >>> List size",Integer.toString(list.size()));

            }editor2.apply();
            //그리고 저장한다. 이거 안해주는 실수 하지마 ㅄ아 좀 이딴거에 실수 하지마
            //이 저장된 값은 밑에서 예쁘게 포장한다. 밑으로 가봐 초록색 주석있는곳으로
        }
        /**  이제 어레이 리스트 list 로 잘 조절하면 된다.
         * list에 각각 인덱스에맞게 게시물이 정보가 담긴다.
         * 로드와 세이브로 리스트의 크기는 알아서 조절된다.*/
    }
    public void save(){
        appData = getSharedPreferences("writing",0);
        editor = appData.edit();
        // Gson gson = new Gson();
        String s = appData.getString("writing_content",null);
        // String s = gson.toJson(list);
        editor.putString("writing_content",s);
        editor.apply();
    }

    public void makeKeylist(){
        for(int k =0; k<keylist.size(); k++){
            String key = keylist.get(k);
            String strValue = appData.getString(key,null);

            if(strValue !=null) {
                Log.e("??? /...KEY"+Integer.toString(k),key);
                Log.e("??? /.,VALUE"+Integer.toString(k),strValue);
                //JSONObject jsonObject = null;
                try {
                    JSONObject jsonObject = new JSONObject(strValue);
                    String title2 = jsonObject.getString("title");
                    String id2 = jsonObject.getString("id");
                    String pw2 = jsonObject.getString("pw");
                    String contents2 = jsonObject.getString("contents");
                    String time2 = "";
                    if (!jsonObject.getString("time").isEmpty()) {
                        time2 = jsonObject.getString("time");
                    }
                    Log.e("??? MYKEY",id2+"/"+pw2+"/"+contents2+"/"+title2+"/"+time2);
                    communityInfos.add(new CommunityInfo(title2, "[1]", "ID : "+id2, "조회수 :", time2));
                    //insertItem(title2,"reply개수2",id2,"views 개수2",time2);
                } catch (JSONException e) {
                    Log.e("??? fuck","뭔가 문제가 있어");
                    e.printStackTrace();
                }
            }
        }
    }
}
