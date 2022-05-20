package com.example.events.DataStructures.Users;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public void addUser(User task){
        users.add(task);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

       public void remove(int position) {
        users.remove(position);
    }

    public void setUserList(List<User> body) {
        users = (ArrayList<User>) body;
    }
}
