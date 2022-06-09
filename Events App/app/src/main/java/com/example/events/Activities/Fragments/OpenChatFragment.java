package com.example.events.Activities.Fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class OpenChatFragment extends Fragment {
    private final User recipient;
    private ArrayList<Message> messages;
    private View view;
    private ImageView profilePic;
    private TextView username;

    public OpenChatFragment(User recipient){
        this.recipient = recipient;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.open_chat_fragment, container, false);
        profilePic = view.findViewById(R.id.profile_pic);
        username = view.findViewById(R.id.username);
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

        return view;
    }

}