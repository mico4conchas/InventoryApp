package edu.utsa.cs3443.inventoryapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RespondToRequestActivity extends AppCompatActivity {

    private TextView itemNameTextView;
    private EditText casesToGiveEditText;
    private EditText itemsPerCaseEditText;
    private EditText messageEditText;
    private Button submitResponseButton;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    private String requestId;
    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respond_to_request);

        // Initialize Firestore and Auth
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Retrieve data from Intent
        itemName = getIntent().getStringExtra("itemName");
        requestId = getIntent().getStringExtra("requestId");

        // Bind UI elements
        itemNameTextView = findViewById(R.id.itemNameTextView);
        casesToGiveEditText = findViewById(R.id.casesToGiveEditText);
        itemsPerCaseEditText = findViewById(R.id.itemsPerCaseEditText);
        messageEditText = findViewById(R.id.messageEditText);
        submitResponseButton = findViewById(R.id.submitResponseButton);

        // Set item name
        itemNameTextView.setText(itemName);

        // Fetch request details from Firestore
        fetchRequestDetails();

        // Set button click listener
        submitResponseButton.setOnClickListener(v -> handleResponseSubmission());
    }

    private void fetchRequestDetails() {
        firestore.collection("requests")
                .document(requestId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Extract the required fields from Firestore document
                        int casesNeeded = documentSnapshot.getLong("casesNeeded").intValue();
                        int itemsPerCase = documentSnapshot.getLong("itemsPerCase").intValue();

                        // Dynamically update the hint text
                        casesToGiveEditText.setHint("Number of Cases to Give (" + casesNeeded + ")");
                        itemsPerCaseEditText.setHint("Items per Case (" + itemsPerCase + ")");
                    } else {
                        Toast.makeText(RespondToRequestActivity.this, "Request not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RespondToRequestActivity.this, "Error fetching request details", Toast.LENGTH_SHORT).show();
                });
    }

    private void handleResponseSubmission() {
        String casesToGiveStr = casesToGiveEditText.getText().toString().trim();
        String itemsPerCaseStr = itemsPerCaseEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        // Validate input
        if (casesToGiveStr.isEmpty() || itemsPerCaseStr.isEmpty()) {
            Toast.makeText(this, "Please fill out all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int casesToGive;
        int itemsPerCase;
        try {
            casesToGive = Integer.parseInt(casesToGiveStr);
            itemsPerCase = Integer.parseInt(itemsPerCaseStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get responder ID
        String responderId = mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getUid() : "Unknown";

        // Prepare data to update Firestore
        Map<String, Object> response = new HashMap<>();
        response.put("responderId", responderId);
        response.put("casesGiven", casesToGive);
        response.put("itemsPerCaseGiven", itemsPerCase);
        response.put("message", message);
        response.put("status", "Accepted");

        // Update Firestore
        firestore.collection("requests").document(requestId)
                .update(response)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Response submitted successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error submitting response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
