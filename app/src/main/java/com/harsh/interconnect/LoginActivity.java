package com.harsh.interconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    String userEmail="", userPassword="";
    FirebaseAuth fbAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        fbAuth = FirebaseAuth.getInstance();

        if(fbAuth.getCurrentUser()!=null){
            navigateToMainActivity();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs()){
                    try{
                        if(fbAuth.getCurrentUser()==null){
                            fbAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                        navigateToMainActivity();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Incorrect Credentials!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (Exception e) {

                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Please enter all details to Login!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void redirectToRegistration(View view) {
        try{
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            finish();
        } catch (Exception e) {
        }
    }
    public Boolean validateInputs(){
        try{
            if(!etEmail.getText().toString().trim().isEmpty() && !etPassword.getText().toString().trim().isEmpty()){
                userEmail = etEmail.getText().toString().trim();
                userPassword = etPassword.getText().toString().trim();
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }
    public void navigateToMainActivity(){
        try{
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } catch (Exception e) {

        }
    }
}