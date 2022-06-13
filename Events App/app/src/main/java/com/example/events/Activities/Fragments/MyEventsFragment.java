package com.example.events.Activities.Fragments;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        // MOVE THE POP UP TO TIMELINE WHEN FINISHED
        dropEvent.setOnClickListener(view -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.drop_event_pop_up);

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
                    // DELETE INFORMATION OF THE EVENT SELECTED IN THE TIMELINE VIEW
                    // ServiceAPI.getInstance().updateEvent();
                }
            });

            dialog.show();
        });
    }
}