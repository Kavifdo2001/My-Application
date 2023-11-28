package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditItemsPage extends AppCompatActivity {

    private Button btnEdit,viewDataBtn;
    EditText fName,fDescription,fPrice;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_items_page);

        btnEdit = (Button) findViewById(R.id.editData);
        viewDataBtn = (Button) findViewById(R.id.viewData);
        fName = (EditText) findViewById(R.id.name);
        fDescription = (EditText) findViewById(R.id.description);
        fPrice = (EditText) findViewById(R.id.price);
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }
}