package com.example.events.DataStructures.Users;

public class AuthUser {
    private static User authUser;

    public static User getAuthUser() {
        if (authUser == null) {
            authUser = new User();
        }

        return authUser;
    }

    public static void setAuthUser(User authUser) {
        authUser = new User(authUser.getName(), authUser.getLastName(), authUser.getEmail(), authUser.getPassword(), authUser.getImageURL());
    }
}
