package com.google.assigner_mobile.activities.groups;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.helpers.GroupHelper;
import com.google.assigner_mobile.helpers.GroupMembersHelper;
import com.google.assigner_mobile.helpers.UserHelper;
import com.google.assigner_mobile.models.Group;
import com.google.assigner_mobile.models.User;

public class GroupDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    GroupHelper groupDB;
    Group group;
    User owner;

    TextView    groupDetailsGroupNameTextView,
                groupDetailsGroupDescriptionTextView,
                groupDetailsGroupOwnerTextView;

    Button      groupDetailsAddAssignmentButton,
                groupDetailsEditGroupButton,
                groupDetailsViewGroupButton,
                groupDetailsLeaveGroupButton,
                groupDetailsDeleteGroupButton;

    AuthFunction auth = new AuthFunction();
    UserHelper userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        Integer groupId = getIntent().getIntExtra("groupId", 0);

        groupDB = new GroupHelper(this);
        userDB = new UserHelper(this);

        groupDB.open();
        userDB.open();

        group = groupDB.getGroupById(groupId);
        owner = userDB.getUser("id", group.getOwnerId());

        Log.i("GroupDetailsActivity", String.format("Activity Started as %s", group.getName()));

        init();

        groupDetailsGroupNameTextView.setText(group.getName());
        groupDetailsGroupDescriptionTextView.setText(group.getDescription());
        groupDetailsGroupOwnerTextView.setText(owner.getUsername());

        if(auth.getAuthID(this).equals(group.getOwnerId())) {
            groupDetailsAddAssignmentButton.setVisibility(View.VISIBLE);
            groupDetailsEditGroupButton.setVisibility(View.VISIBLE);
            groupDetailsLeaveGroupButton.setVisibility(View.GONE);
            groupDetailsDeleteGroupButton.setVisibility(View.VISIBLE);
        } else {
            groupDetailsAddAssignmentButton.setVisibility(View.GONE);
            groupDetailsEditGroupButton.setVisibility(View.GONE);
            groupDetailsLeaveGroupButton.setVisibility(View.VISIBLE);
            groupDetailsDeleteGroupButton.setVisibility(View.GONE);
        }

        groupDB.close();
        userDB.close();
    }

    @Override
    public void onClick(View view) {
        if(view == groupDetailsAddAssignmentButton) {
            // TODO: Add Assignment (Only Admin)
        }

        if(view == groupDetailsEditGroupButton) {
            // TODO: Edit Group (Only Admin)

            EditText groupNameEditText = new EditText(this);
            groupNameEditText.setText(group.getName());

            EditText groupDescriptionEditText = new EditText(this);
            groupDescriptionEditText.setText(group.getDescription());

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(groupNameEditText);
            layout.addView(groupDescriptionEditText);

            new AlertDialog.Builder(this).setTitle("Edit Group")
                    .setMessage("Edit Group Details")
                    .setView(layout)
                    .setPositiveButton("Save", (dialogInterface, i) -> {
                        group.setName(groupNameEditText.getText().toString());
                        group.setDescription(groupDescriptionEditText.getText().toString());

                        groupDB.open();
                        groupDB.update(group.getId(), "name", group.getName());
                        groupDB.update(group.getId(), "description", group.getDescription());

                        groupDB.close();
                        Toast.makeText(this, String.format("Group %s Updated", group.getName()), Toast.LENGTH_SHORT).show();

                        groupDetailsGroupNameTextView.setText(group.getName());
                        groupDetailsGroupDescriptionTextView.setText(group.getDescription());
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        }

        if(view == groupDetailsDeleteGroupButton) {
            // TODO: Delete Group
            if(groupDB.delete(group.getId())) {
                Log.i("GroupDetailsActivity", String.format("Group %s Deleted", group.getName()));
                Toast.makeText(this, String.format("Group %s Deleted", group.getName()), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.i("GroupDetailsActivity", String.format("Group %s Failed to Delete", group.getName()));
                Toast.makeText(this, String.format("Group %s Failed to Delete", group.getName()), Toast.LENGTH_SHORT).show();
            }
        }

        if(view == groupDetailsLeaveGroupButton) {
            // TODO: Leave Group
            GroupMembersHelper gmh = new GroupMembersHelper(this);
            gmh.open();

            if(gmh.removeMember(group.getId(), auth.getAuthID(this))) {
                Log.i("GroupDetailsActivity", String.format("User %s Left Group %s", auth.getAuthID(this), group.getName()));
                Toast.makeText(this, String.format("You left group %s", group.getName()), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Log.i("GroupDetailsActivity", String.format("User %s Failed to Leave Group %s", auth.getAuthID(this), group.getName()));
                Toast.makeText(this,String.format("You failed to left group %s", group.getName()), Toast.LENGTH_SHORT).show();
            }

            gmh.close();
        }

        if(view == groupDetailsViewGroupButton) {
            // TODO: View Members
        }
    }

    public void init() {
        groupDetailsGroupNameTextView = findViewById(R.id.groupDetailsGroupNameTextView);
        groupDetailsGroupDescriptionTextView = findViewById(R.id.groupDetailsGroupDescriptionTextView);
        groupDetailsAddAssignmentButton = findViewById(R.id.groupDetailsAddAssignmentButton);
        groupDetailsEditGroupButton = findViewById(R.id.groupDetailsEditGroupButton);
        groupDetailsLeaveGroupButton = findViewById(R.id.groupDetailsLeaveGroupButton);
        groupDetailsViewGroupButton = findViewById(R.id.groupDetailsViewMembersButton);
        groupDetailsGroupOwnerTextView = findViewById(R.id.groupDetailsGroupOwnerTextView);
        groupDetailsDeleteGroupButton = findViewById(R.id.groupDetailsDeleteGroupButton);

        groupDetailsAddAssignmentButton.setOnClickListener(this);
        groupDetailsEditGroupButton.setOnClickListener(this);
        groupDetailsLeaveGroupButton.setOnClickListener(this);
        groupDetailsViewGroupButton.setOnClickListener(this);
        groupDetailsDeleteGroupButton.setOnClickListener(this);
    }
}