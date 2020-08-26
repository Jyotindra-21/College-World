package com.example.collegeproject.responsemodule;

import com.example.collegeproject.module.StreamModule;

import java.io.Serializable;
import java.util.ArrayList;

public class StreamResponse implements Serializable {

    public ArrayList<StreamModule> message;
    public int  action;

}
