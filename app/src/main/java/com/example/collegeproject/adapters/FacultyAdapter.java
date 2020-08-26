package com.example.collegeproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;
import com.example.collegeproject.module.FacultyModule;

import java.util.ArrayList;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.MyViewHolder> {

    Context context;
    ArrayList<FacultyModule> list;


    public FacultyAdapter(Context context, ArrayList<FacultyModule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_faculty_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FacultyModule module = list.get(position);

        holder.name.setText(module.f_name);
        holder.email.setText(module.f_email);
        holder.department.setText(module.f_department);
        holder.degree.setText(module.f_degree);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, department, degree;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.facultylname);
            email = itemView.findViewById(R.id.facultyemail);
            department = itemView.findViewById(R.id.facultydepartment);
            degree = itemView.findViewById(R.id.facultydegree);
        }
    }
}
