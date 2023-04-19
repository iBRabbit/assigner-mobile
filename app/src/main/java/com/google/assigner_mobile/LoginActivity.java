package com.google.assigner_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText    loginUsernameEditText,
                loginPasswordEditText;

    Button      loginSubmitButton;
    TextView    loginDirectToRegisterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsernameEditText = findViewById(R.id.loginUsernameEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);

        loginSubmitButton = findViewById(R.id.loginSubmitButton);
        loginDirectToRegisterTextView = findViewById(R.id.loginDirectToRegisterTextView);

        loginSubmitButton.setOnClickListener(this);
        loginDirectToRegisterTextView.setOnClickListener(this);

        String  username = loginUsernameEditText.getText().toString(),
                password = loginPasswordEditText.getText().toString();

    }

    @Override
    public void onClick(View view) {
        Intent intent;


        if(view == loginDirectToRegisterTextView) {
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }

        if(view == loginSubmitButton) {
            // Open Database

            if(!validateFormInput())
                return;

            // Insert Data to Database

            // Close Database
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
}