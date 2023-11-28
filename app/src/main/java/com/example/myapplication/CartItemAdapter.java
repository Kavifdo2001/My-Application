package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CartItemAdapter extends ArrayAdapter<CartItem> {

    private Context context;
    private int resource;

    public CartItemAdapter(@NonNull Context context, int resource, @NonNull List<CartItem> cartItemList) {
        super(context, resource, cartItemList);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        // Get the current CartItem
        CartItem cartItem = getItem(position);

        if (cartItem != null) {
            // Set the data to the views in the list item layout
            TextView nameTextView = view.findViewById(R.id.cartItemNameTextView);
            TextView priceTextView = view.findViewById(R.id.cartItemPriceTextView);
            TextView descriptionTextView = view.findViewById(R.id.cartItemDescriptionTextView);

            nameTextView.setText(cartItem.getName());
            priceTextView.setText("Price: $" + cartItem.getPrice());
            descriptionTextView.setText(cartItem.getDescription());
        }

        return view;
    }
}
