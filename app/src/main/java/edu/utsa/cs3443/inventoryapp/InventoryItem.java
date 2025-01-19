package edu.utsa.cs3443.inventoryapp;

public class InventoryItem {

    private static int idCounter = 1; // Ensure unique IDs
    private int id;
    private String itemName;
    private int caseAmount;
    private int itemsPerCase;

    public InventoryItem(int newId, String itemName, int caseAmount, int itemsPerCase) {
        this.id = idCounter++;
        this.itemName = itemName;
        this.caseAmount = caseAmount;
        this.itemsPerCase = itemsPerCase;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCaseAmount() {
        return caseAmount;
    }

    public void setCaseAmount(int caseAmount) {
        this.caseAmount = caseAmount;
    }

    public int getItemsPerCase() {
        return itemsPerCase;
    }

    public void setItemsPerCase(int itemsPerCase) {
        this.itemsPerCase = itemsPerCase;
    }
}
