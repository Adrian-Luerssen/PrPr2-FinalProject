package com.example.events.Persistence;

import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{id}/events")
    Call<List<Event>> getEvents(@Path("id") int id);
}
