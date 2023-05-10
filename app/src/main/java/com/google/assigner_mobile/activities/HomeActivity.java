package com.google.assigner_mobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.assigner_mobile.R;
import com.google.assigner_mobile.fragments.AssignmentsFragment;
import com.google.assigner_mobile.fragments.GroupsFragment;
import com.google.assigner_mobile.fragments.NotificationsFragment;
import com.google.assigner_mobile.fragments.ProfileFragment;
import com.google.assigner_mobile.functions.GlobalFunction;

public class HomeActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    GlobalFunction fragment = new GlobalFunction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        frameLayout = findViewById(R.id.homeFrameLayout);
        bottomNavigationView = findViewById(R.id.homeBottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.assignments)
                    return fragment.openFragment(new AssignmentsFragment(), HomeActivity.this);

                if(item.getItemId() == R.id.groups)
                    return fragment.openFragment(new GroupsFragment(), HomeActivity.this);

                if(item.getItemId() == R.id.notifications)
                    return fragment.openFragment(new NotificationsFragment(), HomeActivity.this);

                if(item.getItemId() == R.id.profile)
                    return fragment.openFragment(new ProfileFragment(), HomeActivity.this);

                return false;
            }
        });

        fragment.openFragment(new AssignmentsFragment(), HomeActivity.this);
    }
}