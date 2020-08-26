package com.example.collegeproject.responsemodule;

import com.example.collegeproject.module.HomeModule;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeResponse implements Serializable {
    public ArrayList<HomeModule> message;
    public int action;
}
