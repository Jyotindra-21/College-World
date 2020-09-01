package com.example.collegeproject.ui.fragments;

import android.media.Image;
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
import com.example.collegeproject.adapters.PlacementAdapter;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.module.PlacementModule;
import com.example.collegeproject.responsemodule.PlacementResponse;
import com.example.collegeproject.utility.SosManagement;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class CollegePlacementFragment extends Fragment {

    ArrayList<PlacementModule> list = new ArrayList<>();
    RecyclerView recyclerView;
    String clg_id;
    TextView clgname;
    ImageView imageView;
    ProgressBar processPlace;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_college_placement, container, false);
        recyclerView = view.findViewById(R.id.placementRe);
        processPlace = view.findViewById(R.id.processPlace);
        clgname = view.findViewById(R.id.placementClg);
        imageView = view.findViewById(R.id.placementNoData);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        clg_id = new SosManagement(getContext()).getCollegeId();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "placement");
        hashMap.put("clg_id", clg_id);

        String name = getActivity().getIntent().getExtras().getString("clg_name");
        clgname.setText(name);

        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                try {
                    imageView.setVisibility(View.INVISIBLE);
                    processPlace.setVisibility(View.VISIBLE);
                    JSONObject reader = new JSONObject(responseStr);
                    PlacementResponse placementResponse = new Gson().fromJson(responseStr, PlacementResponse.class);
                    if (placementResponse.action == 1) {
                        list.addAll(placementResponse.message);
                        PlacementAdapter adapter = new PlacementAdapter(getContext(), list);
                        recyclerView.setAdapter(adapter);
                        processPlace.setVisibility(View.INVISIBLE);
                    } else {
                        processPlace.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    imageView.setVisibility(View.VISIBLE);
                    processPlace.setVisibility(View.INVISIBLE);
                    // Toast.makeText(getContext(), "placement:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        return view;
    }
}