package edu.utsa.cs3443.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestViewHolder> {

    private List<RequestItem> requestList;
    private Context context;
    private FirebaseFirestore firestore;

    public RequestsAdapter(Context context, List<RequestItem> requestList) {
        this.context = context;
        this.requestList = requestList != null ? requestList : new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false);
        return new RequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        RequestItem requestItem = requestList.get(position);

        holder.itemNameTextView.setText(requestItem.getItemName());
        holder.casesNeededTextView.setText("Cases Needed: " + requestItem.getCasesNeeded());
        holder.itemsPerCaseTextView.setText("Items per Case: " + requestItem.getItemsPerCase());
        holder.userIdTextView.setText("Requested by: " + requestItem.getUserId());
        holder.statusTextView.setText("Status: " + requestItem.getStatus());

        if ("Pending".equals(requestItem.getStatus())) {
            holder.acceptButton.setVisibility(View.VISIBLE);
            holder.acceptButton.setOnClickListener(v -> {
                // Start RespondToRequestActivity and pass relevant data
                Intent intent = new Intent(context, RespondToRequestActivity.class);
                intent.putExtra("itemName", requestItem.getItemName()); // Pass item name
                intent.putExtra("requestId", requestItem.getId()); // Pass request ID
                context.startActivity(intent);
            });
        } else {
            holder.acceptButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public void updateRequestList(List<RequestItem> newRequestList) {
        this.requestList = newRequestList;
        notifyDataSetChanged();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView itemNameTextView;
        TextView casesNeededTextView;
        TextView itemsPerCaseTextView;
        TextView userIdTextView;
        TextView statusTextView;
        Button acceptButton;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            casesNeededTextView = itemView.findViewById(R.id.casesNeededTextView);
            itemsPerCaseTextView = itemView.findViewById(R.id.itemsPerCaseTextView);
            userIdTextView = itemView.findViewById(R.id.userIdTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            acceptButton = itemView.findViewById(R.id.acceptButton);
        }
    }
}
