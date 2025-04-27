package com.example.blooddonor;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

public class BloodRequestListActivity extends AppCompatActivity {

    private LinearLayout requestContainer;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request_list);

        requestContainer = findViewById(R.id.requestContainer);

        databaseReference = FirebaseDatabase.getInstance("https://blooddonor-7de7e-default-rtdb.firebaseio.com/")
                .getReference("bloodRequests");

        loadBloodRequests();
    }

    private void loadBloodRequests() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot requestSnapshot : snapshot.getChildren()) {
                        String bloodGroup = requestSnapshot.child("bloodGroup").getValue(String.class);

                        TextView textView = new TextView(BloodRequestListActivity.this);
                        textView.setText("Requested Blood Group: " + bloodGroup);
                        textView.setTextSize(18);
                        textView.setPadding(20, 20, 20, 20);

                        requestContainer.addView(textView);
                    }
                } else {
                    Toast.makeText(BloodRequestListActivity.this, "No blood requests found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(BloodRequestListActivity.this, "Failed to load data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}