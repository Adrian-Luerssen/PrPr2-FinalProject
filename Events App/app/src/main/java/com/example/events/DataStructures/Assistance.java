package com.example.events.DataStructures;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assistance {
    @Expose
    @SerializedName("user_id")
    private int user_id;
    @Expose
    @SerializedName("event_id")
    private int event_id;
    @Expose
    @SerializedName("puntuation")
    private int rating;
    @Expose
    @SerializedName("comentary")
    private String comment;


    public Assistance(int user_id, int event_id) {
        this.user_id = user_id;
        this.event_id = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}
