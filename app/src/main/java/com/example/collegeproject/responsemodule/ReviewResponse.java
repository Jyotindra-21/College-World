package com.example.collegeproject.responsemodule;

import com.example.collegeproject.module.ReviewModule;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewResponse implements Serializable {

    public ArrayList<ReviewModule> message;
    public int action;
}
