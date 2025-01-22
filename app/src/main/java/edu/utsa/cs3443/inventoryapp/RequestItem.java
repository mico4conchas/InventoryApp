package edu.utsa.cs3443.inventoryapp;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class RequestItem {

    @DocumentId
    private String id; // Firebase document ID
    private String itemName;
    private int casesNeeded;
    private int itemsPerCase;
    private String userId;
    private String status;
    private String responderId;

    // No-argument constructor required by Firestore
    public RequestItem() {
        // Empty constructor needed for Firestore to deserialize data
    }

    // Constructor with parameters
    public RequestItem(String itemName, int casesNeeded, int itemsPerCase, String userId, String status, String responderId) {
        this.itemName = itemName;
        this.casesNeeded = casesNeeded;
        this.itemsPerCase = itemsPerCase;
        this.userId = userId;
        this.status = status;
        this.responderId = responderId;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCasesNeeded() {
        return casesNeeded;
    }

    public void setCasesNeeded(int casesNeeded) {
        this.casesNeeded = casesNeeded;
    }

    public int getItemsPerCase() {
        return itemsPerCase;
    }

    public void setItemsPerCase(int itemsPerCase) {
        this.itemsPerCase = itemsPerCase;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponderId() {
        return responderId;
    }

    public void setResponderId(String responderId) {
        this.responderId = responderId;
    }

    @Override
    public String toString() {
        return "RequestItem{" +
                "id='" + id + '\'' +
                ", itemName='" + itemName + '\'' +
                ", casesNeeded=" + casesNeeded +
                ", itemsPerCase=" + itemsPerCase +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", responderId='" + responderId + '\'' +
                '}';
    }
}
