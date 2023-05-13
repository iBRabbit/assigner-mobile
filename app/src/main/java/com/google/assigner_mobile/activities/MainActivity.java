package com.google.assigner_mobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.helpers.seeders.DatabaseSeeder;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button  mainRegisterButton,
            mainLoginButton;


    DatabaseSeeder databaseSeeder = new DatabaseSeeder();

    AuthFunction auth = new AuthFunction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRegisterButton = findViewById(R.id.mainRegisterButton);
        mainLoginButton = findViewById(R.id.mainLoginButton);
        mainRegisterButton.setOnClickListener(this);
        mainLoginButton.setOnClickListener(this);

        auth.setAuthID(this, -1);

        databaseSeeder.seedUsers(this, 3);
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        if(view == mainRegisterButton) {
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

        if(view == mainLoginButton) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.redirect(this);
    }
}