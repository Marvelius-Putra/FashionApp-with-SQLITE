package com.example.fashion_express;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {
    TextView welcome_user;
    RadioButton radioBtnAll, radioBtnShirt, radioBtnJean, radioBtnHat;
    private ArrayList<Product> productList = new ArrayList<>();
    Context context;
    private ProductAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHome() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Menampilkan Halaman Dashboard
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //inisialisasi layout
        welcome_user = rootView.findViewById(R.id.txt_username);
        radioBtnAll = rootView.findViewById(R.id.radio_all);
        radioBtnHat = rootView.findViewById(R.id.radio_hat);
        radioBtnJean = rootView.findViewById(R.id.radio_jeans);
        radioBtnShirt = rootView.findViewById(R.id.radio_shirt);
        RecyclerView recyclerView = rootView.findViewById(R.id.productRecycle);
        this.context = getActivity();
        DatabaseHandler dbHandler = new DatabaseHandler(context);

        //mengambil data user
        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        welcome_user.setText(name);

        //mengambil data dummy
        int productListSize = productList.size();
        if(productListSize <=0){
            dbHandler.insertDummyProducts();
        }
        //menampilkan seluruh data barang berdasarkan kategori
        productList = dbHandler.getAllProducts();

        adapter = new ProductAdapter(productList, context);
        // Get the selected radio button and filter the products accordingly
        radioBtnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList = dbHandler.getAllProducts();
                adapter.setProductList(productList);
                adapter.notifyDataSetChanged();
            }
        });
        radioBtnHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList = dbHandler.getProductsByCategory("Hat");
                adapter.setProductList(productList);
                adapter.notifyDataSetChanged();
            }
        });
        radioBtnJean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList = dbHandler.getProductsByCategory("Jeans");
                adapter.setProductList(productList);
                adapter.notifyDataSetChanged();
            }
        });
        radioBtnShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList = dbHandler.getProductsByCategory("Shirt");
                adapter.setProductList(productList);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));

        for (Product product : productList) {
            Log.d("Product", "ID: " + product.getProductId());
            Log.d("Product", "Name: " + product.getProductName());
            Log.d("Product", "Category: " + product.getCategory());
            Log.d("Product", "Price: " + product.getPrice());
            Log.d("Product", "Store: " + product.getStoreName());
            Log.d("Product", "Stock: " + product.getStok());
            Log.d("Product", "Image URL: " + product.getImgurl());
        }
        return rootView;
    }
}