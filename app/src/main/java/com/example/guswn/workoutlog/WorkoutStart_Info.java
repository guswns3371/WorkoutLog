package com.example.guswn.workoutlog;

public class WorkoutStart_Info {
    public static final int A_TYPE=0;
    public static final int B_TYPE=1;
    int type;


    int ws_img;
    String ws_part;
    String ws_name;

    String ws_wight;
    String ws_reps;

    public WorkoutStart_Info(int type, int ws_img, String ws_part, String ws_name, String ws_wight, String ws_reps) {
        this.type = type;
        this.ws_img = ws_img;
        this.ws_part = ws_part;
        this.ws_name = ws_name;
        this.ws_wight = ws_wight;
        this.ws_reps = ws_reps;
    }

    public static int getaType() {
        return A_TYPE;
    }

    public static int getbType() {
        return B_TYPE;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWs_img() {
        return ws_img;
    }

    public void setWs_img(int ws_img) {
        this.ws_img = ws_img;
    }

    public String getWs_part() {
        return ws_part;
    }

    public void setWs_part(String ws_part) {
        this.ws_part = ws_part;
    }

    public String getWs_name() {
        return ws_name;
    }

    public void setWs_name(String ws_name) {
        this.ws_name = ws_name;
    }

    public String getWs_wight() {
        return ws_wight;
    }

    public void setWs_wight(String ws_wight) {
        this.ws_wight = ws_wight;
    }

    public String getWs_reps() {
        return ws_reps;
    }

    public void setWs_reps(String ws_reps) {
        this.ws_reps = ws_reps;
    }
}
