package com.example.events.Persistence;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
