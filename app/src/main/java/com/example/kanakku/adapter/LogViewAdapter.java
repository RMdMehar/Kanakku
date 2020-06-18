package com.example.kanakku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kanakku.R;
import com.example.kanakku.model.Record;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LogViewAdapter extends FirestoreAdapter<LogViewAdapter.ViewHolder> {


    public LogViewAdapter(Query query) {
        super(query);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.log_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewAdapter.ViewHolder holder, int position) {
        holder.bind(getSnapshot(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView logLabel, logAmt, logTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            logLabel = itemView.findViewById(R.id.log_label);
            logAmt = itemView.findViewById(R.id.log_amt);
            logTimestamp = itemView.findViewById(R.id.log_timestamp);
        }

        public void bind(final DocumentSnapshot snapshot) {
            Record record = snapshot.toObject(Record.class);

            logLabel.setText(record.getTransactionLabel());
            logAmt.setText(record.getTransactionAmount());
        }
    }
}
