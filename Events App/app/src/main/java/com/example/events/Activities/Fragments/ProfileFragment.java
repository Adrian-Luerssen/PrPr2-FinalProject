package com.example.events.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    private final User user;
    private TextView addUserORviewRequests;
    private ProfileListener listener;
    private Button delete;
    private final boolean showBack;

    public ProfileFragment(User user) {
        this.user = user;
        showBack = true;
    }
    public ProfileFragment(User user,boolean showback) {
        this.user = user;
        this.showBack = showback;
    }

    public interface ProfileListener {
        void onEditProfileClicked();

        void viewFriendRequests();

        void onUserChatClicked(User user);

        void viewFriends();

        void onBackClicked();

        void onDeleteUserClicked();

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view2 = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView username = view2.findViewById(R.id.profileName);
        ImageView profilePicture = view2.findViewById(R.id.profileImage);
        TextView editProfileORgotochat = view2.findViewById(R.id.gotochatOReditProfile);
        addUserORviewRequests = view2.findViewById(R.id.addUser);
        TextView viewFriends = view2.findViewById(R.id.see_friends);
        Button delete = view2.findViewById(R.id.deleteAccount);
        delete.setVisibility(View.INVISIBLE);
        viewFriends.setVisibility(View.INVISIBLE);
        username.setText(user.getName() + " " + user.getLastName());

        ImageView back = view2.findViewById(R.id.back_button);
        if (showBack) {
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(view1 -> listener.onBackClicked());
        } else {
            back.setVisibility(View.INVISIBLE);
        }
        if (user.getId() == AuthUser.getAuthUser().getId()) {
            viewFriends.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(view1 -> {
                //delete
            });
            viewFriends.setOnClickListener(view -> listener.viewFriends());
            editProfileORgotochat.setText(R.string.Edit_Profile);
            editProfileORgotochat.setOnClickListener(view1 -> listener.onEditProfileClicked());
            addUserORviewRequests.setText(R.string.View_Requests);
            addUserORviewRequests.setOnClickListener(view1 -> listener.viewFriendRequests());

            delete.setOnClickListener(view1 -> listener.onDeleteUserClicked());

        } else {


            editProfileORgotochat.setText(R.string.Go_to_chat);
            editProfileORgotochat.setOnClickListener(view1 -> listener.onUserChatClicked(user));
            ServiceAPI.getInstance().getFriends(AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        for (User u : response.body()) {
                            if (user.getId() == u.getId()){
                                addUserORviewRequests.setText(R.string.Already_Friends);
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {

                }
            });
            final boolean[] friend = {false};
            ServiceAPI.getInstance().getFriends(AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {

                        for (User u : response.body()) {
                            if (user.getId() == u.getId()){
                                friend[0] = true;
                                break;
                            }
                        }
                        if (!friend[0]){
                            addUserORviewRequests.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {

                }
            });
            addUserORviewRequests.setOnClickListener(view1 -> {
                if (friend[0]) {
                    Toast.makeText(getContext(), R.string.Already_Friends, Toast.LENGTH_SHORT).show();
                } else {
                    ServiceAPI.getInstance().sendFriendRequest(user.getId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Friend request sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Friend request already sent", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), R.string.bad_connection, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }

        DownloadImageTask.loadImage(getContext(), user.getImageURL(),profilePicture ,R.drawable.boy1);

        return view2;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileListener) {
            listener = (ProfileListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement ProfileListener");
        }
    }
}