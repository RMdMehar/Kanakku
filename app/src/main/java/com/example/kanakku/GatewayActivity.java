package com.example.kanakku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GatewayActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.gateway_log_in:
                        startActivity(new Intent(GatewayActivity.this, LoginActivity.class));
                        break;

                    case R.id.gateway_sign_up:
                        startActivity(new Intent(GatewayActivity.this, SignUpActivity.class));
                        break;
                }
            }
        };

        Button login = findViewById(R.id.gateway_log_in);
        login.setOnClickListener(listener);

        Button signUp = findViewById(R.id.gateway_sign_up);
        signUp.setOnClickListener(listener);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(GatewayActivity.this, MainActivity.class));
        }
    }
}
