package com.example.guswn.workoutlog;

import android.os.Parcel;
import android.os.Parcelable;

public class Main2Activity_Info implements Parcelable{
    int main_img;
    String main_name;
    String main_part;

    public Main2Activity_Info(int main_img, String main_name, String main_part) {
        this.main_img = main_img;
        this.main_name = main_name;
        this.main_part = main_part;
    }

    protected Main2Activity_Info(Parcel in) {
        main_img = in.readInt();
        main_name = in.readString();
        main_part = in.readString();
    }

    public static final Creator<Main2Activity_Info> CREATOR = new Creator<Main2Activity_Info>() {
        @Override
        public Main2Activity_Info createFromParcel(Parcel in) {
            return new Main2Activity_Info(in);
        }

        @Override
        public Main2Activity_Info[] newArray(int size) {
            return new Main2Activity_Info[size];
        }
    };

    public int getMain_img() {
        return main_img;
    }

    public void setMain_img(int main_img) {
        this.main_img = main_img;
    }

    public String getMain_name() {
        return main_name;
    }

    public void setMain_name(String main_name) {
        this.main_name = main_name;
    }

    public String getMain_part() {
        return main_part;
    }

    public void setMain_part(String main_part) {
        this.main_part = main_part;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(main_img);
        dest.writeString(main_name);
        dest.writeString(main_part);
    }
}
