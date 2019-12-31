package com.example.guswn.workoutlog;

public class Communit_reply_Info {

    public static final int A_TYPE=0;
    public static final int B_TYPE=1;
    int type;
    String rimage;
    String rid;
    String rtime;
    String rcontents;
    String rlikecount;
    String rViewcount;
    String rReplycount;
    String rTitle;

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }

    public String getrTitle() {
        return rTitle;
    }

    public void setrTitle(String rTitle) {
        this.rTitle = rTitle;
    }

    public Communit_reply_Info(int type, String rimage, String rid, String rtime, String rcontents, String rlikecount , String rViewcount , String rReplycount, String rTitle) {
        this.type = type;

        this.rimage = rimage;
        this.rid = rid;
        this.rtime = rtime;
        this.rcontents = rcontents;
        this.rlikecount = rlikecount;
        this.rViewcount = rViewcount;
        this.rReplycount = rReplycount;
        this.rTitle = rTitle;
    }

    public String getrViewcount() {
        return rViewcount;
    }

    public void setrViewcount(String rViewcount) {
        this.rViewcount = rViewcount;
    }

    public String getrReplycount() {
        return rReplycount;
    }

    public void setrReplycount(String rReplycount) {
        this.rReplycount = rReplycount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRimage() {
        return rimage;
    }

    public void setRimage(String rimage) {
        this.rimage = rimage;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }


    public String getRcontents() {
        return rcontents;
    }

    public void setRcontents(String rcontents) {
        this.rcontents = rcontents;
    }

    public String getRlikecount() {
        return rlikecount;
    }

    public void setRlikecount(String rlikecount) {
        this.rlikecount = rlikecount;
    }
}
