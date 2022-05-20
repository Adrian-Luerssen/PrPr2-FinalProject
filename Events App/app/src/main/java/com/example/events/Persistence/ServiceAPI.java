package com.example.events.Persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceAPI {

    private static Api api;

    public static Api getInstance() {
        return (api == null)?generateApi():api;
    }

    private static Api generateApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.205.68/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient.Builder().addInterceptor(logging).build())
                .build();

        return retrofit.create(Api.class);
    }
}
