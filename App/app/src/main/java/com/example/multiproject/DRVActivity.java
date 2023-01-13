package com.example.multiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DRVActivity extends AppCompatActivity {

    TextView viewStat;
    ImageButton btnBack;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference dbReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drv);

        btnBack = findViewById(R.id.btnback9);
        viewStat = findViewById(R.id.DRV_status);

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(DRVActivity.this, DeviceActivity.class));
        });

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(DRVActivity.this, LoginActivity.class));}
        else {
            dbReference = FirebaseDatabase.getInstance().getReference().child("Users");
            String uid = user.getUid();
            dbReference.child(uid).child("Device").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String DRV_status = snapshot.child("status").getValue().toString();
                    viewStat.setText(DRV_status);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}