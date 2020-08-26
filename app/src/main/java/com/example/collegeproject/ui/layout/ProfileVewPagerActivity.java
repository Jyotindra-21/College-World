package com.example.collegeproject.ui.layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.collegeproject.R;
import com.example.collegeproject.adapters.ProfileViewPager;
import com.google.android.material.tabs.TabLayout;

public class ProfileVewPagerActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tab;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_vew_pager);
        pager=findViewById(R.id.viewPager);
        tab=findViewById(R.id.tabMode);
        toolbar=findViewById(R.id.toolbarPager);


        ProfileViewPager adapter=new ProfileViewPager(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager,true);
    }
}