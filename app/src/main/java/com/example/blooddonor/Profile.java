package com.example.blooddonor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    private TextView textView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView = findViewById(R.id.textViewEmail);
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();


            textView.setText("Email: " + email);
        }
    }
    public void ba(View view){
        startActivity(new Intent(Profile.this, MainActivity.class));
        finish();
    }

    public void lo(View view){
        auth.signOut();
        startActivity(new Intent(Profile.this, LoginActivity.class));
        finish();
    }
}