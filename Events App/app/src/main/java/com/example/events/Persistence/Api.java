package com.example.events.Persistence;

import com.example.events.DataStructures.Assistance;
import com.example.events.DataStructures.Users.BearerToken;
import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.LoginObject;
import com.example.events.DataStructures.Users.Message;
import com.example.events.DataStructures.Users.User;
import com.example.events.DataStructures.Users.UserStatistics;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    // Register a new user
    @POST("users")
    Call<User> registerUser(@Body User user);

    // Login a user
    @POST("users/login")
    Call<BearerToken> authenticateUser(@Body LoginObject loginObject);

    //get all users
    @GET("users")
    Call<List<User>> getUsers();

    //get user by id
    @GET("users/{id}")
    Call<User> getUser(@Path("id") int userId, @Header("Authorization") String token);

    //get all users with a search parameter
    @GET("users/search")
    Call<List<User>> searchUsers(@Query(value = "s",encoded = true) String match, @Header("Authorization") String token);

    //get user statistics
    @GET("users/{id}/statistics")
    Call<UserStatistics> getUserStatistics (@Path("id") int userId, @Header("Authorization") String token);

    //update user
    @PUT("users")
    Call<User> updateUser(@Body User user, @Header("Authorization") String token);

    //delete user
    @DELETE("user")
    Call<ResponseBody> deleteUser(@Header("Authorization") String token);

    //get all events of a user
    @GET("users/{id}/events")
    Call<List<Event>> getEvents(@Path("id") int userId, @Header("Authorization") String token);

    //get all future events of a user
    @GET("users/{id}/events/future")
    Call<List<Event>> getFutureEvents(@Path("id") int userId, @Header("Authorization") String token);

    //get all past events of a user
    @GET("users/{id}/events/finished")
    Call<List<Event>> getFinishedEvents(@Path("id") int userId, @Header("Authorization") String token);

    //get all ongoing events of a user
    @GET("users/{id}/events/current")
    Call<List<Event>> getCurrentEvents(@Path("id") int userId, @Header("Authorization") String token);

    //get users assisted events
    @GET("users/{id}/assistances")
    Call<List<Event>> getAssistedEvents(@Path("id") int userId, @Header("Authorization") String token);

    //get users future assisted events
    @GET("users/{id}/assistances/future")
    Call<List<Event>> getFutureAssistedEvents(@Path("id") int userId, @Header("Authorization") String token);

    //get users past assisted events
    @GET("users/{id}/assistances/finished")
    Call<List<Event>> getFinishedAssistedEvents(@Path("id") int userId, @Header("Authorization") String token);

    //get users friends
    @GET("users/{id}/friends")
    Call<List<User>> getFriends(@Path("id") int userId, @Header("Authorization") String token);


    //create a new event
    @POST("events")
    Call<Event> createEvent(@Body Event event, @Header("Authorization") String token);

    //get all events
    @GET("events")
    Call<List<Event>> getEvents(@Header("Authorization") String token);

    //get event by id
    @GET("events/{id}")
    Call<Event> getEvent(@Path("id") int eventId, @Header("Authorization") String token);

    //get best events
    @GET("events/best")
    Call<List<Event>> getBestEvents(@Header("Authorization") String token);

    //get events by search parameter
    @GET("events/search")
    Call<List<Event>> searchEvents(@Query(value = "s",encoded = true) String match, @Header("Authorization") String token);

    //update event
    @PUT("events/{id}")
    Call<Event> updateEvent(@Path("id") int eventId, @Body Event event, @Header("Authorization") String token);

    //delete event
    @DELETE("events/{id}")
    Call<ResponseBody> deleteEvent(@Path("id") int eventId, @Header("Authorization") String token);

    //get event assistances
    @GET("events/{id}/assistances")
    Call<List<User>> getEventAssistances(@Path("id") int eventId, @Header("Authorization") String token);

    //get assistance of user with matching id for event with matching id
    @GET("events/{id}/assistances/{userId}")
    Call<Assistance> getAssistance(@Path("id") int eventId, @Path("userId") int userId, @Header("Authorization") String token);

    //assist an event
    @POST("events/{id}/assistances")
    Call<ResponseBody> assistEvent(@Path("id") int eventId, @Header("Authorization") String token);

    //edit assistance to an event
    @PUT("events/{id}/assistances")
    Call<ResponseBody> editAssistance(@Path("id") int eventId, @Body Assistance assistance, @Header("Authorization") String token);

    //delete assistance to an event
    @DELETE("events/{id}/assistances")
    Call<ResponseBody> deleteAssistance(@Path("id") int eventId, @Header("Authorization") String token);


    //get user assistence
    @GET("users/{user_id}/{event_id}")
    Call<Assistance> getUserAssistance(@Path("user_id") int userId, @Path("event_id") int eventId, @Header("Authorization") String token);

    //create assistance
    @POST("users/{user_id}/{event_id}")
    Call<ResponseBody> createAssistance(@Path("user_id") int userId, @Path("event_id") int eventId, @Header("Authorization") String token);

    //edit assistance
    @PUT("users/{user_id}/{event_id}")
    Call<ResponseBody> editAssistance(@Path("user_id") int userId, @Path("event_id") int eventId, @Body Assistance assistance, @Header("Authorization") String token);

    //delete assistance
    @DELETE("users/{user_id}/{event_id}")
    Call<ResponseBody> deleteAssistance(@Path("user_id") int userId, @Path("event_id") int eventId, @Header("Authorization") String token);


    //send message
    @POST("messages")
    Call<Message> sendMessage(@Body Message message, @Header("Authorization") String token);

    //get users messaging us
    @GET("messages/users")
    Call<List<User>> getUsersMessages(@Header("Authorization") String token);

    //get messages between us
    @GET("messages/{user_id}")
    Call<List<Message>> getMessages(@Path("user_id") int userId, @Header("Authorization") String token);


    // get friend requests
    @GET("friends/requests")
    Call<List<User>> getFriendRequests(@Header("Authorization") String token);

    //get friends
    @GET("friends")
    Call<List<User>> getFriends(@Header("Authorization") String token);

    //send friend request
    @POST("friends/{user_id}")
    Call<ResponseBody> sendFriendRequest(@Path("user_id") int userId, @Header("Authorization") String token);

    //accept friend request
    @PUT("friends/{user_id}")
    Call<ResponseBody> acceptFriendRequest(@Path("user_id") int userId, @Header("Authorization") String token);

    //delete friend
    @DELETE("friends/{user_id}")
    Call<ResponseBody> declineFriendRequest(@Path("user_id") int userId, @Header("Authorization") String token);

}
