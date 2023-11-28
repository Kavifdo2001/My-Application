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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterPage extends AppCompatActivity {

    TextView goToBack;
    private ImageView back;
    private Button button;
    private EditText userName,email,password;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        auth = FirebaseAuth.getInstance();
        userName=findViewById(R.id.userName);
        email=findViewById(R.id.registerEmail);
        password=findViewById(R.id.registerPassword);

        back=(ImageView) findViewById(R.id.backBtnPage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        });


        goToBack=(TextView) findViewById(R.id.returnTosignIn);
        goToBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(RegisterPage.this,LoginPage.class);
                startActivity(intent);
            }
        });

        button=(Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser() {
        String name = userName.getText().toString();
        String regiEmail = email.getText().toString();
        String regiPassword = password.getText().toString();

            if (TextUtils.isEmpty(name)) {
              Toast.makeText(this,"UserName cannot be empty ",Toast.LENGTH_SHORT).show();
              return;
            }

            if (TextUtils.isEmpty(regiEmail)) {
              Toast.makeText(this,"Email cannot be empty", Toast.LENGTH_SHORT).show();
              return;
            }
            if (TextUtils.isEmpty(regiPassword)) {
                Toast.makeText(this,"Passwrod cannot be empty" ,Toast.LENGTH_SHORT).show();
                return;
            }

            if (regiPassword.length() < 6) {
                Toast.makeText(this, "Password must be greater than 6", Toast.LENGTH_SHORT).show();
                return;
            }

//          Create User

        auth.createUserWithEmailAndPassword(regiEmail,regiPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //Enter data to realtime db
                    ReadwriteUserDetails writeUserDetails = new ReadwriteUserDetails(name , regiEmail , regiPassword);

                    //Extracting from db reference
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                firebaseUser.sendEmailVerification();//email verification
                                Toast.makeText(RegisterPage.this, "Account Created Successfully"
                                        ,Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegisterPage.this , LoginPage.class);

                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(RegisterPage.this, "Account Created failed"
                                        ,Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(RegisterPage.this , task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        }
}