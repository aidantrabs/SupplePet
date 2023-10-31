package com.wlu.aidan.supplepet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ImageButton;
import android.widget.Toast;

public class PetInteractionActivity extends AppCompatActivity {

    private int currentProgress = 0;
    private ProgressBar progressBar;
    private Button feedButton, playButton, petButton, cleanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_interaction);

        progressBar = super.findViewById(R.id.pet_progress_bar);
        feedButton = super.findViewById(R.id.feed_button);
        playButton = super.findViewById(R.id.play_button);
        petButton = super.findViewById(R.id.pet_button);
        cleanButton = super.findViewById(R.id.clean_button);

        // Bottom Nav Bar Buttons
        ImageButton homeButton = super.findViewById(R.id.home_button);
        homeButton.setOnClickListener((view) -> {
            print("Clicked Home Button",Toast.LENGTH_LONG);
        });

        ImageButton checkButton = super.findViewById(R.id.check_button);
        checkButton.setOnClickListener((view) -> {
            print("Clicked Check Button",Toast.LENGTH_LONG);
        });

        ImageButton bookButton = super.findViewById(R.id.book_button);
        bookButton.setOnClickListener((view) -> {
            print("Clicked Book Button",Toast.LENGTH_LONG);
        });

       feedButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               currentProgress = currentProgress + 10;
               progressBar.setProgress(currentProgress);
               progressBar.setMax(100);
           }
       });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentProgress = currentProgress + 10;
                progressBar.setProgress(currentProgress);
                progressBar.setMax(100);
            }
        });

        petButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentProgress = currentProgress + 10;
                progressBar.setProgress(currentProgress);
                progressBar.setMax(100);
            }
        });

        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentProgress = currentProgress + 10;
                progressBar.setProgress(currentProgress);
                progressBar.setMax(100);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getSimpleName(), "onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getSimpleName(), "onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(this.getClass().getSimpleName(), "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(this.getClass().getSimpleName(), "onStop()");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(this.getClass().getSimpleName(), "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(this.getClass().getSimpleName(), "onRestoreInstanceState()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getSimpleName(), "onDestroy()");
    }
    private void print(String msg, int duration){
        Toast toast = Toast.makeText(this, msg, duration);
        toast.show();
    }
}