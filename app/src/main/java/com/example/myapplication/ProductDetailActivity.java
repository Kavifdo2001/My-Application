package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView productNameTextView, productPriceTextView, productDescriptionTextView;
    private Button orderButton, addToCartButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        auth = FirebaseAuth.getInstance();

        // Initialize views
        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        orderButton = findViewById(R.id.orderButton);
        addToCartButton = findViewById(R.id.addToCartButton);

        // Retrieve product details from intent
        Intent intent = getIntent();
        String productName = intent.getStringExtra("productName");
        double productPrice = intent.getDoubleExtra("productPrice", 0.0);
        String productDescription = intent.getStringExtra("productDescription");

        // Display product details
        productNameTextView.setText(productName);
        productPriceTextView.setText(String.valueOf(productPrice));
        productDescriptionTextView.setText(productDescription);

        // Set up order button click listener
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement order functionality here
                Toast.makeText(ProductDetailActivity.this, "Order placed for " + productName, Toast.LENGTH_SHORT).show();
            }
        });

        // Set up "Add to Cart" button click listener
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        // Check if the user is logged in
        if (auth.getCurrentUser() != null) {
            // Retrieve product details from intent
            Intent intent = getIntent();
            String productId = intent.getStringExtra("productId"); // Assume you have a unique product ID
            String productName = intent.getStringExtra("productName");
            double productPrice = intent.getDoubleExtra("productPrice", 0.0);
            String productDescription = intent.getStringExtra("productDescription");

            // Add the product to the user's cart in Firebase
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("carts")
                    .child(auth.getCurrentUser().getUid())
                    .child(productId);

            CartItem cartItem = new CartItem(productName, productPrice, productDescription, 1); // Start with quantity 1
            cartRef.setValue(cartItem);

            Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show();
        } else {
            // User not logged in, show a message or redirect to login
            Toast.makeText(this, "Please log in to add to the cart", Toast.LENGTH_SHORT).show();
            // You can redirect to the login activity or show a login dialog here
        }
    }
}
