package com.example.collegeproject.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.databasecall.jsn;
import com.example.collegeproject.databasecall.utils_string;
import com.example.collegeproject.utility.ImagePicker;
import com.example.collegeproject.utility.Master_Upload;
import com.example.collegeproject.utility.RequestPermisions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class ProfileLoaderFragment extends Fragment {

    EditText name, email, phone, date, gender, city;
    String names, emails, phones, dates, genderr, citys;
    TextView edit, update;
    public static final String PREF = "1";
    FloatingActionButton camera;
    String uid, uploadpath;
    int day, month, year;
    String imagetitle;
    Uri imageUri;
    CircleImageView profileImage;
    final int PICK_IMAGE = 110;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_loder, container, false);
        city = view.findViewById(R.id.procity);
        date = view.findViewById(R.id.prodate);
        email = view.findViewById(R.id.proemail);
        name = view.findViewById(R.id.proname);
        phone = view.findViewById(R.id.prophone);
        gender = view.findViewById(R.id.progender);
        edit = view.findViewById(R.id.editpro);
        camera = view.findViewById(R.id.camera);
        update = view.findViewById(R.id.updatepro);
        profileImage = view.findViewById(R.id.profileImage);

        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();
                year = c.get(android.icu.util.Calendar.YEAR);
                month = c.get(android.icu.util.Calendar.MONTH);
                day = c.get(android.icu.util.Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {


                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });

        //update Profile Here
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgUpload();

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("type", "updateProfile");
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF, MODE_PRIVATE);
                uid = sharedPreferences.getString("student_id", "1");
                hashMap.put("student_id", uid);

                hashMap.put("name", name.getText().toString().trim());
                hashMap.put("email", email.getText().toString().trim());
                hashMap.put("mobile", phone.getText().toString().trim());
                hashMap.put("gender", gender.getText().toString().trim());
                hashMap.put("city", city.getText().toString().trim());
                hashMap.put("dateofbirth", date.getText().toString().trim());

                NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
                    @Override
                    public boolean setResponse(String responseStr) {
                        try {
                            JSONObject reader = new JSONObject(responseStr);
                            if (reader.getString("action").equals("1")) {
                                Toast.makeText(getContext(), "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                SetObjectsFocusable(false);

            }
        });

        //profile edit button
        edit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                edit.setVisibility(View.INVISIBLE);
                update.setVisibility(View.VISIBLE);
                camera.setVisibility(View.VISIBLE);

                name.setFocusable(true);
                name.setFocusableInTouchMode(true);
                email.setFocusable(true);
                email.setFocusableInTouchMode(true);
                phone.setFocusable(true);
                phone.setFocusableInTouchMode(true);
                date.setFocusable(true);
                date.setFocusableInTouchMode(true);
                gender.setFocusable(true);
                gender.setFocusableInTouchMode(true);
                city.setFocusable(true);
                city.setFocusableInTouchMode(true);

            }
        });

        //open camera to set image
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestPermisions r = new RequestPermisions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        111, getActivity());
                RequestPermisions r1 = new RequestPermisions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        112, getActivity());
                if (r.checkPermission() && r1.checkPermission()) {
//                   Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                 startActivityForResult(gallery, PICK_IMAGE);
                    startActivityForResult(ImagePicker.getPickImageIntent(getActivity()), PICK_IMAGE);
                } else {
                    if (!r.checkPermission())
                        r.getPermission();
                    if (!r1.checkPermission())
                        r1.getPermission();
                }


            }

        });


        getImage();
        GetUserDetails();
        return view;
    }

    private void SetObjectsFocusable(Boolean b) {
        gender.setFocusable(b);
        gender.setFocusableInTouchMode(b);
        phone.setFocusable(b);
        phone.setFocusableInTouchMode(b);
        name.setFocusable(b);
        name.setFocusableInTouchMode(b);

        email.setFocusable(b);
        email.setFocusableInTouchMode(b);

        city.setFocusable(b);
        city.setFocusableInTouchMode(b);

        date.setFocusable(b);
        date.setFocusableInTouchMode(b);

        edit.setVisibility(View.VISIBLE);
        if (b) {
            update.setVisibility(View.VISIBLE);
        } else {
            update.setVisibility(View.GONE);

        }
    }

    private void imgUpload() {

        // long id = 1;
        Date currenttime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        imagetitle = df.format(currenttime.getTime());

        Master_Upload master_upload = new Master_Upload();
        JSONObject jsonObject = master_upload.Master_Upload(imageUri, getActivity(),
                imagetitle, ".jpeg", utils_string.IMAGE_URL.USER_PROFILE);


        try {
            if (jsonObject.getString("action").equals("1")) {
                HashMap<String, String> params = new HashMap<>();
                params.put("type","AddImages");

                SharedPreferences sharedPreferences=getContext().getSharedPreferences(PREF,MODE_PRIVATE);
                uid=sharedPreferences.getString("student_id","1");
                params.put("student_id", uid);
                params.put("profile", imagetitle + ".jpeg");
                Toast.makeText(getActivity(), uid, Toast.LENGTH_SHORT).show();

                NetworkCall.call(params).setDataResponseListener(new NetworkCall.SetDataResponse() {
                    @Override
                    public boolean setResponse(String responseStr) {
                        try {

                            JSONObject reader = new JSONObject(responseStr);
                            if (reader.getString("action").equals("1")) {

                                Toast.makeText(getContext(), "Image Uploaded ! ", Toast.LENGTH_SHORT).show();
                                /*Intent returnIntent = new Intent(ProfileActivity.this,SettingActivity.class);
                                setResult(Activity.RESULT_OK, returnIntent);
                                startActivity(returnIntent);
                                finish();*/
                                // Toast.makeText(ProfileActivity.this, "Registered !!!", Toast.LENGTH_LONG).show();
                                //  Intent i = new Intent(CreateCardActivity.this, CardDesignActivity.class);
                                //i.putExtra("email", semail);
                                // startActivity(i);
                            } else {
                                Toast.makeText(getContext(), "There was some problem in Registering.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "network issue" + responseStr, Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            try {
                Bitmap bitmap1 = ImagePicker.getImageFromResult(getContext(), resultCode, data);
                uploadpath = MediaStore.Images.Media.insertImage
                        (getActivity().getContentResolver()
                                , bitmap1, "testImg1", null);

//                uploadImage(finalUri);
                //   clUpload.setVisibility(View.VISIBLE);
                profileImage.setImageBitmap(bitmap1);
//                imageView_user.setImageBitmap(bitmap1);
//                imageView_user.setBlur(5);

            } catch (Exception e) {
                Toast.makeText(getContext(), "Catch:" + e, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getImage() {
        HashMap<String, String> param = new HashMap<>();
        param.put("type", "getImageuserProfile");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF, MODE_PRIVATE);
        uid = sharedPreferences.getString("student_id", "1");
        param.put("student_id", uid);
        Toast.makeText(getContext(), uid + "", Toast.LENGTH_SHORT).show();
        NetworkCall.call(param).setDataResponseListener(new NetworkCall.SetDataResponse() {
            @Override
            public boolean setResponse(String responseStr) {

                try {
                    JSONObject userProfile = jsn.getJSONObjectAt0(responseStr);

                    if (jsn.checkResponseStr(responseStr)) {
                        Glide.with(getActivity()).load(utils_string.BASE_URL + utils_string.IMAGE_URL.USER_PROFILE +
                                userProfile.getString("profile")).into(profileImage);
                    } else {
                        Toast.makeText(getContext(), "not get profile image", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "getImage:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

    }

    private void GetUserDetails() {
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

                        names = userdetails.getString("name");
                        emails = userdetails.getString("email");
                        phones = userdetails.getString("mobile");
                        dates = userdetails.getString("dateofbirth");
                        genderr = userdetails.getString("gender");
                        citys = userdetails.getString("city");

                        name.setText(names);
                        email.setText(emails);
                        phone.setText(phones);
                        date.setText(dates);
                        gender.setText(genderr);
                        city.setText(citys);


                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Catch1:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }
}