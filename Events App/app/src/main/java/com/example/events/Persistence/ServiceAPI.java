package com.example.events.Persistence;

import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public class ServiceAPI {

    private static ServiceAPI apiService;

    public static ServiceAPI getInstance() {

        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://puigmal.salle.url.edu/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ServiceAPI.class);
        }

        return apiService;
    }
}
