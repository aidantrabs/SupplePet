package com.wlu.aidan.supplepet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RegistrationActivity extends AppCompatActivity {
    protected final String ACTIVITY_NAME = this.getClass().getSimpleName();
    protected final static String regexEmail = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

    private EditText emailRegister, passwordRegister, passwordConfirmation;
    private Button registerBtn;
    private TextView accountSignIn;
    private FirebaseAuth mAuth;

    private boolean validEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Log.i(ACTIVITY_NAME, "onCreate(): Registration");

        mAuth = FirebaseAuth.getInstance();

        emailRegister = findViewById(R.id.editEmailRegistration);
        passwordRegister = findViewById(R.id.editPasswordRegistration);
        passwordConfirmation = findViewById(R.id.editPasswordRegistrationConf);
        registerBtn = findViewById(R.id.btnSubmitRegistration);
        accountSignIn = findViewById(R.id.accountLogin);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        accountSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        String email, password, passwordConf;

        email = emailRegister.getText().toString();
        password = passwordRegister.getText().toString();
        passwordConf = passwordConfirmation.getText().toString();

        Pattern regexPattern = Pattern.compile(regexEmail, Pattern.CASE_INSENSITIVE);
        Matcher matcherEmail = regexPattern.matcher(email);

        validEmail = matcherEmail.matches();

        if (email.isEmpty() || !validEmail) {
            Toast.makeText(getApplicationContext(), "Please enter a valid email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a valid password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (passwordConf.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(ACTIVITY_NAME, "createUserWithEmail(): success");
                            Toast.makeText(getApplicationContext(),"Registration successful!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }

                        else {
                            Log.w(ACTIVITY_NAME, "createUserWithEmail(): failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Registration failed!" + " Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}