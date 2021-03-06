package com.example.collegeproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.module.HomeModule;
import com.example.collegeproject.ui.layout.CollegeViewLoaderActivity;
import com.example.collegeproject.utility.SosManagement;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class HomeSearchAdapter extends RecyclerView.Adapter<HomeSearchAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<HomeModule> list;
    ArrayList<HomeModule> listFull;

    public static String PREF = "1";

    public HomeSearchAdapter(Context context, ArrayList<HomeModule> list) {
        this.context = context;
        this.list = list;
        listFull = new ArrayList<>(list);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_holdler, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HomeModule module = list.get(position);
        Glide.with(context).load(utils_string.BASE_URL + utils_string.IMAGE_URL.COLLEGE_GALLERY +
                module.getProfile()).into(holder.collegeImage);

        Glide.with(context).load(utils_string.BASE_URL + utils_string.IMAGE_URL.COLLEGE_GALLERY +
                module.getProfile()).into(holder.collegeLogo);

        holder.collegeText.setText(module.getClg_name());
        holder.wishList.setImageResource(R.drawable.ic_wish_list);

        holder.wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("type", "addwishlist");
                SharedPreferences sharedPreferences = context.getSharedPreferences(PREF, MODE_PRIVATE);
                String uid = sharedPreferences.getString("student_id", "1");
                hashMap.put("student_id", uid);
                hashMap.put("clg_name", module.clg_name);
                hashMap.put("profile", module.profile);
                hashMap.put("clg_id", module.clg_id);
                NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
                    @Override
                    public boolean setResponse(String responseStr) {
                        try {

                            JSONObject reader = new JSONObject(responseStr);
                            if (reader.getString("action").equals("1")) {
                                Toast.makeText(context, "Wish List Add", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "something wrong", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<HomeModule> filterlist = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                //filterlist.addAll(listFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (HomeModule item : listFull) {
                    if (item.getClg_name().toLowerCase().contains(filterPattern)) {
                        filterlist.add(item);
                    }
                }
            }
            FilterResults result = new FilterResults();
            result.values = filterlist;
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView collegeImage, wishList;
        TextView collegeText;
        CircleImageView collegeLogo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            collegeImage = itemView.findViewById(R.id.collegeImage);
            wishList = itemView.findViewById(R.id.wishIcon);
            collegeText = itemView.findViewById(R.id.collegeTitle);
            collegeText = itemView.findViewById(R.id.collegeTitle);
            collegeLogo = itemView.findViewById(R.id.collegeLogo);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HomeModule module = list.get(getAdapterPosition());
                    Intent i = new Intent(context, CollegeViewLoaderActivity.class);
                    new SosManagement(context).setCollegeId(module.clg_id);
                    i.putExtra("clg_name", module.clg_name);
                    i.putExtra("clg_email", module.clg_email);
                    i.putExtra("clg_website", module.clg_websit);
                    i.putExtra("clg_branch", module.clg_branch);
                    i.putExtra("clg_contact", module.clg_contact);
                    i.putExtra("profile", module.profile);
                    context.startActivity(i);
                }
            });
        }
    }
}
