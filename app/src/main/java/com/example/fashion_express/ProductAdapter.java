package com.example.fashion_express;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder>{
    private ArrayList<Product> productList;
    private Context context;
    String imageURL, storeName, productName, stok,price, productId;

    public ProductAdapter(ArrayList<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }
    @NonNull
    @Override

    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_product, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHolder holder, int position) {
        //mengolah data recycle produk
        Product model = productList.get(position);
        holder.product_name.setText(model.getProductName());
        holder.product_storeName.setText(model.getStoreName());
        holder.product_price.setText("Rp " + model.getPrice());
        // Load the image using Picasso
        Picasso.get().load(model.getImgurl()).into(holder.product_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mengolah data recycle
                imageURL = model.getImgurl();
                productName = model.getProductName();
                price = String.valueOf(model.getPrice());
                stok = String.valueOf(model.getStok());
                storeName = model.getStoreName();
                productId = String.valueOf(model.getProductId());

                //passing data ke halaman item details
                Intent i = new Intent(context, ItemDetailActivity.class);
                i.putExtra("imageURL", imageURL);
                i.putExtra("productName", productName);
                i.putExtra("storeName", storeName);
                i.putExtra("price", price);
                i.putExtra("stok", stok);
                i.putExtra("productId", productId);
                context.startActivity(i);
            }
        });
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    //inisial layout komponen recycle
    public  static class ProductHolder extends RecyclerView.ViewHolder{
        ImageView product_image;
        TextView product_storeName, product_name, product_price;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.img_product);
            product_storeName = itemView.findViewById(R.id.txt_outlet);
            product_name = itemView.findViewById(R.id.txt_product);
            product_price = itemView.findViewById(R.id.txt_price);
        }
    }


}

