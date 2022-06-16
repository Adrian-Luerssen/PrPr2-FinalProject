package com.example.events.Activities.Fragments;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events.DataStructures.Comment;
import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendEventFragment extends Fragment {
    private Button attendEvent;
    private ImageView image;
    private TextView name;
    private TextView category;
    private TextView description;
    private TextView startDate;
    private TextView endDate;
    private TextView location;
    private Event event;
    private boolean isAttending;
    private RecyclerView recyclerView;
    private List<Comment> comments;
    private List<User> assistences;
    private CommentAdapter commentAdapter;
    private ImageView back;
    private Button viewAttendence;
    private NumberPicker addRating;
    private Button submitComment;
    private EditText inputComment;
    private AttendEventFragmentOnClickListener listener;

    private class UpdateComment{
        @Expose
        @SerializedName("comentary")
        private String comment;
        @Expose
        @SerializedName("puntuation")
        private int rating;
        public UpdateComment(String comment, int rating) {
            this.comment = comment;
            this.rating = rating;
        }
    }
    public interface AttendEventFragmentOnClickListener {
        void onBackClicked();
        void viewAttendence(Event event);
        void onProfileClicked(User user);
    }
    public AttendEventFragment(Event event) {
        this.event = event;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attend_event_fragment, container, false);
        attendEvent = (Button) view.findViewById(R.id.attend_button);
        image = view.findViewById(R.id.imageView);
        name = (TextView) view.findViewById(R.id.name_attend_event);
        category = (TextView) view.findViewById(R.id.category_attend_event);
        description = (TextView) view.findViewById(R.id.description_attend_event);
        startDate = (TextView) view.findViewById(R.id.start_attend_event);
        endDate = (TextView) view.findViewById(R.id.end_attend_event);
        location = (TextView) view.findViewById(R.id.location_attend_event);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        back = (ImageView) view.findViewById(R.id.back_button);
        viewAttendence = (Button) view.findViewById(R.id.attendants_button);
        addRating = (NumberPicker) view.findViewById(R.id.numberPicker);
        submitComment = (Button) view.findViewById(R.id.buttonComment);
        inputComment = (EditText) view.findViewById(R.id.editTextComment);
        addRating.setMaxValue(10);
        addRating.setMinValue(0);

        submitComment.setOnClickListener(view1 -> {
            if (isAttending) {
                UpdateComment comment = new UpdateComment(inputComment.getText().toString(), addRating.getValue());
                ServiceAPI.getInstance().editAssistance(AuthUser.getAuthUser().getId(), event.getId(), comment, AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            getComments();
                            inputComment.setText("");
                            addRating.setValue(0);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            } else {
                Toast.makeText(view.getContext(), R.string.Comment_Error, Toast.LENGTH_SHORT).show();
                inputComment.setText("");
                addRating.setValue(0);
            }
        });

        getComments();

        viewAttendence.setOnClickListener(view1 -> {
            listener.viewAttendence(event);
        });
        back.setOnClickListener(view1 -> {
            listener.onBackClicked();
        });



        DownloadImageTask.loadImage(getContext(), event.getImageURL(), image,R.drawable.image);


        name.setText(event.getName());
        category.setText(event.getEventType());
        description.setText(event.getDescription());
        startDate.setText(event.getStartDate());
        endDate.setText(event.getEndDate());
        location.setText(event.getLocation());



        return view;
    }
    private void getComments(){
        ServiceAPI.getInstance().getEventAssistances(event.getId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Comment>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                comments =  new ArrayList<>();
                for (Comment comment : response.body()) {
                    if (comment.getText() != null){
                        comments.add(comment);
                    }
                    if (comment.getUserId() == AuthUser.getAuthUser().getId()) {
                        isAttending = true;

                    }
                }
                updateAttendButton();
                updateComments();
            }


            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }
    private void updateComments() {
        commentAdapter = new CommentAdapter(comments);
        recyclerView.setAdapter(commentAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setButtonListener() {
        attendEvent.setOnClickListener(view -> {

            getComments();
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.activity_attend_event_pop_up);
            TextView text = dialog.findViewById(R.id.attend_text);
            TextView mess = dialog.findViewById(R.id.attend_message);
            Button no = dialog.findViewById(R.id.no);
            Button yes = dialog.findViewById(R.id.yes);
            if (isAttending) {
                text.setText(R.string.unattend_text);
                mess.setText(R.string.unattend_message);
            } else {
                text.setText(getText(R.string.attending_Event));
                mess.setText(getText(R.string.add_Event));
            }
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // GET DATA FROM API AND SEND INFO ABOUT EVENT TO MyEvents
                    if (!isAttending) {
                        ServiceAPI.getInstance().assistEvent(event.getId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    dialog.dismiss();
                                    isAttending = true;
                                    updateAttendButton();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    } else {
                        ServiceAPI.getInstance().deleteAssistance(AuthUser.getAuthUser().getId(), event.getId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    dialog.dismiss();
                                    isAttending = false;
                                    updateAttendButton();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }


                }
            });

            dialog.show();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateAttendButton() {
        System.out.printf("isAttending: %b", isAttending);
        if (!isAttending) {
            attendEvent.setText("Attend");
        } else {
            attendEvent.setText("Unattend");
        }
        setButtonListener();
    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

        private final List<Comment> comments;


        private CommentAdapter(List<Comment> comments) {
            this.comments = comments;
        }

        @Override
        public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new CommentHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CommentHolder holder, int position) {
            Comment comment = comments.get(position);
            holder.bind(comment);
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }
    }

    private class CommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Comment comment;
        private User user;
        private final TextView username;
        private final ImageView profilePicture;
        protected final TextView message;
        private final TextView score;

        public CommentHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.event_comment, parent, false));
            itemView.setOnClickListener(this);
            username = (TextView) itemView.findViewById(R.id.comment_user_name);
            profilePicture = (ImageView) itemView.findViewById(R.id.comment_image);
            message = (TextView) itemView.findViewById(R.id.user_comment);
            score = (TextView) itemView.findViewById(R.id.user_score);
        }

        public void bind(Comment comment) {
            this.comment = comment;
            this.username.setText(comment.getName() + " " + comment.getLastName());

            message.setText(comment.getText());
            score.setText(String.valueOf(comment.getScore()));
            ServiceAPI.getInstance().getUser(comment.getUserId(), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    user = response.body().get(0);

                    DownloadImageTask.loadImage(getContext(), user.getImageURL(), profilePicture,R.drawable.boy1);

                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {

                }
            });

        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), this.user.getName() + " clicked!", Toast.LENGTH_SHORT) .show();
            listener.onProfileClicked(user);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AttendEventFragmentOnClickListener) {
            listener = (AttendEventFragmentOnClickListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement AttendEventFragmentOnClickListener");
        }
    }

}
