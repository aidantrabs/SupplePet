package com.wlu.aidan.supplepet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom Nav Bar Buttons
        ImageButton homeButton = super.findViewById(R.id.home_button);
        homeButton.setOnClickListener((view) -> {
            print("Clicked Home Button",Toast.LENGTH_LONG);

            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageButton checkButton = super.findViewById(R.id.check_button);
        checkButton.setOnClickListener((view) -> {
            print("Clicked Check Button",Toast.LENGTH_LONG);

            Intent intent = new Intent(MainActivity.this, SupplementInfoActivity.class);
            startActivity(intent);
        });

        ImageButton bookButton = super.findViewById(R.id.book_button);
        bookButton.setOnClickListener((view) -> {
            print("Clicked Book Button",Toast.LENGTH_LONG);

            Intent intent = new Intent(MainActivity.this, SupplementLog.class);
            startActivity(intent);
        });

        // Activity Buttons/Linear Layouts
        LinearLayout petButton = (LinearLayout )findViewById(R.id.pet_button);
        petButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pet_intent = new Intent(MainActivity.this, PetInteractionActivity.class);
                startActivity(pet_intent);
            }
        });

        LinearLayout infoButton = (LinearLayout)findViewById(R.id.info_button);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info_intent = new Intent(MainActivity.this, SupplementInfoActivity.class);
                startActivity(info_intent);
            }
        });

        LinearLayout logButton = (LinearLayout)findViewById(R.id.log_button);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log_intent = new Intent(MainActivity.this, SupplementLog.class);
                startActivity(log_intent);
            }
        });

        LinearLayout achButton = (LinearLayout )findViewById(R.id.ach_button);
        achButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ach_intent = new Intent(MainActivity.this, AchievementsActivity.class);
                startActivity(ach_intent);
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