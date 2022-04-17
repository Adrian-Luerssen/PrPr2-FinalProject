package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private Button signIn;
    private EditText email;
    private EditText password;
    private Pattern emailPattern;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFields();
        loginButtonSetListener();

    }



    private void initFields() {
        signIn = (Button) findViewById(R.id.sign_in_button);
        email = (EditText) findViewById(R.id.Login_email_input);
        password = (EditText) findViewById(R.id.Login_password_input);
        emailPattern = Pattern.compile("^(.+)@(.+)$");
    }

    private void loginButtonSetListener() {
        signIn.setOnClickListener(view -> {
            if (validEmail(email.getText().toString()) && password.getText().toString().length() != 0){
                System.out.println("VALID");
            }
            //check if email and password are valid (API)
        });
    }

    private boolean validEmail(String email){
        return emailPattern.matcher(email).matches();
    }
}