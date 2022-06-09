package com.example.events.Activities.Fragments.CONVERT_TO_FRAGMENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.Message;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenChatActivity extends AppCompatActivity {
    private User recipient;
    private ArrayList<Message> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_chat);
        Intent intent = getIntent();
        recipient = (User) intent.getSerializableExtra("recipient");

        ServiceAPI.getInstance().getMessages(recipient.getId(),AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    messages = (ArrayList<Message>) response.body();
                    Log.d("messages", messages.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }
}