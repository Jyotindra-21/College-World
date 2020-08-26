package com.example.collegeproject.ui.layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.collegeproject.R;
import com.example.collegeproject.adapters.CollegeViewAdapter;
import com.google.android.material.tabs.TabLayout;

public class CollegeViewLoaderActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tab;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_view_loader);
        pager = findViewById(R.id.viewPager1);
        tab = findViewById(R.id.tabMode1);
        toolbar = findViewById(R.id.toolbarPager1);


        CollegeViewAdapter adapter = new CollegeViewAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager, true);
    }
}