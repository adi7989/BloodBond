package com.example.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blooddonor.LoginActivity;
import com.example.blooddonor.R;
import com.example.blooddonor.RequestBloodActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button buttonRequestBlood, buttonDonateBlood, buttonProfile, buttonLogout,butdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRequestBlood = findViewById(R.id.buttonRequestBlood);
        buttonDonateBlood = findViewById(R.id.buttonDonateBlood);
        buttonProfile = findViewById(R.id.buttonProfile);
        buttonLogout = findViewById(R.id.buttonLogout);
        butdata=findViewById(R.id.buttonData);

        // Set OnClickListeners for the buttons
        buttonRequestBlood.setOnClickListener(v -> openRequestBloodActivity());
        buttonDonateBlood.setOnClickListener(v -> openDonateBloodActivity());
        buttonProfile.setOnClickListener(v -> openProfileActivity());
        buttonLogout.setOnClickListener(v -> logoutUser());
    }

    // Method to open the Request Blood activity
    private void openRequestBloodActivity() {
        Intent intent = new Intent(MainActivity.this, RequestBloodActivity.class);
        startActivity(intent);
    }

    // Method to open the Donate Blood activity
    private void openDonateBloodActivity() {
        Intent intent = new Intent(MainActivity.this, DonateBloodActivity.class);
        startActivity(intent);
    }

    private void openProfileActivity() {
        Intent intent = new Intent(MainActivity.this, Profile.class);
        startActivity(intent);
    }

    private void logoutUser() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
}
public void butondata(View view){
        startActivity(new Intent(MainActivity.this,BloodRequestListActivity.class));
}
}