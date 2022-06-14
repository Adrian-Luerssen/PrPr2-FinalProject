package com.example.events.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.events.R;

public class HomeFragment extends Fragment {
    private HomeFragmentListener listener;
    private ImageButton rate_events;

    public interface HomeFragmentListener {
        void onSearchClicked();
        void onLogoutClicked();
        void onExploreClicked();
        void onMyEventsClicked();
        void onRateClicked();
        void onCreateClicked();
        void onTimelineClicked();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.home_fragment, container, false);

        ImageButton search = (ImageButton) view1.findViewById(R.id.searchUsers);
        ImageButton logout = (ImageButton) view1.findViewById(R.id.logout);
        ImageButton explore = (ImageButton) view1.findViewById(R.id.explore_events);
        ImageButton my_events = (ImageButton) view1.findViewById(R.id.myEvents);
        ImageButton create_events = (ImageButton) view1.findViewById(R.id.createEvents);
        ImageButton timeline = (ImageButton) view1.findViewById(R.id.timeline);

        search.setOnClickListener(view -> {
            listener.onSearchClicked();
        });

        logout.setOnClickListener(view -> {
            listener.onLogoutClicked();
        });

        explore.setOnClickListener(view -> {
            listener.onExploreClicked();
        });

        my_events.setOnClickListener(view -> {
            listener.onMyEventsClicked();
        });

        create_events.setOnClickListener(view -> {
            listener.onCreateClicked();
        });

        timeline.setOnClickListener(view -> {
            listener.onTimelineClicked();
        });

        return view1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragmentListener) {
            listener = (HomeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
