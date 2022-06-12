package com.example.events.Activities.Fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
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
            /*AlertDialog.Builder dialogBuilding = new AlertDialog.Builder(getContext());
            final View attendPopUp = getLayoutInflater().inflate(R.layout.activity_attend_event_pop_up, null);
            dialogBuilding.setView(attendPopUp);
            AlertDialog dialog = dialogBuilding.create();
            dialogBuilding.show();*/

            /*Intent intent = new Intent(getContext(), AttendEventPopUpActivity.class);
            getContext().startActivity(intent);*/

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.activity_attend_event_pop_up, null);
            PopupWindow popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);

            no = (Button)popupView.findViewById(R.id.no);
            no.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            popupWindow.showAsDropDown(no, 50, 0);


        });
    }

}
