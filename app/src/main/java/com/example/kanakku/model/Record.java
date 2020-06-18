package com.example.kanakku.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Record {
    private String transactionLabel;
    private int transactionAmount;

    public Record() {}

    public Record(String label, int amt) {
        this.transactionLabel = label;
        this.transactionAmount = amt;
    }

    public String getTransactionLabel() {
        return transactionLabel;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }
}
