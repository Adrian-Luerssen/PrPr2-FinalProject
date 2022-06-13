package com.example.events.Activities.Fragments;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.events.DataStructures.Event;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.R;

public class AttendEventFragment extends Fragment {
    private View view;
    private Button attendEvent;
    private ImageView image;
    private TextView name;
    private TextView category;
    private TextView description;
    private TextView startDate;
    private TextView endDate;
    private TextView location;
    private Event event;

    public AttendEventFragment (Event event) {
        this.event = event;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.attend_event_fragment, container, false);
        attendEvent = (Button) view.findViewById(R.id.attend_button);
        image = view.findViewById(R.id.imageView);
        name = (TextView) view.findViewById(R.id.name_attend_event);
        category = (TextView) view.findViewById(R.id.category_attend_event);
        description = (TextView) view.findViewById(R.id.description_attend_event);
        startDate = (TextView) view.findViewById(R.id.start_attend_event);
        endDate = (TextView) view.findViewById(R.id.end_attend_event);
        location = (TextView) view.findViewById(R.id.location_attend_event);

        new DownloadImageTask(image).execute(event.getImageURL());
        name.setText(event.getName());
        category.setText(event.getEventType());
        description.setText(event.getDescription());
        startDate.setText(event.getStartDate());
        endDate.setText(event.getEndDate());
        location.setText(event.getLocation());

        setButtonListener();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setButtonListener() {
        attendEvent.setOnClickListener(view -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.activity_attend_event_pop_up);

            Button no = dialog.findViewById(R.id.no);
            Button yes = dialog.findViewById(R.id.yes);

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view){
                    dialog.dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // GET DATA FROM API AND SEND INFO ABOUT EVENT TO MyEvents
                    // ServiceAPI.getInstance().getEvent();
                }
            });

            dialog.show();
        });
    }
}
