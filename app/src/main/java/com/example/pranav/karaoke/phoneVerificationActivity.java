package com.example.pranav.karaoke;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phoneVerificationActivity extends AppCompatActivity {

    private EditText phoneNumberEntered;
    private Button   sendCodeButton;
    private EditText verificationCode;
    private Button   verifyButton;
    private String   verificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callBacks;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        phoneNumberEntered = (EditText) findViewById(R.id.phoneNumberEntered);
        sendCodeButton = (Button) findViewById(R.id.sendCodeButton);
        verificationCode = (EditText) findViewById(R.id.verificationCode);
        verifyButton = (Button) findViewById(R.id.verifyButton);

        firebaseAuth = FirebaseAuth.getInstance();

        callBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();

        View.OnClickListener sendCodeButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEntered.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, phoneVerificationActivity.this, callBacks);
            }
        };
        sendCodeButton.setOnClickListener(sendCodeButtonListener);

        View.OnClickListener verifyButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verification = verificationCode.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verification);
                signIn(credential);
            }
        };
        verifyButton.setOnClickListener(verifyButtonListener);
    }

    private void signIn (PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(phoneVerificationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent registrationActivityIntent  = new Intent(phoneVerificationActivity.this, RegistrationActivity.class);
                    startActivity(registrationActivityIntent);
                    finish();
                    Toast.makeText(phoneVerificationActivity.this, "SignIn Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
