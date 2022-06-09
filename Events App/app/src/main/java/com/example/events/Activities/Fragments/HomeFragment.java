package com.example.events.Activities.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.events.Activities.Fragments.SearchUsersFragment;
import com.example.events.R;

public class HomeFragment extends Fragment {
    private HomeFragmentListener listener;
    private ImageButton search;
    private ImageButton logout;
    private View view;

    public interface HomeFragmentListener {
        void onSearchClicked();
        void onLogoutClicked();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        search = (ImageButton) view.findViewById(R.id.searchUsers);
        logout = (ImageButton) view.findViewById(R.id.logout);
        search.setOnClickListener(view -> {
            listener.onSearchClicked();
        });

        logout.setOnClickListener(view -> {
            listener.onLogoutClicked();
        });
        return view;
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
