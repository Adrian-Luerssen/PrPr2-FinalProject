package com.example.events.Activities.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.Message;
import com.example.events.DataStructures.Users.User;
import com.example.events.DataStructures.Users.UserList;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OurMessagesFragment extends Fragment {
    private OurMessagesFragmentListener listener;
    private UserAdapter userAdapter;
    private EditText searchbar;
    private RecyclerView userRecView;
    private UserList userList;



    public interface OurMessagesFragmentListener {
        void onUserChatClicked(User user);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.our_messages_fragment, container, false);
        searchbar = (EditText) view1.findViewById(R.id.search_users);
        ImageButton search = (ImageButton) view1.findViewById(R.id.search_button);
        userRecView = (RecyclerView) view1.findViewById(R.id.our_messages_users_list);
        userRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        getUserMessages();

        search.setOnClickListener(view -> {
            filterUserlist(searchbar.getText().toString());
        });
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterUserlist(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view1;
    }

    private void filterUserlist(String searchString) {
        if (searchString.isEmpty()) {
            userRecView.setAdapter(userAdapter);
        } else {
            userRecView.setAdapter(new UserAdapter(userList.getFilteredUsers(searchString)));
        }
    }


    private void getUserMessages() {
        ServiceAPI.getInstance().getUsersMessages(AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<User>>() {
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
        userAdapter = new UserAdapter(userList.getUsers());
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
        private final TextView username;
        protected final TextView message;
        private final TextView date;
        private ImageView profilePicture;

        public UserHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.message_user_list_item, parent, false));
            itemView.setOnClickListener(this);
            username = (TextView) itemView.findViewById(R.id.message_list_username);
             profilePicture = (ImageView) itemView.findViewById(R.id.message_user_image);
            message = (TextView) itemView.findViewById(R.id.message_user_list_message);
            date = (TextView) itemView.findViewById(R.id.message_list_date);
        }

        public void bind(User user) {
            this.user = user;
            this.username.setText(user.getName()+" "+user.getLastName());
            getLastMessage(user);

            DownloadImageTask.loadImage(getContext(), user.getImageURL(),profilePicture ,R.drawable.boy1);

        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), this.user.getName() + " clicked!", Toast.LENGTH_SHORT) .show();
            listener.onUserChatClicked(this.user);
        }
        private void getLastMessage(User user) {
            ServiceAPI.getInstance().getMessages(user.getId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    if (response.isSuccessful()){
                        assert response.body() != null;
                        if (response.body().size() > 0){
                            message.setText(response.body().get(response.body().size()-1).getContent());
                            date.setText(response.body().get(response.body().size()-1).getDate());
                        } else {
                            message.setText("");
                            date.setText("");
                        }
                    } else {
                        message.setText("");
                        date.setText("");
                        Toast.makeText(getContext(), R.string.unable_to_find_users, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {
                    Toast.makeText(getContext(), R.string.bad_connection, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OurMessagesFragmentListener) {
            listener = (OurMessagesFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OurMessagesFragmentListener");
        }
    }
}
