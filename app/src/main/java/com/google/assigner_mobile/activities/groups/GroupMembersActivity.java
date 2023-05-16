package com.google.assigner_mobile.activities.groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.adapters.GroupMembersAdapter;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.helpers.GroupHelper;
import com.google.assigner_mobile.helpers.GroupMembersHelper;
import com.google.assigner_mobile.helpers.InvitationHelper;
import com.google.assigner_mobile.helpers.NotificationHelper;
import com.google.assigner_mobile.helpers.UserHelper;
import com.google.assigner_mobile.models.AppNotification;
import com.google.assigner_mobile.models.Group;
import com.google.assigner_mobile.models.Invitation;
import com.google.assigner_mobile.models.User;

import java.time.LocalDate;
import java.util.Vector;

public class GroupMembersActivity extends AppCompatActivity implements View.OnClickListener{


    Vector <User> groupMembersVector;
    GroupMembersAdapter groupMembersAdapter;
    RecyclerView groupMembersRecyclerView;

    GroupMembersHelper groupMembersDB;

    Button groupMembersAddMemberButton;

    UserHelper userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        Integer groupId = getIntent().getIntExtra("groupId", 0);

        userDB = new UserHelper(this);

        groupMembersRecyclerView = findViewById(R.id.groupMembersRecyclerView);
        groupMembersAddMemberButton = findViewById(R.id.groupMembersAddMemberButton);
        groupMembersAddMemberButton.setOnClickListener(this);

        groupMembersVector = new Vector<>();
        groupMembersDB = new GroupMembersHelper(this);

        groupMembersDB.open();
        groupMembersVector = groupMembersDB.getAllMembersByGroupId(groupId);
        groupMembersDB.close();

        groupMembersAdapter = new GroupMembersAdapter(this);
        groupMembersAdapter.setGroupMembersVector(groupMembersVector);
        groupMembersAdapter.setGroup(groupId);

        groupMembersRecyclerView.setAdapter(groupMembersAdapter);
        groupMembersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View view) {
        if(view == groupMembersAddMemberButton){
            EditText targetUsernameEditText = new EditText(this);
            targetUsernameEditText.setHint("Enter Username");

            new AlertDialog.Builder(this)
                    .setTitle("Invite Member")
                    .setMessage("Enter the username of the user you want to invite")
                    .setView(targetUsernameEditText)
                    .setPositiveButton("Invite", (dialogInterface, i) -> {
                        userDB.open();
                        User targetUser;
                        int groupId = getIntent().getIntExtra("groupId", 0);
                        Group group = new GroupHelper(this).getGroupById(groupId);

                        try {
                            targetUser = userDB.getUser("username", targetUsernameEditText.getText().toString());
                        } catch (Exception e) {
                            targetUser = null;
                            e.printStackTrace();
                        }

                        userDB.close();

                        if(targetUser == null){
                            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        InvitationHelper invitationDB = new InvitationHelper(this);
                        invitationDB.open();

                        if(invitationDB.getInvitation(groupId, targetUser.getId()) != null){
                            Toast.makeText(this, "User already invited", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Integer invitationId = invitationDB.invite(groupId, targetUser.getId());
                        invitationDB.close();

                        NotificationHelper notificationDB = new NotificationHelper(this);
                        notificationDB.open();

                        notificationDB.insert(targetUser.getId(), "Group Invite",  String.format("You have been invited to join %s", group.getId()), AppNotification.TYPE_INVITATION, LocalDate.now(), invitationId);
                        notificationDB.close();


                        Toast.makeText(this, "Invitation sent", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        }
    }
}