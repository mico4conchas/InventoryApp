package edu.utsa.cs3443.inventoryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddInventoryActivity extends AppCompatActivity {

    private EditText itemNameEditText;
    private EditText caseAmountEditText;
    private EditText itemsPerCaseEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        itemNameEditText = findViewById(R.id.itemNameEditText);
        caseAmountEditText = findViewById(R.id.caseAmountEditText);
        itemsPerCaseEditText = findViewById(R.id.itemsPerCaseEditText);

        Button addInventoryButton = findViewById(R.id.addInventoryButton);
        addInventoryButton.setOnClickListener(v -> {
            String itemName = itemNameEditText.getText().toString();
            int caseAmount;
            int itemsPerCase;

            // Handle potential NumberFormatException when parsing integers
            try {
                caseAmount = Integer.parseInt(caseAmountEditText.getText().toString());
                itemsPerCase = Integer.parseInt(itemsPerCaseEditText.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid numbers for case amount and items per case.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create an InventoryItem object without an ID (ID will be set by the database)
            InventoryItem newItem = new InventoryItem(itemName, caseAmount, itemsPerCase);

            // Get the InventoryRepository instance and add the item
            InventoryRepository repository = InventoryRepository.getInstance(this);
            repository.addInventoryItem(newItem);

            // Notify the InventoryListActivity to refresh the inventory list
            if (InventoryListActivity.inventoryAdapter != null) {
                InventoryListActivity.inventoryAdapter.notifyDataSetChanged();
            }

            // Show a confirmation message
            Toast.makeText(this, "Added " + itemName + " to inventory.", Toast.LENGTH_SHORT).show();

            // Close the activity after adding
            finish();
        });
    }
}
