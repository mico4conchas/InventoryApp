package edu.utsa.cs3443.inventoryapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.ArrayList;

public class ViewRequestsActivity extends AppCompatActivity {

    private RecyclerView allRequestsRecyclerView;
    private RequestsAdapter requestsAdapter;
    private FirebaseFirestore firestore;
    private List<RequestItem> allRequestsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_requests);

        // Initialize RecyclerView and Firestore instance
        allRequestsRecyclerView = findViewById(R.id.allRequestsRecyclerView);
        firestore = FirebaseFirestore.getInstance();
        allRequestsList = new ArrayList<>();

        // Set up the RecyclerView layout manager
        allRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch user-specific requests from Firestore
        fetchUserRequests();
    }

    private void fetchUserRequests() {
        // Get the current user ID from Firebase Auth
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid(); // Get the current user's UID
            firestore.collection("requests")
                    .whereEqualTo("userId", userId)
                    // Removed orderBy("status") temporarily for testing
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null) {
                                allRequestsList.clear();
                                for (DocumentSnapshot snapshot : querySnapshot) {
                                    RequestItem requestItem = snapshot.toObject(RequestItem.class);
                                    Log.d("ViewRequestsActivity", "Fetched request: " + requestItem);  // Log the fetched request item
                                    allRequestsList.add(requestItem);
                                }

                                // If requests are found, update the RecyclerView with the adapter
                                if (!allRequestsList.isEmpty()) {
                                    requestsAdapter = new RequestsAdapter(ViewRequestsActivity.this, allRequestsList);
                                    allRequestsRecyclerView.setAdapter(requestsAdapter);
                                } else {
                                    Toast.makeText(ViewRequestsActivity.this, "No requests found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(ViewRequestsActivity.this, "Error fetching requests", Toast.LENGTH_SHORT).show();
                            Log.e("ViewRequestsActivity", "Error fetching requests", task.getException());  // Log the error
                        }
                    });
        } else {
            Toast.makeText(ViewRequestsActivity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
