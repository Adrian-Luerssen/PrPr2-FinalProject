package com.example.events.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.DataStructures.Users.UserList;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchUsersFragment extends Fragment {

    private TextView numResults;
    private EditText searchbar;
    private RecyclerView userRecView;
    private UserList userList;
    private SearchUsersOnClickListener listener;

    public interface SearchUsersOnClickListener {
        void onProfileClicked(User user);
    }

    public SearchUsersFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.search_users_fragment, container, false);
        searchbar = (EditText) view1.findViewById(R.id.search_users);
        numResults = (TextView) view1.findViewById(R.id.num_results);
        ImageButton search = (ImageButton) view1.findViewById(R.id.search_button);
        userRecView = (RecyclerView) view1.findViewById(R.id.user_search_rec_view);
        userRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        search.setOnClickListener(view -> {
            searchAPI();
        });
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAPI();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view1;

    }

    private void searchAPI() {
        ServiceAPI.getInstance().searchUsers(searchbar.getText().toString(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    userList = new UserList();
                    userList.setUserList(response.body());
                    updateUI();
                } else {
                    Toast.makeText(getContext(), R.string.unable_to_find_users, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.bad_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        UserAdapter userAdapter = new UserAdapter(userList.getUsers());
        numResults.setText(getString(R.string.matchingFormat, userList.getUsers().size()));
        userRecView.setAdapter(userAdapter);
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {

        private final List<User> userList;


        private UserAdapter(List<User> userList) {
            this.userList = userList;
        }

        @Override
        public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new UserHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(UserHolder holder, int position) {
            User user = userList.get(position);
            holder.bind(user);
        }



        @Override
        public int getItemCount() {
            return userList.size();
        }



    }


    // View Holder
    private class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private User user;
        private TextView username;
        private ImageView profilePicture;
        private ImageButton addFriend;
        private boolean isFriend;


        public UserHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_search_item, parent, false));
            itemView.setOnClickListener(this);
            username = (TextView) itemView.findViewById(R.id.search_user_name);
            profilePicture = (ImageView) itemView.findViewById(R.id.search_user_image);
            addFriend = (ImageButton) itemView.findViewById(R.id.addUser);

        }

        public void  clickFunction(){
            ServiceAPI.getInstance().getFriends(AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        for (User u : response.body()) {
                            if (user.getId() == u.getId()){
                                isFriend = true;
                                addFriend.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_person_add_disabled_24));
                                addFriend.setOnClickListener( view1 -> {
                                    //remove friend
                                    clickFunction();
                                });
                            }
                        }

                    }
                    if (!isFriend){
                        addFriend.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_person_add_24));
                        addFriend.setOnClickListener(view -> {
                            ServiceAPI.getInstance().sendFriendRequest(user.getId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(getContext(), R.string.friend_added, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), R.string.unable_to_add_friend, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getContext(), R.string.bad_connection, Toast.LENGTH_SHORT).show();
                                }
                            });
                            clickFunction();
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {

                }
            });
        }
        public void bind(User user) {
            this.user = user;
            this.username.setText(user.getName()+" "+user.getLastName());
            if (AuthUser.getAuthUser().getId() == user.getId()){
                addFriend.setVisibility(View.INVISIBLE);
            } else {
                clickFunction();
            }
            new DownloadImageTask((ImageView) itemView.findViewById(R.id.search_user_image)).execute(user.getImageURL());
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(getContext(), this.user.getName() + " clicked!", Toast.LENGTH_SHORT) .show();
            listener.onProfileClicked(this.user);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchUsersOnClickListener) {
            listener = (SearchUsersOnClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchUsersOnclickListener");
        }
    }
}