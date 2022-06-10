package com.example.events.DataStructures.Users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Message {
    @Expose(serialize = false)
    @SerializedName("id")
    private int message_id;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("user_id_send")
    private int senderID;
    @Expose
    @SerializedName("user_id_recived")
    private int receiverID;

    @Expose(serialize = false)
    @SerializedName("timeStamp")
    private Date mDate;


    public Message(int senderID, int receiverID,String content) {
        this.content = content;
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getDate() {
        return mDate.toString();
    }
}
