package com.example.blooddonor;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    EditText name,email,password,confirmpassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        // getSupportActionBar().hide();

        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(SignupActivity.this, OnBoardingActivity.class));
            finish();
        }
        name=findViewById(R.id.fullNameEditText);
        email=findViewById(R.id.emailEditText);
        password=findViewById(R.id.passwordEditText);
        confirmpassword=findViewById(R.id.confirmPasswordEditText);
    }
    public void signup(View view){
        String username=name.getText().toString();
        String useremail=email.getText().toString();
        String userpassword=password.getText().toString();
        String usercpassword=confirmpassword.getText().toString();
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Enter Name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(useremail)){
            Toast.makeText(this,"Enter Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userpassword)){
            Toast.makeText(this,"Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(usercpassword)){
            Toast.makeText(this,"Enter confirm password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userpassword.length()<6){
            Toast.makeText(this,"Password is too short enter password of minimum 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!usercpassword.equals(userpassword)){
            Toast.makeText(this,"Enter Same Password As Entered Above!", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(useremail,userpassword)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this,"Successfully Registered",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, OnBoardingActivity.class));
                        }else{
                            Toast.makeText(SignupActivity.this,"Registration Failed"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //startActivity(new Intent(SignupActivity.this, MainActivity.class));
    }
    public void signn(View view){
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
    }
}
