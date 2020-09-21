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
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.adapters.CourseAndFeeAdapter;
import com.example.collegeproject.adapters.ImageFlipperAdapter;
import com.example.collegeproject.adapters.ReviewAdapter;
import com.example.collegeproject.adapters.StreamApplyAdapter;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.databasecall.jsn;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.module.CourseAndFeeModule;
import com.example.collegeproject.module.ImageModule;
import com.example.collegeproject.module.ReviewModule;
import com.example.collegeproject.module.StreamModule;
import com.example.collegeproject.responsemodule.CourseAndFeeResponse;
import com.example.collegeproject.responsemodule.ImageResponse;
import com.example.collegeproject.responsemodule.ReviewResponse;
import com.example.collegeproject.responsemodule.StreamResponse;
import com.example.collegeproject.utility.SosManagement;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;


public class CollegeViewFragment extends Fragment {

    ArrayList<ImageModule> list = new ArrayList<>();
    ArrayList<ReviewModule> reviewList = new ArrayList<>();
    ArrayList<StreamModule> streaAplly = new ArrayList<>();
    ArrayList<CourseAndFeeModule> courseList = new ArrayList<>();
    String clg_id, name;
    EditText nameAp, emailAp, phoneAp, clgNameAp, perAp;
    Spinner streamAp;
    ImageView imageView;
    public static final String PREF = "1";
    Dialog dialog;
    AdapterViewFlipper fp;
    RecyclerView reviewView, courseAndFree;
    Button apply;
    String uid, getStreamApply;
    String id = "1";
    TextView branch1, website1, contact1, email1, name1;
    CircleImageView collegeImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_college_view, container, false);

        fp = view.findViewById(R.id.flipperViewHome);
        name1 = view.findViewById(R.id.clgname);
        branch1 = view.findViewById(R.id.clgbranch);
        website1 = view.findViewById(R.id.clgwebsite);
        email1 = view.findViewById(R.id.clgemail);
        contact1 = view.findViewById(R.id.clgcontact);
        collegeImage = view.findViewById(R.id.collegeLogo);
        imageView = view.findViewById(R.id.dataImage);
        apply = view.findViewById(R.id.apply);
        reviewView = view.findViewById(R.id.clgReview);
        courseAndFree = view.findViewById(R.id.courseAndFree);

        fp.setAutoStart(true);
        fp.setFlipInterval(4000);
        fp.startFlipping();


        clg_id = new SosManagement(getActivity()).getCollegeId();

        name = getActivity().getIntent().getExtras().getString("clg_name");
        String email = getActivity().getIntent().getExtras().getString("clg_email");
        String website = getActivity().getIntent().getExtras().getString("clg_website");
        String branch = getActivity().getIntent().getExtras().getString("clg_branch");
        String contact = getActivity().getIntent().getExtras().getString("clg_contact");
        String profile = getActivity().getIntent().getExtras().getString("profile");

        name1.setText(name);
        email1.setText(email);
        website1.setText(website);
        branch1.setText(branch);
        contact1.setText(contact);

        Glide.with(this).load(utils_string.BASE_URL + utils_string.IMAGE_URL.COLLEGE_GALLERY +
                profile).into(collegeImage);
        //flipperImage
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog();
            }
        });

        //flipperImage
        getImage();

        //getClgReview();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        reviewView.setLayoutManager(manager);

        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        courseAndFree.setLayoutManager(manager1);

        courseList.clear();
        reviewList.clear();
        getReview();

        getCourse();

        return view;


    }

    private void getCourse() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getCourse");
        hashMap.put("clg_id", clg_id);
        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                try {
                    JSONObject r = new JSONObject(responseStr);
                    CourseAndFeeResponse courseResponse = new Gson().fromJson(responseStr, CourseAndFeeResponse.class);
                    if (courseResponse.action == 1) {
                        courseList.addAll(courseResponse.message);
                        CourseAndFeeAdapter adapter = new CourseAndFeeAdapter(getContext(), courseList);
                        courseAndFree.setAdapter(adapter);
                    } else {
                        Toast.makeText(getContext(), "something wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                   // Toast.makeText(getContext(), "getCourse" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }


    //apply Button
    private void Dialog() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_apply);

        TextView back = dialog.findViewById(R.id.applyBack);
        Button applyMain = dialog.findViewById(R.id.applyMain);
        nameAp = dialog.findViewById(R.id.nameApply);
        emailAp = dialog.findViewById(R.id.emailApply);
        phoneAp = dialog.findViewById(R.id.mobileApply);
        clgNameAp = dialog.findViewById(R.id.collegeApply);
        streamAp = dialog.findViewById(R.id.streamApply);
        perAp = dialog.findViewById(R.id.percentageApply);


        dialog.setCanceledOnTouchOutside(false);
        clgNameAp.setText(name);

        //spinner Call
        streamAp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StreamModule module = (StreamModule) adapterView.getItemAtPosition(i);
                getStreamApply = module.s_name;
                Toast.makeText(getContext(), "" + getStreamApply, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        //Dialog apply call
        applyMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("type", "getapply");
                hashMap.put("student_id", uid);
                hashMap.put("clg_id", clg_id);
                hashMap.put("name", nameAp.getText().toString().trim());
                hashMap.put("email", emailAp.getText().toString().trim());
                hashMap.put("mobile", phoneAp.getText().toString().trim());
                hashMap.put("clg_name", clgNameAp.getText().toString().trim());
                hashMap.put("stream", getStreamApply);
                hashMap.put("percentage", perAp.getText().toString());

                NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
                    @Override
                    public boolean setResponse(String responseStr) {
                        try {
                            JSONObject r = new JSONObject(responseStr);
                            if (r.getString("action").equals("1")) {
                                Toast.makeText(getContext(), "Your Application Submit", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });

            }
        });

        //fetching stream data to database
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getstrApply");
        hashMap.put("fix", id);
        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                try {
                    JSONObject r = new JSONObject(responseStr);
                    StreamResponse streamApplyResponse = new Gson().fromJson(responseStr, StreamResponse.class);
                    if (streamApplyResponse.action == 1) {
                        streaAplly.addAll(streamApplyResponse.message);
                        StreamApplyAdapter applyAdapter = new StreamApplyAdapter(getContext(), streaAplly);
                        streamAp.setAdapter(applyAdapter);
                    } else {
                        Toast.makeText(getContext(), "There was some error fetching data.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "catchJson:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        getUserDetails();
        //dialog Exit
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    //get User Details
    public void getUserDetails() {
        HashMap<String, String> param = new HashMap<>();
        param.put("type", "getProfile");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF, MODE_PRIVATE);
        uid = sharedPreferences.getString("student_id", "1");
        param.put("student_id", uid);
        NetworkCall.call(param).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                if (jsn.checkResponseStr(responseStr)) {
                    JSONObject userdetails = jsn.getJSONObjectAt0(responseStr);
                    try {

                        String names = userdetails.getString("name");
                        String emails = userdetails.getString("email");
                        String phones = userdetails.getString("mobile");
                        nameAp.setText(names);
                        emailAp.setText(emails);
                        phoneAp.setText(phones);

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Network Problem\n Check your Internet", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    //get College Review By give users
    void getReview() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getClgReview");
        hashMap.put("clg_id", clg_id);

        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                try {
                    JSONObject r = new JSONObject(responseStr);
                    ReviewResponse reviewResponse = new Gson().fromJson(responseStr, ReviewResponse.class);
                    if (reviewResponse.action == 1) {
                        reviewList.addAll(reviewResponse.message);
                        ReviewAdapter adapter = new ReviewAdapter(getContext(), reviewList);
                        reviewView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    //Toast.makeText(getContext(), "getReviewUser" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }


    //Set College Image In Flipper View
    public void getImage() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "getImage");
        hashMap.put("clg_id", clg_id);

        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {
                imageView.setVisibility(View.INVISIBLE);
                try {
                    ImageResponse response = new Gson().fromJson(responseStr, ImageResponse.class);
                    if (response.action == 1) {
                        list.addAll(response.message);
                        ImageFlipperAdapter adapter = new ImageFlipperAdapter(getContext(), list);
                        fp.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    imageView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

    }
}