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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginPage extends AppCompatActivity {

    private Button button;
    private ImageView adminBtn,backBtn;
    TextView buttonTwo,forgetPassword;
    private EditText email,password;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.loginEmail);
        password=findViewById(R.id.loginPassword);

        adminBtn=(ImageView) findViewById(R.id.adminPageBtn);
        backBtn=(ImageView) findViewById(R.id.backPage);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this,AdminLoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        buttonTwo=(TextView) findViewById(R.id.textFive);
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this,RegisterPage.class);
                startActivity(intent);
                finish();
            }
        });

        forgetPassword=(TextView) findViewById(R.id.forgetPass);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });

        button=(Button) findViewById(R.id.signInButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLog,passwordLog;
                emailLog = String.valueOf(email.getText());
                passwordLog = String.valueOf(password.getText());

                if (emailLog.isEmpty() || passwordLog.isEmpty()){
                    Toast.makeText(LoginPage.this, "Enter a Email", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    auth.signInWithEmailAndPassword(emailLog, passwordLog).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginPage.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HelloPage.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginPage.this, "Wrong Email or Password" +" " +e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}