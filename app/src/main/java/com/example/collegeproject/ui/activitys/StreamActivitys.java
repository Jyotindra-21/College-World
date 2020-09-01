package com.example.collegeproject.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class StreamActivitys extends AppCompatActivity {

    ImageView imageView;
    RecyclerView recyclerView;
    String id = "1";
    ProgressBar processStream;
    ArrayList<StreamModule> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streams);

        processStream = findViewById(R.id.processStream1);
        recyclerView = findViewById(R.id.rv_stream1);
        imageView = findViewById(R.id.streamsNoData);

        GridLayoutManager manager = new GridLayoutManager(StreamActivitys.this, 3);
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
                    imageView.setVisibility(View.GONE);
                    JSONObject reader = new JSONObject(responseStr);
                    StreamResponse response = new Gson().fromJson(responseStr, StreamResponse.class);

                    if (response.action == 1) {
                        list.addAll(response.message);
                        StreamAdapter adapter = new StreamAdapter(StreamActivitys.this, list);
                        recyclerView.setAdapter(adapter);
                    }

                } catch (Exception e) {
                    imageView.setVisibility(View.VISIBLE);
                    Toast.makeText(StreamActivitys.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}