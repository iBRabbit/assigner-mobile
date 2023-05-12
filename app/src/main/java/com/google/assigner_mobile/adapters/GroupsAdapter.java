package com.google.assigner_mobile.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.activities.groups.GroupDetailsActivity;
import com.google.assigner_mobile.helpers.GroupHelper;
import com.google.assigner_mobile.helpers.GroupMembersHelper;
import com.google.assigner_mobile.models.Group;

import java.util.Vector;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupViewHolder>{


    Context context;
    Vector<Group> groupVector;

    public GroupsAdapter(Context context) {
        this.context = context;
    }

    public void setGroupVector(Vector<Group> groupVector) {
        this.groupVector = groupVector;
    }

    @NonNull
    @Override
    public GroupsAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);

        return new GroupViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GroupsAdapter.GroupViewHolder holder, int position) {
        // Group Name Text View
        holder.groupNameTextView.setText(groupVector.get(position).getName());

        // Group Description Text View
        holder.groupDescriptionTextView.setText(groupVector.get(position).getDescription());

        GroupMembersHelper groupMembersDB = new GroupMembersHelper(context);
        groupMembersDB.open();

        // Group Members Text View
        holder.groupMembersTextView.setText(
                groupMembersDB.getGroupMembersSizeByGroupId(
                        groupVector.get(position).getId()
                ) + " members"
        );

        groupMembersDB.close();

        // Group Card View
        holder.groupCardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GroupDetailsActivity.class);
            intent.putExtra("groupId", groupVector.get(position).getId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return groupVector.size();
    }

    public static class GroupViewHolder extends  RecyclerView.ViewHolder {

        TextView    groupNameTextView,
                    groupDescriptionTextView,
                    groupMembersTextView;

        CardView    groupCardView;
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            groupNameTextView = itemView.findViewById(R.id.groupNameTextView);
            groupDescriptionTextView = itemView.findViewById(R.id.groupDescriptionTextView);
            groupMembersTextView = itemView.findViewById(R.id.groupMembersTextView);

            groupCardView = itemView.findViewById(R.id.groupCardView);
        }

    }
}
