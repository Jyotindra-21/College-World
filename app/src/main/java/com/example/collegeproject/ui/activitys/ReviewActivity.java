package com.example.collegeproject.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.collegeproject.R;
import com.example.collegeproject.ui.fragments.MoreInfoFragment;

public class ReviewActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        linearLayout = findViewById(R.id.reviewReplay);
        MoreInfoFragment moreInfoFragment = new MoreInfoFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().
                replace(R.id.reviewReplay, moreInfoFragment);
        ft.commit();
    }
}