package com.example.events.Activities.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.events.DataStructures.Event;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.R;

public class TimelineEventFragment extends Fragment {

    private final Event event;

    public TimelineEventFragment (Event event) {
        this.event = event;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.event_timeline_fragment, container, false);
        ImageView image = view.findViewById(R.id.imageView);
        TextView name = (TextView) view.findViewById(R.id.name_attend_event);
        TextView category = (TextView) view.findViewById(R.id.category_attend_event);
        TextView description = (TextView) view.findViewById(R.id.description_attend_event);
        TextView startDate = (TextView) view.findViewById(R.id.start_attend_event);
        TextView endDate = (TextView) view.findViewById(R.id.end_attend_event);
        TextView location = (TextView) view.findViewById(R.id.location_attend_event);

        new DownloadImageTask(image).execute(event.getImageURL());
        name.setText(event.getName());
        category.setText(event.getEventType());
        description.setText(event.getDescription());
        startDate.setText(event.getStartDate());
        endDate.setText(event.getEndDate());
        location.setText(event.getLocation());

        return view;

    }

}
