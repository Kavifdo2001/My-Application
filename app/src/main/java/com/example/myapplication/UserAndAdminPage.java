package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserAndAdminPage extends AppCompatActivity {

    private Button buttonOne,buttonTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_and_admin_page);

        buttonOne=(Button) findViewById(R.id.userPage);
        buttonTwo=(Button) findViewById(R.id.adminPage);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAndAdminPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAndAdminPage.this,AdminLoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}