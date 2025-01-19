package edu.utsa.cs3443.inventoryapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InventoryListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static InventoryAdapter inventoryAdapter; // Made static to notify from EditInventoryActivity
    private List<InventoryItem> inventoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Setting LayoutManager

        // Get the inventory list from the repository
        inventoryList = InventoryRepository.getInstance(this).getInventoryList();

        // Set the adapter for the RecyclerView
        inventoryAdapter = new InventoryAdapter(inventoryList);
        recyclerView.setAdapter(inventoryAdapter);

        // Set up buttons for return and clear inventory
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity and return to the previous one
            }
        });

        Button clearInventoryButton = findViewById(R.id.clearInventoryButton);
        clearInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearInventoryConfirmation();
            }
        });
    }

    private void showClearInventoryConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Clear Inventory")
                .setMessage("Are you sure you want to clear all items in the inventory?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearInventory();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clearInventory() {
        InventoryRepository.getInstance(InventoryListActivity.this).clearInventory(InventoryListActivity.this);
        Toast.makeText(InventoryListActivity.this, "Inventory cleared", Toast.LENGTH_SHORT).show();

        // Refresh the inventory list and notify the adapter to update the UI
        inventoryAdapter.notifyDataSetChanged();
    }
}
