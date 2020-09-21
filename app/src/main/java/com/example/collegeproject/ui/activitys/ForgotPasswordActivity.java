package com.example.collegeproject.ui.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeproject.R;
import com.example.collegeproject.databasecall.NetworkCall;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText phone, pass, repass, otp;
    Button submit, send, submit1;
    ProgressBar bar;
    TextView resend;
    FirebaseAuth auth;
    LinearLayout otpLayout, passLayout;
    Toolbar toolbar;
    CountryCodePicker codePicker;
    String verificationId;
    ProgressDialog pd;
    Boolean verificationInPro = false;
    String uid;
    public static final String PREF="1";
    PhoneAuthProvider.ForceResendingToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        toolbar = findViewById(R.id.forgetTool);
        otpLayout = findViewById(R.id.otpLayout);
        passLayout = findViewById(R.id.passLayout);
        phone = findViewById(R.id.Fphoneno);
        pass = findViewById(R.id.Fnewpassword);
        repass = findViewById(R.id.Frepassword);
        otp = findViewById(R.id.Fotp);
        submit = findViewById(R.id.Fsubmit);
        submit1 = findViewById(R.id.Fsubmit1);
        send = findViewById(R.id.send);
        bar = findViewById(R.id.progress);
        resend = findViewById(R.id.resend);
        codePicker = findViewById(R.id.ccp);

        auth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid()) {

                    HashMap<String,String>hashMap=new HashMap<>();
                    hashMap.put("type","updatePassword");
                    SharedPreferences sharedPreferences = ForgotPasswordActivity.this.getSharedPreferences(PREF, MODE_PRIVATE);
                    uid = sharedPreferences.getString("student_id", "1");
                    hashMap.put("student_id", uid);
                    hashMap.put("password", pass.getText().toString());

                    NetworkCall.call(hashMap).setDataResponseListener(new NetworkCall.SetDataResponse() {
                        @Override
                        public boolean setResponse(String responseStr) {
                            try {
                                JSONObject reader = new JSONObject(responseStr);
                                if (reader.getString("action").equals("1")) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    //progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(ForgotPasswordActivity.this, "something Wrong", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                               // progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(ForgotPasswordActivity.this, "Network Problem\nCheck Your Internet", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!phone.getText().toString().isEmpty() && phone.getText().toString().length() == 10 || phone.getText().toString().length() > 15) {

                    String country = "+" + codePicker.getSelectedCountryCode() + phone.getText().toString();
                    pd = new ProgressDialog(ForgotPasswordActivity.this);
                    pd.setMessage("Sending Message ......");

                    pd.show();
                    requestOTP(country);

                } else {
                    phone.setError("Enter Valid Phone");
                }

            }
        });


        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useropt = otp.getText().toString();

                if (!useropt.isEmpty() && otp.length() == 6) {

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, useropt);
                    verifyAuth(credential);
                } else {
                    otp.setError("Enter Valid OTP...");
                    otp.setFocusable(true);
                    otp.setFocusableInTouchMode(true);
                    otp.requestFocus();

                }
            }

        });
    }

    private void verifyAuth(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(ForgotPasswordActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    otpLayout.setVisibility(View.GONE);
                    passLayout.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);

                    Toast.makeText(ForgotPasswordActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Authentication Unsuccessful", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void requestOTP(String country) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(country, 60L, TimeUnit.SECONDS, ForgotPasswordActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                otp.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);
                repass.setVisibility(View.VISIBLE);
                verificationInPro = true;
                token = forceResendingToken;
                pd.dismiss();

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(ForgotPasswordActivity.this, "OPT are in Valid" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public boolean isValid() {

        boolean inValid = true;



        if (TextUtils.isEmpty(pass.getText().toString())) {
            pass.setError("Enter Your New Password");
            pass.setFocusable(true);
            pass.setFocusableInTouchMode(true);
            pass.requestFocus();
            inValid = false;

        } else if (isValidPassword(pass.getText().toString())) {

            pass.setError("use a-z, A-Z, @!$#.., length 5-15");
            pass.setFocusable(true);
            pass.setFocusableInTouchMode(true);
            pass.requestFocus();
            inValid = false;

        } else if (TextUtils.isEmpty(repass.getText().toString())) {
            repass.setError("Enter Your New RePassword");
            repass.setFocusable(true);
            repass.setFocusableInTouchMode(true);
            repass.requestFocus();
            inValid = false;

        } else if (repass.equals(pass)) {
            repass.setError("Password are not match");
            repass.setFocusable(true);
            repass.setFocusableInTouchMode(true);
            repass.requestFocus();
            inValid = false;

        } else {
            pass.setError(null);
            repass.setError(null);

        }

        return inValid;

    }

    public boolean isValidPassword(String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,15}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);


        return !matcher.matches();

    }
}