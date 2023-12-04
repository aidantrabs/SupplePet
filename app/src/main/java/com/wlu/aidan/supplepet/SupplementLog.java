package com.wlu.aidan.supplepet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SupplementLog extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private EditText edit_sleep, edit_food, edit_exercise, edit_intake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement_log);

        firestore = FirebaseFirestore.getInstance();

        // Log Edit Texts
        edit_sleep = super.findViewById(R.id.edit_sleep);
        edit_food = super.findViewById(R.id.edit_food);
        edit_exercise = super.findViewById(R.id.edit_exercise);
        edit_intake= super.findViewById(R.id.edit_intake);

        Button btnSleep = super.findViewById(R.id.btnSleep);
        btnSleep.setOnClickListener((view) -> {
            print("Clicked Log Sleep Button",Toast.LENGTH_LONG);

            // Set focus on respective edit text
            edit_sleep.setVisibility(View.VISIBLE);
            edit_food.setVisibility(View.GONE);
            edit_exercise.setVisibility(View.GONE);
            edit_intake.setVisibility(View.GONE);
        });

        Button btnFood = super.findViewById(R.id.btnFood);
        btnFood.setOnClickListener((view) -> {
            print("Clicked Log Food Button",Toast.LENGTH_LONG);

            // Set focus on respective edit text
            edit_sleep.setVisibility(View.GONE);
            edit_food.setVisibility(View.VISIBLE);
            edit_exercise.setVisibility(View.GONE);
            edit_intake.setVisibility(View.GONE);
        });

        Button btnExercise = super.findViewById(R.id.btnExercise);
        btnExercise.setOnClickListener((view) -> {
            print("Clicked Log Exercise Button",Toast.LENGTH_LONG);

            // Set focus on respective edit text
            edit_sleep.setVisibility(View.GONE);
            edit_food.setVisibility(View.GONE);
            edit_exercise.setVisibility(View.VISIBLE);
            edit_intake.setVisibility(View.GONE);
        });

        Button btnIntake = super.findViewById(R.id.btnIntake);
        btnIntake.setOnClickListener((view) -> {
            print("Clicked Log Intake Button",Toast.LENGTH_LONG);

            // Set focus on respective edit text
            edit_sleep.setVisibility(View.GONE);
            edit_food.setVisibility(View.GONE);
            edit_exercise.setVisibility(View.GONE);
            edit_intake.setVisibility(View.VISIBLE);
        });

        Button btnLog = super.findViewById(R.id.btnLog);
        btnLog.setOnClickListener((view) -> {
            print("Clicked Update Log Button",Toast.LENGTH_LONG);

            // Alert user that supplement log update was successful
            AlertDialog.Builder builder = new AlertDialog.Builder(SupplementLog.this);
            builder.setTitle(R.string.SupplementLogDialogTitle);
            builder.setPositiveButton(R.string.SupplementLogDialogOk, (dialog, id) -> dialog.dismiss());

            // Check if user entered an appropriate value to log, then reset edit text
            // (temp code, will fix and check for real data later)
            if (edit_sleep.getVisibility() == View.VISIBLE && !edit_sleep.getText().toString().isEmpty()) {
                builder.create().show();
                edit_sleep.setText("");
            } else if (edit_food.getVisibility() == View.VISIBLE && !edit_food.getText().toString().isEmpty()) {
                builder.create().show();
                edit_food.setText("");
            } else if (edit_exercise.getVisibility() == View.VISIBLE && !edit_exercise.getText().toString().isEmpty()) {
                builder.create().show();
                edit_exercise.setText("");
            } else if (edit_intake.getVisibility() == View.VISIBLE && !edit_intake.getText().toString().isEmpty()) {
                builder.create().show();
                edit_intake.setText("");
            } else {
                builder.setTitle(R.string.SupplementLogDialogError);
                builder.setPositiveButton(R.string.SupplementLogDialogOk, (dialog, id) -> dialog.dismiss());
                builder.create().show();
            }

            addToFirestore(edit_sleep.getText().toString(), edit_food.getText().toString(), edit_exercise.getText().toString(), edit_intake.getText().toString());
        });

        // Bottom Nav Bar Buttons
        ImageButton homeButton = super.findViewById(R.id.home_button);
        homeButton.setOnClickListener((view) -> {
            print("Clicked Home Button",Toast.LENGTH_LONG);

            Intent intent = new Intent(SupplementLog.this, MainActivity.class);
            startActivity(intent);
        });

        ImageButton checkButton = super.findViewById(R.id.check_button);
        checkButton.setOnClickListener((view) -> {
            print("Clicked Check Button",Toast.LENGTH_LONG);

            Intent intent = new Intent(SupplementLog.this, SupplementInfoActivity.class);
            startActivity(intent);
        });

        ImageButton bookButton = super.findViewById(R.id.book_button);
        bookButton.setOnClickListener((view) -> {
            print("Clicked Book Button",Toast.LENGTH_LONG);

            Intent intent = new Intent(SupplementLog.this, SupplementLog.class);
            startActivity(intent);
        });
    }

    private void addToFirestore(String sleep, String food, String exercise, String intake) {
        CollectionReference dbLog = firestore.collection("loginfo");

        LogInfo log = new LogInfo(sleep, food, exercise, intake);

        dbLog.add(log).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(SupplementLog.this, "Log database has been updated!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SupplementLog.this, "Log database has failed to update!\n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    class LogInfo {
        private String sleep, food, exercise, intake;

        public LogInfo() {}

        public LogInfo(String sleep, String food, String exercise, String intake) {
            this.sleep = sleep;
            this.food = food;
            this.exercise = exercise;
            this.intake = intake;
        }

        public String getSleep() {
            return sleep;
        }

        public String getFood() {
            return food;
        }

        public String getExercise() {
            return exercise;
        }

        public String getIntake() {
            return intake;
        }

        public void setSleep(String sleep) {
            this.sleep = sleep;
        }

        public void setFood(String food) {
            this.food = food;
        }

        public void setExercise(String exercise) {
            this.exercise = exercise;
        }

        public void setIntake(String intake) {
            this.intake = intake;
        }
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

    private boolean isChecked = true;
}