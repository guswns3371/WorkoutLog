package com.example.guswn.workoutlog;

public class SearchWorkout_Info {
    int Search_img;
    String Search_part;
    String Search_name;

    public SearchWorkout_Info(int search_img, String search_part, String search_name) {
        Search_img = search_img;
        Search_part = search_part;
        Search_name = search_name;
    }

    public int getSearch_img() {
        return Search_img;
    }

    public void setSearch_img(int search_img) {
        Search_img = search_img;
    }

    public String getSearch_part() {
        return Search_part;
    }

    public void setSearch_part(String search_part) {
        Search_part = search_part;
    }

    public String getSearch_name() {
        return Search_name;
    }

    public void setSearch_name(String search_name) {
        Search_name = search_name;
    }
}
