package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private Button button;
    private ImageView backBtn;
    FirebaseAuth auth;
    EditText emailReset;
    String stringEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        auth=FirebaseAuth.getInstance();

        button=(Button) findViewById(R.id.buttonReset);
        emailReset=(EditText) findViewById(R.id.resetEmailAddress);
        backBtn=(ImageView) findViewById(R.id.backPageBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringEmail = emailReset.getText().toString();
                if (!TextUtils.isEmpty(stringEmail)){
                    resetPassword();
                }else {
                    emailReset.setError("Email field cannot be empty");
                }
            }
        });
    }

    private void resetPassword() {
        auth.sendPasswordResetEmail(stringEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgetPassword.this, "Password reset code is sent to your email", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPassword.this, "Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}