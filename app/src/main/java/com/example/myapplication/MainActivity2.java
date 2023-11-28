package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private ListView productListView;
    private List<Product> productList;
    private ArrayAdapter<Product> productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        productListView = findViewById(R.id.productListView);
        productList = new ArrayList<>();
        productAdapter = new ArrayAdapter<>(this, R.layout.list_item_product, R.id.productNameTextView, productList);
        productListView.setAdapter(productAdapter);

        // Fetch products from Firebase Database
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("products");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

        // Set item click listener for the productListView
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Retrieve the selected product
                Product selectedProduct = productList.get(position);

                // Create an intent to launch the ProductDetailActivity
                Intent intent = new Intent(MainActivity2.this, ProductDetailActivity.class);

                // Pass product details to the intent
                intent.putExtra("productName", selectedProduct.getName());
                intent.putExtra("productPrice", selectedProduct.getPrice());
                intent.putExtra("productDescription", selectedProduct.getDescription());

                // Start the ProductDetailActivity
                startActivity(intent);
            }
        });

    }
}
