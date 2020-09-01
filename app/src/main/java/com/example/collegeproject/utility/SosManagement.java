package com.example.collegeproject.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.collegeproject.databasecall.jsn;

import org.json.JSONObject;

public class SosManagement {

    SharedPreferences sharedPreferences;
    Context context;
    private String Key_Login = "login";

    public SosManagement(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLogin(boolean isLogin) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Key_Login, isLogin);
        editor.apply();
    }

    public boolean getLogin() {
        return sharedPreferences.getBoolean(Key_Login, false);
    }

    public void getLogout(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

    public void setStreamId(String s_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("s_id", s_id);
        editor.apply();
    }

    public String getStreamId() {
        return sharedPreferences.getString("s_id", "-1");
    }

    public void setCollegeId(String clg_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("clg_id", clg_id);
        editor.apply();
    }

    public String getCollegeId() {
        return sharedPreferences.getString("clg_id", "-1");
    }

}
