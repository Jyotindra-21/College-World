package com.example.collegeproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.module.WishListModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    Context context;
    ArrayList<WishListModule> list;
    HashMap<WishListModule, List<String>> listHashMap;

    public WishListAdapter(Context context, ArrayList<WishListModule> list, HashMap<WishListModule, List<String>> listHashMap) {
        this.context = context;
        this.list = list;
        this.listHashMap = listHashMap;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wish_list_holder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WishListModule module = list.get(position);
        Glide.with(context).load(utils_string.BASE_URL + utils_string.IMAGE_URL.
                COLLEGE_GALLERY + module.profile).into(holder.image);
        holder.textView.setText(module.clg_name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CircleImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.wishlistImage);
            imageView = itemView.findViewById(R.id.wishLogo);
            textView = itemView.findViewById(R.id.wishTitle);
        }
    }
}
