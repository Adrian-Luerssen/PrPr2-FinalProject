package com.example.events.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.events.R;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private Button signUp;
    private Button gotoLogin;
    private EditText email;
    private EditText password;
    private EditText phone;
    private EditText firstName;
    private EditText lastName;
    private Pattern emailPattern;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initFields();
        setButtonListener();
    }

    private void setButtonListener() {
        signUp.setOnClickListener(view -> {

            if (validInputs()){
                Intent intent = new Intent();
                intent.putExtra(getString(R.string.email),email.getText().toString());
                setResult(LoginActivity.SIGNED_UP,intent);
                finish();
            }

        });

        gotoLogin.setOnClickListener(view->{
            setResult(LoginActivity.RESULT_CANCELED);
            finish();
        });
    }

    private boolean validInputs() {
        if (validEmail(email.getText().toString()) && password.getText().toString().length() != 0){
            System.out.println("email password fine");
            if (firstName.getText().toString().length() != 0 && (firstName.getText().toString().split(" ")).length == 1 && lastName.getText().toString().length() != 0){
                System.out.println("names fine");
                if (validPhone(phone.getText().toString())){
                    System.out.println("phone fine");
                    return true;
                }

            }

        }
        return false;
    }

    private boolean validPhone(String phoneNumber) {
        //CHECK PHONE NUMBER PLEASEE
        return true;
    }

    private void initFields() {
        signUp = (Button) findViewById(R.id.sign_up_button);
        email = (EditText) findViewById(R.id.SignUp_email_input);
        password = (EditText) findViewById(R.id.SignUp_password_input);
        phone = (EditText) findViewById(R.id.SignUp_Phone_input);
        firstName = (EditText) findViewById(R.id.SignUp_First_Name_input);
        lastName = (EditText) findViewById(R.id.SignUp_Last_Name_input);
        gotoLogin = (Button) findViewById(R.id.move_to_login);
        emailPattern = Pattern.compile("^(.+)@(.+)$");
    }

    private boolean validEmail(String email){
        return emailPattern.matcher(email).matches();
    }
}