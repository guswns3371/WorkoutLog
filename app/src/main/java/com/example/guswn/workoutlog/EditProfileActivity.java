package com.example.guswn.workoutlog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class EditProfileActivity extends AppCompatActivity {

    String ID;
    String PW;
    String NAME;
    EditText nameedittxt;
    EditText idedittxt;
    EditText pwedittxt;
    CardView applycv;
    Toolbar editprofiletb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent = getIntent();
        NAME = intent.getStringExtra("NAME3");
        ID = intent.getStringExtra("ID3");
        PW = intent.getStringExtra("PW3");


        nameedittxt = findViewById(R.id.nameedittxt);
        idedittxt = findViewById(R.id.idedittxt);
        pwedittxt = findViewById(R.id.pwedittxt);
        applycv = findViewById(R.id.applycv);
        editprofiletb = findViewById(R.id.editprofiletb);
        setSupportActionBar(editprofiletb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle("Edit Profile");

        nameedittxt.setText(NAME);
        idedittxt.setText(ID);
        pwedittxt.setText(PW);

        applycv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditProfileActivity.this,"프로필 정보가 변경되었습니다",Toast.LENGTH_SHORT).show();
            }
        });

        
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
}
