package com.google.assigner_mobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.activities.assignments.AssignmentDetailsActivity;
import com.google.assigner_mobile.helpers.GroupHelper;
import com.google.assigner_mobile.models.Assignment;
import com.google.assigner_mobile.models.Group;

import java.util.Vector;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentViewHolder> {

    Context context;
    Vector <Assignment> assignmentVector;

    public AssignmentsAdapter(Context context) {
        this.context = context;
    }

    public void setAssignmentVector(Vector<Assignment> assignmentVector) {
        this.assignmentVector = assignmentVector;
    }

    @NonNull
    @Override
    public AssignmentsAdapter.AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.assignment_item, parent, false);
        return new AssignmentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentsAdapter.AssignmentViewHolder holder, int position) {

        // Assignment Name Textview
        holder.assignmentNameTextView.setText(assignmentVector.get(position).getName());

        // Group Text View //
        GroupHelper groupDB = new GroupHelper(context);
        holder.assignmentGroupTextView.setText(
                groupDB.getGroupById(
                        assignmentVector
                                .get(position)
                                .getGroupId()
                ).getName()
        );

        // Deadline Text View //
        holder.assignmentDeadlineTextView.setTextColor(
                assignmentVector
                        .get(position)
                        .getDueColor(
                                assignmentVector
                                        .get(position)
                                        .getDeadline()
                        )
        );

        // Assignment Progress Bar //
        holder.assignmentProgressBar.setProgress(
                assignmentVector
                        .get(position)
                        .getProgress()
        );
        // set progress bar color
        holder.assignmentProgressBar.setProgressTintList(
                assignmentVector
                        .get(position)
                        .getProgressColor(
                                assignmentVector
                                        .get(position)
                                        .getProgress()
                        )
        );


        holder.assignmentDeadlineTextView.setText(
                assignmentVector
                        .get(position)
                        .calculateDeadlineDueAsText(
                                assignmentVector
                                        .get(position)
                                        .getDeadline()
                        )
        );

        holder.assignmentCardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AssignmentDetailsActivity.class);
            intent.putExtra("assignmentId", assignmentVector.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return assignmentVector.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView    assignmentNameTextView,
                    assignmentGroupTextView,
                    assignmentDeadlineTextView;

        ProgressBar assignmentProgressBar;
        CardView    assignmentCardView;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);

            assignmentNameTextView  = itemView.findViewById(R.id.assignmentNameTextView);
            assignmentGroupTextView = itemView.findViewById(R.id.assignmentGroupTextView);
            assignmentDeadlineTextView = itemView.findViewById(R.id.assignmentDeadlineTextView);
            assignmentProgressBar = itemView.findViewById(R.id.assignmentProgressBar);

            assignmentCardView = itemView.findViewById(R.id.assignmentCardView);
        }
    }
}
