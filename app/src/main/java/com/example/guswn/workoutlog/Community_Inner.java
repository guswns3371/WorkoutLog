package com.example.guswn.workoutlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class Community_Inner extends AppCompatActivity implements Community_reply_Adapter.ReplyRecyclerClickListner{

    Toolbar community_innertb;
    TextView inner_titletxt;
    TextView inner_idtxt;
    TextView inner_timetxt;
    TextView inner_viewtxt;
    TextView inner_replytxt;
    TextView inner_contenttxt;
    EditText inner_relpy_edittxt;
    Button replybtn;
    String contents,title,id,pw,time;
    String positon_;
    String key,goodkey;
    String DATE,TIME,NOW;
    String replycontent;
    String Rid;
    ArrayList<Communit_reply_Info> communitReplyInfos;
    Communit_reply_Info replyInfo;
    RecyclerView replyRecylcerview;
    RecyclerView.LayoutManager replyLayoutManager;
    Community_reply_Adapter replyAdapter;

    SharedPreferences appdata2;
    SharedPreferences.Editor editor2;
    ArrayList<String> RkeyList = new ArrayList<>();



    String memberid;
    String memberimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community__inner);

        community_innertb = findViewById(R.id.community_innertb);
        inner_titletxt = findViewById(R.id.inner_titletxt);
        inner_idtxt = findViewById(R.id.inner_idtxt);
        inner_timetxt = findViewById(R.id.inner_timetxt);
        inner_viewtxt = findViewById(R.id.inner_viewtxt);
        inner_replytxt = findViewById(R.id.inner_replytxt);
        inner_contenttxt = findViewById(R.id.inner_contenttxt);

        setSupportActionBar(community_innertb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        inner_relpy_edittxt = findViewById(R.id.inner_relpy_edittxt);
        replybtn = findViewById(R.id.replybtn);
        //로그인한 사람의 아이디 받아오기
        SharedPreferences app =getSharedPreferences("회원정보",0);
        memberid = app.getString("회원ID",null);
       //로그인한 사람의 아이디 받아오기

        //찍은사진 받아오기
        SharedPreferences appDATA = getSharedPreferences("회원사진"+Main2Activity.Loginname,0);
        final String imagedata = appDATA.getString("회원사진정보","null");
        String cam_or_gall = appDATA.getString("사진유형","null");
        String k =appDATA.getString("PIC","null");
        Log.e("??? 회원사진정보",imagedata);
        Log.e("??? 사진유형",cam_or_gall);
        if(cam_or_gall.equals("카메라")){
            memberimg=imagedata;
        }else if(cam_or_gall.equals("갤러리")){
            memberimg=imagedata;
        }else if(cam_or_gall.equals("null")){
//            if(!k.equals("null"))
//            {
//                memberimg = k;
//            }else
// {
                Log.e("??? 받아온이미지", "아무것도 없어");
                memberimg = "drawable://" + R.drawable.ic_android_green_24dp;
                //기본 이미지가 출력이 안됨
                Uri pathuri = Uri.parse("android.resource://com.example.guswn.workoutlog/" + R.drawable.ic_android_green_24dp);
                // Uri otherPath = Uri.parse("android.resource://com.example.guswn.workoutlog/drawable/ic_android_green_24dp");

                String path = pathuri.toString();
                //String path2 = otherPath .toString();
//              memberimg = path;
            memberimg = "";
          //  }
        }else if(cam_or_gall==null){
            Log.e("??? 받아온이미지","이미지 정보가 sp에 저장되지 않았어");
        }

        replybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inner_relpy_edittxt.getText().toString().isEmpty()) {
                    Date today = new Date();
                    SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat time2 = new SimpleDateFormat("hh:mm:ss a");
                    DATE = date.format(today);
                    TIME = time2.format(today);
                    NOW = DATE+"  "+TIME;
                    replycontent = inner_relpy_edittxt.getText().toString();

                    insertItem3(Communit_reply_Info.B_TYPE, memberimg, "ID : "+memberid, NOW, replycontent, "num", "", "", "");
                    inner_relpy_edittxt.setText(null);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inner_relpy_edittxt.getWindowToken(), 0);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            replyRecylcerview.scrollToPosition(replyAdapter.getItemCount() - 1);
                        }
                    }, 200);
                    //이제 각 댓글의 정보를 SP에 저장해줘야한다.
                     appdata2 =getSharedPreferences("댓글"+key,0);//댓글정보가 담길 폴더명
                     editor2 = appdata2.edit();
                    Replymodel model = new Replymodel(memberimg,memberid,replycontent,NOW);
                    Gson gson = new Gson();
                    String strmodel = gson.toJson(model);
                    String Rkey = memberid+replycontent+NOW;   Log.e("??? Rkey",Rkey);

                    RkeyList.add(Rkey);//키 name 이 저장됨
                    Gson gson1 = new Gson();
                    String rkeylist = gson.toJson(RkeyList);
                    editor2.putString("댓글키들",rkeylist);
                    editor2.putString(Rkey,strmodel);
                    editor2.apply();// strmodel 정보는 load할 필요가 없다.

                    Log.e("??? 댓글키들",appdata2.getString("댓글키들",null));
                    Log.e("??? 저장후 RkeyList사이즈",Integer.toString(RkeyList.size()));


                }
            }
        });


        communitReplyInfos = new ArrayList<>();

        replyRecylcerview = findViewById(R.id.replyRecylcerview);
        replyRecylcerview.setHasFixedSize(true);
        replyLayoutManager = new LinearLayoutManager(this);
        replyAdapter = new Community_reply_Adapter(communitReplyInfos,this);
        replyRecylcerview.setLayoutManager(replyLayoutManager);
        replyRecylcerview.setAdapter(replyAdapter);
        replyAdapter.setOnClickListener_reply(this);
        setSupportActionBar(community_innertb);

        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        contents = intent.getStringExtra("content");
        time = intent.getStringExtra("time");
        pw = intent.getStringExtra("pw");
//        communitReplyInfos.add(new Communit_reply_Info(Communit_reply_Info.A_TYPE,imagedata,"ID : "+id,TIME,contents,"likecount","viewcount","replycount","제목 : "+title));
        communitReplyInfos.add(new Communit_reply_Info(Communit_reply_Info.A_TYPE,imagedata,"ID : "+id,TIME,contents,"likecount","viewcount","댓글 ??:"+Integer.toString(RkeyList.size()),"제목 : "+title));

        inner_titletxt.setText("제목 : "+title);
        inner_idtxt.setText("ID : "+id);
        inner_contenttxt.setText(contents);
        inner_timetxt.setText(time);
        getSupportActionBar().setTitle(" "+title);

        goodkey = intent.getStringExtra("goodkey");
        Log.e("??? 정보들","contents ="+contents+"/id ="+id+"/time ="+time+"/title ="+title);
       // key = id+pw+title+contents+time;// 키값

        /** */
        key=goodkey;//이걸 해줘야 항상 키값이 변하지 않고 저장된다.
        /** */

        keyload();

//        communitReplyInfos.add(new Communit_reply_Info(Communit_reply_Info.B_TYPE,R.drawable.a_abs1,"하현준",time,"댓글입니다","좋아요수","#","#","#"));
//        communitReplyInfos.add(new Communit_reply_Info(Communit_reply_Info.B_TYPE,R.drawable.a_abs1,"하현준",time,"댓글입니다","좋아요수","#","#","#"));
//        communitReplyInfos.add(new Communit_reply_Info(Communit_reply_Info.B_TYPE,R.drawable.a_abs1,"하현준",time,"댓글입니다","좋아요수","#","#","#"));

        Log.e("??? 이정보의 키값:",key);
        //CommunityActivity 액티비티에서 보낸 인텐트를 받는다.
        //s 에 담긴 정보는 리사이클러뷰 아이템의 고유 postion에 맞는 json object 정보이다
        //{"contents":"nice to meet ya","id":"gkguswns","pw":"1","title":"hello~ i'm joon"} 이런식으로 되어있다.
        //s 에 담긴 정보는 제이슨 오브젝트이므로 JSONObject변수에 담겨야한다
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e("??? onStop시 저장된 RkeyList사이즈",Integer.toString(RkeyList.size()));
    }


    ArrayList<String> removeKey = new ArrayList<>();
    public void keyload(){
        SharedPreferences appdata2 =getSharedPreferences("댓글"+key,0);
        Log.e("??? keyload에서 받은 mainkey값",key);
        String strkey = appdata2.getString("댓글키들","null");
        Log.e("??? keyload에서 받은 댓글키들",strkey);
        if(strkey.equals("null")){
            RkeyList = new ArrayList<>();
        } if(!strkey.equals("null")){
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            Gson gson = new Gson();
            RkeyList = gson.fromJson(strkey, type);
            Log.e("??? loadkey 에서 RkeyList사이즈 (1)",Integer.toString(RkeyList.size()));
            //Log.e("??? loadkey 에서 RkeyList에 저장된 값들",RkeyList);
            try {
                //JSONArray ja = new JSONArray(RkeyList);
                for(int i =0 ; i<RkeyList.size(); i++){
                    SharedPreferences a = getSharedPreferences("댓글"+key,0);
                    String value=  a.getString(RkeyList.get(i),"null");
                    if(!value.equals("null")) {
                        Log.e("??? keyload에서 풀어 헤칠 key 값", RkeyList.get(i));
                        Log.e("??? keyload에서 풀어 헤칠 value 값", value);
                        JSONObject jo = new JSONObject(value);
                        //JSONObject jo = ja.getJSONObject(i);
                        String img = jo.getString("rm_img");

                        Rid = jo.getString("rm_id");
                        String content = jo.getString("rm_content");
                        String time = jo.getString("rm_time");
//                        if(!img.equals(memberimg) && id.equals(memberid)){
//                            img = memberimg;
//                        }
                        Log.e("??? keyload에서 풀어해친값!" + Integer.toString(i + 1), img + "/" + Rid + "/" + content + "/" + time);
                        SharedPreferences sp = getSharedPreferences("댓글" + key, 0);
                        //String deleteit = sp.getString(RkeyList.get(i),"null");
                        Log.e("??? keyload에서 리사이클러뷰에 뿌려줄 key 값" + Integer.toString(i + 1), value);
                        // if(!deleteit.equals("null")){

                        SharedPreferences app3 = getSharedPreferences("사진",0);
                        String newimg = app3.getString("사진"+Rid,"null");

//                        communitReplyInfos.add(new Communit_reply_Info(Communit_reply_Info.B_TYPE, img, "ID : " + Rid, time, content, "likecount", "viewcount", "replycount", "제목 : "));
                        communitReplyInfos.add(new Communit_reply_Info(Communit_reply_Info.B_TYPE, newimg, "ID : " + Rid, time, content, "likecount", "viewcount", "replycount", "제목 : "));

                        //}
                    }else {
                        Log.e("??? keyload에서 삭제될 key 값" + Integer.toString(i + 1),RkeyList.get(i));
                        removeKey.add(RkeyList.get(i));
                    }
                }
            } catch (JSONException e) {
                Log.e("??? loadkey 에서 풀어해치지 못함","fuck");
                e.printStackTrace();
            }
        }

        for (int j = 0 ; j< removeKey.size(); j++){
            Log.e("??? keyload에서 이제 곧 삭제 key 값" + Integer.toString(j + 1),removeKey.get(j));
            RkeyList.remove(removeKey.get(j));
        }
        for(int i =0 ; i<RkeyList.size(); i++){
            Log.e("??? loadkey 에서 궁극적으로 남아있는 키값"+Integer.toString(i),RkeyList.get(i));
        }

        Log.e("??? loadkey 에서 RkeyList사이즈 (2)",Integer.toString(RkeyList.size()));
    }
    public void insertItem3(int type, String rimage, String rid, String rtime, String rcontents, String rlikecount , String rViewcount , String rReplycount, String rTitle){
        communitReplyInfos.add(new Communit_reply_Info(type,rimage,rid,rtime,rcontents,rlikecount,rViewcount,rReplycount,rTitle));
        replyAdapter.notifyDataSetChanged();
    }

    public void removeItem3(int position){
        communitReplyInfos.remove(position);
        replyAdapter.notifyDataSetChanged();
    }

    public void editItem3(int position, int type, int rimage, String rid, String rtime, String rcontents, String rlikecount , String rViewcount , String rReplycount, String rTitle){
        //communitReplyInfos.set(position,new Communit_reply_Info(type,rimage,rid,rtime,rcontents,rlikecount,rViewcount,rReplycount,rTitle));
        replyAdapter.notifyDataSetChanged();
    }

    public void loadKeyValue(){
        SharedPreferences sp2 = getSharedPreferences("originalkey",0);
        String goodolkey = sp2.getString("goodolkey",null);
        if(goodolkey==null){
            key = id+pw+title+contents+time;
        } else {
            key=goodolkey;
        }
    }
    public void saveKeyValue(){
        SharedPreferences sp = getSharedPreferences("originalkey",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("goodolkey",key);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.community_inner_setting,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId())
        {
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.community_inner_edit:
                Log.e("@@@ Main2Activity.Loginname / id",Main2Activity.Loginname+" / "+id);
                if(Main2Activity.Loginname.equals(id)) {//로그인한 아이디 Loginname , 게시물의 아이디 id
                    SharedPreferences sp2 = getSharedPreferences("writing", 0);
                    SharedPreferences.Editor editor2 = sp2.edit();
                    Log.e("??? 지금 수정하려는 KEY값", key);
                    Log.e("??? 지금 수정하려는 VALUE값", sp2.getString(key, null));
                    //saveKeyValue();

                    final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Community_Inner.this);
                    View mView = getLayoutInflater().inflate(R.layout.community_inner_edit_delete_dialog, null);
                    final EditText edittxt = mView.findViewById(R.id.innerdialog_edittxt);
                    Button nobtn = mView.findViewById(R.id.innerdial_nobtn);
                    Button yesbtn = mView.findViewById(R.id.innerdial_yesbtn);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    yesbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            val1 = edittxt.getText().toString();
                            if (!val1.equals("") && !val1.equals(null)) {
                                if(Main2Activity.Password.equals(val1)) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(Community_Inner.this, Community_Edit.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("key값", key);
                                    startActivity(intent);
//                            SharedPreferences app = getSharedPreferences("회원정보",0);
//                            String pw =app.getString("회원PW","null");
//                            String id2 = app.getString("회원ID","null");
//                            Log.e("??? 받은 회원 비밀번호값",pw);
//                            if(!pw.equals("null")) {
//                                if (val1.equals(pw) && id.equals(id2)) {
//                                    dialog.dismiss();
//                                    Intent intent = new Intent(Community_Inner.this, Community_Edit.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    intent.putExtra("key값", key);
//                                    startActivity(intent);
//                                } else {
//                                    Toast.makeText(Community_Inner.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
//                                }
//                            }
                                }else if(!Main2Activity.Password.equals(val1)){
                                    Toast.makeText(Community_Inner.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                    nobtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }else if(!Main2Activity.Loginname.equals(id)) {
                    Log.e("@@@ Main2Activity.Loginname / id",Main2Activity.Loginname+" / "+id);
                    Toast.makeText(Community_Inner.this,"수정을 할 수 없는 게시물입니다",Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.community_inner_delete:
                Log.e("@@@ Main2Activity.Loginname / id",Main2Activity.Loginname+" / "+id);
                if(Main2Activity.Loginname.equals(id)) {
                SharedPreferences sp = getSharedPreferences("writing",0);
                SharedPreferences.Editor editor = sp.edit();
                Log.e("??? 지금 삭제하려는 KEY값",key);
                Log.e("??? 지금 삭제하려는 VALUE값",""+sp.getString(key,null));
                editor.remove(key);
                editor.apply();
                final AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(Community_Inner.this);
                View mView2 = getLayoutInflater().inflate(R.layout.community_inner_edit_delete_dialog,null);
                final EditText edittxt2 =  mView2.findViewById(R.id.innerdialog_edittxt);
                Button nobtn2 =  mView2.findViewById(R.id.innerdial_nobtn);
                Button yesbtn2 = mView2.findViewById(R.id.innerdial_yesbtn);

                mBuilder2.setView(mView2);
                final AlertDialog dialog2 = mBuilder2.create();
                dialog2.show();
                yesbtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        val1 = edittxt2.getText().toString();
                        if(!val1.equals("") && !val1.equals(null))
                        {
                            if(Main2Activity.Password.equals(val1)) {
                                dialog2.dismiss();
                                Intent intent = new Intent(Community_Inner.this, CommunityActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
//                            SharedPreferences app = getSharedPreferences("회원정보",0);
//                            String pw =app.getString("회원PW","null");
//                            String id2 = app.getString("회원ID","null");
//                            Log.e("??? 받은 회원 비밀번호값",pw);
//                            if(!pw.equals("null")) {
//                                if (val1.equals(pw)&& id.equals(id2)) {
//                                    dialog2.dismiss();
//                                    Intent intent = new Intent(Community_Inner.this,CommunityActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
//                                } else {
//                                    Toast.makeText(Community_Inner.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
//                                }
//                            }
                            }else if(!Main2Activity.Password.equals(val1)){
                                Log.e("@@@ Main2Activity.Password / Rid",Main2Activity.Password+" / "+val1);
                                Toast.makeText(Community_Inner.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
                nobtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });
                }else  if(!Main2Activity.Loginname.equals(id)){
                    Log.e("@@@ Main2Activity.Loginname / id",Main2Activity.Loginname+" / "+id);
                    Toast.makeText(Community_Inner.this,"삭제를 할 수 없는 게시물입니다",Toast.LENGTH_SHORT).show();
                }
                return  true;

            default:
                // Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }


    String val1;
    @Override
    public void onMoreButtonClicked(final int position, View v) {
        final PopupMenu p = new PopupMenu(getApplicationContext(),v);
        //저 v를 가져오기 위해선
        // 어밷벝의 인터페이스 메소드 onMoreButtonClicked의 파라미터값을 더 줘야 한다.
        getMenuInflater().inflate(R.menu.community_inner_menu,p.getMenu());

        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.edititem)
                {
                   // Toast.makeText(Community_Inner.this,"수정"+position,Toast.LENGTH_SHORT).show();
                    SharedPreferences spp = getSharedPreferences("댓글"+key,0);
                    SharedPreferences.Editor editor = spp.edit();
                    Log.e("??? 수정하려는 댓글 키",RkeyList.get(position-1));
                    Log.e("??? 수정하려는 댓글 value",spp.getString(RkeyList.get(position-1),"null"));

                }
                if(item.getItemId()==R.id.deleteitem)
                {

                    Communit_reply_Info communitReplyInfo = communitReplyInfos.get(position);
                    String RID2 = communitReplyInfo.getRid();
                    String RID3 = RID2.replace("ID : ","");
                    Log.e("@@@ Main2Activity.Loginname / RID2 / memberid",Main2Activity.Loginname+" / "+RID3+" / "+memberid);
                    if(Main2Activity.Loginname.equals(RID3)) {
                        // Toast.makeText(Community_Inner.this,"삭제"+position,Toast.LENGTH_SHORT).show();
                        //removeItem3(position);
                        SharedPreferences spp = getSharedPreferences("댓글" + key, 0);
                        final SharedPreferences.Editor editor = spp.edit();
                        Log.e("??? 삭제하려는 댓글 키", RkeyList.get(position - 1));
                        Log.e("??? 삭제하려는 댓글 value", spp.getString(RkeyList.get(position - 1), "null"));

                        final AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(Community_Inner.this);
                        View mView2 = getLayoutInflater().inflate(R.layout.community_inner_edit_delete_dialog, null);
                        final EditText edittxt2 = mView2.findViewById(R.id.innerdialog_edittxt);
                        Button nobtn2 = mView2.findViewById(R.id.innerdial_nobtn);
                        Button yesbtn2 = mView2.findViewById(R.id.innerdial_yesbtn);

                        mBuilder2.setView(mView2);
                        final AlertDialog dialog2 = mBuilder2.create();
                        dialog2.show();
                        yesbtn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                val1 = edittxt2.getText().toString();
                                if (!val1.equals("") && !val1.equals(null)) {
                                    if(Main2Activity.Password.equals(val1)) {
                                        dialog2.dismiss();
                                        editor.remove(RkeyList.get(position - 1));
                                        editor.apply();
                                        RkeyList.remove(position - 1);
                                        removeItem3(position);
//                                SharedPreferences app = getSharedPreferences("회원정보",0);
//                                String pw =app.getString("회원PW","null");
//                                String id2 = app.getString("회원ID","null");
//                                Log.e("??? 받은 회원 비밀번호값/id값",pw+"/"+id);
//                                if(!pw.equals("null")) {
//                                    if (val1.equals(pw)&& memberid.equals(id2)) {
//                                        dialog2.dismiss();
//
//                                        new Handler().postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                replyRecylcerview.scrollToPosition(replyAdapter.getItemCount() - 1);
//                                            }
//                                        }, 200);
//
//                                        editor.remove(RkeyList.get(position-1));
//                                        editor.apply();
//                                        RkeyList.remove(position-1);
//                                        removeItem3(position);
//                                    } else {
//                                        Toast.makeText(Community_Inner.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
                                    }else if(!Main2Activity.Password.equals(val1)){
                                        Log.e("@@@ Main2Activity.Password / Rid",Main2Activity.Password+" / "+val1);
                                        Toast.makeText(Community_Inner.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });
                        nobtn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog2.dismiss();
                            }
                        });
                    }else if(!Main2Activity.Loginname.equals(RID3)){
                        Log.e("@@@ Main2Activity.Loginname / Rid",Main2Activity.Loginname+" / "+Rid);
                        Toast.makeText(Community_Inner.this,"삭제를 할 수 없는 댓글입니다",Toast.LENGTH_SHORT).show();
                    }


//                    editor.remove(RkeyList.get(position-1));
//                    editor.apply();
//                    RkeyList.remove(position-1);
//                    removeItem3(position);
                }
                return false;
            }
        });
        p.show();

    }

    @Override
    public void onLikeButtonClicked(int position, View v) {
        Toast.makeText(Community_Inner.this,"like "+Integer.toString(position),Toast.LENGTH_SHORT).show();
       // editItem3(position,Communit_reply_Info.B_TYPE,R.drawable.a_abs1,memberid,TIME,replycontent,"좋아요수","","","");
    }

    @Override
    public void onReplyReplyButtonClicked(int position, View v) {
        Toast.makeText(Community_Inner.this,"reply_reply "+Integer.toString(position),Toast.LENGTH_SHORT).show();
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;

        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
