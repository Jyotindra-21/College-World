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
import com.example.collegeproject.module.PlacementModule;

import java.util.ArrayList;

public class PlacementAdapter extends RecyclerView.Adapter<PlacementAdapter.MyViewHolder> {

    Context context;
    ArrayList<PlacementModule> list;

    public PlacementAdapter(Context context, ArrayList<PlacementModule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layput_placement_holder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlacementModule module = list.get(position);
        holder.name.setText(module.p_name);
        holder.description.setText(module.p_com_desc);
        holder.ratting.setText(module.p_com_ratting);
        Glide.with(context).load(utils_string.BASE_URL + utils_string.IMAGE_URL.COLLEGE_GALLERY + module.profile).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, description, ratting;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.placementImage);
            name = itemView.findViewById(R.id.placementName);
            description = itemView.findViewById(R.id.placementDes);
            ratting = itemView.findViewById(R.id.placemenRatting);
        }
    }
}
