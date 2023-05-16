package com.google.assigner_mobile.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.adapters.NotificationsAdapter;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.functions.GlobalFunction;
import com.google.assigner_mobile.helpers.NotificationHelper;
import com.google.assigner_mobile.models.AppNotification;

import java.util.Vector;


public class NotificationsFragment extends Fragment {

    RecyclerView notificationsRecyclerView;
    Vector <AppNotification> notificationsVector;
    NotificationHelper notifDB;
    AuthFunction auth = new AuthFunction();

    NotificationsAdapter notificationsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        if(view == null)
            return null;

        notificationsRecyclerView = view.findViewById(R.id.notificationsRecyclerView);

        notificationsVector = new Vector<>();
        notifDB = new NotificationHelper(getContext());

        notifDB.open();
        notificationsVector = notifDB.getAllNotificationsByUserId(auth.getAuthID(new GlobalFunction().safeGetContext(getContext())));


        Toast.makeText(getContext(), "Notifications: " + notificationsVector.size(), Toast.LENGTH_SHORT).show();
        notifDB.close();

        notificationsAdapter = new NotificationsAdapter(getContext());
        notificationsAdapter.setNotificationsVector(notificationsVector);

        notificationsRecyclerView.setAdapter(notificationsAdapter);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}