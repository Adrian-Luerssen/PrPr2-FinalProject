package com.example.events.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.events.DataStructures.Comment;
import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendantListFragment extends Fragment {
    private Event event;
    private View view;
    private RecyclerView attendantRecView;
    private List<Comment> attendantList;
    private AttendantAdapter attendantAdapter;
    private AttendListFragmentOnClickListener listener;
    private ImageView back;
    public interface AttendListFragmentOnClickListener {
        void onBackClicked();
        void onProfileClicked(User user);
    }
    public AttendantListFragment(Event event) {
        this.event = event;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.attendant_list_fragment, container, false);
        back = (ImageView) view.findViewById(R.id.back_button);
        attendantRecView = (RecyclerView) view.findViewById(R.id.attendant_rec_view);
        attendantRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        back.setOnClickListener(view1 -> {
            listener.onBackClicked();
        });
        ServiceAPI.getInstance().getEventAssistances(event.getId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Comment>>() {

            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                attendantList =  response.body();
                updateAtendants();
            }


            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });

        return view;
    }

    private void updateAtendants() {
        attendantAdapter = new AttendantAdapter(attendantList);
        attendantRecView.setAdapter(attendantAdapter);
    }

    private class AttendantAdapter extends RecyclerView.Adapter<AttendantHolder> {

        private final List<Comment> comments;


        private AttendantAdapter(List<Comment> comments) {
            this.comments = comments;
        }

        @Override
        public AttendantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new AttendantHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(AttendantHolder holder, int position) {
            Comment comment = comments.get(position);
            holder.bind(comment);
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }
    }

    private class AttendantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Comment comment;
        private TextView username;
        private ImageView profilePicture;
        private ImageButton addFriend;

        public AttendantHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_search_item, parent, false));
            itemView.setOnClickListener(this);
            username = (TextView) itemView.findViewById(R.id.search_user_name);
            profilePicture = (ImageView) itemView.findViewById(R.id.search_user_image);
            addFriend = (ImageButton) itemView.findViewById(R.id.addUser);
            addFriend.setVisibility(View.INVISIBLE);
        }

        public void bind(Comment comment) {
            this.comment = comment;
            this.username.setText(comment.getName() + " " + comment.getLastName());
            ServiceAPI.getInstance().getUser(comment.getUserId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    new DownloadImageTask((ImageView) itemView.findViewById(R.id.search_user_image))
                            .execute(response.body().getImageURL());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), this.user.getName() + " clicked!", Toast.LENGTH_SHORT) .show();
            ServiceAPI.getInstance().getUser(comment.getUserId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    listener.onProfileClicked(response.body());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AttendListFragmentOnClickListener) {
            listener = (AttendListFragmentOnClickListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement AttendListFragmentOnClickListener");
        }
    }

}
