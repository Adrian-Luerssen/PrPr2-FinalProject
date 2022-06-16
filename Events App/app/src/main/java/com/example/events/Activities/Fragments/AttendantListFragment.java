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
    private final Event event;
    private RecyclerView attendantRecView;
    private List<Comment> attendantList;
    private AttendListFragmentOnClickListener listener;

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
        View view = inflater.inflate(R.layout.attendant_list_fragment, container, false);
        ImageView back = (ImageView) view.findViewById(R.id.back_button);
        attendantRecView = (RecyclerView) view.findViewById(R.id.attendant_rec_view);
        attendantRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        back.setOnClickListener(view1 -> listener.onBackClicked());
        ServiceAPI.getInstance().getEventAssistances(event.getId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Comment>>() {

            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                attendantList = response.body();
                updateAtendants();
            }


            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });


        return view;
    }

    private void updateAtendants() {

        AttendantAdapter attendantAdapter = new AttendantAdapter(attendantList);
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

        private User user;
        private final TextView username;
        private final ImageView profilePicture;

        public AttendantHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_search_item, parent, false));
            itemView.setOnClickListener(this);
            username = (TextView) itemView.findViewById(R.id.search_user_name);
            profilePicture = (ImageView) itemView.findViewById(R.id.search_user_image);
            ImageButton addFriend = (ImageButton) itemView.findViewById(R.id.addUser);
            addFriend.setVisibility(View.INVISIBLE);
        }

        public void bind(Comment comment) {
            this.username.setText(comment.getName() + " " + comment.getLastName());
            ServiceAPI.getInstance().getUser(comment.getUserId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.body().size() > 0){
                        user = response.body().get(0);

                        DownloadImageTask.loadImage(getContext(), user.getImageURL(), profilePicture,R.drawable.boy1);
                    } else {
                        profilePicture.setImageResource(R.drawable.boy1);
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {

                }
            });


        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), this.comment.getName() + " clicked!", Toast.LENGTH_SHORT).show();
            listener.onProfileClicked(user);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AttendListFragmentOnClickListener) {
            listener = (AttendListFragmentOnClickListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement AttendListFragmentOnClickListener");
        }
    }

}
