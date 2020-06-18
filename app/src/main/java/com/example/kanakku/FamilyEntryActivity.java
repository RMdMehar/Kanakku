package com.example.kanakku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kanakku.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FamilyEntryActivity extends AppCompatActivity {
    private static final String LOG_TAG = FamilyEntryActivity.class.getSimpleName();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_entry);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getCurrentUser();
                CollectionReference users = mFirestore.collection("users");
                switch (view.getId()) {
                    case R.id.button_proceed_create_family:
                        users.add(new User(username, true)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                String userDocID = documentReference.getId();
                                Intent intent1 = new Intent(FamilyEntryActivity.this, CreateFamilyActivity.class);
                                intent1.putExtra("userDocID", userDocID);
                                startActivity(intent1);
                            }
                        });
                        break;

                    case R.id.button_proceed_join_family:
                        users.add(new User(username, false)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                String userDocID = documentReference.getId();
                                Intent intent2 = new Intent(FamilyEntryActivity.this, JoinFamilyActivity.class);
                                intent2.putExtra("userDocID", userDocID);
                                startActivity(intent2);
                            }
                        });
                        break;
                }
            }
        };

        Button createFamilyButton = findViewById(R.id.button_proceed_create_family);
        createFamilyButton.setOnClickListener(listener);

        Button joinFamilyButton = findViewById(R.id.button_proceed_join_family);
        joinFamilyButton.setOnClickListener(listener);
    }
}
