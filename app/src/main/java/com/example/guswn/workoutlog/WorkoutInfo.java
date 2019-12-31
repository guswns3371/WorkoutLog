package com.example.guswn.workoutlog;

        import android.support.v7.widget.CardView;
        import android.widget.ImageButton;
        import android.widget.TextView;

public class WorkoutInfo {

    String workname;
    String workdes;
    String worknumber;

    public WorkoutInfo(String workname,String workdes){
       // this.worknumber = worknumber;
        this. workdes =workdes;
        this.workname = workname;
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname;
    }

    public String getWorkdes() {
        return workdes;
    }

    public void setWorkdes(String workdes) {
        this.workdes = workdes;
    }

    public String getWorknumber() {
        return worknumber;
    }

    public void setWorknumber(String worknumber) {
        this.worknumber = worknumber;
    }
}
