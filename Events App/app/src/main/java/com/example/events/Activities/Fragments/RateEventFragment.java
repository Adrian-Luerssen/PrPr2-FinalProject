package com.example.events.Activities.Fragments;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.events.R;

public class RateEventFragment extends Fragment {
    private View view;
    private Button rateEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rate_event_fragment, container, false);
        initFields();
        setButtonListener();
        return view;
    }

    private void initFields() {
        rateEvent = (Button) view.findViewById(R.id.rate_event);
    }

    private void setButtonListener() {
        rateEvent.setOnClickListener(view -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.rating_bar_event);

            Button rate = dialog.findViewById(R.id.rate);

            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view){
                    dialog.dismiss();
                    // CHANGE IT: WE NEED TO SEND THE RATE TO THE API
                }
            });
            dialog.show();
        });
    }
}