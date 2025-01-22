package edu.utsa.cs3443.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {

    private static InventoryRepository instance;
    private SQLiteDatabase database;
    private InventoryDbHelper dbHelper;
    private List<InventoryItem> inventoryList;

    private InventoryRepository(Context context) {
        dbHelper = new InventoryDbHelper(context);
        database = dbHelper.getWritableDatabase();
        inventoryList = new ArrayList<>();
        loadInventoryList();
    }

    public static InventoryRepository getInstance(Context context) {
        if (instance == null) {
            instance = new InventoryRepository(context);
        }
        return instance;
    }

    // Load inventory items from the database into the list
    private void loadInventoryList() {
        Cursor cursor = database.query(
                InventoryDbHelper.TABLE_INVENTORY,
                null, null, null, null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(InventoryDbHelper.COLUMN_ID));
                String itemName = cursor.getString(cursor.getColumnIndex(InventoryDbHelper.COLUMN_ITEM_NAME));
                int caseAmount = cursor.getInt(cursor.getColumnIndex(InventoryDbHelper.COLUMN_CASE_AMOUNT));
                int itemsPerCase = cursor.getInt(cursor.getColumnIndex(InventoryDbHelper.COLUMN_ITEMS_PER_CASE));

                // Corrected constructor call with 3 parameters
                InventoryItem item = new InventoryItem(id, itemName, caseAmount, itemsPerCase);
                item.setId(id); // Set the ID after creating the object
                inventoryList.add(item);
            }
            cursor.close();
        }
    }

    // Add an inventory item to the list and database
    public void addInventoryItem(InventoryItem item) {
        ContentValues values = new ContentValues();
        values.put(InventoryDbHelper.COLUMN_ITEM_NAME, item.getItemName());
        values.put(InventoryDbHelper.COLUMN_CASE_AMOUNT, item.getCaseAmount());
        values.put(InventoryDbHelper.COLUMN_ITEMS_PER_CASE, item.getItemsPerCase());

        long rowId = database.insert(InventoryDbHelper.TABLE_INVENTORY, null, values);
        if (rowId != -1) {
            item.setId((int) rowId);
            inventoryList.add(item);
        }
    }

    // Get all inventory items
    public List<InventoryItem> getInventoryList() {
        return inventoryList;
    }

    // Get an inventory item by ID
    public InventoryItem getInventoryItemById(int id) {
        for (InventoryItem item : inventoryList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    // Update an inventory item in the database and the list
    public void updateInventoryItem(InventoryItem item) {
        ContentValues values = new ContentValues();
        values.put(InventoryDbHelper.COLUMN_ITEM_NAME, item.getItemName());
        values.put(InventoryDbHelper.COLUMN_CASE_AMOUNT, item.getCaseAmount());
        values.put(InventoryDbHelper.COLUMN_ITEMS_PER_CASE, item.getItemsPerCase());

        String whereClause = InventoryDbHelper.COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(item.getId())};

        database.update(InventoryDbHelper.TABLE_INVENTORY, values, whereClause, whereArgs);

        // Update the list in memory
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).getId() == item.getId()) {
                inventoryList.set(i, item);
                break;
            }
        }
    }

    // Delete an inventory item from the list and database
    public void deleteInventoryItem(InventoryItem item) {
        String whereClause = InventoryDbHelper.COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(item.getId())};

        database.delete(InventoryDbHelper.TABLE_INVENTORY, whereClause, whereArgs);
        inventoryList.remove(item);
    }

    // Clear the entire inventory from both the database and the in-memory list
    public void clearInventory() {
        // Delete all items from the database
        database.delete(InventoryDbHelper.TABLE_INVENTORY, null, null);

        // Clear the in-memory list
        inventoryList.clear();
    }

    public void close() {
        database.close();
    }
}
