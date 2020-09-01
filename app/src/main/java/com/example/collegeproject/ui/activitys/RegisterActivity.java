package com.example.collegeproject.ui.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.ConnectionCall;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.databasecall.jsn;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout passwordLY;
    TextInputEditText name, email, password, phoneno, city, dob;
    Button submit;
    RadioButton male, female;
    RadioGroup radioGroup;
    CheckBox box;
    public final static String PREF = "1";
    ProgressBar processRes;
    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        passwordLY = findViewById(R.id.passwordLayoutre);
        name = findViewById(R.id.firstname);

        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        phoneno = findViewById(R.id.userPhone);
        city = findViewById(R.id.userCity);
        radioGroup = findViewById(R.id.rg);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        submit = findViewById(R.id.submit);
        dob = findViewById(R.id.userDate);
        box = findViewById(R.id.tearms);
        processRes = findViewById(R.id.processRes);


        dob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();
                year = c.get(android.icu.util.Calendar.YEAR);
                month = c.get(android.icu.util.Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {


                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int select = radioGroup.getCheckedRadioButtonId();
                male = findViewById(select);

                if (new ConnectionCall(RegisterActivity.this).connectiondetect()) {

                    if (validation()) {
                        processRes.setVisibility(View.VISIBLE);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("type", "register");

                        hashMap.put("name", name.getText().toString().trim());
                        hashMap.put("email", email.getText().toString().trim());
                        hashMap.put("password", password.getText().toString().trim());
                        hashMap.put("mobile", phoneno.getText().toString().trim());
                        hashMap.put("gender", male.getText().toString().trim());
                        hashMap.put("city", city.getText().toString().trim());
                        hashMap.put("dateofbirth", dob.getText().toString().trim());

                        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
                            @Override
                            public boolean setResponse(String responseStr) {

                                try {
                                    JSONObject reader = new JSONObject(responseStr);
                                    if (reader.getString("action").equals("1")) {

                                        processRes.setVisibility(View.INVISIBLE);
                                        JSONObject cat = jsn.getJSONObjectAt0(responseStr);
                                        String uid = cat.getString("id");
                                        SharedPreferences.Editor share;
                                        share = getSharedPreferences(PREF, MODE_PRIVATE).edit();
                                        share.putString("student_id", uid);
                                        share.apply();

                                        Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(i);

                                    } else {
                                        processRes.setVisibility(View.INVISIBLE);
                                        Toast.makeText(RegisterActivity.this, "There was Some Problem in Registered ", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    processRes.setVisibility(View.INVISIBLE);
                                    Toast.makeText(RegisterActivity.this, "network issue" + responseStr, Toast.LENGTH_SHORT).show();
                                }

                                return true;
                            }
                        });
                    }
                }
            }
        });

    }

    public boolean validation() {
        boolean isValid = true;
        if (TextUtils.isEmpty(name.getText().toString().trim())) {
            name.setError("Enter Your First Name");
            isValid = false;
        } else if (TextUtils.isEmpty(email.getText().toString().trim())) {
            email.setError("Enter Your Email Id");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Enter Your valid Email ID");
            isValid = false;
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            passwordLY.setError("Enter Your Password");
            isValid = false;
        } else if (isValidPassword(password.getText().toString())) {
            passwordLY.setError("use a-z, A-Z, @!$#.., length 5-15");
            isValid = false;
        } else if (TextUtils.isEmpty(phoneno.getText().toString())) {
            phoneno.setError("Enter Your Phone No");
            isValid = false;
        } else if (phoneno.length() < 10 || phoneno.length() > 15) {
            phoneno.setError("Enter Your Valid Phone No");
            isValid = false;
        } else if (male.isChecked() && female.isChecked()) {
            Toast.makeText(RegisterActivity.this, "Please Select your Gender", Toast.LENGTH_LONG).show();
            isValid = false;
        } else if (TextUtils.isEmpty(dob.getText().toString())) {
            dob.setError("Enter Your Date Of Birth");
            isValid = false;
        } else if (TextUtils.isEmpty(city.getText().toString())) {
            city.setError("Enter Your City");
            isValid = false;
        } else if (!box.isChecked()) {
            Toast.makeText(RegisterActivity.this, "Check Term and Condition", Toast.LENGTH_LONG).show();
            box.setFocusable(true);
            isValid = false;
        } else {
            name.setError(null);
            email.setError(null);
            passwordLY.setError(null);
            phoneno.setError(null);
            dob.setError(null);
            city.setError(null);
        }

        return isValid;
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,15}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return !matcher.matches();


    }

}