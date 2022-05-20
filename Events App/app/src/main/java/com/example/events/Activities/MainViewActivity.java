package com.example.events.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.events.R;

public class MainViewActivity extends AppCompatActivity {
    private ImageButton search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        search = (ImageButton) findViewById(R.id.searchUsers);

        search.setOnClickListener(view -> {
            Intent intent = new Intent(MainViewActivity.this,SearchUsersActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
