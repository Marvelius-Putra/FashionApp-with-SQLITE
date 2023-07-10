package com.example.fashion_express;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    //inisialisasi fragment
    FragmentHome homeFrag = new FragmentHome();
    HistoryFragment historyFragment = new HistoryFragment();
    BottomNavigationView bottomNavigationView;

    private String name, userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.BottomNavigation);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        homeFrag.setArguments(bundle);
        //navigasi icon fitur
        getSupportFragmentManager().beginTransaction().replace(R.id.ContainerID, homeFrag).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    //navigasi dashboard
                    case R.id.home:
                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        homeFrag.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.ContainerID,homeFrag).commit();
                        return true;
                        //navigasi histori
                    case R.id.data:
                        bundle = new Bundle();
                        bundle.putString("name", name);
                        historyFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.ContainerID,historyFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
    public void selectCategory() {
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    public void selectHistory() {
        bottomNavigationView.setSelectedItemId(R.id.data);
    }
}