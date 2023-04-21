package com.google.assigner_mobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.helpers.UserHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText    loginUsernameEditText,
                loginPasswordEditText;

    Button      loginSubmitButton;
    TextView    loginDirectToRegisterTextView;

    UserHelper userDB;
    AuthFunction auth = new AuthFunction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsernameEditText = findViewById(R.id.loginUsernameEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);

        loginSubmitButton = findViewById(R.id.loginSubmitButton);
        loginDirectToRegisterTextView = findViewById(R.id.loginDirectToRegisterTextView);

        debugAutoFill();

        loginSubmitButton.setOnClickListener(this);
        loginDirectToRegisterTextView.setOnClickListener(this);

        userDB = new UserHelper(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;


        if(view == loginDirectToRegisterTextView) {
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }

        if(view == loginSubmitButton)
        {
            String  username = loginUsernameEditText.getText().toString(),
                    password = loginPasswordEditText.getText().toString();

            if(!validateFormInput())
                return;

            userDB.open();

            if(!userDB.auth(username, password)) {
                loginUsernameEditText.setError("Username or password is incorrect");
                return;
            }

            userDB.close();

            intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

            auth.setAuthID(this, userDB.getIDByUsername(username));
            Log.i("Authentication", String.format("User authenticated with ID: %d", userDB.getIDByUsername(username)));

            finish();
        }
    }

    boolean validateFormInput() {

        if(loginUsernameEditText.getText().toString().isEmpty()) {
            loginUsernameEditText.setError("Username is required");
            return false;
        }

        if(loginPasswordEditText.getText().toString().isEmpty()) {
            loginPasswordEditText.setError("Password is required");
            return false;
        }

        return true;
    }

    void debugAutoFill(){
        loginUsernameEditText.setText("admin");
        loginPasswordEditText.setText("admin");
    }
}