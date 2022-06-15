package com.example.events.DataStructures;

import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.ServiceAPI;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comment {
    @Expose
    @SerializedName("id")
    private int senderID;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("last_name")
    private String lastName;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("comentary")
    private String text;
    @Expose
    @SerializedName("event_id")
    private int eventID;
    @Expose
    @SerializedName("puntuation")
    private int puntuation;

    private String image;

    public void setImage (String image) {
        this.image = image;
    }

    public String getImage () {
        return image;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public int getUserId() {
        return senderID;
    }



    public int getScore() {
        return puntuation;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setComment(String toString) {
        this.text = toString;
    }

    public void setPuntuation(int value) {
        this.puntuation = value;
    }
}
