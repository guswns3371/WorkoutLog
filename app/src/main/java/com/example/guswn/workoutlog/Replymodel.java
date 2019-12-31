package com.example.guswn.workoutlog;

public class Replymodel {
    String rm_img;
    String rm_id;
    String rm_content;
    String rm_time;

    public Replymodel(String rm_img, String rm_id, String rm_content, String rm_time) {
        this.rm_img = rm_img;
        this.rm_id = rm_id;
        this.rm_content = rm_content;
        this.rm_time = rm_time;
    }

    public String getRm_img() {
        return rm_img;
    }

    public void setRm_img(String rm_img) {
        this.rm_img = rm_img;
    }

    public String getRm_id() {
        return rm_id;
    }

    public void setRm_id(String rm_id) {
        this.rm_id = rm_id;
    }

    public String getRm_content() {
        return rm_content;
    }

    public void setRm_content(String rm_content) {
        this.rm_content = rm_content;
    }

    public String getRm_time() {
        return rm_time;
    }

    public void setRm_time(String rm_time) {
        this.rm_time = rm_time;
    }
}
