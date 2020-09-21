package com.example.collegeproject.ui.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.databasecall.jsn;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.interfaces.DrawerLock;
import com.example.collegeproject.ui.fragments.HomeFragment;
import com.example.collegeproject.ui.fragments.ProfileFragment;
import com.example.collegeproject.ui.fragments.SearchFragment;
import com.example.collegeproject.utility.SosManagement;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements DrawerLock {

    FrameLayout frameLayout;
    BottomNavigationView bottomAppBar;
    Toolbar toolbar;
    AdvanceDrawerLayout drawer;
    NavigationView navigationView;
    String names, emails, uid, profile;
    public static final String PREF = "1";


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.replaceFragment);
        bottomAppBar = findViewById(R.id.bottomNavigation);
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        // drawer.setDrawerListener(actionBarDrawerToggle);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //assign custom behavior for "Left" drawer
        drawer.useCustomBehavior(Gravity.START);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);

        View header = getLayoutInflater().inflate(R.layout.layout_header, navigationView, false);
        navigationView.addHeaderView(header);


        TextView email = header.findViewById(R.id.emailDrawer);
        TextView name = header.findViewById(R.id.nameDrawer);
        CircleImageView imageView = header.findViewById(R.id.imageDrawer);


        HashMap<String, String> param = new HashMap<>();
        param.put("type", "getImageuserProfile");
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(PREF, MODE_PRIVATE);
        uid = sharedPreferences.getString("student_id", "1");
        param.put("student_id", uid);
        NetworkCall.call(param).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                try {
                    JSONObject userProfile = jsn.getJSONObjectAt0(responseStr);
                    if (jsn.checkResponseStr(responseStr)) {
                        Glide.with(MainActivity.this).load(utils_string.BASE_URL +
                                utils_string.IMAGE_URL.USER_PROFILE +
                                userProfile.getString("profile")).into(imageView);

                    } else {
                        Toast.makeText(MainActivity.this, "not get profile image",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Glide.with(MainActivity.this).load(R.drawable.clglogo
                    ).into(imageView);
                    Toast.makeText(MainActivity.this, "Upload Your Profile Image",
                            Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getProfile");
        sharedPreferences = MainActivity.this.getSharedPreferences(PREF, MODE_PRIVATE);
        uid = sharedPreferences.getString("student_id", "1");
        hashMap.put("student_id", uid);
        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                if (jsn.checkResponseStr(responseStr)) {
                    JSONObject userdetails = jsn.getJSONObjectAt0(responseStr);
                    try {
                        names = userdetails.getString("name");
                        emails = userdetails.getString("email");
                        name.setText(names);
                        email.setText(emails);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Network Problem\nCheck Your Internet", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        //add HomeFragment default
        try {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.replaceFragment, homeFragment);
            ft.commit();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        //drawer navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.changeCourse:
                        Intent i = new Intent(MainActivity.this, StreamActivitys.class);
                        drawer.closeDrawer(GravityCompat.START);
                        startActivity(i);
                        break;

                    case R.id.wishList:
                        Intent w = new Intent(MainActivity.this, WishListActivity.class);
                        drawer.closeDrawer(GravityCompat.START);
                        startActivity(w);
                        break;

                    case R.id.review:
                        Intent r = new Intent(MainActivity.this, ReviewActivity.class);
                        drawer.closeDrawer(GravityCompat.START);
                        startActivity(r);
                        break;

                    case R.id.ratting:
                        Toast.makeText(MainActivity.this, "Not Available Yet", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.share:
                        Toast.makeText(MainActivity.this, "Not Available yet", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.exit:
                        Intent e = new Intent(MainActivity.this, LoginActivity.class);
                        drawer.closeDrawer(GravityCompat.START);
                        new SosManagement(MainActivity.this).getLogout();
                        e.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(e);
                        finish();
                        break;

                }
                return false;
            }
        });

        //bottom Navigation
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

        KeyboardVisibilityEvent.setEventListener(MainActivity.this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if(isOpen){
                    bottomAppBar.setVisibility(View.INVISIBLE);
                }else {
                    bottomAppBar.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void setDrawerLocked(boolean sh) {
        if (sh) {
            drawer.setDrawerLockMode(AdvanceDrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            drawer.setDrawerLockMode(AdvanceDrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        drawer.openDrawer(navigationView);
        return super.onOptionsItemSelected(item);
    }
}