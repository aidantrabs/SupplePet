package com.wlu.aidan.supplepet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    protected final String ACTIVITY_NAME = this.getClass().getSimpleName();
    protected final static String regexEmail = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

    private EditText emailLogin, passwordLogin;
    private Button loginBtn;
    private TextView createAccount;

    private boolean validEmail;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailLogin = findViewById(R.id.editEmailLogin);
        passwordLogin = findViewById(R.id.editPasswordLogin);
        loginBtn = findViewById(R.id.btnSubmitLogin);
        createAccount = findViewById(R.id.accountCreate);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String email, password;

        email = emailLogin.getText().toString();
        password = passwordLogin.getText().toString();

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

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(ACTIVITY_NAME, "signInUserWithEmail(): success");
                            Toast.makeText(getApplicationContext(),"Sign in successful!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        else {
                            Log.w(ACTIVITY_NAME, "signInUserWithEmail(): failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Sign in failed!" + " Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}