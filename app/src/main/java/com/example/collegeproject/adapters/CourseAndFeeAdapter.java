package com.example.collegeproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.databasecall.jsn;
import com.example.collegeproject.module.CourseAndFeeModule;
import com.example.collegeproject.utility.SosManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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
        new SosManagement(context).setCoId(module.co_id);
        String s = new SosManagement(context).getCoId();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getFee");
        hashMap.put("co_id", s);
        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                try {
                    JSONObject userProfile = jsn.getJSONObjectAt0(responseStr);
                    if (jsn.checkResponseStr(responseStr)) {
                        module.fees = (String) userProfile.get("fees");
                        holder.fees.setText(module.fees);
                    } else {
                        Toast.makeText(context, "not get profile image", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //Toast.makeText(context, "getCourse" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseName, fees;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            fees = itemView.findViewById(R.id.fees);
        }
    }
}
