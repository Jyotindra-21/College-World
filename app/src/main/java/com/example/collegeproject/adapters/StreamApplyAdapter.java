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
import com.example.collegeproject.module.StreamModule;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StreamApplyAdapter extends ArrayAdapter<StreamModule> {


    Context context;
    ArrayList<StreamModule> list;

    public StreamApplyAdapter(Context context, ArrayList<StreamModule> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public void add(StreamModule object) {
        this.list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StreamModule getItem(int position) {
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
        StreamModule module = getItem(position);
        CircleImageView imageView = convertView.findViewById(R.id.spinnerImage);
        TextView textView = convertView.findViewById(R.id.spinnerText);

        if (module != null) {
            Glide.with(context).load(utils_string.BASE_URL
                    + utils_string.IMAGE_URL.COLLEGE_GALLERY + module.getS_image())
                    .into(imageView);
            textView.setText(module.s_name);
        }
        return convertView;
    }
}

/*
package com.example.collegeproject.adapters;

        import android.content.Context;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import com.example.collegeproject.module.StreamModule;

        import java.util.ArrayList;

        public class StreamApplyAdapter extends ArrayAdapter<StreamModule> {


        Context context;
        ArrayList<StreamModule> list;

        public StreamApplyAdapter(Context context, int resource, ArrayList<StreamModule> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
        }

        @Override
        public void add(StreamModule object) {
        this.list.add(object);
        }

        @Override
        public int getCount() {
        return list.size();
        }

        @Override
        public StreamModule getItem(int position) {
        return list.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView textView = new TextView(context);

        textView.setText(list.get(position).getS_name());

        return textView;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(list.get(position).getS_name());

        return textView;

        }
        }*/

