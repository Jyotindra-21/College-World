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
import com.example.collegeproject.adapters.FacultyAdapter;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.module.FacultyModule;
import com.example.collegeproject.responsemodule.FacultyResponse;
import com.example.collegeproject.utility.SosManagement;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarException;

public class CollegeFacultyFragment extends Fragment {

    ArrayList<FacultyModule> list = new ArrayList<>();
    String name, clg_id;
    TextView faculty;
    ImageView imageView;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_college_faculty, container, false);
        faculty = view.findViewById(R.id.facultyClg);
        recyclerView = view.findViewById(R.id.facultyDetail);
        name = getActivity().getIntent().getExtras().getString("clg_name");
        faculty.setText(name);
        progressBar = view.findViewById(R.id.processFaculty);
        imageView = view.findViewById(R.id.facultyNoData);

        clg_id = new SosManagement(getContext()).getCollegeId();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        list.clear();
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getfaculty");
        hashMap.put("clg_id", clg_id);

        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                try {
                    imageView.setVisibility(View.GONE);
                    JSONObject r = new JSONObject(responseStr);
                    FacultyResponse response = new Gson().fromJson(responseStr, FacultyResponse.class);
                    if (response.action == 1) {
                        list.addAll(response.message);
                        FacultyAdapter adapter = new FacultyAdapter(getContext(), list);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


        return view;
    }
}