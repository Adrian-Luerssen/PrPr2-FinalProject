package com.example.events.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.events.Activities.Fragments.AttendEventFragment;
import com.example.events.Activities.Fragments.AttendantListFragment;
import com.example.events.Activities.Fragments.CreateEventFragment;
import com.example.events.Activities.Fragments.EditEventFragment;
import com.example.events.Activities.Fragments.EditProfileFragment;
import com.example.events.Activities.Fragments.EventsFragment;
import com.example.events.Activities.Fragments.FriendRequestFragment;
import com.example.events.Activities.Fragments.FriendsFragment;
import com.example.events.Activities.Fragments.MyEventsFragment;
import com.example.events.Activities.Fragments.OpenChatFragment;
import com.example.events.Activities.Fragments.OurMessagesFragment;
import com.example.events.Activities.Fragments.HomeFragment;
import com.example.events.Activities.Fragments.ProfileFragment;
import com.example.events.Activities.Fragments.RateEventFragment;
import com.example.events.Activities.Fragments.SearchUsersFragment;
import com.example.events.Activities.Fragments.TimelineFragment;
import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Timer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.HomeFragmentListener,
        OurMessagesFragment.OurMessagesFragmentListener,
        OpenChatFragment.ChatOnClickListener,
        SearchUsersFragment.SearchUsersOnClickListener,
        ProfileFragment.ProfileListener,
        FriendsFragment.FriendsOnClickListener,
        EventsFragment.EventsFragmentOnClickListener,
        AttendEventFragment.AttendEventFragmentOnClickListener,
        TimelineFragment.TimelineFragmentOnClickListener,
        AttendantListFragment.AttendListFragmentOnClickListener,
        FriendRequestFragment.FriendRequestOnclickListener,
        EditProfileFragment.EditProfileListener,
        EditEventFragment.EditEventListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private Timer refreshTimer = new Timer();

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
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();
        toolbar.setTitle(R.string.HomeScreen);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.EditEvent_confirm_button:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateEventFragment()).commit();
                toolbar.setTitle(R.string.createEvents);
                break;
            case R.id.myEvents:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyEventsFragment()).commit();
                toolbar.setTitle(R.string.myEvents);
                break;
            case R.id.explore_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
                toolbar.setTitle(R.string.explore_events);
                break;
            case R.id.timeline:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimelineFragment(AuthUser.getAuthUser())).commit();
                toolbar.setTitle(R.string.timeline);
                break;
            case R.id.searchUsers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchUsersFragment()).commit();
                toolbar.setTitle(R.string.search_users);
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment(AuthUser.getAuthUser(),false)).commit();
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
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new SearchUsersFragment()).commit();
        toolbar.setTitle(R.string.search_users);
    }

    @Override
    public void onCreateClicked() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new CreateEventFragment()).commit();
        toolbar.setTitle(R.string.createEvents);
    }

    @Override
    public void onMyEventsClicked() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MyEventsFragment()).commit();
        toolbar.setTitle(R.string.myEvents);
    }

    @Override
    public void onRateClicked() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new RateEventFragment()).commit();
        toolbar.setTitle(R.string.rateEvents);
    }

    @Override
    public void onExploreClicked() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new EventsFragment()).commit();
        toolbar.setTitle(R.string.explore_events);
    }

    @Override
    public void onEventClicked(Event event) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new AttendEventFragment(event)).commit();
        toolbar.setTitle(R.string.Event);
    }

    @Override
    public void onTimelineClicked() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new TimelineFragment(AuthUser.getAuthUser())).commit();
        toolbar.setTitle(R.string.timeline);
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

    @Override
    public void onUserChatClicked(User user) {
        OpenChatFragment frag = new OpenChatFragment(user);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag).commit();
        toolbar.setTitle(R.string.our_messages);

    }

    @Override
    public void viewFriends() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FriendsFragment()).commit();
        toolbar.setTitle(R.string.friends);
    }



    @Override
    public void onBackClicked() {
        getSupportFragmentManager().beginTransaction().detach(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
        if (getSupportFragmentManager().getFragments().size() <= 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            toolbar.setTitle(R.string.HomeScreen);
        }
    }

    @Override
    public void onDeleteUserClicked() {
        ServiceAPI.getInstance().deleteUser(AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "User deleted!", Toast.LENGTH_SHORT) .show();
                    onLogoutClicked();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }




    @Override
    public void onProfileClicked(User user) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ProfileFragment(user)).commit();
        toolbar.setTitle(R.string.profile);
    }

    @Override
    public void onEditClicked(Event event) {
        getSupportFragmentManager().beginTransaction().detach(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new EditEventFragment(event)).commit();
        toolbar.setTitle(R.string.editEvent);
    }

    @Override
    public void onEditProfileClicked() {
        getSupportFragmentManager().beginTransaction().detach(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new EditProfileFragment()).commit();
        toolbar.setTitle(R.string.edit_profile);
    }

    @Override
    public void viewFriendRequests() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new FriendRequestFragment()).commit();
        toolbar.setTitle(R.string.friend_requests);
    }



    @Override
    public void viewAttendence(Event event) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new AttendantListFragment(event)).commit();
        toolbar.setTitle(R.string.Event);
    }

    @Override
    public void saveEdit() {
        getSupportFragmentManager().beginTransaction().detach(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ProfileFragment(AuthUser.getAuthUser(),false)).commit();
    }

    @Override
    public void onEditEvent(Event event) {
        getSupportFragmentManager().beginTransaction().detach(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new AttendEventFragment(event)).commit();
    }
}