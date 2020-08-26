package com.example.collegeproject.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.collegeproject.R;
import com.example.collegeproject.adapters.StreamAdapter;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.module.StreamModule;
import com.example.collegeproject.responsemodule.StreamResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StreamActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String id = "1";

    ArrayList<StreamModule> list = new ArrayList<>();
    HashMap<StreamModule, List<String>> listHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        recyclerView = findViewById(R.id.rv_stream);
        GridLayoutManager manager = new GridLayoutManager(StreamActivity.this, 3);
        recyclerView.setLayoutManager(manager);

        setRecyclerView();

    }

    public void setRecyclerView() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getstr");
        hashMap.put("fix", id);

        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                try {
                    JSONObject reader = new JSONObject(responseStr);
                    StreamResponse response=new Gson().fromJson(responseStr,StreamResponse.class);

                    if (response.action == 1) {
                        list.addAll(response.message);
                        StreamAdapter adapter=new StreamAdapter(StreamActivity.this,list,listHashMap);
                        recyclerView.setAdapter(adapter);
                    }

                } catch (Exception e) {
                    Toast.makeText(StreamActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}