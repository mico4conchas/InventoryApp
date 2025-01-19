package edu.utsa.cs3443.inventoryapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddInventoryActivity extends AppCompatActivity {

    private EditText itemNameEditText, caseAmountEditText, itemsPerCaseEditText;
    private Button addButton;
    private InventoryRepository inventoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        // Initialize the repository
        inventoryRepository = InventoryRepository.getInstance(this);

        // Initialize the views
        itemNameEditText = findViewById(R.id.itemNameEditText);
        caseAmountEditText = findViewById(R.id.caseAmountEditText);
        itemsPerCaseEditText = findViewById(R.id.itemsPerCaseEditText);
        addButton = findViewById(R.id.addInventoryButton);

        // Set up the add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = itemNameEditText.getText().toString().trim();
                String caseAmountStr = caseAmountEditText.getText().toString().trim();
                String itemsPerCaseStr = itemsPerCaseEditText.getText().toString().trim();

                if (itemName.isEmpty() || caseAmountStr.isEmpty() || itemsPerCaseStr.isEmpty()) {
                    Toast.makeText(AddInventoryActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int caseAmount = Integer.parseInt(caseAmountStr);
                int itemsPerCase = Integer.parseInt(itemsPerCaseStr);

                // Generate a new unique ID for the item
                int newId = inventoryRepository.getInventoryList().size() + 1;

                // Create a new InventoryItem with all required arguments
                InventoryItem newItem = new InventoryItem(newId, itemName, caseAmount, itemsPerCase);

                // Add the item to the repository
                inventoryRepository.addInventoryItem(newItem, AddInventoryActivity.this);
                Toast.makeText(AddInventoryActivity.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
