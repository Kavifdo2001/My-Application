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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class AdminRegisterPage extends AppCompatActivity {

    FirebaseAuth auth;
    EditText registerEmail,registerPassword;
    private Button button,backBtn;
    private ImageView backPageBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register_page);

        auth=FirebaseAuth.getInstance();

        button=(Button) findViewById(R.id.buttonRegister);
        backBtn=(Button) findViewById(R.id.backBtn);
        registerEmail=(EditText) findViewById(R.id.AregiEmail);
        registerPassword=(EditText) findViewById(R.id.AregiPassword);

        backPageBtn=(ImageView) findViewById(R.id.bPageBtn);
        backPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminRegisterPage.this,AdminLoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=registerEmail.getText().toString();
                password=registerPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(AdminRegisterPage.this, "Email Feild cannot be empty", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(password)){
                    Toast.makeText(AdminRegisterPage.this, "Password Feild cannot be empty",Toast.LENGTH_SHORT).show();
                }else if(password.length() < 6){
                    Toast.makeText(AdminRegisterPage.this, "Passwrod must longer than 6 charcters", Toast.LENGTH_SHORT).show();
                }else {
                    auth.createUserWithEmailAndPassword(email,password) .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(AdminRegisterPage.this,"Resgitration Successful" , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminRegisterPage.this,AdminLoginPage.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminRegisterPage.this, "Error" +e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminLoginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}