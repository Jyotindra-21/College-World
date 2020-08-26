package com.example.collegeproject.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ProfileViewPager extends FragmentPagerAdapter {
    ArrayList<Fragment> fragmentList =new ArrayList<>();
    ArrayList<String>fragmentsTitle=new ArrayList<>();
    public ProfileViewPager(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment,String title){
        fragmentList.add(fragment);
        fragmentsTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       return fragmentsTitle.get(position);
    }

}
