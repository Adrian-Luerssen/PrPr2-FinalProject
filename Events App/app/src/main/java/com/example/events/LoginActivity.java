package com.example.events;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    public static final int SIGNED_UP = 1;

    private Button signIn;
    private Button gotoSignUp;
    private EditText email;
    private EditText password;
    private Pattern emailPattern;

    private final ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onActivityResult(ActivityResult result) {
            System.out.println("here");
            Intent intent = result.getData();
            if (result.getResultCode() == SIGNED_UP && intent != null) {

                email.setText(intent.getStringExtra(getString(R.string.email)));

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFields();
        setButtonListener();

    }



    private void initFields() {
        signIn = (Button) findViewById(R.id.sign_in_button);
        email = (EditText) findViewById(R.id.Login_email_input);
        password = (EditText) findViewById(R.id.Login_password_input);
        emailPattern = Pattern.compile("^(.+)@(.+)$");
        gotoSignUp = (Button) findViewById(R.id.move_to_register_account);
    }

    private void setButtonListener() {
        signIn.setOnClickListener(view -> {
            if (validEmail(email.getText().toString()) && password.getText().toString().length() != 0){
                System.out.println("VALID");
            }
            //check if email and password are valid (API)
        });

        gotoSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            activityLauncher.launch(intent);
        });
    }

    private boolean validEmail(String email){
        return emailPattern.matcher(email).matches();
    }
}