package com.example.events.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private Button signUp;
    private Button gotoLogin;
    private EditText email;
    private EditText password;
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
                ServiceAPI.getInstance().registerUser(new User(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString(),password.getText().toString())).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent();
                            intent.putExtra(getString(R.string.email),email.getText().toString());
                            setResult(LoginActivity.SIGNED_UP,intent);
                            finish();
                        }else{
                            Toast.makeText(SignUpActivity.this, R.string.signup_failed, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, R.string.bad_connection, Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

        gotoLogin.setOnClickListener(view->{
            setResult(LoginActivity.RESULT_CANCELED);
            finish();
        });
    }

    private boolean validInputs() {
        if (validEmail(email.getText().toString()) ){
            if (password.getText().toString().length() >= 8) {
                System.out.println("email password fine");

                if (firstName.getText().toString().length() != 0 && (firstName.getText().toString().split(" ")).length == 1 && lastName.getText().toString().length() != 0) {
                    System.out.println("names fine");
                    return true;

                }
            }else{
                Toast.makeText(SignUpActivity.this, R.string.Invalid_password, Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(SignUpActivity.this, R.string.Invalid_email, Toast.LENGTH_SHORT).show();
        }
        return false;
    }



    private void initFields() {
        signUp = (Button) findViewById(R.id.EditUser_confirm_edit);
        email = (EditText) findViewById(R.id.EditUser_Password_input);
        password = (EditText) findViewById(R.id.EditUser_RepeatPassword_input);
        firstName = (EditText) findViewById(R.id.EditUser_FirstName_input);
        lastName = (EditText) findViewById(R.id.EditUser_LastName_input);
        gotoLogin = (Button) findViewById(R.id.move_to_login);
        emailPattern = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
    }

    private boolean validEmail(String email){
        return emailPattern.matcher(email).matches();
    }
}