package com.example.events.Activities;

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
import android.widget.Toast;

import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.BearerToken;
import com.example.events.DataStructures.Users.LoginObject;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.Api;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                ServiceAPI.getInstance().authenticateUser(new LoginObject(email.getText().toString(),password.getText().toString())).enqueue(new Callback<BearerToken>() {
                    @Override
                    public void onResponse(Call<BearerToken> call, Response<BearerToken> response) {
                        if (response.isSuccessful()){
                            getUser(response.body(), new LoginObject(email.getText().toString(),password.getText().toString()));
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BearerToken> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, R.string.bad_connection, Toast.LENGTH_SHORT).show();
                    }
                });
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

    private void getUser(BearerToken token, LoginObject loginObject){
        token.fixToken();
        ServiceAPI.getInstance().searchUsers(loginObject.getEmail(), token.getToken()).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    User user = response.body().get(0);
                    user.setPassword(loginObject.getPassword());
                    user.setToken(token);
                    AuthUser.getAuthUser().setUser(user);
                    Intent intent = new Intent(LoginActivity.this,MainViewActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, R.string.user_not_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, R.string.bad_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }
}