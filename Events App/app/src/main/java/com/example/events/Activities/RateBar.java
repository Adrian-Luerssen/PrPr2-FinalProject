package com.example.events.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.events.R;

public class RateBar extends AppCompatActivity {
    RatingBar ratingbar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButtonClick();
    }

    public void addListenerOnButtonClick(){
        ratingbar = (RatingBar)findViewById(R.id.ratingBar);
        button = (Button)findViewById(R.id.rate);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                String rating = String.valueOf(ratingbar.getRating());
                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
            }

        });
    }
}
