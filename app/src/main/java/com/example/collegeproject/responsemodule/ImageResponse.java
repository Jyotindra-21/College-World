package com.example.collegeproject.responsemodule;

import com.example.collegeproject.module.ImageModule;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageResponse implements Serializable {

    public ArrayList<ImageModule> message;
    public int action;
}
