package com.example.collegeproject.module;

public class StreamModule {

    public String s_id;
    public String s_name;
    public String s_image;

    public StreamModule(String s_id, String s_name, String s_image) {
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_image = s_image;
    }

    public String getS_id() {
        return s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public String getS_image() {
        return s_image;
    }
}
