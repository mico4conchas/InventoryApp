package edu.utsa.cs3443.inventoryapp;

public class InventoryItem {

    private int id;
    private String itemName;
    private int caseAmount;
    private int itemsPerCase;

    // Constructor
    public InventoryItem(int id, String itemName, int caseAmount, int itemsPerCase) {
        this.id = id;
        this.itemName = itemName;
        this.caseAmount = caseAmount;
        this.itemsPerCase = itemsPerCase;
    }

    public InventoryItem(String itemName, int caseAmount, int itemsPerCase) {
        this.id = -1; // Default ID until set by the database
        this.itemName = itemName;
        this.caseAmount = caseAmount;
        this.itemsPerCase = itemsPerCase;
    }

    // Getter for itemName
    public int getId() {
        return id;
    }
    public String getItemName() {
        return itemName;
    }

    // Getter for caseAmount
    public int getCaseAmount() {
        return caseAmount;
    }

    // Getter for itemsPerCase
    public int getItemsPerCase() {
        return itemsPerCase;
    }

    public void setId(int id) {
        this.id = id;
    }
    // Setter for itemName
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // Setter for caseAmount
    public void setCaseAmount(int caseAmount) {
        this.caseAmount = caseAmount;
    }

    // Setter for itemsPerCase
    public void setItemsPerCase(int itemsPerCase) {
        this.itemsPerCase = itemsPerCase;
    }
}

