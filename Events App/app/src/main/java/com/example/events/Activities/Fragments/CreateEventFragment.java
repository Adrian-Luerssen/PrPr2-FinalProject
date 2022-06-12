package com.example.events.Activities.Fragments;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events.Activities.LoginActivity;
import com.example.events.Activities.SignUpActivity;
import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventFragment extends Fragment {
    private View view;
    private ImageButton selectCategory;
    private ImageButton startDate;
    private ImageButton endDate;
    private ImageView image;
    private EditText title;
    private EditText description;
    private EditText location;
    private EditText startText;
    private EditText endText;
    private Button createEvent;
    private Spinner category;
    private String selectedCategory;

    private void initFields() {
        image = (ImageView) view.findViewById(R.id.imageView);
        title = (EditText) view.findViewById(R.id.title);
        description = (EditText) view.findViewById(R.id.description);
        //selectCategory = (ImageButton) view.findViewById(R.id.options);
        category = (Spinner) view.findViewById(R.id.categoriesSpinner);
        startDate = (ImageButton) view.findViewById(R.id.calendar1);
        startText = (EditText) view.findViewById(R.id.startText);
        endDate = (ImageButton) view.findViewById(R.id.calendar2);
        endText = (EditText) view.findViewById(R.id.endText);
        location = (EditText) view.findViewById(R.id.location);
        createEvent = (Button) view.findViewById(R.id.createEvents);
        ArrayAdapter<CharSequence> sequence = ArrayAdapter.createFromResource(getContext(), R.array.categories_array, android.R.layout.simple_spinner_item);
        sequence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(sequence);
        category.setSelection(0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_events_fragment, container, false);
        initFields();
        setButtonListener();
        return view;
    }

    private void setButtonListener() {
        //selectCategory.setOnClickListener(view-> {
            category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.inputText));
                    /*ArrayAdapter<CharSequence> sequence = ArrayAdapter.createFromResource(getContext(), R.array.categories_array, android.R.layout.simple_spinner_item);
                    sequence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(sequence);
                    category.setSelection(0);*/
                    if (parent.getItemAtPosition(pos).equals("Category Event")) {

                    }
                    else {
                        selectedCategory = parent.getItemAtPosition(pos).toString();
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        //});

        startDate.setOnClickListener(view -> {
            showDatePickerDialog(startText);
        });

        endDate.setOnClickListener(view -> {
            showDatePickerDialog(endText);
        });

        //TODO: Check pleaseeee!!!!
        createEvent.setOnClickListener(view -> {
            ServiceAPI.getInstance().createEvent(new Event(title.getText().toString(), "cambiar", location.getText().toString(), description.getText().toString(), startText.getText().toString(), endText.getText().toString(), 1, selectedCategory), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getContext(), "Event created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getContext(), "Error creating event", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {
                    Toast.makeText(getContext(), R.string.bad_connection, Toast.LENGTH_SHORT).show();
                }
            });
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        image.setImageURI(selectedImageUri);
                    }
                }
    });

    public static class DatePickerFragment extends DialogFragment {
        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }


    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment date = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
                editText.setText(selectedDate);
            }
        });
        date.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}