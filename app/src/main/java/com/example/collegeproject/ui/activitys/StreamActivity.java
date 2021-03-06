package com.example.collegeproject.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    ImageView imageView;
    ProgressBar processStream;
    ArrayList<StreamModule> list = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StreamActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        recyclerView = findViewById(R.id.rv_stream);
        processStream = findViewById(R.id.processStream);
        imageView = findViewById(R.id.streamNoData);
        GridLayoutManager manager = new GridLayoutManager(StreamActivity.this, 3);
        recyclerView.setLayoutManager(manager);

        setRecyclerView();
        processStream.setVisibility(View.VISIBLE);

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
                    processStream.setVisibility(View.INVISIBLE);
                    if (response.action == 1) {
                        list.addAll(response.message);
                        StreamAdapter adapter = new StreamAdapter(StreamActivity.this, list);
                        recyclerView.setAdapter(adapter);
                    }

                } catch (Exception e) {
                    processStream.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    //Toast.makeText(StreamActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}