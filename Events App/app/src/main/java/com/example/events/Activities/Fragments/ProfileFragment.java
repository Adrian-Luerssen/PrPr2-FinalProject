package com.example.events.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.R;


public class ProfileFragment extends Fragment {

    private View view;
    private User user;
    private TextView username;
    private ImageView profilePicture;
    private TextView editProfileORgotochat;
    private ProfileListener listener;

    public interface ProfileListener{
        void onEditProfileClicked();
        void onUserChatClicked(User user);
    }

    public ProfileFragment(User user) {
        this.user = user;
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        username = view.findViewById(R.id.profileName);
        profilePicture = view.findViewById(R.id.profileImage);
        editProfileORgotochat = view.findViewById(R.id.gotochatOReditProfile);
        username.setText(user.getName()+" "+user.getLastName());
        if (user.getId() == AuthUser.getAuthUser().getId()){
            editProfileORgotochat.setText(R.string.Edit_Profile);
            editProfileORgotochat.setOnClickListener(view1 -> {
                listener.onEditProfileClicked();
            });
        } else {
            editProfileORgotochat.setText(R.string.Go_to_chat);
            editProfileORgotochat.setOnClickListener(view1 -> {
                listener.onUserChatClicked(user);
            });
        }

        new DownloadImageTask(profilePicture).execute(user.getImageURL());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileListener) {
            listener = (ProfileListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ProfileListener");
        }
    }
}