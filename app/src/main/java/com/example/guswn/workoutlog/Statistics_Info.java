package com.example.guswn.workoutlog;

import android.os.Parcel;
import android.os.Parcelable;

public class Statistics_Info implements Parcelable
{

    int Stat_abs;
    int Stat_back;
    int Stat_biceps;
    int Stat_chest;
    int Stat_legs;
    int Stat_shoulder;
    int Stat_triceps;

    public Statistics_Info(int stat_abs, int stat_back, int stat_biceps, int stat_chest, int stat_legs, int stat_shoulder, int stat_triceps) {
        Stat_abs = stat_abs;
        Stat_back = stat_back;
        Stat_biceps = stat_biceps;
        Stat_chest = stat_chest;
        Stat_legs = stat_legs;
        Stat_shoulder = stat_shoulder;
        Stat_triceps = stat_triceps;
    }

    protected Statistics_Info(Parcel in) {
        Stat_abs = in.readInt();
        Stat_back = in.readInt();
        Stat_biceps = in.readInt();
        Stat_chest = in.readInt();
        Stat_legs = in.readInt();
        Stat_shoulder = in.readInt();
        Stat_triceps = in.readInt();
    }

    public static final Creator<Statistics_Info> CREATOR = new Creator<Statistics_Info>() {
        @Override
        public Statistics_Info createFromParcel(Parcel in) {
            return new Statistics_Info(in);
        }

        @Override
        public Statistics_Info[] newArray(int size) {
            return new Statistics_Info[size];
        }
    };

    public int getStat_abs() {
        return Stat_abs;
    }

    public void setStat_abs(int stat_abs) {
        Stat_abs = stat_abs;
    }

    public int getStat_back() {
        return Stat_back;
    }

    public void setStat_back(int stat_back) {
        Stat_back = stat_back;
    }

    public int getStat_biceps() {
        return Stat_biceps;
    }

    public void setStat_biceps(int stat_biceps) {
        Stat_biceps = stat_biceps;
    }

    public int getStat_chest() {
        return Stat_chest;
    }

    public void setStat_chest(int stat_chest) {
        Stat_chest = stat_chest;
    }

    public int getStat_legs() {
        return Stat_legs;
    }

    public void setStat_legs(int stat_legs) {
        Stat_legs = stat_legs;
    }

    public int getStat_shoulder() {
        return Stat_shoulder;
    }

    public void setStat_shoulder(int stat_shoulder) {
        Stat_shoulder = stat_shoulder;
    }

    public int getStat_triceps() {
        return Stat_triceps;
    }

    public void setStat_triceps(int stat_triceps) {
        Stat_triceps = stat_triceps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Stat_abs);
        dest.writeInt(Stat_back);
        dest.writeInt(Stat_biceps);
        dest.writeInt(Stat_chest);
        dest.writeInt(Stat_legs);
        dest.writeInt(Stat_shoulder);
        dest.writeInt(Stat_triceps);
    }
}
