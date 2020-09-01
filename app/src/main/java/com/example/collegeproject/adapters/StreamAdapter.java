package com.example.collegeproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.module.StreamModule;
import com.example.collegeproject.ui.activitys.MainActivity;
import com.example.collegeproject.utility.SosManagement;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StreamAdapter extends RecyclerView.Adapter<StreamAdapter.MyViewHodler> {

    Context context;
    ArrayList<StreamModule> list;

    public StreamAdapter(Context context, ArrayList<StreamModule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_stream_holder, parent, false);
        return new MyViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodler holder, int position) {

        StreamModule module = list.get(position);
        Glide.with(context).load(utils_string.BASE_URL
                + utils_string.IMAGE_URL.COLLEGE_GALLERY + module.getS_image())
                .into(holder.streamImage);

        holder.streamText.setText(module.getS_name());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodler extends RecyclerView.ViewHolder {
        TextView streamText;
        CircleImageView streamImage;

        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            streamImage = itemView.findViewById(R.id.streamImage);
            streamText = itemView.findViewById(R.id.streamText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StreamModule module=list.get(getAdapterPosition());
                    Toast.makeText(context, ""+module.s_id, Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(context, MainActivity.class);
                    new SosManagement(context).setStreamId(module.s_id);
                    context.startActivity(i);

                }
            });
        }
    }
}
