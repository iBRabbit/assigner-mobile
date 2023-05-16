package com.google.assigner_mobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.activities.HomeActivity;
import com.google.assigner_mobile.helpers.GroupMembersHelper;
import com.google.assigner_mobile.helpers.InvitationHelper;
import com.google.assigner_mobile.helpers.NotificationHelper;
import com.google.assigner_mobile.models.AppNotification;
import com.google.assigner_mobile.models.Invitation;

import java.util.Vector;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>{

    Context context;
    Vector <AppNotification> notificationsVector;

    public NotificationsAdapter(Context context) {
        this.context = context;
    }

    public void setNotificationsVector(Vector<AppNotification> notificationsVector) {
        this.notificationsVector = notificationsVector;
    }

    @NonNull
    @Override
    public NotificationsAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.NotificationViewHolder holder, int position) {
        holder.notificationTitle.setText(notificationsVector.get(position).getTitle());

        holder.notificationMessage.setText(notificationsVector.get(position).getMessage());

        holder.notificationCreatedAt.setText(notificationsVector.get(position).getCreatedAt().toString());

        if(notificationsVector.get(position).getType() == AppNotification.TYPE_INVITATION) {
            holder.notificationAcceptButton.setVisibility(View.VISIBLE);
            holder.notificationDeclineButton.setVisibility(View.VISIBLE);
        }
        else {
            holder.notificationAcceptButton.setVisibility(View.GONE);
            holder.notificationDeclineButton.setVisibility(View.GONE);
        }

        holder.notificationAcceptButton.setOnClickListener(v -> {
            InvitationHelper invitationDB = new InvitationHelper(context);
            invitationDB.open();
            Integer invitationId = notificationsVector.get(position).getInvitationId();

            Invitation invitation = invitationDB.getInvitation(invitationId);

            // Cek apakah invitation masih valid
            if(invitation == null){
                Toast.makeText(context, "Invitation is no longer valid", Toast.LENGTH_SHORT).show();
                return;
            }

            GroupMembersHelper gmh = new GroupMembersHelper(context);
            gmh.open();
            gmh.insert(invitation.getGroupId(), invitation.getUserId());
            gmh.close();

            invitationDB.delete(invitationId);

            NotificationHelper notifDB = new NotificationHelper(context);
            notifDB.open();
            notifDB.delete(notificationsVector.get(position).getId());
            notifDB.close();

            Toast.makeText(context, "Invitation accepted", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);

            invitationDB.close();
        });

        holder.notificationDeclineButton.setOnClickListener(v -> {
            InvitationHelper invitationDB = new InvitationHelper(context);
            invitationDB.open();
            Integer invitationId = notificationsVector.get(position).getInvitationId();

            Invitation invitation = invitationDB.getInvitation(invitationId);

            // Cek apakah invitation masih valid
            if(invitation == null){
                Toast.makeText(context, "Invitation is no longer valid", Toast.LENGTH_SHORT).show();
                return;
            }

            invitationDB.delete(invitationId);

            NotificationHelper notifDB = new NotificationHelper(context);
            notifDB.open();
            notifDB.delete(notificationsVector.get(position).getId());
            notifDB.close();

            Toast.makeText(context, "Invitation Rejected", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);

            invitationDB.close();
        });

    }

    @Override
    public int getItemCount() {
        return notificationsVector.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView        notificationTitle,
                        notificationMessage,
                        notificationCreatedAt;

        Button          notificationAcceptButton,
                        notificationDeclineButton;

        CardView        notificationCardView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationMessage = itemView.findViewById(R.id.notificationMessage);
            notificationCreatedAt = itemView.findViewById(R.id.notificationCreatedAt);

            notificationAcceptButton = itemView.findViewById(R.id.notificationAcceptButton);

            notificationDeclineButton = itemView.findViewById(R.id.notificationDeclineButton);

            notificationCardView = itemView.findViewById(R.id.notificationCardView);
        }
    }
}
