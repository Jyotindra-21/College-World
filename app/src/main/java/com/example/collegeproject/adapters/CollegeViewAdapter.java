package com.example.collegeproject.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.collegeproject.ui.fragments.CollegeFacultyFragment;
import com.example.collegeproject.ui.fragments.CollegeImageFragment;
import com.example.collegeproject.ui.fragments.CollegePlacementFragment;
import com.example.collegeproject.ui.fragments.CollegeViewFragment;

public class CollegeViewAdapter extends FragmentPagerAdapter {


    public CollegeViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        switch (position){

            case 1:
                fragment= new CollegeImageFragment();
                break;

            case 2:
                fragment= new CollegePlacementFragment();
                break;

            case 3:
                fragment= new CollegeFacultyFragment();
                break;
            case 0:

            default:
                    fragment=new CollegeViewFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
