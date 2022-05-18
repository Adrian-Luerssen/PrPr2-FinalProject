package com.example.events.DataStructures.Users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BearerToken {
    @Expose
    @SerializedName("accessToken")
    private String token;

    public BearerToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void fixToken(){
        if (token.length() != 0 && !token.startsWith("Bearer ")) token = "Bearer "+token;
    }
}
