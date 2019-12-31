package com.example.guswn.workoutlog;

public class StatisticsActivity_Info {
    String ST_name;
    String ST_part;
    String ST_reps;

    public StatisticsActivity_Info(String ST_name, String ST_part, String ST_reps) {
        this.ST_name = ST_name;
        this.ST_part = ST_part;
        this.ST_reps = ST_reps;
    }

    public String getST_name() {
        return ST_name;
    }

    public void setST_name(String ST_name) {
        this.ST_name = ST_name;
    }

    public String getST_part() {
        return ST_part;
    }

    public void setST_part(String ST_part) {
        this.ST_part = ST_part;
    }

    public String getST_reps() {
        return ST_reps;
    }

    public void setST_reps(String ST_reps) {
        this.ST_reps = ST_reps;
    }
}
