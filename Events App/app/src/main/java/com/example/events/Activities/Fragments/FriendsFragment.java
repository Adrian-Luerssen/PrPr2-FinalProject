package com.example.events.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsFragment extends Fragment {
    private RecyclerView friendRecView;
    private List<User> friends;
    private FriendsOnClickListener listener;
    private ImageView back;

    public interface FriendsOnClickListener {
        void onProfileClicked(User user);
        void onBackClicked();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_fragment, container, false);
        friendRecView = view.findViewById(R.id.friendRecycler);
        friendRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        back = view.findViewById(R.id.back_button);
        back.setOnClickListener(view1 -> {
            listener.onBackClicked();
        });
        refresh();
        return view;
    }

    private void refresh(){
        ServiceAPI.getInstance().getFriends(AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    friends = response.body();
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
    private void updateUI() {

        FriendAdapter friendAdapter = new FriendAdapter(friends);
        friendRecView.setAdapter(friendAdapter);
    }

    private class FriendAdapter extends RecyclerView.Adapter<FriendHolder> {

        private final List<User> userList;


        private FriendAdapter(List<User> userList) {
            this.userList = userList;
        }

        @Override
        public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new FriendHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FriendHolder holder, int position) {
            User user = userList.get(position);
            holder.bind(user);
        }



        @Override
        public int getItemCount() {
            return userList.size();
        }

    }


    // View Holder
    private class FriendHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private User user;
        private final TextView username;
        private final ImageView profilePicture;

        public FriendHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.friend_item, parent, false));
            itemView.setOnClickListener(this);
            username = (TextView) itemView.findViewById(R.id.username);
            profilePicture = (ImageView) itemView.findViewById(R.id.profileImage);
        }

        public void bind(User user) {
            this.user = user;
            this.username.setText(user.getName()+" "+user.getLastName());
            DownloadImageTask.loadImage(getContext(), user.getImageURL(),profilePicture ,R.drawable.boy1);


        }

        @Override
        public void onClick(View view) {
            listener.onProfileClicked(user);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FriendsOnClickListener) {
            listener = (FriendsOnClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchUsersOnclickListener");
        }
    }
}
