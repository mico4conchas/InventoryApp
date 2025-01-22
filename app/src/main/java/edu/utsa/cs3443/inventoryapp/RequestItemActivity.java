package edu.utsa.cs3443.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestItemActivity extends AppCompatActivity {

    private EditText itemNameEditText;
    private EditText casesNeededEditText;
    private EditText itemsPerCaseEditText;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_item);

        itemNameEditText = findViewById(R.id.itemNameEditText);
        casesNeededEditText = findViewById(R.id.casesNeededEditText);
        itemsPerCaseEditText = findViewById(R.id.itemsPerCaseEditText);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();  // Initialize Firebase Auth

        Button submitRequestButton = findViewById(R.id.submitRequestButton);
        submitRequestButton.setOnClickListener(v -> {
            String itemName = itemNameEditText.getText().toString().trim();
            String casesNeededString = casesNeededEditText.getText().toString().trim();
            String itemsPerCaseString = itemsPerCaseEditText.getText().toString().trim();

            // Validate input fields
            if (itemName.isEmpty() || casesNeededString.isEmpty() || itemsPerCaseString.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            int casesNeeded;
            int itemsPerCase;

            // Validate if casesNeeded and itemsPerCase are valid integers
            try {
                casesNeeded = Integer.parseInt(casesNeededString);
                itemsPerCase = Integer.parseInt(itemsPerCaseString);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid numbers for cases needed and items per case", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the current user ID from Firebase Auth
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
                return;
            }
            String userId = currentUser.getUid();  // Get the current user's UID

            // Create a new RequestItem object
            RequestItem requestItem = new RequestItem(itemName, casesNeeded, itemsPerCase, userId, "Pending", null);

            // Submit the request to Firestore
            firestore.collection("requests")
                    .add(requestItem)
                    .addOnSuccessListener(documentReference -> {
                        // Show toast message
                        Toast.makeText(this, "Request submitted for " + itemName, Toast.LENGTH_SHORT).show();

                        // Navigate back to MainActivity (Main Menu)
                        Intent intent = new Intent(RequestItemActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Clear the activity stack
                        startActivity(intent);

                        // Close the current activity
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Log the error message for debugging
                        e.printStackTrace();
                        Toast.makeText(this, "Error submitting request: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
