package com.example.collegeproject.responsemodule;


import com.example.collegeproject.module.PlacementModule;

import java.io.Serializable;
import java.util.ArrayList;

public class PlacementResponse implements Serializable {
    public ArrayList<PlacementModule> message;
    public int action;
}
