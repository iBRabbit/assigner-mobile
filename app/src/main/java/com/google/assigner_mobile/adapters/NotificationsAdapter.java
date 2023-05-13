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
import com.google.assigner_mobile.models.AppNotification;

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
