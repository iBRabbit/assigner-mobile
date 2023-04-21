package com.google.assigner_mobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.assigner_mobile.R;
import com.google.assigner_mobile.fragments.AssignmentsFragment;
import com.google.assigner_mobile.fragments.GroupsFragment;
import com.google.assigner_mobile.fragments.NotificationsFragment;
import com.google.assigner_mobile.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

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
                    return openFragment(new AssignmentsFragment());

                if(item.getItemId() == R.id.groups)
                    return openFragment(new GroupsFragment());

                if(item.getItemId() == R.id.notifications)
                    return openFragment(new NotificationsFragment());

                if(item.getItemId() == R.id.profile)
                    return openFragment(new ProfileFragment());

                return false;
            }
        });

        openFragment(new AssignmentsFragment());
    }

    boolean openFragment(Fragment fragment) {
        if(fragment == null)
            return false;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFrameLayout, fragment)
                .commit();

        return true;
    }
}