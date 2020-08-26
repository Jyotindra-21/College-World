package com.example.collegeproject.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collegeproject.R;
import com.example.collegeproject.adapters.WishListAdapter;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.module.WishListModule;
import com.example.collegeproject.responsemodule.WishListResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class WishListFragment extends Fragment {


    RecyclerView recyclerView;
    HashMap<WishListModule, List<String>> listHashMap;
    ArrayList<WishListModule> list = new ArrayList<>();
    String uid;
    public static final String PREF = "1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        recyclerView = view.findViewById(R.id.wishlist);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        setRecyclerView();

        return view;
    }

    public void setRecyclerView() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getwishlist");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF, MODE_PRIVATE);
        uid = sharedPreferences.getString("student_id", "1");
        hashMap.put("student_id", uid);

        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                try {
                    WishListResponse response = new Gson().fromJson(responseStr, WishListResponse.class);
                    if (response.action == 1) {
                        list.addAll(response.message);
                        WishListAdapter adapter = new WishListAdapter(getContext(), list, listHashMap);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });


    }

}