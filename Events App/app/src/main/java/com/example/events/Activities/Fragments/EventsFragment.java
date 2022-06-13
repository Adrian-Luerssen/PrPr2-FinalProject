package com.example.events.Activities.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {
    private View view;
    private TextView rectangle;
    private EventsFragmentOnClickListener listener;
    private RecyclerView eventRecView;
    private List<Event> eventList;
    private EventAdapter eventAdapter;

    public interface EventsFragmentOnClickListener{
        void onAttendEventClicked(Event event);
    }

    public EventsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.events_fragment, container, false);
        eventRecView = (RecyclerView) view.findViewById(R.id.events_rec_view);
        eventRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        getAPI();
        //initFields();
        //setButtonListener();
        return view;
    }

    private void getAPI() {
        ServiceAPI.getInstance().getEvents(AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()) {
                    eventList = new ArrayList<>();
                    eventList = (ArrayList<Event>) response.body();
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
        eventAdapter = new EventAdapter(eventList);
        eventRecView.setAdapter(eventAdapter);
    }

    /*private void initFields() {
        rectangle = (TextView) view.findViewById(R.id.event_rectangle);
    }*/

    /*private void setButtonListener() {
        rectangle.setOnClickListener(view -> {
           getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AttendEventFragment()).commit();
           //listener.onAttendEventClicked();
        });
    }*/

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {
        private final List<Event> eventList;

        private EventAdapter(List<Event> eventList) {
            this.eventList = eventList;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            Event event = eventList.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }
    }

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Event event;
        private ImageView picture;
        private TextView eventName;
        private TextView startDate;
        private TextView location;
        private TextView rectangle;

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.rectangle_event, parent, false));
            itemView.setOnClickListener(this);
            picture = (ImageView) itemView.findViewById(R.id.imageView1);
            eventName = (TextView) itemView.findViewById(R.id.event_name);
            startDate = (TextView) itemView.findViewById(R.id.event_start_date);
            location = (TextView) itemView.findViewById(R.id.event_location);
            rectangle = (TextView) view.findViewById(R.id.event_rectangle);

            /*rectangle.setOnClickListener(view -> {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AttendEventFragment(event)).commit();
                listener.onAttendEventClicked(event);
            });*/
        }

        public void bind(Event event) {
            this.event = event;
            new DownloadImageTask((ImageView) itemView.findViewById(R.id.imageView1)).execute(event.getImageURL());
            this.eventName.setText(event.getName());
            this.startDate.setText(event.getStartDate());
            this.location.setText(event.getLocation());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(), this.event.getName() + " clicked!", Toast.LENGTH_SHORT) .show();
            listener.onAttendEventClicked(this.event);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventsFragmentOnClickListener) {
            listener = (EventsFragmentOnClickListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement EventsFragmentOnclickListener");
        }
    }
}