package com.example.guswn.workoutlog;

public class Logs_Daily_Info {
    String Lpart;
    String Lname;
    String Ldata;

    public Logs_Daily_Info(String lpart, String lname, String ldata) {
        Lpart = lpart;
        Lname = lname;
        Ldata = ldata;
    }

    public String getLpart() {
        return Lpart;
    }

    public void setLpart(String lpart) {
        Lpart = lpart;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getLdata() {
        return Ldata;
    }

    public void setLdata(String ldata) {
        Ldata = ldata;
    }
}
