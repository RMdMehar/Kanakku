package com.example.kanakku;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private static final String LOG_TAG = SignUpActivity.class.getSimpleName();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    EditText emailEditText, usernameEditText, passwordEditText, cnfmPasswordEditText;
    String email, username, password, cnfmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEditText = findViewById(R.id.sign_up_email);
        usernameEditText = findViewById(R.id.sign_up_username);
        passwordEditText = findViewById(R.id.sign_up_password);
        cnfmPasswordEditText = findViewById(R.id.sign_up_cnfm_password);


        Button signup_button = findViewById(R.id.button_sign_up);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString();
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                cnfmPassword = cnfmPasswordEditText.getText().toString();
                if(validateForm()) {
                    signUp(email, username, password);
                }
            }
        });
    }

    private void signUp(String email, final String username, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(LOG_TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignUpActivity.this, FamilyEntryActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(LOG_TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
    }

    private boolean validateForm() {
        if (email.isEmpty()) {
            emailEditText.setError("E-Mail should not be empty");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Password should not be empty");
            return false;
        }
        if (cnfmPassword.isEmpty()) {
            cnfmPasswordEditText.setError("Password should not be empty");
            return false;
        }
        if (!password.equals(cnfmPassword)) {
            cnfmPasswordEditText.setError("Password not matching");
            return false;
        }
        return true;
    }
}
