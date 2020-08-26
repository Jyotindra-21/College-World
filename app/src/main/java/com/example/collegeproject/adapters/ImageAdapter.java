package com.example.collegeproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.module.ImageModule;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    Context context;
    ArrayList<ImageModule> list;


    public ImageAdapter(Context context, ArrayList<ImageModule> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_gallery_iamge, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ImageModule module = list.get(position);
        Glide.with(context).load(utils_string.BASE_URL + utils_string.IMAGE_URL.COLLEGE_GALLERY + module.getProfile())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.flipperImage1);
        }
    }
}
