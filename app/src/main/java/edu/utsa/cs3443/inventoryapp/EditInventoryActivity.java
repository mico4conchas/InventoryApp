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
    private int itemPosition; // Track the position of the item in the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory);

        itemNameEditText = findViewById(R.id.itemNameEditText);
        caseAmountEditText = findViewById(R.id.caseAmountEditText);
        itemsPerCaseEditText = findViewById(R.id.itemsPerCaseEditText);
        saveButton = findViewById(R.id.saveChangesButton);

        // Retrieve the item ID passed via the intent
        int itemId = getIntent().getIntExtra("inventory_item_id", -1);

        if (itemId == -1) {
            Toast.makeText(this, "Invalid item ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Get the repository instance
        InventoryRepository repository = InventoryRepository.getInstance(this);
        inventoryItem = repository.getInventoryItemById(itemId);

        if (inventoryItem != null) {
            // Find the position of the item in the list
            itemPosition = repository.getInventoryList().indexOf(inventoryItem);

            // Populate the fields with current item data
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

                    // Update the inventory item with new values
                    inventoryItem.setItemName(itemName);
                    inventoryItem.setCaseAmount(caseAmount);
                    inventoryItem.setItemsPerCase(itemsPerCase);

                    // Update the repository and notify the adapter
                    repository.updateInventoryItem(inventoryItem);
                    InventoryListActivity.inventoryAdapter.notifyItemChanged(itemPosition);

                    Toast.makeText(EditInventoryActivity.this, "Item updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (NumberFormatException e) {
                    Toast.makeText(EditInventoryActivity.this, "Invalid input. Please check your values.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
