package com.google.assigner_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.models.User;

import java.util.Vector;

public class GroupMembersAdapter extends RecyclerView.Adapter<GroupMembersAdapter.GroupMemberViewHolder>{

    Context context;
    Vector <User> groupMembersVector;


    public GroupMembersAdapter(Context context) {
        this.context = context;
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

            holder.groupMembersNameTextView.setText(groupMembersVector.get(position).getUsername());

            holder.groupMembersAssignButton.setOnClickListener(view -> {
                // TODO : Pindahin add assignment ke sini
            });

            holder.groupMembersRemoveButton.setOnClickListener(view -> {
                // TODO : Remove Member if Owner
            });
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
