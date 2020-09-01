package com.example.collegeproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;
import com.example.collegeproject.module.CourseAndFeeModule;

import java.util.ArrayList;

public class CourseAndFeeAdapter extends RecyclerView.Adapter<CourseAndFeeAdapter.MyViewHolder> {

    Context context;
    ArrayList<CourseAndFeeModule> list;

    public CourseAndFeeAdapter(Context context, ArrayList<CourseAndFeeModule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.laylout_course_holder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CourseAndFeeModule module = list.get(position);
        holder.courseName.setText(module.co_name);
        holder.fees.setText(module.fees);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseName, fees;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            fees = itemView.findViewById(R.id.fees);
        }
    }
}
