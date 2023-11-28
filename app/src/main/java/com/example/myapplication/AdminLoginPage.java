package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginPage extends AppCompatActivity {

    private Button button;
    TextView regiSignIn;
    FirebaseAuth auth;
    private ImageView backBtn;
    EditText adminEmail,adminPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);

        button=(Button) findViewById(R.id.adminSignInbtn);
        backBtn=(ImageView) findViewById(R.id.bBtn);
        regiSignIn=(TextView) findViewById(R.id.adminSignup);
        adminEmail=(EditText) findViewById(R.id.adminRegiEmail);
        adminPassword=(EditText) findViewById(R.id.adminRegiPassword);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLoginPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        auth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adminE,adminP;

                adminE=String.valueOf(adminEmail.getText());
                adminP=String.valueOf(adminPassword.getText());

                if (adminE.isEmpty() || adminP.isEmpty()){
                    Toast.makeText(AdminLoginPage.this, "Enter a Email or Password", Toast.LENGTH_SHORT).show();
                }else {
                    auth.signInWithEmailAndPassword(adminE,adminP).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(AdminLoginPage.this,"Login Successful" ,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminHelloPage.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminLoginPage.this,"Error" +e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        regiSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminRegisterPage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}