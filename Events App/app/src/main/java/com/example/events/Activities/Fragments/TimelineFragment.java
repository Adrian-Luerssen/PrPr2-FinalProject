package com.example.events.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimelineFragment extends Fragment {
    public View view;
    private RecyclerView timelineRecView;
    private List<Event> timelineList;
    private TimelineAdapter timelineAdapter;
    private TimelineFragmentOnClickListener listener;
    private User user;

    public interface TimelineFragmentOnClickListener {
        void onTimelineClicked();
    }

    public TimelineFragment(User user) {
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.timeline_item, container, false);
        timelineRecView = (RecyclerView) view.findViewById(R.id.timeline_rec_view);
        timelineRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        getAPI();

        return view;
    }

    private void getAPI() {
        ServiceAPI.getInstance().getEvents(AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    timelineList = new ArrayList<>();
                    // Add the events just if the users are the same
                    timelineList = (ArrayList<Event>) response.body();
                    updateUI();
                }

            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.bad_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        timelineAdapter = new TimelineFragment.TimelineAdapter(timelineList);
        timelineRecView.setAdapter(timelineAdapter);
    }

    private class TimelineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {
        private final List<Event> timelineList;

        private TimelineAdapter(List<Event> timelineList) {
            this.timelineList = timelineList;
        }

        @Override
        public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new TimeLineViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TimeLineViewHolder holder, int position) {
            Event event = timelineList.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return timelineList.size();
        }
    }

    private class TimeLineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView timelineName;
        private TextView timelineCat;
        private TextView timelineStartDate;
        private TextView timelineLoc;
        private ImageView timelineImage;
        private Event event;

        public TimeLineViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.timeline_fragment, parent, false));
            itemView.setOnClickListener(this);
            timelineImage = (ImageView) itemView.findViewById(R.id.imageView1);
            timelineName = (TextView) itemView.findViewById(R.id.name_event);
            timelineCat = (TextView) itemView.findViewById(R.id.category_event);
            timelineStartDate = (TextView) itemView.findViewById(R.id.start_date);
            timelineLoc = (TextView) itemView.findViewById(R.id.location_event);

        }

        public void bind(Event event) {
            this.event = event;
            new DownloadImageTask(timelineImage).execute(event.getImageURL());
            this.timelineName.setText(event.getName());
            this.timelineCat.setText(event.getEventType());
            this.timelineStartDate.setText(event.getStartDate());
            this.timelineLoc.setText(event.getLocation());
        }

        @Override
        public void onClick(View view) {
            listener.onTimelineClicked();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TimelineFragment.TimelineFragmentOnClickListener) {
            listener = (TimelineFragment.TimelineFragmentOnClickListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement TimelineFragmentOnClickListener");
        }
    }
}
