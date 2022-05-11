package com.example.events.Persistence;

import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class RetrofitAPI {
    private Retrofit retrofit;

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private List<User> userList;
    private List<Event> eventsList;
    public RetrofitAPI(){
        userList = new ArrayList<>();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://puigmal.salle.url.edu/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    }

    public void getUsers(){
        Call<List<User>> users = jsonPlaceHolderApi.getUsers();

        users.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    System.out.println("Error: "+ response.code());
                    return;
                }
                userList = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println("Error: "+ t.getMessage());
            }
        });
    }

    public boolean checkEmailExists(String email){
        for (User user: userList){
            if (user.getEmail() == email) return true;
        }
        return false;
    }

    public boolean checkPassword(String email, String password){
        for (User user: userList){
            if (user.getEmail() == email) {
                if (user.getPassword() == password) return true;
                return false;
            }
        }
        return false;
    }

    public User getUser(String email, String password){
        for (User user: userList){
            if (user.getEmail() == email) {
                if (user.getPassword() == password) {
                    return user;
                }
                return null;
            }
        }
        return null;
    }

    public void readUserEvents(int userId){
        Call<List<Event>> events = jsonPlaceHolderApi.getEvents(userId);

        events.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (!response.isSuccessful()){
                    System.out.println("Error: "+ response.code());
                    return;
                }
                eventsList = response.body();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                System.out.println("Error: "+ t.getMessage());
            }
        });

    }
    public List<Event> getUserEvents(){
        return eventsList;
    }
}
