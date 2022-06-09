package com.example.events.DataStructures.Users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @Expose(serialize = false)
    @SerializedName("id")
    private int id;
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
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("image")
    private String imageURL;



    private BearerToken token;

    public User() {
    }

    public User(String name, String lastName, String email, String password, String imageURL) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.imageURL = imageURL;
    }

    public User(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.imageURL = "https://imgur.com/mCHMpLT";
    }


    public BearerToken getToken() {
        return token;
    }

    public void setToken(BearerToken token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setUser(User user) {
        this.name = user.name;
        this.lastName = user.lastName;
        this.email = user.email;
        this.password = user.password;
        this.imageURL = user.imageURL;
        this.token = user.getToken();
    }

    public int getId() {
        return id;
    }
}
