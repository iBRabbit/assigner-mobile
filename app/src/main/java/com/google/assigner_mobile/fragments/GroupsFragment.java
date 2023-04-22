package com.google.assigner_mobile.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.adapters.GroupsAdapter;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.functions.GlobalFunction;
import com.google.assigner_mobile.helpers.GroupHelper;
import com.google.assigner_mobile.models.Group;

import java.util.Vector;

public class GroupsFragment extends Fragment {

    Vector<Group> groupVector;
    GroupsAdapter groupsAdapter;
    RecyclerView groupsRecyclerView;

    GroupHelper groupDB;

    AuthFunction auth = new AuthFunction();
    GlobalFunction cont = new GlobalFunction();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        if(view == null)
            return null;

        groupsRecyclerView = view.findViewById(R.id.groupRecyclerView);
        groupVector = new Vector<>();

        groupDB = new GroupHelper(getContext());

        groupDB.open();
        groupVector = groupDB.getAllGroupsByUserId(auth.getAuthID(cont.safeGetContext(getContext())));
        groupDB.close();

        groupsAdapter = new GroupsAdapter(getContext());
        groupsAdapter.setGroupVector(groupVector);

        groupsRecyclerView.setAdapter(groupsAdapter);
        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}