package com.example.collegeproject.ui.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.collegeproject.R;
import com.example.collegeproject.adapters.ProfileViewPager;
import com.example.collegeproject.interfaces.DrawerLock;
import com.google.android.material.tabs.TabLayout;


public class ProfileFragment extends Fragment {

    ViewPager pager;
    TabLayout tab;
    Toolbar toolbar;

    /*public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.drawerMenu);
        if (item != null)
            item.setVisible(false);
        *//*item = menu.findItem(R.id.noti);
        if (item != null)
            item.setVisible(true);*//*
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        pager = view.findViewById(R.id.viewPager);
        tab = view.findViewById(R.id.tabMode);
        toolbar = view.findViewById(R.id.toolbarPager);

        ((DrawerLock) getActivity()).setDrawerLocked(true);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

        ProfileViewPager adapter = new ProfileViewPager(getChildFragmentManager());
        adapter.addFragment(new ProfileLoaderFragment(), "Profile");
        adapter.addFragment(new WishListFragment(), "Wish List");
        adapter.addFragment(new MoreInfoFragment(), "Review");

        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager, true);


        return view;
    }
}