package com.example.kanakku;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kanakku.adapter.MainAdapter;
import com.example.kanakku.model.Family;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements
        MainAdapter.OnMemberSelectedListener {

    String familyDocID, userDocID;
    List<String> membersDocID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        familyDocID = intent.getStringExtra("familyDocID");
        userDocID = intent.getStringExtra("userDocID");

        FirebaseFirestore.setLoggingEnabled(true);
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        DocumentReference reference = mFirestore.collection("families").document(familyDocID);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                Family family = snapshot.toObject(Family.class);
                membersDocID = family.getMembersDocID();
            }
        });

        RecyclerView mMembersRecycler = findViewById(R.id.family_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMembersRecycler.setLayoutManager(layoutManager);
        MainAdapter mAdapter = new MainAdapter(membersDocID, this);
        mMembersRecycler.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu_item:
                AuthUI.getInstance().signOut(this);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMemberSelected(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, LogViewActivity.class);
        String selectedUserDocID = membersDocID.get(clickedItemIndex);
        intent.putExtra("selectedUserDocID", selectedUserDocID);
        intent.putExtra("userDocID", userDocID);
        startActivity(intent);
    }
}