package com.google.assigner_mobile.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.activities.groups.GroupMembersActivity;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.helpers.AssignmentHelper;
import com.google.assigner_mobile.helpers.GroupHelper;
import com.google.assigner_mobile.helpers.GroupMembersHelper;
import com.google.assigner_mobile.helpers.NotificationHelper;
import com.google.assigner_mobile.helpers.UserHelper;
import com.google.assigner_mobile.models.AppNotification;
import com.google.assigner_mobile.models.Group;
import com.google.assigner_mobile.models.User;

import java.time.LocalDate;
import java.util.Vector;

public class GroupMembersAdapter extends RecyclerView.Adapter<GroupMembersAdapter.GroupMemberViewHolder>{

    Context context;
    Vector <User> groupMembersVector;
    Group group;
    
    public GroupMembersAdapter(Context context) {
        this.context = context;
    }

    public void setGroup(Integer groupId) {
        GroupHelper groupDB = new GroupHelper(context);
        groupDB.open();
        this.group = groupDB.getGroupById(groupId);
        groupDB.close();
    }

    public void setGroupMembersVector(Vector<User> groupMembersVector) {
        this.groupMembersVector = groupMembersVector;
    }

    @NonNull
    @Override
    public GroupMembersAdapter.GroupMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_members_item, parent, false);

        return new GroupMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMembersAdapter.GroupMemberViewHolder holder, int position) {
        AssignmentHelper asgDB = new AssignmentHelper(context);

        UserHelper userDB = new UserHelper(context);
        userDB.open();

        holder.groupMembersNameTextView.setText(groupMembersVector.get(position).getUsername());

        Integer authID = new AuthFunction().getAuthID(context);

        if(authID.equals(group.getOwnerId())) {
            holder.groupMembersRemoveButton.setVisibility(View.VISIBLE);
            holder.groupMembersAssignButton.setVisibility(View.VISIBLE);
        } else {
            holder.groupMembersRemoveButton.setVisibility(View.GONE);
            holder.groupMembersAssignButton.setVisibility(View.GONE);
        }

        if(groupMembersVector.get(position).getId() == authID)
            holder.groupMembersRemoveButton.setVisibility(View.GONE);


        holder.groupMembersAssignButton.setOnClickListener(view -> {

            EditText assignmentNameEditText = new EditText(context);
            EditText assignmentDescriptionEditText = new EditText(context);
            DatePicker assignmentDueDatePicker = new DatePicker(context);

            assignmentNameEditText.setHint("Assignment Name");
            assignmentDescriptionEditText.setHint("Assignment Description");

            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(assignmentNameEditText);
            layout.addView(assignmentDescriptionEditText);
            layout.addView(assignmentDueDatePicker);

            new AlertDialog.Builder(context).setTitle("Add Assignment")
                    .setMessage("Add Assignment to Group")
                    .setView(layout)
                    .setPositiveButton("Add", (dialogInterface, i) -> {
                        asgDB.open();
                        String  assignmentName = assignmentNameEditText.getText().toString(),
                                assignmentDescription = assignmentDescriptionEditText.getText().toString();

                        LocalDate assignmentDueDate = LocalDate.of(assignmentDueDatePicker.getYear(), assignmentDueDatePicker.getMonth() + 1, assignmentDueDatePicker.getDayOfMonth());

                        LocalDate assignmentCreatedAt = LocalDate.now();
                        Integer progress = 0;

                        if(assignmentDueDate.isBefore(assignmentCreatedAt)) {
                            Toast.makeText(context, "Due Date cannot be before today", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(assignmentName.isEmpty() || assignmentDescription.isEmpty()) {
                            Toast.makeText(context, "Assignment Name and Description cannot be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        asgDB.insert(groupMembersVector.get(position).getId(), this.group.getId(), assignmentName, assignmentDescription, assignmentCreatedAt, assignmentDueDate, progress);

                        asgDB.close();

                        Toast.makeText(context, String.format("Assignment %s created.", assignmentName), Toast.LENGTH_SHORT).show();

                        NotificationHelper notifDB = new NotificationHelper(context);
                        notifDB.open();
                        notifDB.insert(groupMembersVector.get(position).getId(), "New Assignment", String.format("You have been assigned to %s on group %s", assignmentName, this.group.getName()), AppNotification.TYPE_ASSIGNMENT, assignmentCreatedAt);
                        notifDB.close();

                        })
                    .setNegativeButton("Cancel", null)
                    .show();
            });

            holder.groupMembersRemoveButton.setOnClickListener(view -> {
                GroupMembersHelper gmh = new GroupMembersHelper(context);

                new AlertDialog.Builder(context).setTitle("Remove Member")
                        .setMessage("Are you sure you want to remove this member?")
                        .setPositiveButton("Remove", (dialogInterface, i) -> {

                            gmh.open();
                            gmh.removeMember(group.getId(),groupMembersVector.get(position).getId());
                            gmh.close();

                            Toast.makeText(context, String.format("Member %s removed.", groupMembersVector.get(position).getUsername()), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(context, GroupMembersActivity.class);
                            intent.putExtra("groupId", group.getId());
                            context.startActivity(intent);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });

    userDB.close();
    }

    @Override
    public int getItemCount() {
        return groupMembersVector.size();
    }

    public class GroupMemberViewHolder extends RecyclerView.ViewHolder{

        TextView        groupMembersNameTextView;
        Button          groupMembersRemoveButton,
                        groupMembersAssignButton;

        CardView        groupMembersCardView;

        public GroupMemberViewHolder(@NonNull View itemView) {
            super(itemView);

            groupMembersNameTextView = itemView.findViewById(R.id.groupMembersNameTextView);
            groupMembersRemoveButton = itemView.findViewById(R.id.groupMembersRemoveButton);
            groupMembersAssignButton = itemView.findViewById(R.id.groupMembersAssignButton);

            groupMembersCardView = itemView.findViewById(R.id.groupMembersCardView);

        }
    }
}
