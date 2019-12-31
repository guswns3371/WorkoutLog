package com.example.guswn.workoutlog;

public class CompleteSetInfo {
    String comName;
//    String comSet;
//    String comWeight;
//    String comReps;
    String comData;
    String comTime;
    public CompleteSetInfo(String comName, String comData, String comTime) {
        this.comName = comName;
        this.comData = comData;
        this.comTime = comTime;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComData() {
        return comData;
    }

    public void setComData(String comData) {
        this.comData = comData;
    }

    public String getComTime() {
        return comTime;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }
}
