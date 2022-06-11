package com.example.events.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.events.R;

import java.util.regex.Pattern;


public class CreateEventActivity extends AppCompatActivity {
    private ImageButton selectCategory;
    private ImageButton startDate;
    private ImageButton endDate;
    private ImageView image;
    private EditText title;
    private EditText description;
    private EditText location;
    private Button createEvent;
    private RecyclerView eventRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_events_fragment);
        initFields();
        /*getUserFromSharedPref();*/
    }

    private void initFields() {
        image = (ImageView) findViewById(R.id.imageView);
        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        selectCategory = (ImageButton) findViewById(R.id.options);
        startDate = (ImageButton) findViewById(R.id.calendar1);
        endDate = (ImageButton) findViewById(R.id.calendar2);
        location = (EditText) findViewById(R.id.location);
        createEvent = (Button) findViewById(R.id.createEvents);
    }
}
