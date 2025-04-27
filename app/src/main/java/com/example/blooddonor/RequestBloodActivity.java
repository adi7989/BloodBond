package com.example.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonor.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestBloodActivity extends AppCompatActivity {

    private EditText editTextBloodType, editTextLocation, editTextUrgency;
    private Button buttonSubmitRequest;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        editTextBloodType = findViewById(R.id.editTextBloodType);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextUrgency = findViewById(R.id.editTextUrgency);
        buttonSubmitRequest = findViewById(R.id.buttonSubmitRequest);

        // Set up the button's click listener
        buttonSubmitRequest.setOnClickListener(v -> submitBloodRequest());
    }

    // Method to submit the blood request to Firebase
    private void submitBloodRequest() {
        String bloodType = editTextBloodType.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String urgency = editTextUrgency.getText().toString().trim();

        if (bloodType.isEmpty() || location.isEmpty() || urgency.isEmpty()) {
            Toast.makeText(RequestBloodActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Get the current logged-in user
            FirebaseUser user = auth.getCurrentUser();
            if (user == null) {
                Toast.makeText(RequestBloodActivity.this, "Please log in first", Toast.LENGTH_SHORT).show();
                return;
            }

            String uid = user.getUid();

            // Create a reference to Firebase Realtime Database
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://blooddonor-7de7e-default-rtdb.firebaseio.com/");
            DatabaseReference ref = database.getReference("requests").child(uid);  // Using user UID to store requests

            // Create a blood request object
            String requestId = ref.push().getKey();  // Generate a unique ID for the request
            BloodRequest bloodRequest = new BloodRequest(bloodType, location, urgency);

            // Push the request data to Firebase
            if (requestId != null) {
                ref.child(requestId).setValue(bloodRequest)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Notify the user and redirect to the main activity
                                Toast.makeText(RequestBloodActivity.this, "Blood request submitted successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RequestBloodActivity.this, MainActivity.class));
                                finish();
                            } else {
                                // Handle error
                                Toast.makeText(RequestBloodActivity.this, "Failed to submit request. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    // BloodRequest class to structure the blood request data
    public static class BloodRequest {
        public String bloodType;
        public String location;
        public String urgency;

        public BloodRequest() {
            // Default constructor required for Firebase
        }

        public BloodRequest(String bloodType, String location, String urgency) {
            this.bloodType = bloodType;
            this.location = location;
            this.urgency = urgency;
        }
    }
}