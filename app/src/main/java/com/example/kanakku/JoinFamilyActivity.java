package com.example.kanakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kanakku.model.Family;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.Reference;
import java.util.ArrayList;

public class JoinFamilyActivity extends AppCompatActivity {
    EditText familyNameEditText, secretCodeEditText;
    String familyName, secretCode;

    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_family);

        Intent intent = getIntent();
        final String userDocID = intent.getStringExtra("userDocID");

        familyNameEditText = findViewById(R.id.edit_text_join_family_name);


        secretCodeEditText = findViewById(R.id.enter_secret_code);

        Button done = findViewById(R.id.button_proceed_join_family);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                familyName = familyNameEditText.getText().toString();
                secretCode = secretCodeEditText.getText().toString();

                if (validateForm()) {
                    Query mQuery = mFirestore.collection("families").whereEqualTo("familyName", familyName)
                            .whereEqualTo("secretCode", Integer.parseInt(secretCode));
                    mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                String familyDocID;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    familyDocID = document.getId();
                                    DocumentReference family = mFirestore.collection("families").document(familyDocID);
                                    family.update("membersDocID", FieldValue.arrayUnion(userDocID));
                                }
                                Intent intent = new Intent(JoinFamilyActivity.this, LogViewActivity.class);
                                intent.putExtra("userDocID", userDocID);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validateForm() {
        if (familyName.isEmpty()) {
            familyNameEditText.setError("Family Name should not be empty");
            return false;
        }
        if (secretCode.isEmpty()) {
            secretCodeEditText.setError("Secret code should not be empty");
            return false;
        }
        return true;
    }
}
