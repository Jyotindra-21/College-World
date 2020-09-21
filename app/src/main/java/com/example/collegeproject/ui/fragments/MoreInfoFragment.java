package com.example.collegeproject.ui.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import static android.os.Handler.*;

public class MoreInfoFragment extends Fragment {

    RecyclerView userReviews;
    Button min, max, reviewSubmit;
    ArrayList<ReviewModule> list = new ArrayList<>();
    EditText clgDescription;
    Spinner reSpinner;
    RatingBar ratingBar;
    String uid, clgid, name, profiles, date, clgname;
    Float getRatingBar;
    ProgressBar progressBar;
    ImageView imageView;
    Dialog dialog;
    ReviewAdapter reviewAdapter;
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
        imageView = view.findViewById(R.id.reviewNoData);
        progressBar = view.findViewById(R.id.processReview);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        userReviews.setLayoutManager(manager);

        clgid = new SosManagement(getContext()).getCollegeId();

        /*if (reviewAdapter.getItemCount() > 0) {
            min.setVisibility(View.INVISIBLE);
            max.setVisibility(View.VISIBLE);

        } else {
            min.setVisibility(View.VISIBLE);
            max.setVisibility(View.INVISIBLE);
        }*/

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

        getReview();
        list.clear();

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

        Date currenttime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        date = df.format(currenttime.getTime());
        getUserImage();
        //add review
        reviewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()) {
                    getRatingBar = ratingBar.getRating();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("type", "addReview");
                    hashMap.put("clg_id", clgid);
                    hashMap.put("name", name);
                    hashMap.put("profile", profiles);
                    hashMap.put("student_id", uid);
                    hashMap.put("clg_name", clgname);
                    hashMap.put("description", clgDescription.getText().toString().trim());
                    hashMap.put("ratting", getRatingBar.toString());
                    hashMap.put("rdate", date);
                    NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
                        @Override
                        public boolean setResponse(String responseStr) {

                            try {
                                JSONObject reader = new JSONObject(responseStr);
                                if (reader.getString("action").equals("1")) {
                                    Toast.makeText(getContext(), "Your Review Added", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    getReview();
                                    list.clear();
                                } else {
                                    Toast.makeText(getContext(), "some thing is wrong", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            return false;
                        }
                    });
                }
            }
        });

        //get profile detail
        HashMap<String, String> param = new HashMap<>();
        param.put("type", "getProfile");
        param.put("student_id", uid);
        NetworkCall.call(param).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                if (jsn.checkResponseStr(responseStr)) {
                    JSONObject userDetails = jsn.getJSONObjectAt0(responseStr);
                    try {
                        name = userDetails.getString("name");
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
                    Toast.makeText(getContext(), "getCollegeName:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void getUserImage() {
        HashMap<String, String> param = new HashMap<>();
        param.put("type", "getImageuserProfile");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF, MODE_PRIVATE);
        uid = sharedPreferences.getString("student_id", "1");
        param.put("student_id", uid);
        NetworkCall.call(param).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                try {
                    JSONObject userProfile = jsn.getJSONObjectAt0(responseStr);
                    if (jsn.checkResponseStr(responseStr)) {
                        profiles = userProfile.getString("profile");
                        Toast.makeText(getContext(), profiles, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "not get profile image", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Upload Your Profile Image", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

    }

    //get Review by user
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
                    imageView.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    JSONObject r = new JSONObject(responseStr);
                    ReviewResponse reviewResponse = new Gson().fromJson(responseStr, ReviewResponse.class);
                    if (reviewResponse.action == 1) {
                        list.addAll(reviewResponse.message);
                        reviewAdapter = new ReviewAdapter(getContext(), list);
                        userReviews.setAdapter(reviewAdapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                   // imageView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                   // Toast.makeText(getContext(), "getReviewUser" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    boolean validation() {
        boolean isValid = true;
        if (TextUtils.isEmpty(clgDescription.getText().toString())) {
            clgDescription.setError("Please Enter Description");
            isValid = false;
        } else if (ratingBar.getRating() == 0.0) {
            Toast.makeText(getContext(), "give Some Star", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

}