package com.example.bookyevent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class SendOTPActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SendOTPActivity";
    private CountryCodePicker ccp;
    String code;
    private EditText mInputMobile;
    private Button mBtnGetOTP;
    private String verificationId="";
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private String  phoneNumber ;
    private PhoneAuthProvider.ForceResendingToken mSeResendingToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_o_t_p);

        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progress_bar);
        mInputMobile = findViewById(R.id.input_mobile);
        mBtnGetOTP = findViewById(R.id.btn_getOTP);
        ccp=findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(mInputMobile);
        mBtnGetOTP.setOnClickListener(this);
        phoneNumber=Constant.NUMBER;

    }

    @Override
    public void onClick(View v) {
        if (mInputMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(SendOTPActivity.this, "please enter mobile number", Toast.LENGTH_SHORT).show();
                }



        else {
            progressBar.setVisibility(View.VISIBLE);
            mBtnGetOTP.setVisibility(View.INVISIBLE);
                 /*
        When you call PhoneAuthProvider.verifyPhoneNumber,
         you must also provide an instance of OnVerificationStateChangedCallbacks,
         which contains implementations of the callback functions that handle the results of the request
         */
            mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    progressBar.setVisibility(View.GONE);
                    mBtnGetOTP.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(SendOTPActivity.this,MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    progressBar.setVisibility(View.GONE);
                    mBtnGetOTP.setVisibility(View.VISIBLE);
                    Toast.makeText(SendOTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    if (!s.toString().trim().isEmpty())
                    {
                        Constant.mVerificationId=s;
                        progressBar.setVisibility(View.GONE);
                        mBtnGetOTP.setVisibility(View.VISIBLE);
                        Constant.mResendToken=forceResendingToken;
                        Intent intent=new Intent(SendOTPActivity.this,VerifyOTPActivity.class);
                        intent.putExtra("mobile",mInputMobile.getText().toString());
                        intent.putExtra("verificationId",Constant.mVerificationId);
                        startActivity(intent);
                    }
                }
            };
        }


        code = ccp.getSelectedCountryCodeWithPlus();
        Constant.NUMBER = code + mInputMobile.getText().toString();
        phoneNumber = Constant.NUMBER;
        if (!phoneNumber.equals("")) {
            //Send a verification code to the user's phone
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this)                 // Activity (for callback binding)
                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);

        } else {
            Toast.makeText(this, "please write a valid phone number", Toast.LENGTH_SHORT).show();
        }
        }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");

                        Toast.makeText(SendOTPActivity.this, "Congratulation ,you are logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SendOTPActivity.this, MainActivity.class));
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(SendOTPActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Intent intent=new Intent(SendOTPActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    }



