package com.example.collegeproject.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeproject.R;
import com.example.collegeproject.adapters.ImageAdapter;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.module.ImageModule;
import com.example.collegeproject.responsemodule.ImageResponse;
import com.example.collegeproject.utility.SosManagement;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CollegeImageFragment extends Fragment {

    ArrayList<ImageModule> list = new ArrayList<>();
    RecyclerView recyclerView;
    String clg_id, name;
    TextView textView;
    ImageView imageView;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_college_image, container, false);
        recyclerView = view.findViewById(R.id.imageGridView);
        textView = view.findViewById(R.id.imageClg);
        imageView=view.findViewById(R.id.imageNoData);
        progressBar=view.findViewById(R.id.processImage);

        name = getActivity().getIntent().getExtras().getString("clg_name");
        textView.setText(name);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        list.clear();

        clg_id = new SosManagement(getContext()).getCollegeId();
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getImage");
        hashMap.put("clg_id", clg_id);

        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                try {
                    imageView.setVisibility(View.INVISIBLE);
                    JSONObject r = new JSONObject(responseStr);
                    ImageResponse response = new Gson().fromJson(responseStr, ImageResponse.class);
                    if (response.action == 1) {
                        list.addAll(response.message);
                        ImageAdapter adapter = new ImageAdapter(getContext(), list);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    //Toast.makeText(getActivity(), "iamge :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                     imageView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        return view;
    }
}