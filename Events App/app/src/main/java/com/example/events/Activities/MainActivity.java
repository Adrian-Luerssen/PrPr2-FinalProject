package com.example.events.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.events.Activities.Fragments.OurMessagesFragment;
import com.example.events.Activities.Fragments.HomeFragment;
import com.example.events.Activities.Fragments.SearchUsersFragment;
import com.example.events.R;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.HomeFragmentListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        toolbar.setTitle(R.string.HomeScreen);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createEvents:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateEventsFragment()).commit();
                toolbar.setTitle(R.string.createEvents);
                break;
            case R.id.myEvents:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyEventsFragment()).commit();
                toolbar.setTitle(R.string.myEvents);
                break;
            case R.id.explore_events:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
                toolbar.setTitle(R.string.explore_events);
                break;
            case R.id.timeline:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimelineFragment()).commit();
                toolbar.setTitle(R.string.timeline);
                break;
            case R.id.searchUsers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchUsersFragment()).commit();
                toolbar.setTitle(R.string.search_users);
                break;
            case R.id.nav_profile:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                toolbar.setTitle(R.string.profile);
                break;
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OurMessagesFragment()).commit();
                toolbar.setTitle(R.string.our_messages);
                break;
            case R.id.logout:
                onLogoutClicked();
                break;
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                toolbar.setTitle(R.string.HomeScreen);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSearchClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchUsersFragment()).commit();
        toolbar.setTitle(R.string.search_users);
    }

    @Override
    public void onLogoutClicked() {
        SharedPreferences prefs = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Token", null);
        editor.putString("Email", null);
        editor.putString("Password", null);
        editor.commit();
        finish();
    }
}