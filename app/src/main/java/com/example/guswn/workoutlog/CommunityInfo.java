package com.example.guswn.workoutlog;

public class CommunityInfo {
    public String name;
    public String reply;
    public String id;
    public String views;
    public String time;

    public CommunityInfo(String name, String reply, String id, String views, String time) {
        this.name = name;
        this.reply = reply;
        this.id = id;
        this.views = views;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
