package edu.utsa.cs3443.inventoryapp;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import edu.utsa.cs3443.inventoryapp.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

    public class MainActivity extends AppCompatActivity {

        private Button btnViewInventory, btnAddInventory;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            btnViewInventory = findViewById(R.id.btn_view_inventory);
            btnAddInventory = findViewById(R.id.btn_add_inventory);

            // Navigate to Inventory List
            btnViewInventory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, InventoryListActivity.class);
                    startActivity(intent);
                }
            });

            // Navigate to Add Inventory
            btnAddInventory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddInventoryActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
