package com.example.pranav.karaoke;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signUpActivity extends AppCompatActivity {
    private EditText        nameEditText;
    private EditText        emailEditText;
    private EditText        userNameEditText;
    private EditText        passwordEditText;
    private Button          submitButton;
    private ProgressDialog  progressDialog;
    private FirebaseAuth    firebaseAuth;
    private FirebaseUser    currUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog  = new ProgressDialog(this);
        firebaseAuth    = FirebaseAuth.getInstance();
        currUser        =  firebaseAuth.getCurrentUser();
        //Assigning views from the layout file to their corresponding views
        nameEditText        =   (EditText)  findViewById(R.id.signUpFullNameEditText);
        emailEditText       =   (EditText)  findViewById(R.id.signUpEmailEditText);
        userNameEditText    =   (EditText)  findViewById(R.id.signUpUserNameEditText);
        passwordEditText    =   (EditText)  findViewById(R.id.signUpPasswordEditText);
        submitButton        =   (Button)    findViewById(R.id.signUpSubmitButton);

        //Adding functionality to the signUpSubmitButton
        View.OnClickListener signUpSubmitButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    //Display message to user that email is empty
                    Toast.makeText(signUpActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    //Stop further execution
                    return;
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(signUpActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    //Stop further execution
                    return;
                }


                //Display progressDialog to user
                progressDialog.setMessage("Signing Up!");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signUpActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        else {
                            Toast.makeText(signUpActivity.this, "Sign Up Failed:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        };
        submitButton.setOnClickListener(signUpSubmitButtonListener);
    }
}
