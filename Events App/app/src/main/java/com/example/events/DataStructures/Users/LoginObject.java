package com.example.events.DataStructures.Users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginObject {
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("password")
    private String password;

    public LoginObject(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
