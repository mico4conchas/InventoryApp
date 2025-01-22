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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InventoryViewHolder holder, int position) {
        InventoryItem item = inventoryList.get(position);
        holder.nameTextView.setText(item.getItemName());
        holder.caseAmountTextView.setText(String.valueOf(item.getCaseAmount()));
        holder.itemsPerCaseTextView.setText(String.valueOf(item.getItemsPerCase()));

        // Set the click listener for the Edit button
        holder.editButton.setOnClickListener(v -> {
            // Create an intent to start the EditInventoryActivity
            Intent intent = new Intent(v.getContext(), EditInventoryActivity.class);

            // Pass the item ID to EditInventoryActivity
            intent.putExtra("inventory_item_id", item.getId());

            // Start the activity
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public static class InventoryViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView caseAmountTextView;
        TextView itemsPerCaseTextView;

        Button editButton;

        public InventoryViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            caseAmountTextView = itemView.findViewById(R.id.case_amount);
            itemsPerCaseTextView = itemView.findViewById(R.id.items_per_case);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }
}
