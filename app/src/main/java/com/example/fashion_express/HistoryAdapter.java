package com.example.fashion_express;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    private ArrayList<History> historyList;
    String storeName, productName;
    private Context context;

    public HistoryAdapter(ArrayList<History> historyList, Context context) {
        this.historyList = historyList;
        this.context = context;
    }
    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        History model = historyList.get(position);

        // Mengambil atribut produk
        int productId = model.getProductId();
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        ArrayList<Product> productList = dbHandler.getAllProducts();
        String productName = "";
        String storeName = "";

        // mencari produk berdasarkan produk id
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                productName = product.getProductName();
                storeName = product.getStoreName();
                break;
            }
        }

        //mengolah data tampilan
        holder.txt_storeName.setText(storeName);
        holder.txt_productName.setText(productName);
        holder.date.setText(model.getTransactionDate());
        holder.totalPrice.setText(String.valueOf(model.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
    public static class HistoryHolder extends RecyclerView.ViewHolder {
        //inisialisasi komponen recycle history
        TextView txt_storeName, txt_productName, date, totalPrice;
        ImageView borders;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            borders = itemView.findViewById(R.id.border);
            txt_storeName = itemView.findViewById(R.id.hist_store);
            txt_productName = itemView.findViewById(R.id.hist_product);
            date = itemView.findViewById(R.id.hist_date);
            totalPrice = itemView.findViewById(R.id.hist_price);
        }
    }
    public void setHistoryList(ArrayList<History> historyList) {
        this.historyList = historyList;
    }


}

