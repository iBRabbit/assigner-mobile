package com.google.assigner_mobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.activities.LoginActivity;
import com.google.assigner_mobile.helpers.UserHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText    registerUsernameEditText,
                registerPasswordEditText,
                registerEmailEditText,
                registerPhoneNumberEditText;

    Button      registerSubmitButton;
    TextView    registerDirectToLoginTextView;

    UserHelper userDB;

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

        userDB = new UserHelper(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        String  username = registerUsernameEditText.getText().toString(),
                password = registerPasswordEditText.getText().toString(),
                email = registerEmailEditText.getText().toString(),
                phoneNumber = registerPhoneNumberEditText.getText().toString();

        if(view == registerDirectToLoginTextView) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        if(view == registerSubmitButton) {
            if(!validateFormInput())
                return;

            userDB.open();
            userDB.insert(username, password, email, phoneNumber);
            userDB.close();

            Toast.makeText(this, "Register success", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
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

        if(!registerEmailEditText.getText().toString().matches("^[a-zA-Z\\d+_.-]+@[a-zA-Z\\d.-]+.com$")) {
            registerEmailEditText.setError("Email is not valid");
            registerEmailEditText.requestFocus();
            return false;
        }

        if(!registerPhoneNumberEditText.getText().toString().matches("^0[\\d]{5,10}+$")) {
            registerPhoneNumberEditText.setError("Phone number is not valid");
            registerPhoneNumberEditText.requestFocus();
            return false;
        }


        userDB.open();
        if(userDB.isUserExists("email", registerEmailEditText.getText().toString())) {
            registerEmailEditText.setError("Email already exists");
            registerEmailEditText.requestFocus();
            userDB.close();
            return false;
        }

        if(userDB.isUserExists("username", registerUsernameEditText.getText().toString())) {
            registerUsernameEditText.setError("Username already exists");
            registerUsernameEditText.requestFocus();
            userDB.close();
            return false;
        }

        if(userDB.isUserExists("phone_number", registerPhoneNumberEditText.getText().toString())) {
            registerPhoneNumberEditText.setError("Phone number already exists");
            registerPhoneNumberEditText.requestFocus();
            userDB.close();
            return false;
        }
        userDB.close();
        return true;
    }
}