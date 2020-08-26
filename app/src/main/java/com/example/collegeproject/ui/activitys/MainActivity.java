package com.example.collegeproject.ui.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.collegeproject.R;
import com.example.collegeproject.ui.fragments.HomeFragment;
import com.example.collegeproject.ui.fragments.ProfileFragment;
import com.example.collegeproject.ui.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bottomAppBar;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.replaceFragment);
        bottomAppBar = findViewById(R.id.bottomNavigation);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        try {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.replaceFragment, homeFragment);
            ft.commit();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        bottomAppBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        menuItem.setIcon(R.drawable.home_selector);
                        break;

                    case R.id.search:
                        fragment = new SearchFragment();
                        break;

                    case R.id.profiles:
                        fragment = new ProfileFragment();
                        menuItem.setIcon(R.drawable.profile_selector);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.replaceFragment, fragment).commit();
                return true;
            }
        });
    }

}