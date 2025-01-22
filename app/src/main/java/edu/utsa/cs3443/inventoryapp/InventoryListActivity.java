package edu.utsa.cs3443.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InventoryListActivity extends AppCompatActivity {

    // Make inventoryAdapter static and public for access from other activities
    public static InventoryAdapter inventoryAdapter;

    private RecyclerView recyclerView;
    private InventoryRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        recyclerView = findViewById(R.id.inventoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = InventoryRepository.getInstance(this);

        // Initialize the adapter and set it to the RecyclerView
        inventoryAdapter = new InventoryAdapter(repository.getInventoryList());
        recyclerView.setAdapter(inventoryAdapter);

        // Set up Clear Inventory Button with Confirmation Dialog
        Button clearInventoryButton = findViewById(R.id.clearInventoryButton);
        clearInventoryButton.setOnClickListener(v -> showClearInventoryConfirmation());

        // Set up Return to Menu Button
        Button returnToMenuButton = findViewById(R.id.returnToMenuButton);
        returnToMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(InventoryListActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the current activity and return to the previous one
        });
    }

    // Method to show the confirmation dialog
    private void showClearInventoryConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Clear Inventory")
                .setMessage("Are you sure you want to clear all inventory items?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Clear inventory and refresh the RecyclerView
                    repository.clearInventory();
                    inventoryAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Inventory cleared.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Do nothing if user cancels
                    dialog.dismiss();
                })
                .show();
    }
}
