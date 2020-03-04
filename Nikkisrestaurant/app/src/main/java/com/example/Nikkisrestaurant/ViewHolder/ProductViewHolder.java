package com.example.Nikkisrestaurant.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Nikkisrestaurant.Interface.ItemClickListner;
import com.example.Nikkisrestaurant.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textProductName, textProductDescription, textProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.product_image);
        textProductName = itemView.findViewById(R.id.product_name);
        textProductDescription = itemView.findViewById(R.id.product_description);
        textProductPrice = itemView.findViewById(R.id.product_price);


    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);

    }
}
