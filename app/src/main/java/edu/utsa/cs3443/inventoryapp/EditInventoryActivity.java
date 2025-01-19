package edu.utsa.cs3443.inventoryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditInventoryActivity extends AppCompatActivity {

    private EditText itemNameEditText, caseAmountEditText, itemsPerCaseEditText;
    private Button saveButton;
    private InventoryItem inventoryItem;
    private int itemPosition;  // Track the position of the item in the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory);

        itemNameEditText = findViewById(R.id.itemNameEditText);
        caseAmountEditText = findViewById(R.id.caseAmountEditText);
        itemsPerCaseEditText = findViewById(R.id.itemsPerCaseEditText);
        saveButton = findViewById(R.id.saveChangesButton);

        int itemId = getIntent().getIntExtra("inventory_item_id", -1);

        InventoryRepository repository = InventoryRepository.getInstance(this);
        inventoryItem = repository.getInventoryItemById(itemId);

        if (inventoryItem != null) {
            itemPosition = repository.getInventoryList().indexOf(inventoryItem);  // Track the item's position
            itemNameEditText.setText(inventoryItem.getItemName());
            caseAmountEditText.setText(String.valueOf(inventoryItem.getCaseAmount()));
            itemsPerCaseEditText.setText(String.valueOf(inventoryItem.getItemsPerCase()));
        } else {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String itemName = itemNameEditText.getText().toString().trim();
                    int caseAmount = Integer.parseInt(caseAmountEditText.getText().toString().trim());
                    int itemsPerCase = Integer.parseInt(itemsPerCaseEditText.getText().toString().trim());

                    inventoryItem.setItemName(itemName);
                    inventoryItem.setCaseAmount(caseAmount);
                    inventoryItem.setItemsPerCase(itemsPerCase);

                    // Update the inventory in the repository
                    repository.updateInventoryItem(inventoryItem, EditInventoryActivity.this);

                    // Update the item in the list and notify the adapter
                    repository.getInventoryList().set(itemPosition, inventoryItem);  // Update the list
                    InventoryListActivity.inventoryAdapter.notifyItemChanged(itemPosition);  // Notify the adapter

                    Toast.makeText(EditInventoryActivity.this, "Item updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(EditInventoryActivity.this, "Invalid input. Please check your values.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
