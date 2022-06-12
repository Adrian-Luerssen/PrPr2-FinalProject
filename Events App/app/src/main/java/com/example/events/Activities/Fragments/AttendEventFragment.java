package com.example.events.Activities.Fragments;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.events.R;

public class AttendEventFragment extends Fragment {
    private View view;
    private Button attendEvent;

    private void initFields() {
        attendEvent = (Button) view.findViewById(R.id.attend_button);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.attend_event_fragment, container, false);
        initFields();
        setButtonListener();
        return view;
    }

    private void setButtonListener() {
        attendEvent.setOnClickListener(view -> {
            AlertDialog.Builder dialogBuilding = new AlertDialog.Builder(getContext());
            final View attendPopUp = getLayoutInflater().inflate(R.layout.activity_attend_event_pop_up, null);
            dialogBuilding.setView(attendPopUp);
            AlertDialog dialog = dialogBuilding.create();
            dialogBuilding.show();
        });
    }
}
