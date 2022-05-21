package com.example.events.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.events.R;

public class MainViewActivity extends AppCompatActivity {
    private ImageButton search;
    private ImageButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        search = (ImageButton) findViewById(R.id.searchUsers);
        logout = (ImageButton) findViewById(R.id.logout);

        search.setOnClickListener(view -> {
            Intent intent = new Intent(MainViewActivity.this,SearchUsersActivity.class);
            startActivity(intent);
            finish();
        });

        logout.setOnClickListener(view -> {
            SharedPreferences prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Token", null);
            editor.putString("Email", null);
            editor.putString("Password", null);
            editor.commit();
            finish();
        });
    }
}
