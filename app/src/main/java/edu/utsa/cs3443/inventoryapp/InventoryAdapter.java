package edu.utsa.cs3443.inventoryapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private List<InventoryItem> inventoryList;

    public InventoryAdapter(List<InventoryItem> inventoryList) {
        this.inventoryList = inventoryList;
    }

    @Override
    public InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new InventoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InventoryViewHolder holder, int position) {
        InventoryItem inventoryItem = inventoryList.get(position);

        // Ensure the data is being correctly set
        holder.itemNameTextView.setText(inventoryItem.getItemName());
        holder.caseAmountTextView.setText(String.valueOf(inventoryItem.getCaseAmount()));
        holder.itemsPerCaseTextView.setText(String.valueOf(inventoryItem.getItemsPerCase()));

        // Clear any previous listeners and set the new listener for this position
        holder.editButton.setOnClickListener(null);  // Reset previous listeners
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ensure we are passing the correct item ID
                Intent intent = new Intent(holder.itemView.getContext(), EditInventoryActivity.class);
                intent.putExtra("inventory_item_id", inventoryItem.getId());  // Pass the unique item ID
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public static class InventoryViewHolder extends RecyclerView.ViewHolder {

        public TextView itemNameTextView;
        public TextView caseAmountTextView;
        public TextView itemsPerCaseTextView;
        public Button editButton;

        public InventoryViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            caseAmountTextView = itemView.findViewById(R.id.caseAmountTextView);
            itemsPerCaseTextView = itemView.findViewById(R.id.itemsPerCaseTextView);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
