package com.wlu.aidan.supplepet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PetInteractionActivity extends AppCompatActivity {

    private int currentProgress = 0;
    private ProgressBar progressBar;
    private ImageButton feedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_interaction);

        progressBar = findViewById(R.id.pet_progress_bar);
        feedButton = findViewById(R.id.feed_button);

       feedButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               currentProgress = currentProgress + 10;
               progressBar.setProgress(currentProgress);
               progressBar.setMax(100);
           }
       });

    }
}