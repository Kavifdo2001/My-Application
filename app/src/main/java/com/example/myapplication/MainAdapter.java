package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewFolder> {

    Context context;
    ArrayList<MainModel> list;

    public MainAdapter(Context context, ArrayList<MainModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewFolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_items,parent ,false);
        return new MyViewFolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewFolder holder, int position) {
        MainModel data = list.get(position);
        holder.fName.setText(data.getFlowerName());
        holder.fDescription.setText(data.getFlowerDescription());
        holder.fPrice.setText(data.getFlowerPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewFolder extends RecyclerView.ViewHolder{
        TextView fName, fDescription,fPrice;

        public MyViewFolder(@NonNull View itemView) {
            super(itemView);

            fName=itemView.findViewById(R.id.name);
            fDescription=itemView.findViewById(R.id.description);
            fPrice=itemView.findViewById(R.id.price);

        }
    }
}
