package com.example.events.Activities.Fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.events.Activities.AttendEventPopUpActivity;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

public class AttendEventFragment extends Fragment {
    private View view;
    private Button attendEvent;
    private Button no;
    private Button yes;

    private void initFields() {
        attendEvent = (Button) view.findViewById(R.id.attend_button);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.attend_event_fragment, container, false);
        initFields();
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
