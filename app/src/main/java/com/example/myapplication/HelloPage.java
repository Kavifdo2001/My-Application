package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HelloPage extends AppCompatActivity {
    TextView welcomeTextView, nameTextView,emailTextView;
    ProgressBar progressBar;
    String name, email;
    ImageView imageView;
    Button signOutBtn , homeBtn;
    FirebaseAuth authProfile;
    ImageView cartIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_page);

        welcomeTextView = findViewById(R.id.welcome_txt);
        nameTextView = findViewById(R.id.textview_show_name);
        emailTextView = findViewById(R.id.textview_show_email);
        imageView = findViewById(R.id.profile_dp);
        progressBar = findViewById(R.id.progress_bar);
        signOutBtn = findViewById(R.id.signout_btn);
        homeBtn = findViewById(R.id.home_btn);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser == null){
            Toast.makeText(HelloPage.this,
                    "Something went wrong, User details are not available at the moment", Toast.LENGTH_SHORT).show();
        }else {
            showUserProfile(firebaseUser);
        }

        signOutBtn.setOnClickListener(v -> signoutMthod());
        homeBtn.setOnClickListener(v -> homeMethod());


        cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCartActivity();
            }
        });


    }


    private void openCartActivity() {
        Intent intent = new Intent(HelloPage.this, LoginPage.class);
        startActivity(intent);
    }

    private void homeMethod(){
        Intent intent = new Intent(HelloPage.this , MainActivity.class);
        startActivity(intent);
    }
    private void signoutMthod() {
        authProfile.getInstance();
        authProfile.getCurrentUser();
        authProfile.signOut();

        Toast.makeText(HelloPage.this, "Logout Success", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(HelloPage.this, LoginPage.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        progressBar.setVisibility(View.VISIBLE);
        String userID = firebaseUser.getUid();

        //Extracting user data from db
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.GONE);

                ReadwriteUserDetails readUserDetails = snapshot.getValue(ReadwriteUserDetails.class);
                if (readUserDetails != null) {
                    name = readUserDetails.name;
                    email = firebaseUser.getEmail();

                    welcomeTextView.setText("Hello " + name + "!");
                    nameTextView.setText(name);
                    emailTextView.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HelloPage.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }
}