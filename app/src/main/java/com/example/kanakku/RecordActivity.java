package com.example.kanakku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.kanakku.model.Record;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecordActivity extends AppCompatActivity {
    FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Intent intent = getIntent();
        String userDocID = intent.getStringExtra("userDocID");

        EditText txnLabel = findViewById(R.id.edit_text_txn_label);
        String label = txnLabel.getText().toString();
        EditText txnAmount = findViewById(R.id.edit_text_txn_amt);
        String amount = txnAmount.getText().toString();

        mFirestore = FirebaseFirestore.getInstance();
        CollectionReference records = mFirestore.collection("users")
                .document(userDocID)
                .collection("records");
        records.add(new Record(label, Integer.parseInt(amount))).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                finish();
            }
        });
    }
}