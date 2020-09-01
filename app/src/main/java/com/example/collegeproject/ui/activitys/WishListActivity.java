package com.example.collegeproject.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.collegeproject.R;
import com.example.collegeproject.ui.fragments.HomeFragment;
import com.example.collegeproject.ui.fragments.WishListFragment;

public class WishListActivity extends AppCompatActivity {

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        linearLayout=findViewById(R.id.wishReplace);

        WishListFragment wishListFragment = new WishListFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.wishReplace, wishListFragment);
        ft.commit();

    }
}