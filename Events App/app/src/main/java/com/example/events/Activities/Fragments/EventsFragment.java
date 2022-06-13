package com.example.events.Activities.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.User;
import com.example.events.R;

public class EventsFragment extends Fragment {
    private View view;
    private TextView rectangle;
    //private EventsFragmentOnClickListener listener;

    /*public interface EventsFragmentOnClickListener{
        void onAttendEventClicked();
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.events_fragment, container, false);
        initFields();
        //setButtonListener();
        return view;
    }

    private void initFields() {
        rectangle = (TextView) view.findViewById(R.id.event_rectangle);
    }

    /*private void setButtonListener() {
        rectangle.setOnClickListener(view -> {
           getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AttendEventFragment()).commit();
           //listener.onAttendEventClicked();
        });
    }*/

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {
        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            /*Event event = event.;
            holder.*/
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public EventHolder(LayoutInflater layoutInflater, View itemView) {
            super(itemView);

            rectangle.setOnClickListener(view -> {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AttendEventFragment()).commit();
            });
        }

        @Override
        public void onClick(View view) {

        };
    }
}