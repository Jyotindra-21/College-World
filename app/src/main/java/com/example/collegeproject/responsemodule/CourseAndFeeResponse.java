package com.example.collegeproject.responsemodule;

import com.example.collegeproject.module.CourseAndFeeModule;
import com.example.collegeproject.module.FacultyModule;

import java.io.Serializable;
import java.util.ArrayList;

public class CourseAndFeeResponse implements Serializable {
    public ArrayList<CourseAndFeeModule> message;
    public int action;
}
