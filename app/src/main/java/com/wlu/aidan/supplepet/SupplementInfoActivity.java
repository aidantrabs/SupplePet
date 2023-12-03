package com.wlu.aidan.supplepet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplementInfoActivity extends AppCompatActivity {

    public class SupplementAdapter extends RecyclerView.Adapter<SupplementAdapter.SupplementViewHolder> {
        private List<Supplement> supplements;
        private View parentView;

        public SupplementAdapter(List<Supplement> supplements, View parentView) {
            this.parentView = parentView;
            this.supplements = supplements;
        }

        @NonNull
        @Override
        public SupplementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplement_item, parent, false);
            return new SupplementViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SupplementViewHolder holder, int position) {
            Supplement supplement = supplements.get(position);
            holder.bind(supplement);
        }

        @Override
        public int getItemCount() {
            return supplements.size();
        }

        class SupplementViewHolder extends RecyclerView.ViewHolder {
            private TextView nameTextView;
            private TextView selectedName;
            private TextView selectedTime;
            private TextView selectedDosage;
            private TextView selectedType;
            private TextView selectedDesc;
//            private TextView typeTextView;
            // Other TextViews for dosage, timing, description

            public SupplementViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                selectedName = parentView.findViewById(R.id.selectedSuppName);
                selectedTime = parentView.findViewById(R.id.selectedSuppTiming);
                selectedDosage = parentView.findViewById(R.id.selectedSuppDosage);
                selectedType = parentView.findViewById(R.id.selectedSuppType);
                selectedDesc = parentView.findViewById(R.id.selectedSuppDescription);
//                typeTextView = itemView.findViewById(R.id.typeTextView);
                // Initialize other TextViews

                itemView.setOnClickListener(view -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Supplement clickedSupplement = supplements.get(position);
                        // Handle the click action, for example, display a Toast
                        Toast.makeText(itemView.getContext(), "Clicked: " + clickedSupplement.getName(), Toast.LENGTH_SHORT).show();
                        selectedName.setText(clickedSupplement.getName());
                        selectedTime.setText("Time: " + clickedSupplement.getTiming());
                        selectedDosage.setText("Dosage: " + clickedSupplement.getDosage());
                        selectedType.setText("Type: " + clickedSupplement.getType());
                        selectedDesc.setText("Description: " + clickedSupplement.getDescription());
                    }
                });
            }

            public void bind(Supplement supplement) {
                nameTextView.setText(supplement.getName());
//                typeTextView.setText(supplement.getType());
                // Set other TextViews with supplement details
            }
        }
    }
    public class Supplement {
        private String name;
        private String type;
        private String dosage;
        private String timing;
        private String description;

        public Supplement(String name, String type, String dosage, String timing, String description) {
            this.name = name;
            this.type = type;
            this.dosage = dosage;
            this.timing = timing;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getDosage() {
            return dosage;
        }

        public String getTiming() {
            return timing;
        }

        public String getDescription() {
            return description;
        }
    }

    public class SupplementQuery extends AsyncTask<Void, Void, String> {
        private TextView textView;
        private Spinner spinner;
        private FirebaseFirestore firestore;

        public SupplementQuery(TextView textView, Spinner spinner, FirebaseFirestore firestore) {
            this.textView = textView;
            this.spinner = spinner;
            this.firestore = firestore;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                firestore.collection("supplements")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<String> supplementNames = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = document.getString("name");
                                    supplementNames.add(name);
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(SupplementInfoActivity.this, android.R.layout.simple_spinner_item, supplementNames);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(adapter);
                            } else {
                                Log.d("SupplementQuery", "Error getting documents: ", task.getException());
                            }
                        });

                return "Success";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Handle post-execution tasks if needed
        }

    }

    FirebaseFirestore firestore;

//    private List<Map<String, Object>> createSupplementsList() {
//        List<Map<String, Object>> supplements = new ArrayList<>();
//
//        supplements.add(createSupplementMap(
//                "Melatonin",
//                "Hormone",
//                "0.5-5mg/day",
//                "Before bedtime",
//                "Melatonin is a hormone that regulates sleep-wake cycles. It is commonly used as a supplement to improve sleep quality and manage sleep disorders."
//        ));
//        // Add other supplements similarly...
//
//        return supplements;
//    }
//
//    private Map<String, Object> createSupplementMap(String name, String type, String dosage, String timing, String description) {
//        Map<String, Object> supplement = new HashMap<>();
//        supplement.put("name", name);
//        supplement.put("type", type);
//        supplement.put("dosage", dosage);
//        supplement.put("timing", timing);
//        supplement.put("description", description);
//        return supplement;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement_info);

        firestore = FirebaseFirestore.getInstance();

//        List<Map<String, Object>> supplements = createSupplementsList();
//
//        for (Map<String, Object> supplement : supplements) {
//            firestore.collection("supplements")
//                    .add(supplement)
//                    .addOnSuccessListener(documentReference -> Log.d("SupplementInfoActivity", "Supplement added with ID: " + documentReference.getId()))
//                    .addOnFailureListener(e -> Log.w("SupplementInfoActivity", "Error adding supplement", e));
//        }

        TextView textView = findViewById(R.id.selectedSuppDescription);
        TextView test = findViewById(R.id.selectedSuppName);
        Spinner spinner = findViewById(R.id.supplementsSpinner); // Replace with your Spinner ID

        SupplementQuery supplementQuery = new SupplementQuery(textView, spinner, firestore);
        supplementQuery.execute();

        // Bottom Nav Bar Buttons (Assuming you have these set up already)

        ImageButton homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener((view) -> {
            print("Clicked Home Button", Toast.LENGTH_LONG);
            // Intent for Home Button
        });

        ImageButton checkButton = findViewById(R.id.check_button);
        checkButton.setOnClickListener((view) -> {
            print("Clicked Check Button", Toast.LENGTH_LONG);
            // Intent for Check Button
        });

        ImageButton bookButton = findViewById(R.id.book_button);
        bookButton.setOnClickListener((view) -> {
            print("Clicked Book Button", Toast.LENGTH_LONG);
            // Intent for Book Button
        });

        Button addSupp = findViewById(R.id.addSuppBtn);
        addSupp.setOnClickListener((view) -> {
            print("Clicked Add Supplement Button", Toast.LENGTH_LONG);
            // Handle Add Supplement Button click
        });
        RecyclerView recyclerViewSupplements = findViewById(R.id.recyclerViewSupplements);
        recyclerViewSupplements.setLayoutManager(new LinearLayoutManager(this));

        // Fetch supplements data from Firebase
        FirebaseFirestore.getInstance().collection("supplements")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Supplement> supplements = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String type = document.getString("type");
                            String dosage = document.getString("dosage");
                            String timing = document.getString("timing");
                            String description = document.getString("description");

                            supplements.add(new Supplement(name, type, dosage, timing, description));
                        }

                        // Create and set adapter for RecyclerView
                        SupplementAdapter adapter = new SupplementAdapter(supplements, findViewById(R.id.activity_supplement_info_layout));
                        recyclerViewSupplements.setAdapter(adapter);
                    } else {
                        Log.d("SupplementQuery", "Error getting documents: ", task.getException());
                    }
                });
        // Supplement buttons (Assuming you have these set up already)
//        LinearLayout supp1 = findViewById(R.id.supp1);
//        supp1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                print("Clicked Supp 1 Button", Toast.LENGTH_LONG);
//                // Handle Supp 1 Button click
//            }
//        });
//        LinearLayout supp2 = findViewById(R.id.supp2);
//        supp2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                print("Clicked Supp 2 Button", Toast.LENGTH_LONG);
//                // Handle Supp 2 Button click
//            }
//        });
//        LinearLayout supp3 = findViewById(R.id.supp3);
//        supp3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                print("Clicked Supp 3 Button", Toast.LENGTH_LONG);
//                // Handle Supp 3 Button click
//            }
//        });
//        LinearLayout supp4 = findViewById(R.id.supp4);
//        supp4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                print("Clicked Supp 4 Button", Toast.LENGTH_LONG);
//                // Handle Supp 4 Button click
//            }
//        });
    }

    private void print(String msg, int duration) {
        Toast.makeText(this, msg, duration).show();
    }
}
