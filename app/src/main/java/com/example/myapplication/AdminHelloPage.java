package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHelloPage extends AppCompatActivity {

    private Button btn,createBtn,lgOutBtn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hello_page);

        btn=(Button) findViewById(R.id.productBtn);
        createBtn=(Button) findViewById(R.id.createProduct);
        lgOutBtn=(Button) findViewById(R.id.logOutBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        lgOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(AdminHelloPage.this,AdminLoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminInsertproducts.class);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),adminRecycleView.class);
                startActivity(intent);
            }
        });
    }
}