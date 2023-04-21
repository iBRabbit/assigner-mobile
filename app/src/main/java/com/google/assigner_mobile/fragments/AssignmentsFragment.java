package com.google.assigner_mobile.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.adapters.AssignmentsAdapter;
import com.google.assigner_mobile.helpers.seeders.DatabaseSeeder;
import com.google.assigner_mobile.models.Assignment;

import java.util.Vector;


public class AssignmentsFragment extends Fragment {

    Assignment assignment;

    Vector<Assignment> assignmentVector;
    AssignmentsAdapter assignmentsAdapter;
    RecyclerView assignmentsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assignments, container, false);

        if(view == null)
            return null;

        assignmentsRecyclerView = view.findViewById(R.id.assignmentsRecyclerView);

        assignmentVector = new Vector<>();
        DatabaseSeeder seed = new DatabaseSeeder();
        assignmentVector = seed.generateAssignmentDummyVector();


        // Print All Assignment Vector

        assignmentsAdapter = new AssignmentsAdapter(getContext());
        assignmentsAdapter.setAssignmentVector(assignmentVector);

        assignmentsRecyclerView.setAdapter(assignmentsAdapter);
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}