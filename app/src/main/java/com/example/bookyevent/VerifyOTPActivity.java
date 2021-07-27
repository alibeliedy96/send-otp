package com.example.bookyevent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "VerifyOTPActivity";
    private TextView mTextMobile;
    private EditText mVerficationNum1Et;
    private EditText mVerficationNum2Et;
    private EditText mVerficationNum3Et;
    private EditText mVerficationNum4Et;
    private EditText mVerficationNum5Et;
    private EditText mVerficationNum6Et;
    private TextView mSentItAgainTv;
    private TextView timerTv;
    private TextView changeNumberTv;
    private Button mVerifyButton;
    private ProgressBar progressBar;
    private String verificationId="";
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phoneNumber;
    private String code;


    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);
        mTextMobile = findViewById(R.id.textMobile);

        phoneNumber = Constant.NUMBER;
        mTextMobile.setText(getIntent().getStringExtra("mobile"));
        mVerficationNum1Et = findViewById(R.id.verfication_num_1_et);
        mVerficationNum2Et = findViewById(R.id.verfication_num_2_et);
        mVerficationNum3Et = findViewById(R.id.verfication_num_3_et);
        mVerficationNum4Et = findViewById(R.id.verfication_num_4_et);
        mVerficationNum5Et = findViewById(R.id.verfication_num_5_et);
        mVerficationNum6Et = findViewById(R.id.verfication_num_6_et);
        timerTv = findViewById(R.id.timer_tv);
        changeNumberTv = findViewById(R.id.tv_change_number);
        mSentItAgainTv = findViewById(R.id.sent_it_again_tv);
        mVerifyButton = findViewById(R.id.verify_button);
        progressBar = findViewById(R.id.progress_bar);

        mSentItAgainTv.setOnClickListener(this);
        mVerifyButton.setOnClickListener(this);
        changeNumberTv.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();


        Constant.mVerificationId = getIntent().getStringExtra("verificationId");

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                Log.e("haaaaaaaaaaaaaaaaa", "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("haaaaaaaaaaaaaaaaa", "onVerificationFailed", e);
                Toast.makeText(VerifyOTPActivity.this, ""+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String mVerificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.e("haaaaaaaaaaaaaaaaa", "onCodeSent:" + mVerificationId);
                   Constant.mVerificationId = mVerificationId;
                   Constant.mResendToken = token;

            }
        };
        mVerficationNum1Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // specify length of your editext here to move on next edittext
               /* if(mVerificationNum1_ET.getText().toString().trim().length()>=1){
                    mVerificationNum2_ET.requestFocus();
                }*/
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(mVerficationNum1Et.getText().toString().trim().length()>=1){
                    mVerficationNum1Et.clearFocus();
                    mVerficationNum2Et.requestFocus();
                    mVerficationNum2Et.setCursorVisible(true);
                }
            }
        });
        mVerficationNum2Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // specify length of your editext here to move on next edittext
               /* if(mVerificationNum2_ET.getText().toString().trim().length()>=1){
                    mVerificationNum3_ET.requestFocus();
                }*/
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(mVerficationNum2Et.getText().toString().trim().length()>=1){
                    mVerficationNum2Et.clearFocus();
                    mVerficationNum3Et.requestFocus();
                    mVerficationNum3Et.setCursorVisible(true);
                }
            }
        });
        mVerficationNum3Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // specify length of your editext here to move on next edittext
               /* if(mVerificationNum3_ET.getText().toString().trim().length()>=1){
                    mVerificationNum4_ET.requestFocus();
                }*/
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(mVerficationNum3Et.getText().toString().trim().length()>=1){
                    mVerficationNum3Et.clearFocus();
                    mVerficationNum4Et.requestFocus();
                    mVerficationNum4Et.setCursorVisible(true);
                }
            }
        });
        mVerficationNum4Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // specify length of your editext here to move on next edittext
                /*if(mVerificationNum4_ET.getText().toString().trim().length()>=1){
                    mVerificationNum5_ET.requestFocus();
                }*/
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(mVerficationNum4Et.getText().toString().trim().length()>=1){
                    mVerficationNum4Et.clearFocus();
                    mVerficationNum5Et.requestFocus();
                    mVerficationNum5Et.setCursorVisible(true);
                }
            }
        });
        mVerficationNum5Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // specify length of your editext here to move on next edittext
                /*if(mVerificationNum5_ET.getText().toString().trim().length()>=1){
                    mVerificationNum6_ET.requestFocus();
                }*/
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(mVerficationNum5Et.getText().toString().trim().length()>=1){
                    mVerficationNum5Et.clearFocus();
                    mVerficationNum6Et.requestFocus();
                    mVerficationNum6Et.setCursorVisible(true);
                }
            }
        });
        mVerficationNum5Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // specify length of your editext here to move on next edittext
                /*if(mVerificationNum5_ET.getText().toString().trim().length()>=1){
                    mVerificationNum6_ET.requestFocus();
                }*/
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                code = mVerficationNum1Et.getText().toString() +
                        mVerficationNum2Et.getText().toString() + mVerficationNum3Et.getText().toString()
                        + mVerficationNum4Et.getText().toString() + mVerficationNum5Et.getText().toString()
                        + mVerficationNum6Et.getText().toString();
                //verifyPhoneNumberWithCode(Constants.mVerificationId,code);
            }
        });
        startTimer();
    }
    private void startTimer() {
        mSentItAgainTv.setVisibility(View.INVISIBLE);
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTv.setText("Seconds Remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTv.setText("");
                mSentItAgainTv.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
        Toast.makeText(VerifyOTPActivity.this, "Verifying...", Toast.LENGTH_SHORT).show();

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");

                        Toast.makeText(VerifyOTPActivity.this, "Congratulation ,you are logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(VerifyOTPActivity.this, MainActivity.class));
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(VerifyOTPActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                    }
                });
    }

    private void resendVerificationCode(String phoneNumber) {
        startTimer();
        mVerficationNum1Et.setText("");
        mVerficationNum2Et.setText("");
        mVerficationNum3Et.setText("");
        mVerficationNum4Et.setText("");
        mVerficationNum5Et.setText("");
        mVerficationNum6Et.setText("");


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(Constant.mResendToken)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verify_button:
                code = mVerficationNum1Et.getText().toString() +
                        mVerficationNum2Et.getText().toString() + mVerficationNum3Et.getText().toString()
                        + mVerficationNum4Et.getText().toString() + mVerficationNum5Et.getText().toString()
                        + mVerficationNum6Et.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mVerficationNum1Et.setError("Cannot be empty.");
                    mVerficationNum2Et.setError("Cannot be empty.");
                    mVerficationNum3Et.setError("Cannot be empty.");
                    mVerficationNum4Et.setError("Cannot be empty.");
                    mVerficationNum5Et.setError("Cannot be empty.");
                    mVerficationNum6Et.setError("Cannot be empty.");
                    return;
                }
                verifyPhoneNumberWithCode(verificationId, code);
                break;
            case R.id.sent_it_again_tv:
                resendVerificationCode(phoneNumber);
                break;
            case R.id.tv_change_number:
                changeNumber();
                break;
        }
    }
    private void changeNumber() {
        startActivity(new Intent(VerifyOTPActivity.this,SendOTPActivity.class));
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Intent intent=new Intent(VerifyOTPActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
