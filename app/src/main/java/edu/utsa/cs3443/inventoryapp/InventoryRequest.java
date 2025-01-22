package edu.utsa.cs3443.inventoryapp;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class InventoryRequest {

    private String requesterId;
    private String itemName;
    private int casesNeeded;
    private int itemsPerCase;
    private String status;
    private String responderId;

    public InventoryRequest(String requesterId, String itemName, int casesNeeded, int itemsPerCase) {
        this.requesterId = requesterId;
        this.itemName = itemName;
        this.casesNeeded = casesNeeded;
        this.itemsPerCase = itemsPerCase;
        this.status = "Pending";
        this.responderId = null;
    }

    // Getters and setters
    public String getRequesterId() {
        return requesterId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getCasesNeeded() {
        return casesNeeded;
    }

    public int getItemsPerCase() {
        return itemsPerCase;
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
}
