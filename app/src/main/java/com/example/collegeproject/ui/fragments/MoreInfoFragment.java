package com.example.collegeproject.ui.fragments;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.adapters.ReviewAdapter;
import com.example.collegeproject.adapters.ReviewCollegeAdapter;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.databasecall.jsn;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.module.HomeModule;
import com.example.collegeproject.module.ReviewModule;

import com.example.collegeproject.responsemodule.HomeResponse;
import com.example.collegeproject.responsemodule.ReviewResponse;
import com.example.collegeproject.utility.SosManagement;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MoreInfoFragment extends Fragment {

    RecyclerView userReviews;
    Button min, max, reviewSubmit;
    ArrayList<ReviewModule> list = new ArrayList<>();
    HashMap<ReviewModule, List<String>> listHashMap;
    EditText clgDescription;
    Spinner reSpinner;
    RatingBar ratingBar;
    String uid, clgid, name, date, profiles, getRatingBar, clgname;
    Dialog dialog;
    ArrayList<HomeModule> modulesList = new ArrayList<>();
    public static final String PREF = "1";
    String id = "1";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_info, container, false);
        userReviews = view.findViewById(R.id.userReview);
        min = view.findViewById(R.id.addReviewMin);
        max = view.findViewById(R.id.addReviewMax);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        userReviews.setLayoutManager(manager);

        clgid = new SosManagement(getContext()).getCollegeId();

        getReview();

        if (list.size() > 0) {
            max.setVisibility(View.VISIBLE);
            min.setVisibility(View.INVISIBLE);
        } else {
            min.setVisibility(View.VISIBLE);
            max.setVisibility(View.INVISIBLE);
        }

        max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Dialog();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Dialog();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    //review Dialogs Call
    private void Dialog() throws ParseException {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_add_review);

        reSpinner = dialog.findViewById(R.id.reSpinner);
        clgDescription = dialog.findViewById(R.id.descriptionRe);
        ratingBar = dialog.findViewById(R.id.ratingRe);
        reviewSubmit = dialog.findViewById(R.id.reviewSubmit);
        TextView back = dialog.findViewById(R.id.applyBack);

        dialog.setCanceledOnTouchOutside(false);


        getRatingBar = String.valueOf(ratingBar.getRating());

        Date currenttime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        date = df.format(currenttime.getTime());


        reviewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "" + getRatingBar, Toast.LENGTH_SHORT).show();

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("type", "addReview");
                hashMap.put("clg_id", clgid);
                hashMap.put("student_id", uid);
                hashMap.put("profile", profiles);
                hashMap.put("clg_name", clgname);
                hashMap.put("description", clgDescription.getText().toString().trim());
                hashMap.put("ratting", getRatingBar);
                hashMap.put("rdate", date);

                NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
                    @Override
                    public boolean setResponse(String responseStr) {

                        try {
                            JSONObject reader = new JSONObject(responseStr);
                            if (reader.getString("action").equals("1")) {
                                Toast.makeText(getContext(), "Your Review Added", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });
            }
        });

        HashMap<String, String> param = new HashMap<>();
        param.put("type", "getProfile");
        param.put("student_id", uid);
        NetworkCall.call(param).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                if (jsn.checkResponseStr(responseStr)) {
                    JSONObject userdetails = jsn.getJSONObjectAt0(responseStr);
                    try {
                        name = userdetails.getString("name");
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Catch1:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        reSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                HomeModule module = (HomeModule) adapterView.getItemAtPosition(i);
                clgname = module.clg_name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //fetching  stream data
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getCollegeName");
        hashMap.put("fix", id);
        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                try {
                    JSONObject r = new JSONObject(responseStr);
                    HomeResponse homeResponse = new Gson().fromJson(responseStr, HomeResponse.class);
                    if (homeResponse.action == 1) {
                        modulesList.addAll(homeResponse.message);
                        ReviewCollegeAdapter applyAdapter = new ReviewCollegeAdapter(getContext(), modulesList);
                        reSpinner.setAdapter(applyAdapter);

                    } else {
                        Toast.makeText(getContext(), "There was some error fetching data.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "catchJson:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        HashMap<String, String> param1 = new HashMap<>();
        param.put("type", "getImageuserProfile");
        param.put("student_id", uid);
        Toast.makeText(getContext(), uid + "", Toast.LENGTH_SHORT).show();
        NetworkCall.call(param1).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                if (jsn.checkResponseStr(responseStr)) {
                    JSONObject userdetails = jsn.getJSONObjectAt0(responseStr);
                    try {
                        profiles = userdetails.getString("profile");
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "getImage:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    void getReview() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getreviewuser");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF, MODE_PRIVATE);
        uid = sharedPreferences.getString("student_id", "1");
        hashMap.put("student_id", uid);

        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                try {
                    JSONObject r = new JSONObject(responseStr);
                    ReviewResponse reviewResponse = new Gson().fromJson(responseStr, ReviewResponse.class);
                    if (reviewResponse.action == 1) {
                        list.addAll(reviewResponse.message);
                        ReviewAdapter adapter = new ReviewAdapter(getContext(), list, listHashMap);
                        userReviews.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }
}