package com.wlu.aidan.supplepet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
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

import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplementInfoActivity extends AppCompatActivity {
    private RecyclerView recyclerViewSupplements;
    private List<String> supplementNames = new ArrayList<>();
    private Spinner spinner;
    private SupplementAdapter adapter;
    private FirebaseFirestore firestore;
    private static final String PREFS_NAME = "UserSupplementsPrefs";

    // Inner class for managing the RecyclerView Adapter
    public class SupplementAdapter extends RecyclerView.Adapter<SupplementAdapter.SupplementViewHolder> {
        private List<Supplement> supplements;
        private View parentView;

        public List<Supplement> getSupplements(){
            return supplements;
        }

        // Constructor for the SupplementAdapter
        public SupplementAdapter(List<Supplement> supplements, View parentView) {
            this.parentView = parentView;
            this.supplements = supplements;
        }

        // Method to add a supplement to the RecyclerView
        public void addSupplement(Supplement supplement) {
            supplements.add(supplement);
            notifyDataSetChanged(); // Notify adapter that the data set has changed
        }

        // Method to remove a supplement from the RecyclerView
        public void removeSupplement(int position) {
            supplements.remove(position);
            notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public SupplementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate the layout for the RecyclerView item
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supplement_item, parent, false);
            return new SupplementViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SupplementViewHolder holder, int position) {
            // Bind data to the ViewHolder
            Supplement supplement = supplements.get(position);
            holder.bind(supplement);
        }

        @Override
        public int getItemCount() {
            // Return the total number of items in the RecyclerView
            return supplements.size();
        }

        // ViewHolder class for managing item views in the RecyclerView
        class SupplementViewHolder extends RecyclerView.ViewHolder {
            private TextView nameTextView;
            private TextView selectedName;
            private TextView selectedTime;
            private TextView selectedDosage;
            private TextView selectedType;
            private TextView selectedDesc;

            public SupplementViewHolder(@NonNull View itemView) {
                super(itemView);
                // Initialize views for displaying supplement information
                nameTextView = itemView.findViewById(R.id.nameTextView);
                selectedName = parentView.findViewById(R.id.selectedSuppName);
                selectedTime = parentView.findViewById(R.id.selectedSuppTiming);
                selectedDosage = parentView.findViewById(R.id.selectedSuppDosage);
                selectedType = parentView.findViewById(R.id.selectedSuppType);
                selectedDesc = parentView.findViewById(R.id.selectedSuppDescription);

                // Handle item click to display details
                itemView.setOnClickListener(view -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Supplement clickedSupplement = supplements.get(position);
                        // Display the clicked supplement's information
                        Toast.makeText(itemView.getContext(), "Clicked: " + clickedSupplement.getName(), Toast.LENGTH_SHORT).show();
                        selectedName.setText(clickedSupplement.getName());
                        selectedTime.setText(clickedSupplement.getTiming());
                        selectedDosage.setText("Dosage: " + clickedSupplement.getDosage());
                        selectedType.setText("Type: " + clickedSupplement.getType());
                        selectedDesc.setText("Description: " + clickedSupplement.getDescription());
                    }
                });
            }

            // Bind data to the ViewHolder
            public void bind(Supplement supplement) {
                nameTextView.setText(supplement.getName());
                selectedName.setText(supplement.getName());
                selectedTime.setText("Time: " + supplement.getTiming());
                selectedDosage.setText("Dosage: " + supplement.getDosage());
                selectedType.setText("Type: " + supplement.getType());
                selectedDesc.setText("Description: " + supplement.getDescription());
            }
        }
    }

    public class Supplement {
        // Attributes for supplement details
        private String name;
        private String type;
        private String dosage;
        private String timing;
        private String description;

        // Constructor to initialize supplement details
        public Supplement(String name, String type, String dosage, String timing, String description) {
            this.name = name;
            this.type = type;
            this.dosage = dosage;
            this.timing = timing;
            this.description = description;
        }

        // Getter methods to retrieve supplement details

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
        // Attributes for SupplementQuery
        private TextView textView;
        private Spinner spinner;
        private FirebaseFirestore firestore;

        // Constructor to initialize SupplementQuery with necessary components
        public SupplementQuery(TextView textView, Spinner spinner, FirebaseFirestore firestore) {
            this.textView = textView;
            this.spinner = spinner;
            this.firestore = firestore;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Fetch supplements data asynchronously
                firestore.collection("supplements")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Clear existing data before adding new data
                                supplementNames.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = document.getString("name");
                                    supplementNames.add(name);
                                }

                                // Create an adapter for the Spinner with fetched data
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(SupplementInfoActivity.this,
                                        android.R.layout.simple_spinner_item, supplementNames);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(adapter);
                            } else {
                                // Log error if document retrieval fails
                                Log.d("SupplementQuery", "Error getting documents: ", task.getException());
                            }
                        });

                return "Success";
            } catch (Exception e) {
                // Log any exceptions during the background task
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Perform any post-execution tasks if needed
        }
    }


    private void addSelectedSupplement() {
        // Check if there are available supplements
        if (!supplementNames.isEmpty()) {
            // Get the selected supplement's name and index
            String selectedSupplementName = spinner.getSelectedItem().toString();
            int selectedIndex = spinner.getSelectedItemPosition();

            // Fetch supplement details from Firestore
            FirebaseFirestore.getInstance().collection("supplements")
                    .whereEqualTo("name", selectedSupplementName)
                    .get()
                    .addOnCompleteListener(task -> {
                        // Check if the data fetch was successful and not null
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Extract supplement details from the document
                                String name = document.getString("name");
                                String type = document.getString("type");
                                String dosage = document.getString("dosage");
                                String timing = document.getString("timing");
                                String description = document.getString("description");

                                // Add the selected supplement to the RecyclerView
                                adapter.addSupplement(new Supplement(name, type, dosage, timing, description));

                                // Remove the selected supplement from the Spinner
                                supplementNames.remove(selectedIndex);
                                ArrayAdapter<String> updatedAdapter = new ArrayAdapter<>(this,
                                        android.R.layout.simple_spinner_item, supplementNames);
                                updatedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(updatedAdapter);

                                // Exit the loop after adding the supplement
                                break;
                            }
                        } else {
                            // Display a message if fetching supplement details fails
                            Toast.makeText(this, "Failed to fetch supplement details", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Display a message if no supplements are available
            Toast.makeText(this, "No supplements available", Toast.LENGTH_SHORT).show();
        }
    }

    // Save added supplements using SharedPreferences
    private void saveSupplements() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        Gson gson = new Gson();
        String supplementsJson = gson.toJson(adapter.getSupplements()); // Assuming getSupplements() returns the list of added supplements
        editor.putString("supplements", supplementsJson);
        editor.apply();
    }

    // Retrieve saved supplements from SharedPreferences
    private void retrieveSavedSupplements() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String supplementsJson = preferences.getString("supplements", null);

        if (supplementsJson != null) {
            Type type = new TypeToken<ArrayList<Supplement>>() {}.getType();
            ArrayList<Supplement> savedSupplements = gson.fromJson(supplementsJson, type);

            if (savedSupplements != null && !savedSupplements.isEmpty()) {
                // Update the RecyclerView with saved supplements
                adapter = new SupplementAdapter(savedSupplements, findViewById(R.id.activity_supplement_info_layout));
                recyclerViewSupplements.setAdapter(adapter);

                // Remove saved supplements from the Spinner
                for (Supplement savedSupplement : savedSupplements) {
                    supplementNames.remove(savedSupplement.getName());
                }

                // Update the Spinner
                ArrayAdapter<String> updatedAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, supplementNames);
                updatedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(updatedAdapter);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save the supplements when leaving the activity
        saveSupplements();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement_info);

        // Find views by their IDs
        TextView textView = findViewById(R.id.selectedSuppDescription);
        spinner = findViewById(R.id.supplementsSpinner);
        recyclerViewSupplements = findViewById(R.id.recyclerViewSupplements);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Set up the RecyclerView
        recyclerViewSupplements.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SupplementAdapter(new ArrayList<>(), findViewById(R.id.activity_supplement_info_layout));
        recyclerViewSupplements.setAdapter(adapter);

        // Retrieve saved supplements and update RecyclerView
        retrieveSavedSupplements();

        // Execute a query to fetch supplement data
        SupplementQuery supplementQuery = new SupplementQuery(textView, spinner, firestore);
        supplementQuery.execute();

        // Set click listeners for bottom navigation buttons
        ImageButton homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener((view) -> {
            print("Clicked Home Button", Toast.LENGTH_LONG);
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

            // Get the SharedPreferences Editor
            SharedPreferences.Editor editor = preferences.edit();

            // Clear all the preferences stored in PREFS_NAME
            editor.clear();
            editor.apply();

            adapter.getSupplements().clear();
            adapter.notifyDataSetChanged();
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

        // Set click listener for adding a supplement
        Button addSupp = findViewById(R.id.addSuppBtn);
        addSupp.setOnClickListener((view) -> {
            addSelectedSupplement();
            print("Clicked Add Supplement Button", Toast.LENGTH_LONG);
            // Handle Add Supplement Button click
        });

    }

    // Helper method for displaying toast messages
    private void print(String msg, int duration) {
        Toast.makeText(this, msg, duration).show();
    }

}
