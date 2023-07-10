package com.example.fashion_express;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemDetailActivity extends AppCompatActivity {
    //inisial komponen
    String imageURL, storeName, productName, price;
    int  total_price,  stokRequest, stok, productId;
    ImageView imageDetails, plus, minus;
    TextView detail_product, detail_store, detail_price, detail_totalPrice, detail_requestStok;
    Button btn_buy;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        // Initialize layout detailitem
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        imageDetails = findViewById(R.id.detailImage);
        detail_product = findViewById(R.id.txt_productName);
        detail_store = findViewById(R.id.txt_store);
        detail_price =findViewById(R.id.txt_productPrice);
        detail_totalPrice = findViewById(R.id.txt_priceTotal);
        detail_requestStok = findViewById(R.id.txt_stok);
        plus = findViewById(R.id.img_plus);
        minus = findViewById(R.id.img_minus);
        btn_buy = findViewById(R.id.btn_buy);

        //mengambil data dari dashboard yang dipilih
        Intent i = getIntent();
        productName = i.getStringExtra("productName");
        storeName = i.getStringExtra("storeName");
        imageURL = i.getStringExtra("imageURL");
        price = i.getStringExtra("price");
        stok = Integer.parseInt(i.getStringExtra("stok"));
        productId = Integer.parseInt(i.getStringExtra("productId"));

        //mengubah text dan data pada layout
        detail_product.setText(productName);
        detail_store.setText(storeName);
        detail_price.setText("Rp "+price);
        detail_totalPrice.setText("Rp "+price);
        Picasso.get().load(imageURL).into(imageDetails);

        int priceInteger = Integer.parseInt(price);
        total_price = priceInteger;
        stokRequest = 1;
        detail_requestStok.setText(String.valueOf(stokRequest));

        // mengambil data login
        String username = sharedPreferences.getString("username", "");

        // Rmengambil userID
        DatabaseHandler dbHandler = new DatabaseHandler(this);
        int userId = dbHandler.getUserIdByUsername(username);

        //metode penambahan stok
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stokRequest ++;
                stok--;
                detail_requestStok.setText(String.valueOf(stokRequest));
                total_price = priceInteger*stokRequest;
                detail_totalPrice.setText(String.valueOf("Rp "+total_price));
            }
        });
        //metode pengurangan stok
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stokRequest > 1) {
                    stokRequest--;
                    stok++;
                    detail_requestStok.setText(String.valueOf(stokRequest));
                    total_price = priceInteger * stokRequest;
                    detail_totalPrice.setText(String.valueOf("Rp " + total_price));
                }
            }
        });
        //metode membeli stok
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                // Assuming you have retrieved the stored username from SharedPreferences
                String username = sharedPreferences.getString("username", "");

                /// update stok barang sesuai yang dibeli dengan rumus (stok - request stok user)
                boolean isStockUpdated = dbHandler.updateProductStock(productId, stok - stokRequest);

                //metode jiika stok terupdate
                if (isStockUpdated) {
                    // Insert the history data with the retrieved userId
                    long historyId = dbHandler.insertHistory(userId, productId, currentDate, stokRequest, total_price);

                    if (historyId != -1) {
                        Toast.makeText(ItemDetailActivity.this, "History record inserted successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Go back to the previous fragment
                    } else {
                        Toast.makeText(ItemDetailActivity.this, "Failed to insert history record", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ItemDetailActivity.this, "Failed to update product stock", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}