package com.example.events.Activities.Fragments;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.events.DataStructures.Event;
import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEventFragment extends Fragment {
    private View view;
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
    private EditText numberofpeople;
    private EditText url;

    private void initFields() {
        image = (ImageView) view.findViewById(R.id.EditEvent_imageview);
        title = (EditText) view.findViewById(R.id.EditEvent_title_input);
        numberofpeople = (EditText) view.findViewById(R.id.numberofpeople);
        description = (EditText) view.findViewById(R.id.EditEvent_description_input);
        category = (Spinner) view.findViewById(R.id.EditEvent_category_spinner);
        startDate = (ImageButton) view.findViewById(R.id.EditEvent_calendar1);
        startText = (EditText) view.findViewById(R.id.EditEvent_startDate_input);
        endDate = (ImageButton) view.findViewById(R.id.EditEvent_calendar2);
        endText = (EditText) view.findViewById(R.id.EditEvent_endDate_input);
        location = (EditText) view.findViewById(R.id.EditEvent_location_input);
        createEvent = (Button) view.findViewById(R.id.EditEvent_confirm_button);
        ArrayAdapter<CharSequence> sequence = ArrayAdapter.createFromResource(getContext(), R.array.categories_array, android.R.layout.simple_spinner_item);
        sequence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(sequence);
        category.setSelection(0);
        url = view.findViewById(R.id.imageURL);
        selectedCategory = "";

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
                    if (pos > 0){
                        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
                    }
                    selectedCategory = parent.getItemAtPosition(pos).toString();
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

        createEvent.setOnClickListener(view -> {
            if (!selectedCategory.equals("Category Event")){
                ServiceAPI.getInstance().createEvent(new Event(title.getText().toString(), url.getText().toString(), location.getText().toString(), description.getText().toString(), startText.getText().toString(), endText.getText().toString(), Integer.parseInt(numberofpeople.getText().toString()), selectedCategory), AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(), "Event created", Toast.LENGTH_SHORT).show();

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
            } else {
                Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_SHORT).show();
            }


        });

        url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DownloadImageTask.loadImage(getContext(),url.getText().toString(),image,R.drawable.image);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }



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