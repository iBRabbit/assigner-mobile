package com.google.assigner_mobile.activities.assignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.helpers.AssignmentHelper;
import com.google.assigner_mobile.models.Assignment;

public class AssignmentDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    AssignmentHelper asgDB;
    TextView assignmentNameTextView,
            assignmentDescriptionTextView,
            assignmentCreatedAtTextView,
            assignmentDeadlineTextView;

    ProgressBar assignmentProgressBar;
    Button assignmentUpdateProgressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);

        int asgId = getIntent().getIntExtra("assignmentId", 0);
        Log.i("AssignmentDetails", "onCreate: " + asgId);

        assignmentNameTextView = findViewById(R.id.assignmentDetailsAssignmentNameTextView);
        assignmentDescriptionTextView = findViewById(R.id.assignmentDetailsDescriptionTextView);
        assignmentCreatedAtTextView = findViewById(R.id.assignmentDetailsCreatedAtTextView);
        assignmentDeadlineTextView = findViewById(R.id.assignmentDetailsDeadlineTextView);
        assignmentProgressBar = findViewById(R.id.assignmentDetailsProgressBar);
        assignmentUpdateProgressButton = findViewById(R.id.assignmentDetailsUpdateProgressButton);

        asgDB = new AssignmentHelper(this);
        asgDB.open();

        Assignment asg = asgDB.getAssignmentById(asgId);

        assignmentNameTextView.setText(String.format("Name : %s", asg.getName()));
        assignmentDescriptionTextView.setText(String.format("Description : %s", asg.getDescription()));
        assignmentCreatedAtTextView.setText(String.format("Created At : %s", asg.getCreatedAt()));
        assignmentDeadlineTextView.setText(String.format("Deadline : %s", asg.getDeadline()));
        assignmentProgressBar.setProgress(asg.getProgress());

        assignmentProgressBar.setProgressTintList(asg.getProgressColor(asg.getProgress()));

        // Cek apakah user adalah owner dari assignment
        int authID = new AuthFunction().getAuthID(this);

        if(!asg.getUserId().equals(authID))
            assignmentUpdateProgressButton.setVisibility(View.GONE);

        assignmentUpdateProgressButton.setOnClickListener(this);

        asgDB.close();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.assignmentDetailsUpdateProgressButton){
            asgDB.open();
            EditText progressEditText = new EditText(this);
            progressEditText.setHint("Enter updated progress here [0 - 100] : ");
            new AlertDialog.Builder(this)
                    .setTitle("Update Progress")
                    .setMessage("Enter updated progress here [0 - 100] : ")
                    .setView(progressEditText)
                    .setPositiveButton("Update", (dialogInterface, i) -> {

                        if(progressEditText.getText().toString().isEmpty()){
                            Toast.makeText(this, "Progress cannot be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(!progressEditText.getText().toString().matches("[0-9]+")){
                            Toast.makeText(this, "Progress must be a number", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int progress = Integer.parseInt(progressEditText.getText().toString());

                        if(progress < 0 || progress > 100){
                            Toast.makeText(this, "Invalid progress value", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        asgDB.update(getIntent().getIntExtra("assignmentId", 0),"progress", progress);
                        assignmentProgressBar.setProgress(progress);
                        assignmentProgressBar.setProgressTintList(asgDB.getAssignmentById(getIntent().getIntExtra("assignmentId", 0)).getProgressColor(progress));

                        Toast.makeText(this, "Progress updated", Toast.LENGTH_SHORT).show();

                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            asgDB.close();
        }
    }
}