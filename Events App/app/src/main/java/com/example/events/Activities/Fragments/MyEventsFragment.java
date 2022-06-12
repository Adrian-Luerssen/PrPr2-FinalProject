package com.example.events.Activities.Fragments;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.events.R;

public class MyEventsFragment extends Fragment {
    private View view;
    private TextView dropEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_events_fragment, container, false);
        initFields();
        setButtonListener();
        return view;
    }

    private void initFields() {
        dropEvent = (TextView) view.findViewById(R.id.dropEvent);
    }

    private void setButtonListener() {
        dropEvent.setOnClickListener(view -> {
            AlertDialog.Builder dialogBuilding = new AlertDialog.Builder(getContext());
            final View attendPopUp = getLayoutInflater().inflate(R.layout.drop_event_pop_up, null);
            dialogBuilding.setView(attendPopUp);
            AlertDialog dialog = dialogBuilding.create();
            dialogBuilding.show();
        });
    }
}