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
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://puigmal.salle.url.edu/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(new OkHttpClient.Builder().addInterceptor(logging).build())
                    .build();

            api = retrofit.create(Api.class);
        }

        return api;
    }
}
