package com.example.kanakku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kanakku.model.Family;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreateFamilyActivity extends AppCompatActivity {
    EditText familyNameEditText, setSecretCodeEditText, confirmSecretCodeEditText;
    String familyName, setSecretCode, confirmSecretCode;

    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family);

        Intent intent = getIntent();
        final String userDocID = intent.getStringExtra("userDocID");

        familyNameEditText = findViewById(R.id.edit_text_create_family_name);
        setSecretCodeEditText = findViewById(R.id.set_secret_code);
        confirmSecretCodeEditText = findViewById(R.id.confirm_secret_code);

        Button done = findViewById(R.id.button_proceed_create_family);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                familyName = familyNameEditText.getText().toString();
                setSecretCode = setSecretCodeEditText.getText().toString();
                confirmSecretCode = confirmSecretCodeEditText.getText().toString();
                if (validateForm()) {
                    ArrayList<String> membersDocID = new ArrayList<>();
                    membersDocID.add(userDocID);

                    CollectionReference families = mFirestore.collection("families");
                    families.add(new Family(familyName, Integer.parseInt(setSecretCode), membersDocID)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String familyDocID = documentReference.getId();
                            Intent intent = new Intent(CreateFamilyActivity.this, MainActivity.class);
                            intent.putExtra("familyDocID", familyDocID);
                            intent.putExtra("userDocID", userDocID);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    private boolean validateForm() {
        if (familyName.isEmpty()) {
            familyNameEditText.setError("Family name should not be empty");
            return false;
        }
        if (setSecretCode.isEmpty()) {
            setSecretCodeEditText.setError("Secret code should not be empty");
            return false;
        }
        if (confirmSecretCode.isEmpty()) {
            confirmSecretCodeEditText.setError("Secret code should not be empty");
            return false;
        }
        if (!setSecretCode.equals(confirmSecretCode)) {
            confirmSecretCodeEditText.setError("Secret code not matching");
            return false;
        }
        return true;
    }
}
