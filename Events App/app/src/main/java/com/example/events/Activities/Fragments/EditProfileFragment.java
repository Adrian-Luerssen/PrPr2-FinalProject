package com.example.events.Activities.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.DownloadImageTask;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
    private EditText url;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText repeatPassword;
    private EditProfileListener listener;
    private ImageView image;
    public interface EditProfileListener {
        void saveEdit();
    }
    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        firstName = (EditText) view.findViewById(R.id.EditUser_FirstName_input);
        lastName = (EditText) view.findViewById(R.id.EditUser_LastName_input);
        email = (EditText) view.findViewById(R.id.EditUser_Email_input);
        password = (EditText) view.findViewById(R.id.EditUser_Password_input);
        repeatPassword = (EditText) view.findViewById(R.id.EditUser_RepeatPassword_input);
        //TODO: Image edition
        Button saveButton = (Button) view.findViewById(R.id.EditUser_confirm_edit);
        firstName.setText(AuthUser.getAuthUser().getName());
        lastName.setText(AuthUser.getAuthUser().getLastName());
        email.setText(AuthUser.getAuthUser().getEmail());
        image = view.findViewById(R.id.profileImage);
        DownloadImageTask.loadImage(getContext(),AuthUser.getAuthUser().getImageURL(),image,R.drawable.boy1);
        url = view.findViewById(R.id.imageURL);
        url.setText(AuthUser.getAuthUser().getImageURL());
        url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DownloadImageTask.loadImage(getContext(),url.getText().toString(),image,R.drawable.boy1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        saveButton.setOnClickListener(v -> {

            String firstNameString = firstName.getText().toString().equals("") ? AuthUser.getAuthUser().getName() : firstName.getText().toString();
            String lastNameString = lastName.getText().toString().equals("") ? AuthUser.getAuthUser().getLastName() : lastName.getText().toString();
            String passwordString = password.getText().toString().equals("") && repeatPassword.getText().toString().equals("") ? AuthUser.getAuthUser().getPassword() : password.getText().toString();
            String repeatPasswordString = password.getText().toString().equals("") && repeatPassword.getText().toString().equals("") ? AuthUser.getAuthUser().getPassword() : repeatPassword.getText().toString();
            String emailString = email.getText().toString().equals("") ? AuthUser.getAuthUser().getEmail() : email.getText().toString();


            if (!passwordString.equals(repeatPasswordString)) {
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();

            } else {
                User user = new User(firstNameString, lastNameString, emailString, passwordString);
                user.setImageURL(url.getText().toString());
                user.setToken(AuthUser.getAuthUser().getToken());
                AuthUser.getAuthUser().setUser(user);

                ServiceAPI.getInstance().updateUser(user, AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "User updated", Toast.LENGTH_SHORT).show();
                            listener.saveEdit();
                            } else {
                            Toast.makeText(getContext(), "User not updated", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "User not updated", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



        return view;

    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditProfileListener) {
            listener = (EditProfileListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement EditProfileListener");
        }
    }
}
