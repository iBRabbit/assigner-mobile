package com.google.assigner_mobile.activities.assignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.adapters.AssignmentsAdapter;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.functions.GlobalFunction;
import com.google.assigner_mobile.helpers.AssignmentHelper;
import com.google.assigner_mobile.models.Assignment;

import java.util.Vector;

public class AssignmentListActivity extends AppCompatActivity {

    Assignment assignment;
    Vector<Assignment> assignmentVector;
    AssignmentsAdapter assignmentsAdapter;
    RecyclerView assignmentsRecyclerView;

    AssignmentHelper asgDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);

        assignmentsRecyclerView = findViewById(R.id.assignmentListRecyclerView);

        assignmentVector = new Vector<>();
        asgDB = new AssignmentHelper(this);

        asgDB.open();
        assignmentVector = asgDB.getAllAssignmentsByGroupId(getIntent().getIntExtra("groupId", 0));
        asgDB.close();

        assignmentsAdapter = new AssignmentsAdapter(this);
        assignmentsAdapter.setAssignmentVector(assignmentVector);

        assignmentsRecyclerView.setAdapter(assignmentsAdapter);
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}