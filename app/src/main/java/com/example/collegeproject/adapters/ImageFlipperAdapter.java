package com.example.collegeproject.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.module.ImageModule;

import java.util.ArrayList;

public class ImageFlipperAdapter extends BaseAdapter {

    Context context;
    ArrayList<ImageModule> list;


    public ImageFlipperAdapter(Context context, ArrayList<ImageModule> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.layout_flipper_image_holder, viewGroup, false);


        ImageView imageView = view.findViewById(R.id.flipperImage);

        ImageModule module = list.get(i);
        Glide.with(context).load(utils_string.BASE_URL + utils_string.IMAGE_URL.COLLEGE_GALLERY + module.getProfile())
                .into(imageView);
        
        return view;
    }
}
