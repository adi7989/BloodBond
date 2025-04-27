package com.example.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonateBloodActivity extends AppCompatActivity {

    private EditText editTextBloodType, editTextLocation, editTextUrgency,diseases,name;
    private Button buttonSubmitDonation;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);
        auth = FirebaseAuth.getInstance();
        editTextBloodType = findViewById(R.id.editTextBloodType);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextUrgency = findViewById(R.id.editTextUrgency);
        buttonSubmitDonation = findViewById(R.id.buttonSubmitDonation);
        diseases=findViewById(R.id.disease);
        name=findViewById(R.id.donarname);
        buttonSubmitDonation.setOnClickListener(v -> submitDonationRequest());
    }
    private void submitDonationRequest() {
        String bloodType = editTextBloodType.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String Why = editTextUrgency.getText().toString().trim();
        String anydisease=diseases.getText().toString().trim();
        String namee=name.getText().toString().trim();

        if (bloodType.isEmpty() || location.isEmpty() || Why.isEmpty()) {
            Toast.makeText(DonateBloodActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUser user = auth.getCurrentUser();
            if (user == null) {
                Toast.makeText(DonateBloodActivity.this, "Please log in first", Toast.LENGTH_SHORT).show();
                return;
            }

            String uid = user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://blooddonor-7de7e-default-rtdb.firebaseio.com/");
            DatabaseReference ref = database.getReference("donations").child(uid);

            String donationId = ref.push().getKey();
            DonationRequest donationRequest = new DonationRequest(bloodType, location, Why,anydisease,namee);

            if (donationId != null) {
                ref.child(donationId).setValue(donationRequest)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {

                                Toast.makeText(DonateBloodActivity.this, "Donation request submitted successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DonateBloodActivity.this, MainActivity.class));
                                finish();
                            } else {

                                Toast.makeText(DonateBloodActivity.this, "Failed to submit donation request. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    public static class DonationRequest {
        public String bloodType;
        public String location;
        public String Why;
        public String namee;
        public String anydisease;

        public DonationRequest() {
            // Default constructor required for Firebase
        }

        public DonationRequest(String bloodType, String location, String urgency,String anydisease,String namee) {
            this.bloodType = bloodType;
            this.location = location;
            this.Why = urgency;
            this.namee=namee;
            this.anydisease=anydisease;
        }
    }
}