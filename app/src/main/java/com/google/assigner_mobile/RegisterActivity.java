package com.google.assigner_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText    registerUsernameEditText,
                registerPasswordEditText,
                registerEmailEditText,
                registerPhoneNumberEditText;

    Button      registerSubmitButton;
    TextView    registerDirectToLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsernameEditText = findViewById(R.id.registerUsernameEditText);
        registerPasswordEditText = findViewById(R.id.registerPasswordEditText);
        registerEmailEditText = findViewById(R.id.registerEmailEditText);
        registerPhoneNumberEditText = findViewById(R.id.registerPhoneNumberEditText);

        registerSubmitButton = findViewById(R.id.registerSubmitButton);
        registerDirectToLoginTextView = findViewById(R.id.registerDirectToLoginTextView);

        registerSubmitButton.setOnClickListener(this);
        registerDirectToLoginTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        if(view == registerDirectToLoginTextView) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        if(view == registerSubmitButton) {
            // Open Database

            if(!validateFormInput())
                return;

            // Insert Data to Database

            // Close Database

            finish();
        }
    }

    boolean validateFormInput() {
        if (registerUsernameEditText.getText().toString().isEmpty()) {
            registerUsernameEditText.setError("Username is required");
            registerUsernameEditText.requestFocus();
            return false;
        }

        if (registerPasswordEditText.getText().toString().isEmpty()) {
            registerPasswordEditText.setError("Password is required");
            registerPasswordEditText.requestFocus();
            return false;
        }

        if (registerEmailEditText.getText().toString().isEmpty()) {
            registerEmailEditText.setError("Email is required");
            registerEmailEditText.requestFocus();
            return false;
        }

        if (registerPhoneNumberEditText.getText().toString().isEmpty()) {
            registerPhoneNumberEditText.setError("Phone number is required");
            registerPhoneNumberEditText.requestFocus();
            return false;
        }

        if(registerPasswordEditText.getText().toString().length() < 6) {
            registerPasswordEditText.setError("Password must be at least 6 characters");
            registerPasswordEditText.requestFocus();
            return false;
        }

        return true;
    }
}