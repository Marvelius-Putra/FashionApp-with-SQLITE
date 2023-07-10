package com.example.fashion_express;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //inisial komponen
    EditText user_username, user_password;
    Button btn_login;
    String username, password;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler dbHandler = new DatabaseHandler(this);
        dbHandler.addRecordUserAccount();

        // inisialisasi layout login
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        //        Declaration
        user_username = findViewById(R.id.etUsername);
        user_password = findViewById(R.id.etPassword);
        btn_login = findViewById(R.id.btn_login);

        //metode userLogin
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = user_username.getText().toString();
                password = user_password.getText().toString();
                if(dbHandler.authentication(username,password)){
                    // Store the username in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("name", username);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Credential not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}