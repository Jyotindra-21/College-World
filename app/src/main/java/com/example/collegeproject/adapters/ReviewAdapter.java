package com.example.collegeproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.module.ReviewModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    Context context;
    ArrayList<ReviewModule> list;

    public ReviewAdapter(Context context, ArrayList<ReviewModule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_review_holder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ReviewModule module = list.get(position);
        holder.name.setText(module.name);
        holder.description.setText(module.description);
        holder.ratingBar.setRating(module.ratting);
        holder.date.setText(module.rdate);
        Glide.with(context).load(utils_string.BASE_URL + utils_string.IMAGE_URL.USER_PROFILE
                + module.profile).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        CircleImageView circleImageView;
        TextView name, description, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingReview);
            name = itemView.findViewById(R.id.nameReview);
            date = itemView.findViewById(R.id.dateReview);
            description = itemView.findViewById(R.id.descriptionReview);
            circleImageView = itemView.findViewById(R.id.profileReview);
        }
    }
}
