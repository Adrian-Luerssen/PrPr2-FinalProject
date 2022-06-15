package com.example.events.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.events.R;

public class AttendEventPopUpActivity extends AppCompatActivity {
    /*Button yes;
    Button no;
    View view;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //yes = (Button) view.findViewById(R.id.yes);
        //no = (Button) view.findViewById(R.id.no);
        //setButtonListener();
        setContentView(R.layout.activity_attend_event_pop_up);
    }

    private void setButtonListener() {
        /*yes.setOnClickListener(view -> {
            Toast.makeText(AttendEventPopUpActivity.this, "Press YES", Toast.LENGTH_SHORT).show();
            ServiceAPI.getInstance().assistEvent(0, AuthUser.getAuthUser().getToken().getToken());
        });
        no.setOnClickListener(view -> {
            Toast.makeText(AttendEventPopUpActivity.this, "Press NO", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.attend_event_fragment);
        });*/
    }
}