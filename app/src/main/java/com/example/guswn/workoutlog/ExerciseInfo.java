package com.example.guswn.workoutlog;

import android.os.Parcel;
import android.os.Parcelable;

public class ExerciseInfo implements Parcelable{

    public int exerciseimg;
    public String exercisename;
    public  String part;
    Boolean ischecked;
    Boolean isLiked;

    protected ExerciseInfo(Parcel in) {
        exerciseimg = in.readInt();
        exercisename = in.readString();
        part = in.readString();
        byte tmpIschecked = in.readByte();
        ischecked = tmpIschecked == 0 ? null : tmpIschecked == 1;
        byte tmpIsLiked = in.readByte();
        isLiked = tmpIsLiked == 0 ? null : tmpIsLiked == 1;
    }

    public static final Creator<ExerciseInfo> CREATOR = new Creator<ExerciseInfo>() {
        @Override
        public ExerciseInfo createFromParcel(Parcel in) {
            return new ExerciseInfo(in);
        }

        @Override
        public ExerciseInfo[] newArray(int size) {
            return new ExerciseInfo[size];
        }
    };

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public ExerciseInfo( String part,String exercisename,int exerciseimg, Boolean ischecked,Boolean isLiked) {
        this.exerciseimg = exerciseimg;
        this.exercisename = exercisename;
        this.part = part;
        this.ischecked = ischecked;
        this.isLiked = isLiked;
    }




    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }


    public int getExerciseimg() {
        return exerciseimg;
    }

    public void setExerciseimg(int exerciseimg) {
        this.exerciseimg = exerciseimg;
    }

    public String getExercisename() {
        return exercisename;
    }

    public void setExercisename(String exercisename) {
        this.exercisename = exercisename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(exerciseimg);
        dest.writeString(exercisename);
        dest.writeString(part);
        dest.writeByte((byte) (ischecked == null ? 0 : ischecked ? 1 : 2));
        dest.writeByte((byte) (isLiked == null ? 0 : isLiked ? 1 : 2));
    }
}
