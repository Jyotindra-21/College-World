package com.example.collegeproject.ui.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.collegeproject.R;
import com.example.collegeproject.adapters.HomeAdapter;
import com.example.collegeproject.adapters.HomeSearchAdapter;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.interfaces.DrawerLock;
import com.example.collegeproject.module.HomeModule;
import com.example.collegeproject.responsemodule.HomeResponse;
import com.example.collegeproject.utility.SosManagement;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<HomeModule> list = new ArrayList<>();
    ImageView emptyData;
    public String s_id;
    android.widget.SearchView searchView;
    HomeSearchAdapter adapter;
    ProgressBar processHome;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        recyclerView = view.findViewById(R.id.searchRecycleView);
        emptyData = view.findViewById(R.id.noDataSearch);
        processHome = view.findViewById(R.id.processSearch);
        searchView = view.findViewById(R.id.searchView);


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        s_id = new SosManagement(getContext()).getStreamId();

        ((DrawerLock) getActivity()).setDrawerLocked(true);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Search");

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }
        });

        recyclerView.setVisibility(View.INVISIBLE);
        processHome.setVisibility(View.VISIBLE);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "gethome");
        hashMap.put("s_id", s_id);

        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                try {
                    emptyData.setVisibility(View.GONE);
                    HomeResponse homeResponse = new Gson().fromJson(responseStr, HomeResponse.class);
                    if (homeResponse.action == 1) {
                        list.addAll(homeResponse.message);
                        adapter = new HomeSearchAdapter(getContext(), list);
                        recyclerView.setAdapter(adapter);
                        processHome.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(getContext(), "something wrong", Toast.LENGTH_SHORT).show();
                        processHome.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    emptyData.setVisibility(View.VISIBLE);
                    processHome.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        return view;
    }
}