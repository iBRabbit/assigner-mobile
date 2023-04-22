package com.google.assigner_mobile.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.helpers.GroupHelper;
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

        GroupHelper groupDB = new GroupHelper(context);
        groupDB.open();

        // Group Members Text View
        holder.groupMembersTextView.setText(
                groupDB.getGroupMemberSizeById(
                        groupVector.get(position).getId()
                ).toString() + " members"
        );

        groupDB.close();


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
