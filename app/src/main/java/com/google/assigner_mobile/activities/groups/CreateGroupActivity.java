package com.google.assigner_mobile.activities.groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.activities.HomeActivity;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.helpers.GroupHelper;

public class CreateGroupActivity extends AppCompatActivity implements View.OnClickListener{

    EditText    createGroupNameEditText,
                createGroupDescriptionEditText;

    Button      createGroupButton;

    GroupHelper groupDB;
    AuthFunction auth = new AuthFunction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        createGroupNameEditText = findViewById(R.id.createGroupNameEditText);
        createGroupDescriptionEditText = findViewById(R.id.createGroupDescriptionEditText);
        createGroupButton = findViewById(R.id.createGroupButton);

        createGroupButton.setOnClickListener(this);

        groupDB = new GroupHelper(this);
    }

    @Override
    public void onClick(View view) {
        if(view == createGroupButton) {
            String  groupName = createGroupNameEditText.getText().toString(),
                    groupDescription = createGroupDescriptionEditText.getText().toString();

            if(!validateGroupName(groupName, groupDescription))
                return;

            groupDB.open();
            groupDB.insert(groupName, groupDescription, auth.getAuthID(this));
            groupDB.close();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
    }

    public boolean validateGroupName(String groupName, String groupDescription) {
        if (groupName.isEmpty()) {
            createGroupNameEditText.setError("Group name cannot be empty");
            createGroupNameEditText.requestFocus();
            return false;
        }

        if (groupDescription.isEmpty()) {
            createGroupDescriptionEditText.setError("Group description cannot be empty");
            createGroupDescriptionEditText.requestFocus();
            return false;
        }

        if (groupName.length() < 3) {
            createGroupNameEditText.setError("Group name must be at least 3 characters");
            createGroupNameEditText.requestFocus();
            return false;
        }

        if (groupDescription.length() < 3) {
            createGroupDescriptionEditText.setError("Group description must be at least 3 characters");
            createGroupDescriptionEditText.requestFocus();
            return false;
        }

        if (groupName.length() > 20) {
            createGroupNameEditText.setError("Group name must be less than 20 characters");
            createGroupNameEditText.requestFocus();
            return false;
        }

        if (groupDescription.length() > 20) {
            createGroupDescriptionEditText.setError("Group description must be less than 20 characters");
            createGroupDescriptionEditText.requestFocus();
            return false;
        }

        return true;
    }
}