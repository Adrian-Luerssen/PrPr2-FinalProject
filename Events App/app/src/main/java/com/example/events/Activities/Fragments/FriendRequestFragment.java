package com.example.events.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class FriendRequestFragment extends Fragment {
    private RecyclerView requestRecView;
    private List<User> requests;
    private FriendRequestOnclickListener listener;

    public interface FriendRequestOnclickListener{
        void onProfileClicked(User user);
        void onBackClicked();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friend_requests_fragment, container, false);
        requestRecView = view.findViewById(R.id.friendRequestsRecycler);
        ImageView back = view.findViewById(R.id.back_button);
        back.setOnClickListener(view1 -> listener.onBackClicked());
        requestRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        refresh();
        return view;
    }

    private void refresh(){
        ServiceAPI.getInstance().getFriendRequests(AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    requests = response.body();
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
    private void updateUI() {

        RequestAdapter requestAdapter = new RequestAdapter(requests);
        requestRecView.setAdapter(requestAdapter);
    }

    private class RequestAdapter extends RecyclerView.Adapter<RequestHolder> {

        private final List<User> userList;


        private RequestAdapter(List<User> userList) {
            this.userList = userList;
        }

        @Override
        public RequestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new RequestHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RequestHolder holder, int position) {
            User user = userList.get(position);
            holder.bind(user);
        }



        @Override
        public int getItemCount() {
            return userList.size();
        }



    }


    // View Holder
    private class RequestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView username;
        private User user;
        private final ImageButton acceptFriend;
        private final ImageButton declineFriend;
        private final ImageView profilePicture;

        public RequestHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.friend_request_item, parent, false));
            itemView.setOnClickListener(this);
            username = (TextView) itemView.findViewById(R.id.username);
            profilePicture = (ImageView) itemView.findViewById(R.id.profileImage);
            acceptFriend = (ImageButton) itemView.findViewById(R.id.acceptRequest);
            declineFriend = (ImageButton) itemView.findViewById(R.id.declineRequest);
        }

        public void bind(User user) {
            this.user = user;
            this.username.setText(user.getName()+" "+user.getLastName());
            DownloadImageTask.loadImage(getContext(), user.getImageURL(),profilePicture ,R.drawable.boy1);

            acceptFriend.setOnClickListener(v -> ServiceAPI.getInstance().acceptFriendRequest(user.getId(),AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(getContext(), "Friend request accepted", Toast.LENGTH_SHORT).show();
                        refresh();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }));
            
            declineFriend.setOnClickListener(v -> ServiceAPI.getInstance().declineFriendRequest(user.getId(),AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(getContext(), "Friend request declined", Toast.LENGTH_SHORT).show();
                        refresh();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }));
        }

        @Override
        public void onClick(View view) {
            listener.onProfileClicked(user);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FriendRequestOnclickListener) {
            listener = (FriendRequestOnclickListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement FriendRequestOnclickListener");
        }
    }
}
