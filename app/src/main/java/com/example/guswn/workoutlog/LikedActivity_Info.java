package com.example.guswn.workoutlog;

import android.os.Parcel;
import android.os.Parcelable;

public class LikedActivity_Info implements Parcelable{
    int liked_img;
    String liked_name;
    String liked_part;

    protected LikedActivity_Info(Parcel in) {
        liked_img = in.readInt();
        liked_name = in.readString();
        liked_part = in.readString();
    }

    public static final Creator<LikedActivity_Info> CREATOR = new Creator<LikedActivity_Info>() {
        @Override
        public LikedActivity_Info createFromParcel(Parcel in) {
            return new LikedActivity_Info(in);
        }

        @Override
        public LikedActivity_Info[] newArray(int size) {
            return new LikedActivity_Info[size];
        }
    };

    public String getLiked_part() {
        return liked_part;
    }

    public void setLiked_part(String liked_part) {
        this.liked_part = liked_part;
    }

    public LikedActivity_Info(int liked_img, String liked_name, String liked_part) {
        this.liked_img = liked_img;
        this.liked_name = liked_name;
        this.liked_part = liked_part;
    }

    public int getLiked_img() {
        return liked_img;
    }

    public void setLiked_img(int liked_img) {
        this.liked_img = liked_img;
    }

    public String getLiked_name() {
        return liked_name;
    }

    public void setLiked_name(String liked_name) {
        this.liked_name = liked_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(liked_img);
        dest.writeString(liked_name);
        dest.writeString(liked_part);
    }
}
