package edu.utsa.cs3443.inventoryapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import java.util.List;
import java.util.ArrayList;

public class ViewAllRequestsActivity extends AppCompatActivity {

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

        // Fetch all requests from Firestore
        fetchAllRequests();
    }

    private void fetchAllRequests() {
        firestore.collection("requests")
                .orderBy("status") // Optional: Order by status or any field you prefer
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            allRequestsList.clear();
                            for (DocumentSnapshot snapshot : querySnapshot) {
                                RequestItem requestItem = snapshot.toObject(RequestItem.class);
                                allRequestsList.add(requestItem);
                            }

                            // If requests are found, update the RecyclerView with the adapter
                            if (!allRequestsList.isEmpty()) {
                                requestsAdapter = new RequestsAdapter(ViewAllRequestsActivity.this, allRequestsList);
                                allRequestsRecyclerView.setAdapter(requestsAdapter);
                            } else {
                                Toast.makeText(ViewAllRequestsActivity.this, "No requests found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(ViewAllRequestsActivity.this, "Error fetching requests", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
