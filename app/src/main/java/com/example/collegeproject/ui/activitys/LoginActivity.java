package com.example.collegeproject.ui.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.ConnectionCall;
import com.example.collegeproject.databasecall.NetworkCall;
import com.example.collegeproject.databasecall.jsn;
import com.example.collegeproject.module.HomeModule;
import com.example.collegeproject.utility.SosManagement;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText email, password;
    TextInputLayout passwordLay;
    Button button;
    CheckBox box;
    TextView fpassword;
    ProgressBar processLog;
    public final static String PREF = "1";
    TextView user, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.log_emil);
        password = findViewById(R.id.log_password);
        button = findViewById(R.id.login);
        user = findViewById(R.id.log_newUser);
        passwordLay = findViewById(R.id.passwordLayout);
        signup = findViewById(R.id.log_sign);
        box = findViewById(R.id.remember);
        processLog = findViewById(R.id.processLog);
        fpassword = findViewById(R.id.fpassword);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new ConnectionCall(LoginActivity.this).connectiondetect()) {
                    if (validation()) {
                        processLog.setVisibility(View.VISIBLE);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("type", "login");

                        hashMap.put("email", email.getText().toString());
                        hashMap.put("password", password.getText().toString());

                        NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
                            @Override
                            public boolean setResponse(String responseStr) {

                                try {
                                    JSONObject reader = new JSONObject(responseStr);
                                    if (reader.getString("action").equals("1")) {
                                        processLog.setVisibility(View.INVISIBLE);
                                        JSONObject cat = jsn.getJSONObjectAt0(responseStr);
                                        SharedPreferences.Editor share;
                                        share = getSharedPreferences(PREF, MODE_PRIVATE).edit();
                                        share.putString("email", email.getText().toString());
                                        share.putString("student_id", cat.getString("student_id"));
                                        share.apply();
                                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, StreamActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (Exception e) {
                                processLog.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Check Your Login Id", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                                return false;
                            }
                        });
                    }
                }
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });
        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    public boolean validation() {
        boolean isValid = true;

        if (TextUtils.isEmpty(email.getText().toString().trim())) {
            email.setError("Enter Your Email Id");
            email.setFocusable(true);
            email.setFocusableInTouchMode(true);
            email.requestFocus();
            isValid = false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Enter valid Email Id");
            email.setFocusable(true);
            email.requestFocus();
            isValid = false;

        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            passwordLay.setError("Enter Your password");
            password.setFocusable(true);
            password.setFocusableInTouchMode(true);
            password.requestFocus();
            isValid = false;
        } else if (box.isChecked()) {
            new SosManagement(LoginActivity.this).setLogin(true);
        } else {
            email.setError(null);
            password.setError(null);
        }

        return isValid;
    }
}