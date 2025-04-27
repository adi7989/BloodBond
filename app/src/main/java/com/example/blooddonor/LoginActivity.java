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

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.emailEditText);
        password=findViewById(R.id.passwordEditText);
    }
    public void signIn(View view){
        String useremail=email.getText().toString();
        String userpassword=password.getText().toString();
        if(TextUtils.isEmpty(useremail)){
            Toast.makeText(this,"Enter Email!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userpassword)){
            Toast.makeText(this,"Enter Password!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(userpassword.length()<6){
            Toast.makeText(this,"Password too short, enter password of minimum 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(useremail,userpassword)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,OnBoardingActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this,"Error:"+task.getException(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void signup(View view){
        startActivity(new Intent(LoginActivity.this,SignupActivity.class));
    }
}