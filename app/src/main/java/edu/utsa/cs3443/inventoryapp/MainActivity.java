package edu.utsa.cs3443.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // FirebaseAuth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth

        // Setup other buttons and their actions
        Button viewInventoryButton = findViewById(R.id.btn_view_inventory);
        viewInventoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InventoryListActivity.class);
            startActivity(intent);
        });

        Button addInventoryButton = findViewById(R.id.btn_add_inventory);
        addInventoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddInventoryActivity.class);
            startActivity(intent);
        });

        Button requestItemButton = findViewById(R.id.btn_request_item);
        requestItemButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RequestItemActivity.class);
            startActivity(intent);
        });

        Button myRequestsButton = findViewById(R.id.btn_my_requests);
        myRequestsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewRequestsActivity.class);
            startActivity(intent);
        });

        Button viewAllRequestsButton = findViewById(R.id.btn_view_all_requests);
        viewAllRequestsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewAllRequestsActivity.class);
            startActivity(intent);
        });

        // Add a sign-out button to allow users to log out
        Button signOutButton = findViewById(R.id.btn_sign_out);
        signOutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(MainActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
            // Redirect to sign-in page after sign out
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in on app start
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // If no user is signed in, redirect to sign-in screen
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        }
    }
}
