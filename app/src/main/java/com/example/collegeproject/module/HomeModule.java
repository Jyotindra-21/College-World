package com.example.collegeproject.module;

public class HomeModule {

    public String clg_id;
    public String clg_name;
    public String clg_email;
    public String clg_websit;
    public String clg_branch;
    public String clg_contact;
    public String profile;



    public HomeModule(String clg_name, String clg_email, String clg_websit, String clg_branch, String clg_contact, String profile) {
        this.clg_name = clg_name;
        this.clg_email = clg_email;
        this.clg_websit = clg_websit;
        this.clg_branch = clg_branch;
        this.clg_contact = clg_contact;
        this.profile = profile;
    }



    public String getClg_name() {
        return clg_name;
    }

    public String getProfile() {
        return profile;
    }
}
