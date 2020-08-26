package com.example.collegeproject.responsemodule;

import com.example.collegeproject.module.HomeModule;
import com.example.collegeproject.module.StreamApplyModule;

import java.io.Serializable;
import java.util.ArrayList;

public class StreamApplyResponse implements Serializable {
    public ArrayList<StreamApplyModule> message;
    public int action;
}
