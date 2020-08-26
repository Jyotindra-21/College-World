package com.example.collegeproject.responsemodule;

import com.example.collegeproject.module.FacultyModule;

import java.io.Serializable;
import java.util.ArrayList;

public class FacultyResponse implements Serializable {
    public ArrayList<FacultyModule> message;
    public int action;
}
