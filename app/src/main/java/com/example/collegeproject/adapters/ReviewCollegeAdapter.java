package com.example.collegeproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.module.HomeModule;
import com.example.collegeproject.module.StreamModule;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewCollegeAdapter extends ArrayAdapter<HomeModule> {


    Context context;
    ArrayList<HomeModule> list;

    public ReviewCollegeAdapter(Context context, ArrayList<HomeModule> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public void add(HomeModule object) {
        this.list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HomeModule getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return CollegeView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return CollegeView(position, convertView, parent);
    }

    public View CollegeView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_stream_apply, parent, false);
        }
        HomeModule module = getItem(position);
        TextView textView = convertView.findViewById(R.id.spinnerText);

        if (module != null) {
            textView.setText(module.clg_name);
        }
        return convertView;
    }
}


