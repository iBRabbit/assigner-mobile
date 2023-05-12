package com.google.assigner_mobile.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.assigner_mobile.R;
import com.google.assigner_mobile.activities.LoginActivity;
import com.google.assigner_mobile.functions.AuthFunction;
import com.google.assigner_mobile.functions.GlobalFunction;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Temporary Logout
        AuthFunction auth = new AuthFunction();
        auth.setAuthID(new GlobalFunction().safeGetContext(getContext()), -1);

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

        return inflater.inflate(R.layout.fragment_profile, container, false);


    }
}