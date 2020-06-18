package com.example.kanakku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.kanakku.adapter.LogViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class LogViewActivity extends AppCompatActivity {
    private static final String LOG_TAG = LogViewActivity.class.getSimpleName();

    FirebaseFirestore mFirestore;
    private Query mQuery;

    private LogViewAdapter mAdapter;
    private RecyclerView mLogRecyclerView;
    private ViewGroup mEmptyView;

    private String selectedUserDocID, userDocID, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);

        Intent intent = getIntent();
        userDocID = intent.getStringExtra("userDocID");
        if (intent.hasExtra("selectedUserDocID")) {
            selectedUserDocID = intent.getStringExtra("selectedUserDocID");
            id = selectedUserDocID;
        } else {
            id = userDocID;
        }


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        if (selectedUserDocID.equals(userDocID)) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LogViewActivity.this, RecordActivity.class);
                    intent.putExtra("userDocID", userDocID);
                    startActivity(intent);
                }
            });
        } else {
            fab.setVisibility(View.GONE);
        }

        mLogRecyclerView = findViewById(R.id.log_view_recycler_view);
        mEmptyView = findViewById(R.id.empty_view);

        FirebaseFirestore.setLoggingEnabled(true);

        initFirestore();
        initRecyclerView();
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();

        mQuery = mFirestore.collection("users")
                .document(id)
                .collection("records");
    }

    private void initRecyclerView() {
        if (mQuery == null) {
            Log.w(LOG_TAG, "No query, not initializing RecyclerView");
        }

        mAdapter = new LogViewAdapter(mQuery) {
            @Override
            protected void onDataChanged() {
                if (getItemCount() == 0) {
                    mLogRecyclerView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mLogRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {

            }
        };

        mLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLogRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }
}