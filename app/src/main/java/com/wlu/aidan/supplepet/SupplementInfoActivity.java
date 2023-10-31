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

public class SupplementInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement_info);
        // Bottom Nav Bar Buttons
        ImageButton homeButton = super.findViewById(R.id.home_button);
        homeButton.setOnClickListener((view) -> {
            print("Clicked Home Button",Toast.LENGTH_LONG);

            Intent intent = new Intent(SupplementInfoActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageButton checkButton = super.findViewById(R.id.check_button);
        checkButton.setOnClickListener((view) -> {
            print("Clicked Check Button",Toast.LENGTH_LONG);

            Intent intent = new Intent(SupplementInfoActivity.this, SupplementInfoActivity.class);
            startActivity(intent);
        });

        ImageButton bookButton = super.findViewById(R.id.book_button);
        bookButton.setOnClickListener((view) -> {
            print("Clicked Book Button",Toast.LENGTH_LONG);

            Intent intent = new Intent(SupplementInfoActivity.this, SupplementLog.class);
            startActivity(intent);
        });


        Button addSupp = super.findViewById(R.id.addSuppBtn);
        addSupp.setOnClickListener((view) -> {
            print("Clicked Add Suppliment Button", Toast.LENGTH_LONG);
        });

        //suppliment buttons
        LinearLayout supp1 = (LinearLayout) findViewById(R.id.supp1);
        supp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print("Clicked Supp 1 Button", Toast.LENGTH_LONG);
            }
        });
        LinearLayout supp2 = (LinearLayout) findViewById(R.id.supp2);
        supp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print("Clicked Supp 2 Button", Toast.LENGTH_LONG);
            }
        });
        LinearLayout supp3 = (LinearLayout) findViewById(R.id.supp3);
        supp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print("Clicked Supp 3 Button", Toast.LENGTH_LONG);
            }
        });
        LinearLayout supp4 = (LinearLayout) findViewById(R.id.supp4);
        supp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print("Clicked Supp 4 Button", Toast.LENGTH_LONG);
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

    private void print(String msg, int duration) {
        Toast toast = Toast.makeText(this, msg, duration);
        toast.show();
    }
}
