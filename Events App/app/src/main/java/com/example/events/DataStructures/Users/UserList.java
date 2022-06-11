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

    public ArrayList<User> getUserList() {
        return users;
    }

    public List<User> getFilteredUsers(String searchString) {
        List<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(searchString.toLowerCase())
                    || user.getLastName().toLowerCase().contains(searchString.toLowerCase())
                    || user.getEmail().split("@")[0].toLowerCase().contains(searchString.toLowerCase())) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }
}
