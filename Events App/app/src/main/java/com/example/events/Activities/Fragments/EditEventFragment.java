package com.example.events.Activities.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.events.R;


public class EditEventFragment extends Fragment {

    private ImageView imageView;
    private EditText title;
    private EditText description;
    private EditText location;
    private EditText startText;
    private EditText endText;
    private Spinner category;

    public EditEventFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //TODO: No se puede hacer sin crear un event antes
    /*@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    }*/


}
