package com.example.guswn.workoutlog;

import android.os.Parcel;
import android.os.Parcelable;

public class RankingActivity_Info  {
    String rank_img;
    String rank_id;
    String rank_allnum;

//    String rank_abs;
//    String rank_back;
//    String rank_biceps;
//    String rank_chest;
//    String rank_legs;
//    String rank_shoulder;
//    String rank_triceps;


    public RankingActivity_Info(String rank_img, String rank_id, String rank_allnum) {
        this.rank_img = rank_img;
        this.rank_id = rank_id;
        this.rank_allnum = rank_allnum;
    }

    protected RankingActivity_Info(Parcel in) {
        rank_img = in.readString();
        rank_id = in.readString();
        rank_allnum = in.readString();
    }


    public String getRank_img() {
        return rank_img;
    }

    public void setRank_img(String rank_img) {
        this.rank_img = rank_img;
    }

    public String getRank_id() {
        return rank_id;
    }

    public void setRank_id(String rank_id) {
        this.rank_id = rank_id;
    }

    public String getRank_allnum() {
        return rank_allnum;
    }

    public void setRank_allnum(String rank_allnum) {
        this.rank_allnum = rank_allnum;
    }

}
