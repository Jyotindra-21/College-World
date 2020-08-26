package com.example.collegeproject.responsemodule;

import com.example.collegeproject.module.HomeModule;
import com.example.collegeproject.module.WishListModule;

import java.io.Serializable;
import java.util.ArrayList;

public class WishListResponse implements Serializable {
    public ArrayList<WishListModule> message;
    public int action;
}
