package com.example.events.Activities.Fragments;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {
    private View view;
    private EventsFragmentOnClickListener listener;
    private RecyclerView eventRecView;
    private List<Event> eventList;
    private EventAdapter eventAdapter;
    private EditText name;
    private EditText location;
    private ImageButton datePicker;
    private EditText date;
    private Button searchButton;
    private Button searchBestButton;
    private Button clearButton;

    public interface EventsFragmentOnClickListener{
        void onEventClicked(Event event);
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
        name = (EditText) view.findViewById(R.id.event_name);
        location = (EditText) view.findViewById(R.id.event_location);
        date = (EditText) view.findViewById(R.id.event_date);
        datePicker = (ImageButton) view.findViewById(R.id.calendar1);
        searchButton = (Button) view.findViewById(R.id.searchButton);
        searchBestButton = (Button) view.findViewById(R.id.searchButtonBest);
        clearButton = (Button) view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(view1 -> {
            name.setText("");
            location.setText("");
            date.setText("");
        });
        searchButton.setOnClickListener(view1 -> {
            searchAll();
        });
        searchBestButton.setOnClickListener(view1 -> {
            searchBest();
        });
        searchAll();

        datePicker.setOnClickListener(view1 -> {
            showDatePickerDialog(date);
        });
        //initFields();
        //setButtonListener();
        return view;
    }

    private void searchBest() {
        ServiceAPI.getInstance().getBestEvents(AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()){
                    eventList = response.body();
                    updateUI();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.bad_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchAll() {
        Map<String, String> map = new HashMap<>();
        if (!name.getText().toString().isEmpty()) {
            map.put("keyword", name.getText().toString());
        }
        if (!location.getText().toString().isEmpty()) {
            map.put("location", location.getText().toString());
        }
        if (!date.getText().toString().isEmpty()) {
            map.put("date", date.getText().toString());
        }
        if (map.isEmpty()) {
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
        }else {
            ServiceAPI.getInstance().searchEvents(map,AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Event>>() {
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

    }

    private void updateUI() {
        EventAdapter eventAdapter = new EventAdapter(eventList);
        eventRecView.setAdapter(eventAdapter);
    }

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
        private final TextView eventName;
        private final TextView startDate;
        private final TextView location;
        private final ImageView picture;

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.rectangle_event, parent, false));
            itemView.setOnClickListener(this);
            picture = (ImageView) itemView.findViewById(R.id.imageView1);
            eventName = (TextView) itemView.findViewById(R.id.event_name);
            startDate = (TextView) itemView.findViewById(R.id.event_start_date);
            location = (TextView) itemView.findViewById(R.id.event_location);
            TextView rectangle = (TextView) view.findViewById(R.id.event_rectangle);

        }

        public void bind(Event event) {
            this.event = event;
            DownloadImageTask.loadImage(getContext(), event.getImageURL(), picture,R.drawable.image);
            this.eventName.setText(event.getName());
            this.startDate.setText(event.getStartDate());
            this.location.setText(event.getLocation());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(), this.event.getName() + " clicked!", Toast.LENGTH_SHORT) .show();
            listener.onEventClicked(this.event);
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

    public static class DatePickerFragment extends DialogFragment {
        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }


    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment date = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
                editText.setText(selectedDate);
            }
        });
        date.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

}