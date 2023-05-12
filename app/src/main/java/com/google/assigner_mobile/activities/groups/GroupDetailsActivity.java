package com.google.assigner_mobile.activities.groups;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.helpers.GroupHelper;
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