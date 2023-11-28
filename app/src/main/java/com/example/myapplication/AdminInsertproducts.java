package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AdminInsertproducts extends AppCompatActivity {

    private Button btnInsert,viewDataBtn;
    EditText fName,fDescription,fPrice;
    TextView sImage;
    private ImageView backBtn,upImageBtn;
    Uri image;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                upImageBtn.setEnabled(true);
                image = result.getData().getData();
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_insertproducts);

        btnInsert = (Button) findViewById(R.id.submitData);
        upImageBtn = (ImageView) findViewById(R.id.upImage);
        viewDataBtn = (Button) findViewById(R.id.viewData);
        fName = (EditText) findViewById(R.id.name);
        fDescription = (EditText) findViewById(R.id.description);
        fPrice = (EditText) findViewById(R.id.price);
        backBtn=(ImageView) findViewById(R.id.bPbtn);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        sImage = (TextView) findViewById(R.id.uplImage);

        FirebaseApp.initializeApp(AdminInsertproducts.this);
        storageReference = FirebaseStorage.getInstance().getReference();


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminInsertproducts.this,AdminHelloPage.class);
                startActivity(intent);
                finish();
            }
        });

        viewDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminRecycleView.class);
                startActivity(intent);
                finish();
            }
        });

        sImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

        upImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadImage(image);
            }
        });
    }

    private void upLoadImage(Uri image) {
        StorageReference reference = storageReference.child("image/" + UUID.randomUUID().toString());
        reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminInsertproducts.this,"Image upload successfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminInsertproducts.this,"Image upload Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void InsertData() {
        String nameOfFlower = fName.getText().toString();
        String descriptionOfFlower = fDescription.getText().toString();
        String priceOfFlower = fPrice.getText().toString();
        String id = databaseReference.push().getKey();

        MainModel data = new MainModel(nameOfFlower,descriptionOfFlower,priceOfFlower);
        databaseReference.child("flowers").child(id).setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        if (nameOfFlower.isEmpty() || descriptionOfFlower.isEmpty() || priceOfFlower.isEmpty()){
                            Toast.makeText(AdminInsertproducts.this,"Feild Cannot be empty",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AdminInsertproducts.this,"Insert Successful",Toast.LENGTH_SHORT);
                            Intent intent = new Intent(getApplicationContext(), adminRecycleView.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminInsertproducts.this,"Error"+e.getMessage(),Toast.LENGTH_SHORT);
                    }
                });
    }
}