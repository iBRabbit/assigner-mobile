package com.google.assigner_mobile.activities.groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.adapters.GroupMembersAdapter;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.helpers.GroupMembersHelper;
import com.google.assigner_mobile.helpers.UserHelper;
import com.google.assigner_mobile.models.User;

import java.util.Vector;

public class GroupMembersActivity extends AppCompatActivity {


    Vector <User> groupMembersVector;
    GroupMembersAdapter groupMembersAdapter;
    RecyclerView groupMembersRecyclerView;

    GroupMembersHelper groupMembersDB;

    AuthFunction auth = new AuthFunction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        Integer groupId = getIntent().getIntExtra("groupId", 0);

        groupMembersRecyclerView = findViewById(R.id.groupMembersRecyclerView);

        groupMembersVector = new Vector<>();
        groupMembersDB = new GroupMembersHelper(this);

        groupMembersDB.open();
        groupMembersVector = groupMembersDB.getAllMembersByGroupId(groupId);
        groupMembersDB.close();

        groupMembersAdapter = new GroupMembersAdapter(this);
        groupMembersAdapter.setGroupMembersVector(groupMembersVector);

        groupMembersRecyclerView.setAdapter(groupMembersAdapter);
        groupMembersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}