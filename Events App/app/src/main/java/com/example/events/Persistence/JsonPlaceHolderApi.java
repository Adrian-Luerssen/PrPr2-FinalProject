package com.example.events.Persistence;

import com.example.events.DataStructures.Users.BearerToken;
import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.User;
import com.example.events.DataStructures.Users.UserStatistics;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @POST("users")
    Call<User> registerUser(@Body User user);

    @POST("users/login")
    Call<BearerToken> authenticateUser(@Field("email") String email, @Field("password") String password);


    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{id}")
    Call<User> getUser(@Path("id") int userId, @Header("Authorisation") String token);

    @GET("users/search")
    Call<List<User>> searchUsers(@Query("s") String match, @Header("Authorisation") String token);

    @GET("users/{id}/statistics")
    Call<UserStatistics> getUserStatistics (@Path("id") int userId, @Header("Authorisation") String token);

    @PUT("users")
    Call<User> updateUser(@Body User user, @Header("Authorization") String token);

    @DELETE("user")
    Call<ResponseBody> deleteUser(@Header("Authorization") String token);


    @GET("users/{id}/events")
    Call<List<Event>> getEvents(@Path("id") int userId, @Header("Authorization") String token);

    @GET("users/{id}/events/future")
    Call<List<Event>> getFutureEvents(@Path("id") int userId, @Header("Authorization") String token);

    @GET("users/{id}/events/finished")
    Call<List<Event>> getFinishedEvents(@Path("id") int userId, @Header("Authorization") String token);

    @GET("users/{id}/events/current")
    Call<List<Event>> getCurrentEvents(@Path("id") int userId, @Header("Authorization") String token);

    @GET("users/{id}/assistances")
    Call<List<Event>> getAssistedEvents(@Path("id") int userId, @Header("Authorization") String token);

    @GET("users/{id}/assistances/future")
    Call<List<Event>> getFutureAssistedEvents(@Path("id") int userId, @Header("Authorization") String token);

    @GET("users/{id}/assistances/finished")
    Call<List<Event>> getFinishedAssistedEvents(@Path("id") int userId, @Header("Authorization") String token);

    @GET("users/{id}/friends")
    Call<List<User>> getFriends(@Path("id") int userId, @Header("Authorization") String token);

}
