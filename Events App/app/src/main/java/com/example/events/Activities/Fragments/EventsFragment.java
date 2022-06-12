package com.example.events.Activities.Fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.events.R;

public class EventsFragment extends Fragment {
    private View view;
    private TextView rectangle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.events_fragment, container, false);
        initFields();
        setButtonListener();
        return view;
    }

    private void initFields() {
        rectangle = (TextView) view.findViewById(R.id.event_rectangle);
    }

    private void setButtonListener() {
        rectangle.setOnClickListener(view -> {
           getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AttendEventFragment()).commit();
        });
    }
}