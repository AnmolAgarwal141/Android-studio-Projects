package com.example.naaniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naaniz.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class VerificationActivity extends AppCompatActivity {

    TextView phone;
    TextView otp;
    Button verify;

    private FirebaseAuth auth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private boolean verificationInProgress = false;
    private static final String TAG = "Verification";
    private String phNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        phone = findViewById(R.id.phone);
        otp = findViewById(R.id.otp);
        verify = findViewById(R.id.verify);

        User user = (User) getIntent().getSerializableExtra("user_details");

        phNumber = user.getPhoneNumber();
        String content = "Enter the code sent to +91 " + phNumber;
        phone.setText(content);

        auth = FirebaseAuth.getInstance();
        initCallbacks();
        initListener();
    }

    private void initCallbacks()
    {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "Verification completed");
                verificationInProgress = false;
                nextActivity();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d("Verification","Verification failed");
                verificationInProgress = false;
                if(e instanceof FirebaseAuthInvalidCredentialsException)
                    Toast.makeText(VerificationActivity.this, "Invalid phone number",Toast.LENGTH_SHORT).show();
                else if(e instanceof FirebaseTooManyRequestsException)
                    Toast.makeText(VerificationActivity.this, "Quota exceeded",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                verify.setText("Verify");
                verify.setClickable(true);
                VerificationActivity.this.verificationId = verificationId;
                mResendToken = token;
            }
        };
    }

    private void startVerification(String phone)
    {
        verificationInProgress = true;
        Log.d(TAG, "Phone number : "+phone);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                VerificationActivity.this,
                callbacks
        );
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential: success : Verification Id : "+verificationId);

                            FirebaseUser user = task.getResult().getUser();
                            Log.d(TAG,"user name : "+user.getDisplayName());
                            Log.d(TAG,"user phone number : "+user.getPhoneNumber());
                            Log.d(TAG,"user provider id : "+user.getProviderId());
                            Log.d(TAG,"user uid : "+user.getUid());

                            nextActivity();
                        } else {
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerificationActivity.this,"Invalid code.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void initListener()
    {
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify.setClickable(false);
                if(verificationInProgress) {
                    verifyPhoneNumberWithCode(verificationId,otp.getText().toString());
                }
                else{
                    Log.d(TAG, "starting verification");
                    startVerification("+91"+phNumber);
                }
            }
        });
    }

    private void nextActivity()
    {
        User user = (User) getIntent().getSerializableExtra("user_details");
        if(getIntent().getBooleanExtra("login",false)) {
            Intent intent =  new Intent(VerificationActivity.this, Home.class);
            intent.putExtra("user_details", user);
            intent.putExtra("name","Default");
            intent.putExtra("phone",user.getPhoneNumber());
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(VerificationActivity.this, SignUp1.class);
            intent.putExtra("user_details", user);
            startActivity(intent);
        }

    }
}
