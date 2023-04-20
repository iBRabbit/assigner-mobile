package com.google.assigner_mobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.assigner_mobile.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button  mainRegisterButton,
            mainLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRegisterButton = findViewById(R.id.mainRegisterButton);
        mainLoginButton = findViewById(R.id.mainLoginButton);
        mainRegisterButton.setOnClickListener(this);
        mainLoginButton.setOnClickListener(this);

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
}