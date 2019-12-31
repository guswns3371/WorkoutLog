package com.example.guswn.workoutlog;

import android.os.Parcel;
import android.os.Parcelable;

public class InnerWorkout_Info implements Parcelable{
    int inn_img;
    String inn_part;
    String inn_name;

    public InnerWorkout_Info(int inn_img, String inn_part, String inn_name) {
        this.inn_img = inn_img;
        this.inn_part = inn_part;
        this.inn_name = inn_name;
    }

    protected InnerWorkout_Info(Parcel in) {
        inn_img = in.readInt();
        inn_part = in.readString();
        inn_name = in.readString();
    }

    public static final Creator<InnerWorkout_Info> CREATOR = new Creator<InnerWorkout_Info>() {
        @Override
        public InnerWorkout_Info createFromParcel(Parcel in) {
            return new InnerWorkout_Info(in);
        }

        @Override
        public InnerWorkout_Info[] newArray(int size) {
            return new InnerWorkout_Info[size];
        }
    };

    public int getInn_img() {
        return inn_img;
    }

    public void setInn_img(int inn_img) {
        this.inn_img = inn_img;
    }

    public String getInn_part() {
        return inn_part;
    }

    public void setInn_part(String inn_part) {
        this.inn_part = inn_part;
    }

    public String getInn_name() {
        return inn_name;
    }

    public void setInn_name(String inn_name) {
        this.inn_name = inn_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(inn_img);
        dest.writeString(inn_part);
        dest.writeString(inn_name);
    }
}
