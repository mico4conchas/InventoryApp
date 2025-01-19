package edu.utsa.cs3443.inventoryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {

    private static InventoryRepository instance;
    private List<InventoryItem> inventoryList;
    private static final String PREFS_NAME = "InventoryPrefs";
    private static final String INVENTORY_KEY = "inventory";

    private InventoryRepository(Context context) {
        loadInventory(context);
    }

    public static synchronized InventoryRepository getInstance(Context context) {
        if (instance == null) {
            instance = new InventoryRepository(context);
        }
        return instance;
    }

    public List<InventoryItem> getInventoryList() {
        return inventoryList;
    }

    public InventoryItem getInventoryItemById(int id) {
        for (InventoryItem item : inventoryList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void addInventoryItem(InventoryItem item, Context context) {
        inventoryList.add(item);
        saveInventory(context);
        Toast.makeText(context, "Item added successfully", Toast.LENGTH_SHORT).show();
    }

    public void updateInventoryItem(InventoryItem updatedItem, Context context) {
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).getId() == updatedItem.getId()) {
                inventoryList.set(i, updatedItem);
                saveInventory(context);
                return;
            }
        }
        Toast.makeText(context, "Item not found in repository", Toast.LENGTH_SHORT).show();
    }

    public void clearInventory(Context context) {
        inventoryList.clear();
        saveInventory(context);
        Toast.makeText(context, "Inventory cleared", Toast.LENGTH_SHORT).show();
    }

    private void saveInventory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(inventoryList);
        editor.putString(INVENTORY_KEY, json);
        editor.apply();
    }

    private void loadInventory(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(INVENTORY_KEY, null);
        Type type = new TypeToken<ArrayList<InventoryItem>>() {}.getType();
        inventoryList = gson.fromJson(json, type);

        if (inventoryList == null) {
            inventoryList = new ArrayList<>();
        }
    }
}
