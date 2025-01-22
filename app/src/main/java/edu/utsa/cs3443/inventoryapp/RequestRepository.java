package edu.utsa.cs3443.inventoryapp;

import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RequestRepository {

    private static RequestRepository instance;
    private FirebaseFirestore firestore;
    private List<RequestItem> requestList;

    // Private constructor to initialize Firestore and request list
    private RequestRepository(Context context) {
        firestore = FirebaseFirestore.getInstance();
        requestList = new ArrayList<>();
        loadRequestList();
    }

    // Singleton pattern to get the instance of RequestRepository
    public static RequestRepository getInstance(Context context) {
        if (instance == null) {
            instance = new RequestRepository(context);
        }
        return instance;
    }

    // Load all requests from Firestore
    private void loadRequestList() {
        firestore.collection("requests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null) {
                                requestList.clear();
                                for (DocumentSnapshot snapshot : querySnapshot) {
                                    RequestItem requestItem = snapshot.toObject(RequestItem.class);
                                    requestList.add(requestItem);
                                }
                            }
                        } else {
                            // Handle failure (optional)
                        }
                    }
                });
    }

    // Add a request to Firestore
    public void addRequest(RequestItem request) {
        firestore.collection("requests")
                .add(request)
                .addOnSuccessListener(documentReference -> {
                    request.setId(documentReference.getId());
                    requestList.add(request);
                });
    }

    // Get all requests
    public List<RequestItem> getRequestList() {
        return requestList;
    }

    // Get requests for a specific user from Firestore
    public void getUserRequests(String userId, final RequestRepositoryCallback callback) {
        firestore.collection("requests")
                .whereEqualTo("userId", userId)  // Filter by userId
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            List<RequestItem> userRequests = new ArrayList<>();
                            if (querySnapshot != null) {
                                for (DocumentSnapshot snapshot : querySnapshot) {
                                    RequestItem requestItem = snapshot.toObject(RequestItem.class);
                                    userRequests.add(requestItem);
                                }
                            }
                            // Call the callback on success
                            callback.onSuccess(userRequests);
                        } else {
                            // Call the callback on failure
                            callback.onFailure("Failed to load user requests");
                        }
                    }
                });
    }

    // Define the callback interface
    public interface RequestRepositoryCallback {
        void onSuccess(List<RequestItem> requestList);
        void onFailure(String errorMessage);
    }
}
