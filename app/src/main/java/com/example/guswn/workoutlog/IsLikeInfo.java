package com.example.guswn.workoutlog;

import android.os.Parcel;
import android.os.Parcelable;

public class IsLikeInfo implements Parcelable{
    String islike_name;
    Boolean islike;

    protected IsLikeInfo(Parcel in) {
        islike_name = in.readString();
        byte tmpIslike = in.readByte();
        islike = tmpIslike == 0 ? null : tmpIslike == 1;
    }

    public static final Creator<IsLikeInfo> CREATOR = new Creator<IsLikeInfo>() {
        @Override
        public IsLikeInfo createFromParcel(Parcel in) {
            return new IsLikeInfo(in);
        }

        @Override
        public IsLikeInfo[] newArray(int size) {
            return new IsLikeInfo[size];
        }
    };

    public String getIslike_name() {
        return islike_name;
    }

    public void setIslike_name(String islike_name) {
        this.islike_name = islike_name;
    }

    public Boolean getIslike() {
        return islike;
    }

    public void setIslike(Boolean islike) {
        this.islike = islike;
    }

    public IsLikeInfo(String islike_name, Boolean islike) {

        this.islike_name = islike_name;
        this.islike = islike;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(islike_name);
        dest.writeByte((byte) (islike == null ? 0 : islike ? 1 : 2));
    }
}
